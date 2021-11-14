class Cinema:

    scheme = ['name', 'path']

    def __init__(self) -> None:
        self.attributes = {}
    
    def add_name(self, name):
        self.attributes['name'] = name
        return self
    
    def get_name(self):
        return self.attributes['name']

    def add_path(self, path):
        self.attributes['path'] = path
        return self
    
    def get_path(self):
        return self.attributes['path']

    def __str__(self) -> str:
        return self.attributes['name'] + " " + self.attributes['path']

    def to_csv(self):
        scheme = Cinema.scheme
        result = []
        for scheme_element in scheme:
            if scheme_element not in self.attributes.keys():
                element = ""
            else:
                element = self.attributes[scheme_element]
            result.append(element)
        return result
