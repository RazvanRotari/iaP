#!/usr/bin/env python3

import requests
import model
import os

ENDPOINT = "http://razvanrotari.me/media"
ENDPOINT = "http://localhost:8080/media/ImageItem"
temp = os.getenv("UPLOAD_ENDPOINT")
if temp:
    ENDPOINT = temp

def upload_model(item):
    payload = item.to_json()
    print(payload)
    print("")
    print()
    r = requests.post(ENDPOINT, data=payload)
    # r = None
    print(r.text)
    print(r.status_code)
    return r
