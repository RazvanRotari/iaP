#!/usr/bin/env python3
import re
import sys
import model

PATTERN = "\{\{.*?\}\}"

class RSQL:
    def __init__(self):
        self.prefix = {} 
    def find_term_intern(self, item):
        (cls_name, field) = item.replace("{", "").replace("}", "").split(".")
        try:
            getattr(model, cls_name)
        except:
            print("Invalid class: ", cls_name, " in the input file")
        obj = getattr(model, cls_name)("test")
        link = obj.data[field]["link"]
        self.prefix[link[2]] = link[0]
        return link[2]+":"+link[1]

    def find_term(self, match_group):
        mat = match_group.group()
        return self.find_term_intern(mat)

    def __call__(self, group):
        return self.find_term(group)

def main():
    if len(sys.argv) < 2:
        print("Usage qsql.py <file>")
        return
    file_name = sys.argv[1]

    data = ""
    with open(file_name, "r") as fd:
        data = fd.read()
    ql = RSQL()
    output = re.sub(PATTERN, ql, data)
    prefix_str = ""
    for prefix in ql.prefix.items():
        prefix_str += "\nPREFIX {prefix}:<{link}>".format(prefix=prefix[0], link=prefix[1])
    output = prefix_str + "\n" + output 
    print(output)
    #{{item.field}}




if __name__ == "__main__":
    main()
