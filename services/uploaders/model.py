['http://purl.org/dc/terms/', 'accrualPeriodicity', 'dc']

import json

class ModelEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Model):
            return {"data": obj.data, "URI": obj.URI, "class": obj.__class__.__name__}
            
        # Let the base class default method raise the TypeError
        return json.JSONEncoder.default(self, obj)

def create_insert(object_list):
    prefix_text = ""
    insert = "INSERT DATA {\n"
    for obj in object_list:
        d = obj.create_partial_insert()
        insert += d[0]
        prefix_text += d[1]
        
    insert += "\n}"
    insert = prefix_text  + insert
    return insert 


class Model:
    def to_json(self):
        return json.dumps(self, cls=ModelEncoder)

    @staticmethod
    def from_json(input):
        model = json.loads(input)
        return Model.from_dict(model)

    @staticmethod
    def from_dict(model):
        cls = globals()[model["class"]]
        obj = cls(model["URI"])
        obj.data = model["data"]
        #recreate inner objects
        for prop in obj.data.items():
            val = prop[1]
            if "ref" in val and val["ref"] and val["value"]:
                #We have a valid inner object. Let's go recursive
                inner_obj = Model.from_dict(val["value"])
                setattr(obj, prop[0], inner_obj)
        return obj

    def __getattribute__(self,name):
        data = object.__getattribute__(self, "data")
        if name == "data":
            return data
        if name not in data:
            return object.__getattribute__(self, name)
        return data[name]["value"]

    def __setattr__(self, name, value):
        if name in ["data", "URI"]:
            object.__setattr__(self, name, value)
            return
        data = object.__getattribute__(self, "data")
        if name not in data:
            raise NameError(name)
        data[name]["value"] = value

    def __dir__(self):
        return super().__dir__() + [str(x) for x in self.data.keys()]

    def create_partial_insert(self):
        child_prefix = []
        child_objects = []
        insert = "<{URI}> ".format(URI=self.URI)
        prefix_set = {}
        for prop in self.data.items():
            if "ref" in prop[1] and prop[1]["ref"]:
                if prop[1]["value"]:
                    value = prop[1]["value"].URI
                    (tmp_insert, tmp_prefix) = prop[1]["value"].create_partial_insert()
                    child_prefix.extend(tmp_prefix)
                    child_objects.append(tmp_insert)
                else:
                    value = None
            else:
                value = prop[1]["value"]
            if value is None:
                continue
            line = "{link} \"{value}\" ;".format(link=prop[1]["link"][2] + ":" + prop[1]["link"][1], value=value)
            prefix_list = prop[1]["link"]
            prefix = prop[1]["link"][2]
            prefix_set[prefix] = prefix_list[0]
            insert += line
        prefix = ""
        for p in prefix_set.items():
            prefix += "PREFIX {prefix}: <{base_url}>\n".format(prefix=p[0], base_url=p[1])
        for p in child_prefix:
            prefix += p
        insert = insert[::-1].replace(";", ".", 1)[::-1]
        for insert_obj in child_objects:
            insert += insert_obj
        return (insert, prefix)



class Popularity(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'Popularity'},
    'comment_count': {   'link': [   'http://razvanrotari.me/terms/',
                                     'comment_count',
                                     'rr'],
                         'ref': None,
                         'value': None},
    'down_votes': {   'link': [   'http://razvanrotari.me/terms/',
                                  'down_votes',
                                  'rr'],
                      'ref': None,
                      'value': None},
    'up_votes': {   'link': ['http://razvanrotari.me/terms/', 'up_votes', 'rr'],
                    'ref': None,
                    'value': None},
    'views': {   'link': ['http://razvanrotari.me/terms/', 'views', 'rr'],
                 'ref': None,
                 'value': None}}


class User(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'User'},
    'description': {   'link': [   'http://razvanrotari.me/terms/',
                                   'description',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'external_id': {   'link': [   'http://razvanrotari.me/terms/',
                                   'external_id',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'followers': {   'link': [   'http://razvanrotari.me/terms/',
                                 'followers',
                                 'rr'],
                     'ref': None,
                     'value': None},
    'location': {   'link': ['http://razvanrotari.me/terms/', 'location', 'rr'],
                    'ref': None,
                    'value': None},
    'popularity': {   'link': [   'http://razvanrotari.me/terms/',
                                  'popularity',
                                  'rr'],
                      'ref': 'Popularity',
                      'value': None},
    'profile_image_url': {   'link': [   'http://razvanrotari.me/terms/',
                                         'profile_image_url',
                                         'rr'],
                             'ref': None,
                             'value': None},
    'profile_url': {   'link': [   'http://razvanrotari.me/terms/',
                                   'profile_url',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'real_name': {   'link': [   'http://razvanrotari.me/terms/',
                                 'real_name',
                                 'rr'],
                     'ref': None,
                     'value': None},
    'username': {   'link': ['http://razvanrotari.me/terms/', 'username', 'rr'],
                    'ref': None,
                    'value': None}}


class AudioUrl(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'AudioUrl'},
    'name': {   'link': ['http://razvanrotari.me/terms/', 'name', 'rr'],
                'ref': None,
                'value': None},
    'url': {   'link': ['http://razvanrotari.me/terms/', 'url', 'rr'],
               'ref': None,
               'value': None}}


class AudioItem(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'audio_links': {   'link': [   'http://razvanrotari.me/terms/',
                                   'audio_links',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'categories': {   'link': [   'http://razvanrotari.me/terms/',
                                  'categories',
                                  'rr'],
                      'ref': None,
                      'value': None},
    'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'AudioItem'},
    'creator': {   'link': ['http://razvanrotari.me/terms/', 'creator', 'rr'],
                   'ref': 'User',
                   'value': None},
    'description': {   'link': [   'http://razvanrotari.me/terms/',
                                   'description',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'duration': {   'link': ['http://razvanrotari.me/terms/', 'duration', 'rr'],
                    'ref': None,
                    'value': None},
    'external_category_id': {   'link': [   'http://razvanrotari.me/terms/',
                                            'external_category_id',
                                            'rr'],
                                'ref': None,
                                'value': None},
    'external_id': {   'link': [   'http://razvanrotari.me/terms/',
                                   'external_id',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'provider': {   'link': [   'http://purl.org/dc/terms/',
                                'accrualPeriodicity',
                                'dc'],
                    'ref': None,
                    'value': None},
    'recorded_at': {   'link': [   'http://razvanrotari.me/terms/',
                                   'recorded_at',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'supports_comments': {   'link': [   'http://razvanrotari.me/terms/',
                                         'supports_comments',
                                         'rr'],
                             'ref': None,
                             'value': None},
    'tags': {   'link': ['http://razvanrotari.me/terms/', 'tags', 'rr'],
                'ref': None,
                'value': None},
    'title': {   'link': ['http://razvanrotari.me/terms/', 'title', 'rr'],
                 'ref': None,
                 'value': None},
    'uploaded_at': {   'link': [   'http://razvanrotari.me/terms/',
                                   'uploaded_at',
                                   'rr'],
                       'ref': None,
                       'value': None}}


class NewsItem(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'author': {   'link': ['http://razvanrotari.me/terms/', 'author', 'rr'],
                  'ref': None,
                  'value': None},
    'categories': {   'link': [   'http://razvanrotari.me/terms/',
                                  'categories',
                                  'rr'],
                      'ref': None,
                      'value': None},
    'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'NewsItem'},
    'description': {   'link': [   'http://razvanrotari.me/terms/',
                                   'description',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'image_url': {   'link': [   'http://razvanrotari.me/terms/',
                                 'image_url',
                                 'rr'],
                     'ref': None,
                     'value': None},
    'provider': {   'link': ['http://razvanrotari.me/terms/', 'provider', 'rr'],
                    'ref': None,
                    'value': None},
    'published_at': {   'link': [   'http://razvanrotari.me/terms/',
                                    'published_at',
                                    'rr'],
                        'ref': None,
                        'value': None},
    'title': {   'link': ['http://razvanrotari.me/terms/', 'title', 'rr'],
                 'ref': None,
                 'value': None},
    'url': {   'link': ['http://razvanrotari.me/terms/', 'url', 'rr'],
               'ref': None,
               'value': None}}


class ImageItem(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'animated': {   'link': ['http://razvanrotari.me/terms/', 'animated', 'rr'],
                    'ref': None,
                    'value': None},
    'categories': {   'link': [   'http://razvanrotari.me/terms/',
                                  'categories',
                                  'rr'],
                      'ref': None,
                      'value': None},
    'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'ImageItem'},
    'creator': {   'link': ['http://razvanrotari.me/terms/', 'creator', 'rr'],
                   'ref': 'User',
                   'value': None},
    'datetime': {   'link': ['http://razvanrotari.me/terms/', 'datetime', 'rr'],
                    'ref': None,
                    'value': None},
    'description': {   'link': [   'http://razvanrotari.me/terms/',
                                   'description',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'external_id': {   'link': [   'http://razvanrotari.me/terms/',
                                   'external_id',
                                   'rr'],
                       'ref': None,
                       'value': None},
    'external_topic_id': {   'link': [   'http://razvanrotari.me/terms/',
                                         'external_topic_id',
                                         'rr'],
                             'ref': None,
                             'value': None},
    'gifv': {   'link': ['http://razvanrotari.me/terms/', 'gifv', 'rr'],
                'ref': None,
                'value': None},
    'height': {   'link': ['http://razvanrotari.me/terms/', 'height', 'rr'],
                  'ref': None,
                  'value': None},
    'link': {   'link': ['http://razvanrotari.me/terms/', 'link', 'rr'],
                'ref': None,
                'value': None},
    'mime_type': {   'link': [   'http://razvanrotari.me/terms/',
                                 'mime_type',
                                 'rr'],
                     'ref': None,
                     'value': None},
    'mp4': {   'link': ['http://razvanrotari.me/terms/', 'mp4', 'rr'],
               'ref': None,
               'value': None},
    'mp4_size': {   'link': ['http://razvanrotari.me/terms/', 'mp4_size', 'rr'],
                    'ref': None,
                    'value': None},
    'nsfw': {   'link': ['http://razvanrotari.me/terms/', 'nsfw', 'rr'],
                'ref': None,
                'value': None},
    'popularity': {   'link': [   'http://razvanrotari.me/terms/',
                                  'popularity',
                                  'rr'],
                      'ref': 'Popularity',
                      'value': None},
    'section': {   'link': ['http://razvanrotari.me/terms/', 'section', 'rr'],
                   'ref': None,
                   'value': None},
    'size': {   'link': ['http://razvanrotari.me/terms/', 'size', 'rr'],
                'ref': None,
                'value': None},
    'title': {   'link': ['http://razvanrotari.me/terms/', 'title', 'rr'],
                 'ref': None,
                 'value': None},
    'topic': {   'link': ['http://razvanrotari.me/terms/', 'topic', 'rr'],
                 'ref': None,
                 'value': None},
    'views': {   'link': ['http://razvanrotari.me/terms/', 'views', 'rr'],
                 'ref': None,
                 'value': None},
    'vote': {   'link': ['http://razvanrotari.me/terms/', 'vote', 'rr'],
                'ref': None,
                'value': None},
    'width': {   'link': ['http://razvanrotari.me/terms/', 'width', 'rr'],
                 'ref': None,
                 'value': None}}

