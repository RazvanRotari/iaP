#/usr/bin/env python

import requests
import sys

if len(sys.argv) < 2:
    print("Usage: inserter <ip>")
    sys.exit(2)


ip = sys.argv[1]

r = requests.get("http://{ip}/media".format(ip=ip))
print(r.text)

