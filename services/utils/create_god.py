#!/usr/bin/env python3

import yaml
import sys
import re
import pprint

#GOD = General Object Description

DEFAULT_URI = "http://razvanrotari.me/terms/"
DEFAULT_URI_PREFIX = "rr"

FUNCTION_TEMPLATE = """
import json

def create_insert(object_list):
    prefix_text = ""
    insert = "INSERT DATA {\\n"
    for obj in object_list:
        d = obj.create_partial_insert()
        insert += d[0]
        prefix_text += d[1]
        
    insert += "\\n}"
    insert = prefix_text  + insert
    return insert 


class Model:
    def to_json(self):
        return json.dumps({"data": self.data, "URI": self.URI, "class": self.__class__.__name__})

    @staticmethod
    def from_json(input):
        model = json.loads(input)
        cls = globals()[model["class"]]
        obj = cls(model["URI"])
        obj.data = model["data"]
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
        insert = "<{URI}> ".format(URI=self.URI)
        prefix_set = set()
        for prop in self.data.items():
            value = prop[1]["value"]
            if value is None:
                continue
            line = "{link} \\"{value}\\" ;".format(link=prop[1]["link"][2] + ":" + prop[1]["link"][1], value=value)
            prefix_set.add(tuple(prop[1]["link"]))
            insert += line
        prefix = ""
        for p in prefix_set:
            prefix += "PREFIX {prefix}: <{base_url}>\\n".format(prefix=p[2], base_url=p[0])
        insert = insert[::-1].replace(";", ".", 1)[::-1]
        return (insert, prefix)

"""

INIT_TEMPLATE = """
class {class_name}(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {data_struct}
"""
#example
"""
class Object
    def __init__(self, URI):
        self.data = {"prop": {
            "link":["http://razvanrotari.me/terms/","prop", "rr"},
            "value":None}
        self.URI = URI
            """

def create_class(definition):
    class_name = definition[0]
    props = definition[1]["properties"]
    data_dict = {}
    depend = []
    for prop in props.items():
        name = prop[0]
        attr = prop[1]
        if name == "URI":
            continue
        link = [DEFAULT_URI, name, DEFAULT_URI_PREFIX]
        if "description" in attr:
            description = attr["description"]
            PATTERN = "^\[(.*?)\]"
            r = re.findall(PATTERN, description)
            if len(r) != 0:
                link = r[0]
                tmp = link.split(",")
                print(tmp)
                link = [tmp[0], tmp[1], tmp[2]]
        if "$ref" in attr:

            depend.append(attr["$ref"].split("/")[-1])
        data_dict[name] = {"link": link, "value": None}
        pp = pprint.PrettyPrinter(indent=4)
    text = INIT_TEMPLATE.format(class_name=class_name, data_struct=pp.pformat(data_dict))
    # text += "\n" + BODY_TEMPLATE 
    return  {
            "name":class_name,
            "body": text,
            "dependencies":depend}



def main():
    if len(sys.argv) < 2:
        print("Usage: create_god.py <swagger_file>")
    swagger = sys.argv[1]
    data = ""
    with open(swagger) as data_file:
        data = data_file.read()

    structure = yaml.load(data)
    defs = structure["definitions"]
    # for d in defs.items():
    #     print(d)
    text = FUNCTION_TEMPLATE
    classes = {}
    for d in defs.items():
        cls = create_class(d)

        classes[cls["name"]] = cls
    # print(text)
    # text += "\n" + create_class(d)
    sorted_dep = []

    #resolve dependency
    #https://www.electricmonk.nl/docs/dependency_resolving_algorithm/dependency_resolving_algorithm.html
    def resolve_dep(node, resolved):
        for edge in [classes[x] for x in node["dependencies"]]:
                resolve_dep(edge, resolved)
        resolved.append(node)
    resolved = []
    resolve_dep(classes[list(classes.keys())[0]], resolved)
    inserted = [x["name"] for x in resolved]
    for item in classes.items():
        name = item[0]
        if name not in inserted:
            resolved.append(item[1])
    for cls in resolved:
        text += '\n' + cls["body"]
    print(text)


if __name__ == "__main__":
    main()
