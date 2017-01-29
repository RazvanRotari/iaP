#!/usr/bin/env python3

from __future__ import print_function
import yaml
import sys
import re
import pprint
import sys
import datetime

def eprint(*args, **kwargs):
    print(*args, file=sys.stderr, **kwargs)

#GOD = General Object Description

DEFAULT_URI = "http://razvanrotari.me/terms/"
DEFAULT_URI_PREFIX = "rr"


INIT_TEMPLATE = """
class {class_name}(Model):
    def __init__(self, URI):
        self.URI = URI
        self.data = {data_struct}
base_model.{class_name} = {class_name}
"""
#example
"""
class Object
    def __init__(self, URI):
        self.data = {"prop": {
            "link":["http://razvanrotari.me/terms/","prop", "rr"},
            "value":None,
            "type":Datetime}
        self.URI = URI
"""


def parse_type(attr):
    """
    We need to pass all attr to identify the type. 
    There is a big mismatch between sparql and swagger
    Returs a class of a type
    """
    print(attr)
    if "$ref" in attr:
        ref = attr["$ref"].split("/")[-1]
        return attr["$ref"].split("/")[-1]
    if "type" not in attr:
        return "str"

    ty = attr["type"]
    format = None
    if "format" in attr:
        format = attr["format"]
    if ty == "boolean":
        return "bool"
    if ty == "number":
        if format:
            if "int" == format:
                return "int"
            if "float" == format:
                return "float"
        return "int"
    if ty == "string":
        if "date" == format:
            return "datetime"
        return "str"
    return "str"

def create_class(definition):
    class_name = definition[0]
    props = definition[1]["properties"]
    data_dict = {"class_name": {"link":[DEFAULT_URI, "className", DEFAULT_URI_PREFIX], "value": class_name}}
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
        ref = None
        if "$ref" in attr:
            ref = attr["$ref"].split("/")[-1]
            depend.append(attr["$ref"].split("/")[-1])
        data_dict[name] = {"link": link, "value": None, "ref": ref, "type":parse_type(attr)}
        pp = pprint.PrettyPrinter(indent=4)
    text = INIT_TEMPLATE.format(class_name=class_name, data_struct=pp.pformat(data_dict))
    # text += "\n" + BODY_TEMPLATE 
    return  {
            "name":class_name,
            "body": text,
            "dependencies":depend}



FUNCTION_TEMPLATE = """import base_model
from base_model import Model
create_insert = base_model.create_insert
"""
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
