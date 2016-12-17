#/usr/bin/env python

import requests
import sys

if len(sys.argv) < 2:
    print("Usage: inserter <ip>")
    sys.exit(2)


ip = sys.argv[1]


payload = {'key1': 'value1', 'key2': 'value2'}
r = requests.post("http://{ip}/media/audio".format(ip=ip), data=payload)
