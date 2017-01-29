import json
import datetime

class ModelEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Model):
            return {"data": obj.data, "URI": obj.URI, "class": obj.__class__.__name__}
        if isinstance(obj, datetime.datetime):
            #Sparqql in special:D
            #2014-05-23T10:20:13+05:30
            return obj.strftime("%Y-%m-%dT%H:%M:%S%z")
            
        # Let the base class default method raise the TypeError
        return json.JSONEncoder.default(self, obj)

def create_insert(object_list):
    prefix_text = ""
    insert = "INSERT DATA {\n"
    for obj in object_list:
        d = obj.create_partial_insert()
        insert += d[0]
        prefix_text += d[1]
        
    insert += "\n}"
    insert = prefix_text  + insert
    return insert 

type_dict = {"int": int,
            "str": str,
            "float": float,
            "datetime": datetime.datetime,
            "bool": bool
            }
def parse_type(type_str):
    return type_dict[type_str]

sparql_type_dict = {"int": "xsd:integer",
        "str": "xsd:string",
        "float": "xsd:float",
        "datetime": "xsd:dateTime",
        "bool": "xsd:boolean"}

def py2sparql(ty):
    if ty in sparql_type_dict:
        return sparql_type_dict[ty]
    return ""

class Model:
    def to_json(self):
        return json.dumps(self, cls=ModelEncoder)
    
    @classmethod
    def create_query(cls, **args):
        """Generate the query string """
        tmp_model = cls("test.com")
        predicates = ["?wadesubject"]
        where_string = ""
        optional_strings = ""
        for prop, metadata in tmp_model.data.items():
            if prop == "class_name":
                continue
            link = metadata["link"][0] + metadata["link"][1]
            optional_strings += '\nOPTIONAL{{\n?wadesubject <{link}> {predicate} }}.'.format(link=link, predicate="?"+prop)
            if prop in args:
                where_string += '\n?wadesubject <{link}> \"{value}\".'.format(link=link, value=args[prop])
            predicates.append("?" + prop)
        select_string = "SELECT "
        for pred in predicates:
            select_string += pred + " "
        metadata = tmp_model.data["class_name"]
        link = metadata["link"][0] + metadata["link"][1]
        class_name_string = '\n?wadesubject <{classlink}> \"{classname}\".'.format(classlink=link, classname=metadata["value"])
        filter = ""
        if "URI" in args:
            filter = "\nfilter(?wadesubject=<{URI}>)".format(URI=args["URI"])
        where_string = class_name_string + where_string + optional_strings + filter
        # where_string = class_name_string + "\n" + "OPTIONAL"
        select_string += "\nWHERE {\n" + where_string + "\n}"
        
        return select_string
    
    def create_delete(self):
        """
        Deletes the whole item
        """
        return "DELETE WHERE {{ <{URI}>  ?property ?value; }}".format(URI=self.URI)

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

    def __eq__(self, model):
        if len(self.data) != len(model.data):
            return False

    def create_partial_insert(self):
        child_prefix = []
        child_objects = []
        insert = "<{URI}> ".format(URI=self.URI)
        prefix_set = {"xsd": "http://www.w3.org/2001/XMLSchema#"}
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
            print(prop)
            print("Value  ",value)
            line = ""
            if "class_name" not in prop[0]:
                print("Value  ", value)
                if isinstance(value, list):
                    for item in value:
                        line += "{link} \"{value}\" ;".format(link=prop[1]["link"][2] + ":" + prop[1]["link"][1], value=item )
                else:
                    line = "{link} \"{value}\"^^{type} ;".format(link=prop[1]["link"][2] + ":" + prop[1]["link"][1], value=value, type=py2sparql(prop[1]["type"]))
            else:
                line = "{link} \"{value}\" ;".format(link=prop[1]["link"][2] + ":" + prop[1]["link"][1], value=value )

            prefix_list = prop[1]["link"]
            prefix = prop[1]["link"][2]
            prefix_set[prefix] = prefix_list[0]
            insert += line
        prefix = ""
        for p in prefix_set.items():
            prefix += "PREFIX {prefix}: <{base_url}>\n".format(prefix=p[0], base_url=p[1])
        for p in child_prefix:
            prefix += p
        insert = insert[::-1].replace(";", ".", 1)[::-1]
        for insert_obj in child_objects:
            insert += insert_obj
        return (insert, prefix)
