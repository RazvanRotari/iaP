#!/usr/bin/env python3
import requests
import xmltodict
import upload
import model

RSS_ENDPOINT = "http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml"
def get_rss():
    response = requests.get(RSS_ENDPOINT)
    return xmltodict.parse(response.text)["rss"]


def main():
    data = get_rss()
    for item in data["channel"]["item"]:
        newsItem = model.NewsItem(item["guid"]["#text"])
        newsItem.title = item["title"]
        newsItem.provenance = "http://rss.nytimes.com"
        newsItem.author = item["dc:creator"]
        newsItem.timestamp = item["pubDate"]
        if "category" in item:
            print("\n\n\n")
            print(item["category"])
            if isinstance(item["category"], list):
                newsItem.categories = [x["#text"] for x in item["category"]]
            else:
                newsItem.categories = [item["category"]["#text"]]
        newsItem.description = item["description"]
        upload.upload_model(newsItem)

if __name__ == "__main__":
    main()
