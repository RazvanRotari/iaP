import json


class ResponseBody:

    def __init__(self, body: str):
        self.data = self.__get_data__(str)

    def get_attribute(self, attribute):
        return self.data[attribute] or self.__raise_KeyError__(attribute)

    @staticmethod
    def __get_data__(body):
        json_acceptable_string = body.replace("'", "\"")
        return json.loads(json_acceptable_string)

    @staticmethod
    def __raise_KeyError__(key=''): raise KeyError(key + " is missing")