from rest_framework import authentication
from rest_framework.request import Request
from rest_framework import exceptions

import json
from rest_service.model.model import User


class SparqlAuthentiction(authentication.BaseAuthentication):
    def authenticate(self, request: Request):
        data = self.__get_data__(request)
        user = data["username"]
        if not user:
            return None
        usr: User = User.create_query(username=user)
        if usr is not None:
            if usr.password == data["password"]:
                return (usr, None)
            else:
                return None
        else:
            raise exceptions.AuthenticationFailed('No such user')

    def __get_data__(self, request):
        body = request.data
        json_acceptable_string = body.replace("'", "\"")
        return json.loads(json_acceptable_string)
