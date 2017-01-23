#!/usr/bin/env python3

from __future__ import print_function
import yaml
import sys
import re
import pprint
import sys

def eprint(*args, **kwargs):
    print(*args, file=sys.stderr, **kwargs)

#GOD = General Object Description

DEFAULT_URI = "http://razvanrotari.me/terms/"
DEFAULT_URI_PREFIX = "rr"

FUNCTION_TEMPLATE = """
import json

class ModelEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Model):
            return {"data": obj.data, "URI": obj.URI, "class": obj.__class__.__name__}
            
        # Let the base class default method raise the TypeError
        return json.JSONEncoder.default(self, obj)

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
        return json.dumps(self, cls=ModelEncoder)

    @staticmethod
    def from_json(input):
        model = json.loads(input)
        return Model.from_dict(model)

    @staticmethod
    def from_dict(model):
        cls = globals()[model["class"]]
        obj = cls(model["URI"])
        obj.data = model["data"]
        #recreate inner objects
        for prop in obj.data.items():
            val = prop[1]
            if "ref" in val and val["ref"] and val["value"]:
                #We have a valid inner object. Let's go recursive
                inner_obj = Model.from_dict(val["value"])
                setattr(obj, prop[0], inner_obj)
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
        child_prefix = []
        child_objects = []
        insert = "<{URI}> ".format(URI=self.URI)
        prefix_set = {}
        for prop in self.data.items():
            if "ref" in prop[1] and prop[1]["ref"]:
                if prop[1]["value"]:
                    value = prop[1]["value"].URI
                    (tmp_insert, tmp_prefix) = prop[1]["value"].create_partial_insert()
                    child_prefix.extend(tmp_prefix)
                    child_objects.append(tmp_insert)
                else:
                    value = None
            else:
                value = prop[1]["value"]
            if value is None:
                continue
            line = "{link} \\"{value}\\" ;".format(link=prop[1]["link"][2] + ":" + prop[1]["link"][1], value=value)
            prefix_list = prop[1]["link"]
            prefix = prop[1]["link"][2]
            prefix_set[prefix] = prefix_list[0]
            insert += line
        prefix = ""
        for p in prefix_set.items():
            prefix += "PREFIX {prefix}: <{base_url}>\\n".format(prefix=p[0], base_url=p[1])
        for p in child_prefix:
            prefix += p
        insert = insert[::-1].replace(";", ".", 1)[::-1]
        for insert_obj in child_objects:
            insert += insert_obj
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
        data_dict[name] = {"link": link, "value": None, "ref": ref}
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
