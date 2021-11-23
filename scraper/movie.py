# Plain python object for movie (sorry for be too much java-like in python xd)
# Setters for this class support builder pattern, so you can use it like obj.set_name('name').add_info('info')...
class Movie:
    
    scheme = ["actors", "categories", "description", "director", "info", "name", "original_name", "photos", "poster", "screenwriter", "short_desc", "trailer"]
    whitespace_filter = lambda a: a != '\n' and a != '\r' and a != '\t'

    def __init__(self):
        self.attributes = {}
        
    def __str__(self):
        return self.attributes.__str__()

    def set_name(self, name):
        self.attributes['name'] = name.strip()
        return self
    
    def get_name(self):
        return self.attributes['name']

    def set_original_name(self, name):
        self.attributes['original_name'] = name.strip()
        return self

    def get_name(self):
        return self.attributes['name']

    def set_director(self, director_name):
        self.attributes['director'] = director_name.strip()
        return self

    def get_director(self):
        return self.attributes['director']
        

    def set_description(self, description):
        description = filter(Movie.whitespace_filter, description)
        description = "".join(description)
        self.attributes['description'] = description.strip()
        return self

    def get_description(self):
        return self.attributes['description']

    def get_full_description(self):
        
        info = ', '.join(self.attributes['info'] if 'info' in self.attributes.keys() else ['-'])
        directors = self.attributes['director'] if 'director' in self.attributes.keys() else '-'
        screenwriters = self.attributes['screenwriter'] if 'screenwriter' in self.attributes.keys() else '-'
        genre = self.attributes['categories'] if 'categories' in self.attributes.keys() else '-'
        actors = self.attributes['actors'] if 'actors' in self.attributes.keys() else '-'
        desc = self.get_description()
        return f'{info}<br><br>Reżyseria:<br>{directors}<br><br>' +\
        f'Gatunek: {genre}<br><br>'+\
        f'Scenariusz:<br>{screenwriters}<br><br>Obsada:<br>{actors}<br><br>Opis:<br>{desc}'

    def set_short_desc(self, desc):
        desc = filter(Movie.whitespace_filter, desc)
        desc = "".join(desc)
        self.attributes['short_desc'] = desc.strip()
        return self
    
    def get_short_desc(self):
        return self.attributes['short_desc']

    def add_category(self, category_item):
        if 'categories' not in self.attributes.keys():
            self.attributes['categories'] = []
        self.attributes['categories'].append(category_item.strip())
        return self

    def get_categories(self):
        return self.attributes['categories']

    def set_screenwriter(self, screenwriter):
        self.attributes['screenwriter'] = screenwriter.strip()
        return self
    
    def get_screenwriter(self):
        return self.attributes['screenwriter']

    def set_actors(self, actors):
        actors = filter(Movie.whitespace_filter, actors)
        actors = "".join(actors)
        self.attributes['actors'] = actors.strip()
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
        if 'info' not in self.attributes.keys():
            self.attributes['info'] = []

        info = filter(Movie.whitespace_filter, info)
        info = "".join(info)
        self.attributes['info'].append(info.strip())
        return self
    
    def get_info(self):
        return self.attributes['info']

    def get_movie(self):
        return self.attributes

    def to_csv(self):
        scheme = Movie.scheme
        result = []
        for scheme_element in scheme:
            if scheme_element not in self.attributes.keys():
                element = "" 
            else:
                element = self.attributes[scheme_element]
                element = ",".join(element) if isinstance(element, list) else element
            result.append(element)
        return result