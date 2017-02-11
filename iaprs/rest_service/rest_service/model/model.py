{'type': 'string'}
{'type': 'string'}
{'type': 'string'}
{'type': 'string'}
{'type': 'number', 'format': 'int'}
{'type': 'string', 'description': 'The id used by the provider'}
{'type': 'string', 'format': 'url'}
{'type': 'string', 'format': 'url'}
['http://purl.org/dc/elements/1.1/', 'publisher', 'dc']
{'type': 'string', 'format': 'url', 'description': '[http://purl.org/dc/elements/1.1/,publisher,dc]The base url for the provider'}
{'$ref': '#/definitions/Popularity'}
{'type': 'number', 'format': 'int'}
{'type': 'number', 'format': 'int'}
{'type': 'number', 'format': 'int'}
{'type': 'number', 'format': 'int'}
['http://xmlns.com/foaf/spec/#', 'term_nick', 'foaf']
{'type': 'string', 'description': '[http://xmlns.com/foaf/spec/#,term_nick,foaf] The username'}
{'type': 'string', 'description': 'The sha256 of the password'}
['http://xmlns.com/foaf/spec/#', 'term_mbox', 'foaf']
{'type': 'string', 'description': '[http://xmlns.com/foaf/spec/#,term_mbox,foaf] Email addres'}
['http://purl.org/media#', 'duration', 'media']
{'type': 'array', 'description': '[http://purl.org/media#,duration,media]The categories for this image'}
{'type': 'string', 'format': 'url'}
{'type': 'string', 'description': 'The name of the quality i.e High, Low, Poor'}
{'type': 'integer', 'description': 'An unique identifier'}
['http://purl.org/dc/terms/', 'accrualPeriodicity', 'dc']
{'type': 'string', 'description': '[http://purl.org/dc/terms/,accrualPeriodicity,dc]The base url for the provider'}
{'type': 'string'}
{'type': 'string'}
['http://purl.org/media#', 'duration', 'media']
{'type': 'array', 'description': '[http://purl.org/media#,duration,media]The categories for this image'}
{'type': 'string'}
{'type': 'number', 'format': 'float', 'description': 'In seconds'}
{'type': 'string', 'format': 'date'}
{'type': 'string', 'format': 'date'}
{'type': 'boolean'}
{'type': 'string', 'description': "If the provider does not use id's for categories use category name"}
['http://purl.org/dc/terms/', 'creator', 'dc']
{'$ref': '#/definitions/Author', 'description': '[http://purl.org/dc/terms/,creator,dc] The creator of the content'}
['http://purl.org/dc/elements/1.1/', 'publisher', 'dc']
{'type': 'string', 'format': 'url', 'description': '[http://purl.org/dc/elements/1.1/,publisher,dc]The base url for the provider'}
{'type': 'array', 'items': {'type': 'string'}}
{'type': 'array', 'items': {'$ref': '#/definitions/AudioUrl'}}
{'type': 'integer', 'description': 'An unique identifier'}
['http://purl.org/dc/elements/1.1/', 'publisher', 'dc']
{'type': 'string', 'format': 'url', 'description': '[http://purl.org/dc/elements/1.1/,publisher,dc]The base url for the provider'}
['http://purl.org/dc/terms/', 'creator', 'dc']
{'$ref': '#/definitions/Author', 'description': '[http://purl.org/dc/terms/,creator,dc] The creator of the content'}
['http://purl.org/dc/elements/1.1/', 'title', 'dc']
{'type': 'string', 'description': '[http://purl.org/dc/elements/1.1/,title,dc]'}
['http://purl.org/media#', 'duration', 'media']
{'type': 'array', 'description': '[http://purl.org/media#,duration,media]The categories for this image'}
['http://purl.org/dc/elements/1.1/', 'description', 'dc']
{'type': 'string', 'description': '[http://purl.org/dc/elements/1.1/,description,dc]'}
{'type': 'string'}
{'type': 'string'}
['http://purl.org/dc/elements/1.1/', 'created', 'dc']
{'type': 'string', 'format': 'date', 'description': '[http://purl.org/dc/elements/1.1/,created,dc]'}
{'type': 'integer', 'description': 'An unique identifier'}
{'type': 'string', 'description': 'The ID for the image'}
['http://purl.org/dc/elements/1.1/', 'title', 'dc']
{'type': 'string', 'description': '[http://purl.org/dc/elements/1.1/,title,dc]'}
['http://purl.org/media#', 'duration', 'media']
{'type': 'array', 'description': '[http://purl.org/media#,duration,media]The categories for this image'}
['http://purl.org/dc/elements/1.1/', 'description', 'dc']
{'type': 'string', 'description': '[http://purl.org/dc/elements/1.1/,description,dc]'}
['http://purl.org/dc/terms/', 'creator', 'dc']
{'$ref': '#/definitions/Author', 'description': '[http://purl.org/dc/terms/,creator,dc] The creator of the content'}
['http://purl.org/dc/elements/1.1/', 'created', 'dc']
{'type': 'integer', 'description': '[http://purl.org/dc/elements/1.1/,created,dc] Time inserted into the gallery, epoch time'}
{'type': 'string', 'description': 'Image MIME type.'}
{'type': 'boolean', 'description': 'is the image animated'}
{'type': 'integer', 'description': 'The width of the image in pixels'}
{'type': 'integer', 'description': 'The height of the image in pixels'}
{'type': 'integer', 'description': 'The size of the image in bytes'}
{'type': 'integer', 'description': 'The number of image views'}
{'type': 'string', 'description': 'The direct link to the the image. (Note: if fetching an animated GIF that was over 20MB in original size, a .gif thumbnail will be returned)'}
{'type': 'string', 'description': "The .gifv link. Only available if the image is animated and type is 'image/gif'."}
{'type': 'string', 'description': "The direct link to the .mp4. Only available if the image is animated and type is 'image/gif'."}
{'type': 'integer', 'description': 'The Content-Length of the .mp4. Only available if the image is animated and type is image/gif. Note that a zero value (0) is possible if the video has not yet been generated'}
{'type': 'string', 'description': "The current user's vote on the album. null if not signed in or if the user hasn't voted on it."}
{'type': 'boolean', 'description': 'Indicates if the image has been marked as nsfw or not. Defaults to null if information is not available.'}
{'type': 'string', 'description': 'Topic of the gallery image.'}
{'type': 'integer', 'description': 'Topic ID of the gallery image.'}
{'type': 'string', 'description': 'If the image has been categorized by our backend then this will contain the section the image belongs in. (funny, cats, adviceanimals, wtf, etc)'}
['http://purl.org/dc/elements/1.1/', 'publisher', 'dc']
{'type': 'string', 'format': 'url', 'description': '[http://purl.org/dc/elements/1.1/,publisher,dc]The base url for the provider'}
{'$ref': '#/definitions/Popularity'}
import rest_service.model.base_model as base_model
from rest_service.model.base_model import Model
create_insert = base_model.create_insert


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
                         'type': 'int',
                         'value': None},
    'down_votes': {   'link': [   'http://razvanrotari.me/terms/',
                                  'down_votes',
                                  'rr'],
                      'ref': None,
                      'type': 'int',
                      'value': None},
    'up_votes': {   'link': ['http://razvanrotari.me/terms/', 'up_votes', 'rr'],
                    'ref': None,
                    'type': 'int',
                    'value': None},
    'views': {   'link': ['http://razvanrotari.me/terms/', 'views', 'rr'],
                 'ref': None,
                 'type': 'int',
                 'value': None}}
base_model.Popularity = Popularity


class Author(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'Author'},
    'description': {   'link': [   'http://razvanrotari.me/terms/',
                                   'description',
                                   'rr'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'external_id': {   'link': [   'http://razvanrotari.me/terms/',
                                   'external_id',
                                   'rr'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'followers': {   'link': [   'http://razvanrotari.me/terms/',
                                 'followers',
                                 'rr'],
                     'ref': None,
                     'type': 'int',
                     'value': None},
    'location': {   'link': ['http://razvanrotari.me/terms/', 'location', 'rr'],
                    'ref': None,
                    'type': 'str',
                    'value': None},
    'name': {   'link': ['http://razvanrotari.me/terms/', 'name', 'rr'],
                'ref': None,
                'type': 'str',
                'value': None},
    'popularity': {   'link': [   'http://razvanrotari.me/terms/',
                                  'popularity',
                                  'rr'],
                      'ref': 'Popularity',
                      'type': 'Popularity',
                      'value': None},
    'profile_image_url': {   'link': [   'http://razvanrotari.me/terms/',
                                         'profile_image_url',
                                         'rr'],
                             'ref': None,
                             'type': 'str',
                             'value': None},
    'profile_url': {   'link': [   'http://razvanrotari.me/terms/',
                                   'profile_url',
                                   'rr'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'provenance': {   'link': [   'http://purl.org/dc/elements/1.1/',
                                  'publisher',
                                  'dc'],
                      'ref': None,
                      'type': 'str',
                      'value': None},
    'real_name': {   'link': [   'http://razvanrotari.me/terms/',
                                 'real_name',
                                 'rr'],
                     'ref': None,
                     'type': 'str',
                     'value': None}}
base_model.Author = Author


class User(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'categories': {   'link': ['http://purl.org/media#', 'duration', 'media'],
                      'ref': None,
                      'type': 'str',
                      'value': None},
    'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'User'},
    'email': {   'link': ['http://xmlns.com/foaf/spec/#', 'term_mbox', 'foaf'],
                 'ref': None,
                 'type': 'str',
                 'value': None},
    'name': {   'link': ['http://xmlns.com/foaf/spec/#', 'term_nick', 'foaf'],
                'ref': None,
                'type': 'str',
                'value': None},
    'password': {   'link': ['http://razvanrotari.me/terms/', 'password', 'rr'],
                    'ref': None,
                    'type': 'str',
                    'value': None}}
base_model.User = User


class AudioUrl(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'AudioUrl'},
    'name': {   'link': ['http://razvanrotari.me/terms/', 'name', 'rr'],
                'ref': None,
                'type': 'str',
                'value': None},
    'url': {   'link': ['http://razvanrotari.me/terms/', 'url', 'rr'],
               'ref': None,
               'type': 'str',
               'value': None}}
base_model.AudioUrl = AudioUrl


class AudioItem(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'audio_links': {   'link': [   'http://razvanrotari.me/terms/',
                                   'audio_links',
                                   'rr'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'author': {   'link': ['http://purl.org/dc/terms/', 'creator', 'dc'],
                  'ref': 'Author',
                  'type': 'Author',
                  'value': None},
    'categories': {   'link': ['http://purl.org/media#', 'duration', 'media'],
                      'ref': None,
                      'type': 'str',
                      'value': None},
    'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'AudioItem'},
    'description': {   'link': [   'http://razvanrotari.me/terms/',
                                   'description',
                                   'rr'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'duration': {   'link': ['http://razvanrotari.me/terms/', 'duration', 'rr'],
                    'ref': None,
                    'type': 'float',
                    'value': None},
    'external_category_id': {   'link': [   'http://razvanrotari.me/terms/',
                                            'external_category_id',
                                            'rr'],
                                'ref': None,
                                'type': 'str',
                                'value': None},
    'external_id': {   'link': [   'http://razvanrotari.me/terms/',
                                   'external_id',
                                   'rr'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'id': {   'link': ['http://razvanrotari.me/terms/', 'id', 'rr'],
              'ref': None,
              'type': 'str',
              'value': None},
    'provenance': {   'link': [   'http://purl.org/dc/elements/1.1/',
                                  'publisher',
                                  'dc'],
                      'ref': None,
                      'type': 'str',
                      'value': None},
    'provider': {   'link': [   'http://purl.org/dc/terms/',
                                'accrualPeriodicity',
                                'dc'],
                    'ref': None,
                    'type': 'str',
                    'value': None},
    'recorded_at': {   'link': [   'http://razvanrotari.me/terms/',
                                   'recorded_at',
                                   'rr'],
                       'ref': None,
                       'type': 'datetime',
                       'value': None},
    'supports_comments': {   'link': [   'http://razvanrotari.me/terms/',
                                         'supports_comments',
                                         'rr'],
                             'ref': None,
                             'type': 'bool',
                             'value': None},
    'tags': {   'link': ['http://razvanrotari.me/terms/', 'tags', 'rr'],
                'ref': None,
                'type': 'str',
                'value': None},
    'title': {   'link': ['http://razvanrotari.me/terms/', 'title', 'rr'],
                 'ref': None,
                 'type': 'str',
                 'value': None},
    'uploaded_at': {   'link': [   'http://razvanrotari.me/terms/',
                                   'uploaded_at',
                                   'rr'],
                       'ref': None,
                       'type': 'datetime',
                       'value': None}}
base_model.AudioItem = AudioItem


class NewsItem(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'author': {   'link': ['http://purl.org/dc/terms/', 'creator', 'dc'],
                  'ref': 'Author',
                  'type': 'Author',
                  'value': None},
    'categories': {   'link': ['http://purl.org/media#', 'duration', 'media'],
                      'ref': None,
                      'type': 'str',
                      'value': None},
    'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'NewsItem'},
    'description': {   'link': [   'http://purl.org/dc/elements/1.1/',
                                   'description',
                                   'dc'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'id': {   'link': ['http://razvanrotari.me/terms/', 'id', 'rr'],
              'ref': None,
              'type': 'str',
              'value': None},
    'image_url': {   'link': [   'http://razvanrotari.me/terms/',
                                 'image_url',
                                 'rr'],
                     'ref': None,
                     'type': 'str',
                     'value': None},
    'provenance': {   'link': [   'http://purl.org/dc/elements/1.1/',
                                  'publisher',
                                  'dc'],
                      'ref': None,
                      'type': 'str',
                      'value': None},
    'timestamp': {   'link': [   'http://purl.org/dc/elements/1.1/',
                                 'created',
                                 'dc'],
                     'ref': None,
                     'type': 'datetime',
                     'value': None},
    'title': {   'link': ['http://purl.org/dc/elements/1.1/', 'title', 'dc'],
                 'ref': None,
                 'type': 'str',
                 'value': None},
    'url': {   'link': ['http://razvanrotari.me/terms/', 'url', 'rr'],
               'ref': None,
               'type': 'str',
               'value': None}}
base_model.NewsItem = NewsItem


class ImageItem(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {   'animated': {   'link': ['http://razvanrotari.me/terms/', 'animated', 'rr'],
                    'ref': None,
                    'type': 'bool',
                    'value': None},
    'author': {   'link': ['http://purl.org/dc/terms/', 'creator', 'dc'],
                  'ref': 'Author',
                  'type': 'Author',
                  'value': None},
    'categories': {   'link': ['http://purl.org/media#', 'duration', 'media'],
                      'ref': None,
                      'type': 'str',
                      'value': None},
    'class_name': {   'link': [   'http://razvanrotari.me/terms/',
                                  'className',
                                  'rr'],
                      'value': 'ImageItem'},
    'description': {   'link': [   'http://purl.org/dc/elements/1.1/',
                                   'description',
                                   'dc'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'external_id': {   'link': [   'http://razvanrotari.me/terms/',
                                   'external_id',
                                   'rr'],
                       'ref': None,
                       'type': 'str',
                       'value': None},
    'external_topic_id': {   'link': [   'http://razvanrotari.me/terms/',
                                         'external_topic_id',
                                         'rr'],
                             'ref': None,
                             'type': 'str',
                             'value': None},
    'gifv': {   'link': ['http://razvanrotari.me/terms/', 'gifv', 'rr'],
                'ref': None,
                'type': 'str',
                'value': None},
    'height': {   'link': ['http://razvanrotari.me/terms/', 'height', 'rr'],
                  'ref': None,
                  'type': 'str',
                  'value': None},
    'id': {   'link': ['http://razvanrotari.me/terms/', 'id', 'rr'],
              'ref': None,
              'type': 'str',
              'value': None},
    'link': {   'link': ['http://razvanrotari.me/terms/', 'link', 'rr'],
                'ref': None,
                'type': 'str',
                'value': None},
    'mime_type': {   'link': [   'http://razvanrotari.me/terms/',
                                 'mime_type',
                                 'rr'],
                     'ref': None,
                     'type': 'str',
                     'value': None},
    'mp4': {   'link': ['http://razvanrotari.me/terms/', 'mp4', 'rr'],
               'ref': None,
               'type': 'str',
               'value': None},
    'mp4_size': {   'link': ['http://razvanrotari.me/terms/', 'mp4_size', 'rr'],
                    'ref': None,
                    'type': 'str',
                    'value': None},
    'nsfw': {   'link': ['http://razvanrotari.me/terms/', 'nsfw', 'rr'],
                'ref': None,
                'type': 'bool',
                'value': None},
    'popularity': {   'link': [   'http://razvanrotari.me/terms/',
                                  'popularity',
                                  'rr'],
                      'ref': 'Popularity',
                      'type': 'Popularity',
                      'value': None},
    'provenance': {   'link': [   'http://purl.org/dc/elements/1.1/',
                                  'publisher',
                                  'dc'],
                      'ref': None,
                      'type': 'str',
                      'value': None},
    'section': {   'link': ['http://razvanrotari.me/terms/', 'section', 'rr'],
                   'ref': None,
                   'type': 'str',
                   'value': None},
    'size': {   'link': ['http://razvanrotari.me/terms/', 'size', 'rr'],
                'ref': None,
                'type': 'str',
                'value': None},
    'timestamp': {   'link': [   'http://purl.org/dc/elements/1.1/',
                                 'created',
                                 'dc'],
                     'ref': None,
                     'type': 'str',
                     'value': None},
    'title': {   'link': ['http://purl.org/dc/elements/1.1/', 'title', 'dc'],
                 'ref': None,
                 'type': 'str',
                 'value': None},
    'topic': {   'link': ['http://razvanrotari.me/terms/', 'topic', 'rr'],
                 'ref': None,
                 'type': 'str',
                 'value': None},
    'views': {   'link': ['http://razvanrotari.me/terms/', 'views', 'rr'],
                 'ref': None,
                 'type': 'str',
                 'value': None},
    'vote': {   'link': ['http://razvanrotari.me/terms/', 'vote', 'rr'],
                'ref': None,
                'type': 'str',
                'value': None},
    'width': {   'link': ['http://razvanrotari.me/terms/', 'width', 'rr'],
                 'ref': None,
                 'type': 'str',
                 'value': None}}
base_model.ImageItem = ImageItem

