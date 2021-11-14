from collections import defaultdict
import csv

def save_elements(path, scheme, elements):
    with open(path, mode='w', newline='', encoding='utf-8') as elements_file:
        elements_writer = csv.writer(elements_file, delimiter=';', quotechar='"', quoting=csv.QUOTE_MINIMAL)

        elements_writer.writerow(scheme)
        for element in elements:
            elements_writer.writerow(element.to_csv())