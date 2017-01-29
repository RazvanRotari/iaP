#/usr/bin/env python3

import requests
import model
import base_model
import sys
import os
from importlib import import_module
import json

fusakienv = os.getenv("FUSAKI_URL")
if fusakienv:
    HOSTNAME = fusakienv
else:
    HOSTNAME = "http://razvanrotari.me:3030/"

UPDATE_URL = HOSTNAME + "default/update" 
QUERY_URL = HOSTNAME + "default/query"

def submit_data(data):
    insert_cmd = base_model.create_insert([data])
    print(insert_cmd)
    r = requests.post(UPDATE_URL, data=insert_cmd.encode("utf-8"))

def query(modelcls, **args):
    query_cmd = modelcls.create_query(**args)
    print(query_cmd)
    r = requests.post(QUERY_URL, data={"query":query_cmd.encode("utf-8")})
    response_text = r.text
    print(response_text)
    data = json.loads(response_text)["results"]["bindings"]
    models = {}
    for binding in data:
        uri = binding["wadesubject"]["value"]
        if uri not in models:
            models[uri] = modelcls(uri)
        model = models[uri]
        for stuff in binding.items():
            name = stuff[0]
            meta = stuff[1]
            if name == "wadesubject":
                continue
            print(meta["value"])
            setattr(model, name, meta["value"])
    return list(models.values())


def delete(model):
    delete_cmd = model.create_delete()
    r = requests.post(UPDATE_URL,data={"update":delete_cmd.encode("utf-8")})
    print(r.text)


def main():
    plugins = load_plugins()
    while True:
        b = fetch_item()
        for plugin in plugins:
            if getattr(plugin, "should_process")(b):
                b = plugin.process(b)
        submit_data(b)
        # break

if __name__ == "__main__":
    main()
