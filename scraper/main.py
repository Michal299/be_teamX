#! python3
import requests
from movie import Movie
from bs4 import BeautifulSoup
from product import Product
from seance import Seance
import csv_handler as csv_handler
import csv
from cinema import Cinema


# returns list of tuples (left, right) where:
# left is string with name of cinema created from city name and cinema name in this city
# right is string with path to the main page of given cinema (without base helios url)
def get_cities_from_map():
    map_of_cinemas_url = 'https://www.helios.pl/42,Gdynia/MapaKin/'
    page = requests.get(map_of_cinemas_url)
    soup = BeautifulSoup(page.content, 'html.parser')

    cities_div = soup.find("div", class_='list')
    cities_links = cities_div.find_all("a")
    cinemas_with_links = list(
        map(lambda a: Cinema().add_name(get_cinema_name_from_link(a)).add_path(a['href']).add_super_category('Repertuar'), cities_links))
    return cinemas_with_links


def get_cinema_name_from_link(link):
    return link.find("strong", class_='city').text + " " + link.find("span", class_='name').text

def get_trailer(link):
    URL = "https://helios.pl" + link
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, "html.parser")
    return soup.find("div", class_="carousel-preview").find("iframe")['src']

def get_gallery(link):

    URL = "https://helios.pl" + link
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, "html.parser")
    photos = soup.find("div", class_="carousel-preview").find_all("li", class_="slide")
    photos = list(map(lambda photo: photo.find("img")['src'], photos))
    return photos
    

# get all known seances from cinema pointed by link for the main page of this cinema
def get_seances_from_cinema(link):
    URL = link + 'Repertuar'
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, 'html.parser')

    days_nav = soup.find('ul', class_='days').find_all('li')
    seances_list = []
    for day in days_nav:

        link_for_repertoire_in_day = day.find('a')
        if link_for_repertoire_in_day.has_attr('href'):
            link_for_repertoire_in_day = link_for_repertoire_in_day['href']
            seances_in_day = get_seances_from_day(link_for_repertoire_in_day)
            seances_list = seances_list + seances_in_day

    return seances_list

def get_seances_from_day(link):
    link = "https://helios.pl" + link
    page = requests.get(link)
    soup = BeautifulSoup(page.content, 'html.parser')

    
    date = soup.find('em', class_='view-emphasis').text
    date_arr = date.split(sep=' ')
    day_name = date_arr[0]
    date = date_arr[1]

    seances_list = soup.find('ul', class_='seances-list').find_all('li', class_='seance')

    seances = []
    for seance in seances_list:
        hours = seance.find_all('a', class_='hour-link')
        
        movie_title = seance.find('h2', class_='movie-title').find('a').text
        for hour in hours:
            s = Seance()\
                .set_date(date)\
                .set_day_name(day_name)\
                .set_movie_name(movie_title)\
                .set_time(hour.text)\
                .add_movie_link(seance.find('a', class_='movie-link')['href'])
            
            seances.append(s)
        
    return seances

def prepare_categories():
    scheme = ['Nazwa', 'Aktywny (0 lub 1)', 'Kategoria nadrzedna']

    fake_cinema = Cinema().add_name('Repertuar').add_super_category('Strona główna')
    cinemas = [fake_cinema] + get_cities_from_map()
    csv_handler.save_elements('categories.csv', scheme, cinemas)

def alt_movie(url):
    movie_page = requests.get(url)
    movie_soup = BeautifulSoup(movie_page.content, "html.parser")

    movie = Movie()

    desc = movie_soup.find("div", class_="html-area").text
    movie.set_description(desc)
    
    title = movie_soup.find("h1", class_="title-big").text
    movie.set_name(title)

    poster = movie_soup.find("aside", class_="column-sidebar").find("img")['src']
    movie.set_poster(poster)

    return movie


def scrap_movie(link):

    movie_url = "https://helios.pl" + link
    movie_page = requests.get(movie_url)
    movie_soup = BeautifulSoup(movie_page.content, "html.parser").find("div", class_="movie-page")
    if not movie_soup:
        return alt_movie(movie_url)

    movie_title_div = movie_soup.find("h1", class_="movie-title")

    movie = Movie()\
        .set_name(movie_title_div.text.strip())\
        .set_original_name(movie_soup.find("h2", class_="movie-title-original").text)

    details = movie_soup.find("div", class_="details")
    categories = details.find("ul", class_="categories").find_all("span", class_="item")
    for category in categories:
        movie = movie.add_category(category.text)
    
    infos = details.find_all("ul", class_="info")
    for info in infos:
        movie = movie.add_info(info.text)
    
    movie = movie\
        .set_director(movie_soup.find("div", class_="direction").find("p").text)\
        .set_screenwriter(movie_soup.find("div", class_="scenario").find("p").text)\
        .set_description(movie_soup.find("div", class_="description").find("p").text)\
        .set_poster(movie_soup.find_all("aside")[0].find("img")['src'])
    
    trailer_button = movie_soup.find_all("aside")[0].find("a", class_="btn-round btn-play show-trailers")
    if trailer_button:
        trailer = get_trailer(trailer_button['href'])
        movie = movie.set_trailer(trailer)
    
    gallery_button = movie_soup.find_all("aside")[0].find("a", class_="btn-round btn-gallery show-gallery")
    if gallery_button:
        gallery = get_gallery(gallery_button['href'])
        for photo in gallery: 
            movie = movie.add_photo(photo)

    actors = movie_soup.find("div", class_="actors")
    if actors:
        movie = movie.set_actors(actors.find("p").text)
    
    return movie

def get_price_for_cinema(cinema):
    URL = 'https://www.helios.pl/' + cinema.get_path().split('/')[1] + '/Cennik/'
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, 'html.parser')

    price_row = soup.find('div', class_='set-2d').find_all('div', class_='price-row')[1]
    price = price_row.find_all('div', class_='price-grid')[1].text
    return price

def prepare_products():
    
    # movie_name -> movie
    movies = {}
    # name -> product
    products = {}

    cinemas = get_cities_from_map()

    for cinema in cinemas: 
        price = get_price_for_cinema(cinema)
        print(cinema.get_name())
        seances = get_seances_from_cinema('https://www.helios.pl/' + cinema.get_path().split('/')[1] + '/')    
        
        for seance in seances:
            movie_name = seance.get_movie_name() 
            if movie_name not in movies.keys():
                movies[movie_name] = scrap_movie(seance.get_movie_link())

            movie_for_cinema = movie_name + cinema.get_name()
            if movie_for_cinema not in products.keys():
                products[movie_for_cinema] = Product().add_name(movie_name).add_price(price).add_category(cinema.get_name()).add_id(len(products.keys()) + 1)
            

            products[movie_for_cinema].add_seance(seance.get_time().replace(':', '\'') + ' ' + seance.get_date()).add_number(130)

    with open("products.csv", mode='w', newline='', encoding='utf-8') as elements_file:
        elements_writer = csv.writer(elements_file, delimiter=';', quotechar='"', quoting=csv.QUOTE_MINIMAL)

        elements_writer.writerow(['Indeks produktu', 'Nazwa', 'Cena', 'Kategoria', 'Opis', 'Adresy URL zdjęcia (x,y,z...)'])
        for element in products.values():
            movie = movies[element.get_name()]
            photos = []
    
            if 'poster' in movie.attributes.keys():
                photos = photos + [movie.get_poster()]

            if 'photos' in movie.attributes.keys(): 
                photos = photos + movie.get_photos()
            
            photos = ','.join(photos)
            elements_writer.writerow([element.get_id(), element.get_name(), element.get_price(), element.get_category(), movie.get_full_description(), photos])


    with open("combinations.csv", mode='w', newline='', encoding='utf-8') as elements_file:
    
        elements_writer = csv.writer(elements_file, delimiter=';', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        
        attribute_name = 'Terminy:select:1'
        attribute = 'Attribute (Name:Type:Position)*'
        
        elements_writer.writerow(['Product ID*',attribute, 'Value (Value:Position)*', 'Ilość'])

        for product in products.values():
            i = 0
            for seance in product.get_seance():
                elements_writer.writerow([product.get_id(), attribute_name, seance + f':{i}', 130])
                i = i + 1
           

def main():
    print("----------------- Scraping categories -----------------")
    prepare_categories()

    print("----------------- Scraping products -----------------")
    prepare_products()

main()