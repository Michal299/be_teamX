class Seance:

    scheme = ['date', 'day_name', 'movie_name', 'time']

    def __init__(self):
        self.attributes = {}

    def set_movie_name(self, name):
        self.attributes['movie_name'] = name.strip()
        return self

    def get_movie_name(self):
        return self.attributes['movie_name']

    def add_movie_link(self, link):
        self.attributes['link'] = link
        return self

    def get_movie_link(self):
        return self.attributes['link']

    def set_date(self, date):
        self.attributes['date'] = date
        return self

    def get_date(self):
        return self.attributes['date']

    def set_day_name(self, day):
        self.attributes['day_name'] = day
        return self

    def get_day_name(self):
        return self.attributes['day_name']

    def set_time(self, time):
        self.attributes['time'] = time
        return self

    def get_time(self):
        return self.attributes['time']

    def to_csv(self):
        record = []
        for field in Seance.scheme:
            record.append(self.attributes[field])

        return record

    def get_seance(self):
        return self.attributes

    def __str__(self):
        return self.attributes.__str__() + '\n'