from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.response import Response
from rest_framework.request import Request
from rest_framework.views import APIView
from rest_framework.renderers import JSONRenderer

from rest_service.model.model import User;

class UserRoot(APIView):
    permission_classes = (AllowAny,)

    def post(self, request:Request, format=None):
        content = {
            'status': 'request was permitted'
        }
        return Response(content)

    def get(self, request:Request, format=None):
        return Response(status=200, data=User.create_query())


class EditUser(APIView):
    permission_classes = (IsAuthenticated,)

    def put(self, request:Request, format=None):
        content = {
            'status': 'request was permitted'
        }
        return Response(content)

class GetUserDetails(APIView):
    permission_classes = (IsAuthenticated,)
    renderer_classes = (JSONRenderer,)

    def get(self, request:Request, format=None):
        content = {
            'status': 'get was permitted'
        }
        return Response(content)


class DeleteUser(APIView):
    permission_classes = (IsAuthenticated,)
    renderer_classes = (JSONRenderer,)

    def delete(self, request:Request, format=None):
        content = {
            'status': 'delete was permitted'
        }
        return Response(content)