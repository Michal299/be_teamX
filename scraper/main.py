#! python3
import requests
import time
from bs4 import BeautifulSoup

# return list of all helion cinemas from select in header
def get_cities_from_select():
    URL = 'https://www.helios.pl/37,Belchatow/StronaGlowna/'
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, "html.parser")
    city_select = soup.find("select", {"name": "selected_id"})
    cityOptions = list(filter(lambda option: int(option['value']) >= 0, city_select.find_all("option")))
    cities = list(map(lambda option: option.text.strip(), cityOptions))
    
# return list of tuples (left, right) where: 
# left is string with name of cinema created from city name and cinema name in this city
# right is string with path to the main page of given cinema (without base helios url)
def get_cities_from_map():
    map_of_cinemas_url = 'https://www.helios.pl/42,Gdynia/MapaKin/'
    page = requests.get(map_of_cinemas_url)
    soup = BeautifulSoup(page.content, 'html.parser')

    cities_div = soup.find("div", class_='list')
    cities_links = cities_div.find_all("a")
    cinemas_with_links = list(map(lambda a: (get_cinema_name_from_link(a), a['href']), cities_links))
    return cinemas_with_links

def get_cinema_name_from_link(link):
    return link.find("strong", class_='city').text + " " + link.find("span", class_='name').text

print("Hello world")
time.sleep(10)