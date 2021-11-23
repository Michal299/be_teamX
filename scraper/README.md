# Helion scraper
Script for scraping helios cinema. Example result of script is placed in sample_data.

## Requirements

* python3
* requests
* beautifulsoup4

You can install libraries using command below
```
python -m pip install requests, beautifulsoup4
```
## Run
./main.py

## Description
This script can scrap helion page and save data into .csv files.
Scraped data are stored in several files to make it easier to read and to avoid to much redundancy.

## Import order
Files should be imported into shop in given order:
1. categories.csv
2. products.csv
3. combinations.csv