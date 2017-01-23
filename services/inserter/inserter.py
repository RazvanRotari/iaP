#/usr/bin/env python3

import requests
import model
import sys
import os
from importlib import import_module

fusakienv = os.getenv("FUSAKI_URL")
if fusakienv:
    HOSTNAME = fusakienv
else:
    HOSTNAME = "http://razvanrotari.me:3030/"

UPDATE_URL = HOSTNAME + "default/update" 

sysbus = os.getenv("BUS_URL")
if sysbus:
    BUS = sysbus
else:
    BUS = "http://localhost:8080/media"

def fetch_item_debug():
    popularity = model.Popularity("test.com/pop/1234")
    popularity.views = 300
    user = model.User("test.com/user/5484")
    user.username = "Vasile Test"
    user.popularity = popularity
    a = model.AudioItem("test.com/4554")
    a.title = "cal"
    a.creator = user
    j = a.to_json()
    b = model.Model.from_json(j)
    return b

def fetch_item():
    data = requests.get(BUS).text
    item = model.Model.from_json(data)
    return item

def submit_data(data):
    insert_cmd = model.create_insert([data])
    print(insert_cmd)
    r = requests.post(UPDATE_URL, data=insert_cmd.encode("utf-8"))
    # print(r.text)

def load_plugins():
    curr_path = os.path.realpath(__file__)
    curr_path = os.path.dirname(curr_path)
    plugin_path = os.path.join(curr_path, "plugins")
    plugin_files =  [os.path.splitext(f)[0] for f in os.listdir(plugin_path) if os.path.isfile(os.path.join(plugin_path, f)) and f != "__init__.py"]
    print(plugin_files)
    plugins = []
    for plugin_file in plugin_files:
        plugins.append(import_module("plugins." + plugin_file))
    return plugins

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
