#! python3
from types import NoneType
import requests
from movie import Movie
from bs4 import BeautifulSoup
import pprint
from seance import Seance
import csv_handler as csv
from progress.bar import Bar
from cinema import Cinema
import argparse


# returns list of all helion cinemas from select in header
def get_cities_from_select():
    URL = 'https://www.helios.pl/37,Belchatow/StronaGlowna/'
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, "html.parser")
    city_select = soup.find("select", {"name": "selected_id"})
    cityOptions = list(filter(lambda option: int(
        option['value']) >= 0, city_select.find_all("option")))
    return list(map(lambda option: option.text.strip(), cityOptions))


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
        map(lambda a: Cinema().add_name(get_cinema_name_from_link(a)).add_path(a['href']), cities_links))
    return cinemas_with_links


def get_cinema_name_from_link(link):
    return link.find("strong", class_='city').text + " " + link.find("span", class_='name').text

def get_movies_from_cinema(cinema_main_page): 
    URL = cinema_main_page + "BazaFilmow"
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, "html.parser")

    pages_number = int(soup.find_all("li", class_='page')[-1].text)

    bar = Bar('Page', max=pages_number)
    bar.next()

    movies = []
    for page in range(1, pages_number):
        movies = movies + get_movies(URL + "/index/strona/" + str(page))
        bar.next()
    bar.finish()
    
    return movies

def get_movies(link):
    page = requests.get(link)
    soup = BeautifulSoup(page.content, "html.parser")
    movies_list = soup.find_all("article", class_="movie")

    movies = []
    for movie_article in movies_list:
        movie_link = movie_article.find("h3", class_="movie-title").find("a")['href']
        
        movie_url = "https://helios.pl" + movie_link
        movie_page = requests.get(movie_url)
        movie_soup = BeautifulSoup(movie_page.content, "html.parser").find("div", class_="movie-page")

        movie_title_div = movie_soup.find("h1", class_="movie-title")

        movie = Movie()\
            .set_short_desc(movie_article.find("div", class_="movie-content").find("p").text)\
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
        movies.append(movie)

    return movies

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
                .set_time(hour.text)
            
            seances.append(s)
        
    return seances


commands = {
    '-seances <cinema_main_page_link>': 'get all known, available seances for the cinema in given city',
    '-seances': 'get all seances for each cinema',
    '-movies <page_limit>': 'get all movies from cinema database, up to pages specified by parameter (each page contains 10 movies)',
    '-movies': 'get all movies from cinema database',
    '-help': 'list all commands'
}


def scrap_seances(city):
    seances = get_seances_from_cinema('https://www.helios.pl/' + city + '/')
    csv.save_elements(city + '_seances.csv', Seance.scheme, seances)

def scrap_movies(city):
    movies = get_movies_from_cinema('https://www.helios.pl/' + city + '/')
    csv.save_elements(city + '_movies.csv', Seance.scheme, movies)

def scrap_cinemas():
    cinemas = get_cities_from_map()
    csv.save_elements('cinemas.csv', Cinema.scheme, cinemas)

def main():
    parser = argparse.ArgumentParser(description='Lets scrap Helios')
    parser.add_argument('-s', '--seances', type=str, required=False, help='Get all seances from specified cinema (for example --movies \'37, Belchatow\') or from all cinemas with \'all\' option')
    parser.add_argument('-m', '--movies', type=str, required=False, help='Get all movies from specified cinema (for example --movies \'37, Belchatow\') or from all cinemas with \'all\' option or from default cinema with option \'default\'')
    parser.add_argument('-c', '--cinemas', action='store_true')

    args = parser.parse_args()
    
    if args.seances:
        if args.seances == 'all':
            cities = get_cities_from_map()
            for city in cities:
                link = city.get_path().split('/')[1]
                scrap_seances(link)
        else:
            link = args.seances
            scrap_seances(link)
    
    if args.movies:
        if args.movies == 'all':
            cities = get_cities_from_map()
            for city in cities:
                link = city.get_path().split('/')[1]
                scrap_movies(link)
        elif args.movies == 'default':
            link = '37,Belchatow'
            scrap_movies(link)
        else:
            link = args.movies
            scrap_movies(link)
    
    if args.cinemas is True:
        scrap_cinemas()


main()