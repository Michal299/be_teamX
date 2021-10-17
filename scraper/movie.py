# Plain python object for movie (sorry for be too much java-like in python xd)
# Setters for this class support builder pattern, so you can use it like obj.set_name('name').add_info('info')...
class Movie:
    attributes = {}

    def __init__(self):
        pass

    def __str__(self):
        return self.attributes.__str__()

    def set_name(self, name):
        self.attributes['name'] = name
        return self
    
    def get_name(self):
        return self.attributes['name']

    def set_director(self, director_name):
        self.attributes['director'] = director_name
        return self

    def get_director(self):
        return self.attributes['director']
        

    def set_description(self, description):
        self.attributes['description'] = description
        return self

    def get_description(self):
        return self.attributes['description']

    def add_category(self, category_item):
        if 'category' not in self.attributes.keys():
            self.attributes['categories'] = []
        self.attributes['categories'].append(category_item)
        return self

    def get_categories(self):
        return self.attributes['categories']

    def set_screenwriter(self, screenwriter):
        self.attributes['screenwriter'] = screenwriter
        return self
    
    def get_screenwriter(self):
        return self.attributes['screenwriter']

    def set_actors(self, actors):
        self.attributes['actors'] = actors
        return self

    def get_actors(self):
        return self.attributes['actors']

    def set_trailer(self, trailer_url):
        self.attributes['trailer'] = trailer_url
        return self

    def get_trailer(self):
        return self.attributes['trailer']
    
    def set_poster(self, poster_url):
        self.attributes['poster'] = poster_url
        return self

    def get_poster(self):
        return self.attributes['poster']

    def add_photo(self, photo_url):
        if 'photos' not in self.attributes.keys():
            self.attributes['photos'] = []
        self.attributes['photos'].append(photo_url)
        return self

    def get_photos(self):
        return self.attributes['photos']

    def add_info(self, info):
        if 'infos' not in self.attributes.keys():
            self.attributes['keys'] = []
        self.attributes['keys'].append(info)
        return self
    
    def get_info(self):
        return self.attributes['info']

    def get_movie(self):
        return self.attributes

    def to_csv(self):
        pass

