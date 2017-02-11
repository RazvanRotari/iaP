"""rest_service URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin
from .views.user import *
from .views.query import *
from .views.media import *
from .views.category import *
from .views.author import *
urlpatterns = [
    url(r'^admin/', admin.site.urls),

    url(r'^api/v1/api-auth/', include('rest_framework.urls', namespace='rest_framework')),

    url(r'^api/v1/search', QueryView.as_view()),

    url(r'^api/v1/media', AllMedia.as_view()),
    url(r'^api/v1/media', NewMedia.as_view()),
    url(r'^api/v1/media/(?P<uid>[A-Za-z0-9]+)', DeleteMedia.as_view()),
    url(r'^api/v1/media/(?P<uid>[A-Za-z0-9]+)', FindMedia.as_view()),
    url(r'^api/v1/media/(?P<uid>[A-Za-z0-9]+)', UpdateMedia.as_view()),

    url(r'^api/v1/categories', AllCategories.as_view()),
    url(r'^api/v1/category/(?P<uid>[A-Za-z0-9]+)', NewCategory.as_view()),
    url(r'^api/v1/category/(?P<uid>[A-Za-z0-9]+)', DeleteCategory.as_view()),

    url(r'^api/v1/users', UserRoot.as_view()),
    url(r'^api/v1/users/(?P<uid>[A-Za-z0-9]+)', EditUser.as_view()),
    url(r'^api/v1/users/(?P<uid>[A-Za-z0-9]+)', GetUserDetails.as_view()),
    url(r'^api/v1/users/(?P<uid>[A-Za-z0-9]+)', DeleteUser.as_view()),

    url(r'^api/v1/author', AuthorRoot.as_view()),
    url(r'^api/v1/author/(?P<uid>[A-Za-z0-9]+)', EditAuthor.as_view()),
    url(r'^api/v1/author/(?P<uid>[A-Za-z0-9]+)', GetAuthorDetails.as_view()),
    url(r'^api/v1/author/(?P<uid>[A-Za-z0-9]+)', DeleteAuthor.as_view()),
]
from .views.author import *

