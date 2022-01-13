class Product:

    def __init__(self) -> None:
        self.attributes = {}

    def add_id(self, id):
        self.attributes['id'] = id
        return self

    def get_id(self):
        return self.attributes['id']

    def add_number(self, number):
        self.attributes['number'] = number
        return self

    def get_number(self):
        return self.attributes['number']
        
    def add_seance(self, seance):
        # 23:12 21.11.2021
        if  'seances' not in self.attributes.keys():
            self.attributes['seances'] = []
        self.attributes['seances'].append(seance)
        return self

    def get_seance(self):
        return self.attributes['seances']

    def add_name(self, name):
        self.attributes['name'] = name
        return self
    
    def get_name(self):
        return self.attributes['name']

    def add_price(self, price):
        self.attributes['price'] = price
        return self

    def get_price(self):
        return self.attributes['price'].strip()

    def add_price_without_tax(self, price):
        self.attributes['price_without_tax'] = price
        return self

    def get_price_without_tax(self):
        return self.attributes['price_without_tax']

    def add_category(self, category):
        self.attributes['category'] = category
        return self
    
    def get_category(self):
        return self.attributes['category']

    # Nazwa;Cena zawiera podatek;Kategorie (x,y,z...)
    def to_csv(self):
        return [self.get_name(), self.get_price(), self.get_category()]
    