from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.response import Response
from rest_framework.request import Request
from rest_framework.views import APIView


class QueryView(APIView):
    permission_classes = (AllowAny,)

    def post(self, request:Request, format=None):
        auth = request.auth;
        content = {
            'status': 'request was permitted',
            'other': auth
        }
        return Response(content)