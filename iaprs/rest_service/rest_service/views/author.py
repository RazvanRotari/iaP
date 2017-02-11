from rest_framework.permissions import AllowAny, IsAdminUser
from rest_framework.response import Response
from rest_framework.request import Request
from rest_framework.views import APIView
from rest_framework.renderers import JSONRenderer

from rest_service.model.model import Author, create_insert, Model;
from rest_service.util.response_body import ResponseBody;


class AuthorRoot(APIView):
    permission_classes = (AllowAny,)

    def post(self, request: Request):
        response_body = ResponseBody(request.data)
        try:
            query = Author.create_query(name=response_body.get("name"))
            if query is None:
                author: Author = Author()
                author.name = response_body.get("name")
                author.provenance = response_body.get("provenance")
                author.provenance = response_body.get("real_name")
                create_insert(author)
                return Response(data="Author created", status=201)
            else:
                return Response(data="Author already exists", status=409)
        except KeyError:
            return Response(data="Author not created", status=400)

    def get(self, request: Request):
        return Response(Author.create_query())



class EditAuthor(APIView):
    permission_classes = (IsAdminUser,)

    def put(self, request: Request, uid=None):
        response_body = ResponseBody(request.data)
        query = Author.create_query(external_id=uid)
        if query is not None:
            return Response(data="Author updated", status=201)
        else:
            return Response(data="Author already exists", status=404)

class GetAuthorDetails(APIView):
    permission_classes = (AllowAny,)
    renderer_classes = (JSONRenderer,)

    def get(self, request: Request, uid=None):
        author: Author = Author.create_query(external_id=uid)
        if author is not None:
            return Response(data=author, status=200)
        else:
            return Response(data="Author does not exist", status=404)


class DeleteAuthor(APIView):
    permission_classes = (IsAdminUser,)

    def delete(self, request: Request, uid=None):
        author: Author = Author.create_query(id=uid)
        if author is None:
            return Response(data="Author does not exist", status=404)
        author.create_delete()
        return Response(data="Successful delete operation", status=204)
