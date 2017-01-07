#/usr/bin/env python3

import requests
import model

HOSTNAME = "http://razvanrotari.me:3030/"
UPDATE_URL = HOSTNAME + "Persistent_Dataset/update" 

BUS = "http://localhost:8080/media"

def fetch_item_debug():
    a = model.AudioItem("test.com/4554")
    a.title = "cal"
    j = a.to_json()
    print(j)
    b = model.Model.from_json(j)
    return b

def fetch_item():
    data = requests.get(BUS).text
    item = model.Model.from_json(data)
    return item

def submit_data(data):
    insert_cmd = model.create_insert([data])
    print(insert_cmd)
    r = requests.post(UPDATE_URL, data=insert_cmd)
    print(r.text)
    
def main():
    while True:
        b = fetch_item()
        submit_data(b)



if __name__ == "__main__":
    main()
