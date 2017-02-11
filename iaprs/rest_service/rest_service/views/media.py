from rest_framework.permissions import AllowAny, IsAdminUser
from rest_framework.response import Response
from rest_framework.request import Request
from rest_framework.views import APIView

from rest_service.model.model import Model;

class AllMedia(APIView):
    permission_classes = (AllowAny, )

    def get(self, request:Request):
        return Response(status=200, data=Model.create_query())


class NewMedia(APIView):
    permission_classes = (IsAdminUser, )

    def post(self, request:Request, format=None):
        content = {
            'status': 'request was permitted'
        }
        return Response(content)


class DeleteMedia(APIView):
    permission_classes = (IsAdminUser,)

    def delete(self, request:Request, format=None):
        content = {
            'status': 'request was permitted'
        }
        return Response(content)


class FindMedia(APIView):
    permission_classes = (AllowAny,)

    def get(self, request:Request, format=None):
        content = {
            'status': 'request was permitted'
        }
        return Response(content)


class UpdateMedia(APIView):
    permission_classes = (IsAdminUser,)

    def put(self, request:Request, format=None):
        content = {
            'status': 'request was permitted'
        }
        return Response(content)
