#!/usr/bin/env python3

import requests

rdf = """
PREFIX dc: <http://purl.org/dc/elements/1.1/>
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX si: <http://www.w3schools.com/rdf/>
PREFIX si: <http://xmlns.com/foaf/0.1/>
INSERT DATA
{ 
  <GOD> dc:title "A new book" ;
                         dc:creator "A.N.Other" .
}
"""


r = requests.post("http://razvanrotari.me:3030/Persistent_Dataset/update", data=rdf)
print(r.text)
