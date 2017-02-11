from rest_framework.permissions import AllowAny, IsAdminUser
from rest_framework.response import Response
from rest_framework.request import Request
from rest_framework.views import APIView


class AllCategories(APIView):
    permission_classes = (AllowAny, )

    def get(self, request:Request, format=None):
        return Response(status=200, data="A,B,C")


class NewCategory(APIView):
    permission_classes = (IsAdminUser, )

    def post(self, request:Request, format=None):
        content = {
            'status': 'Category created'
        }
        return Response(content)


class DeleteCategory(APIView):
    permission_classes = (IsAdminUser,)

    def delete(self, request:Request, format=None):
        content = {
            'status': 'Category Deleted'
        }
        return Response(content)
