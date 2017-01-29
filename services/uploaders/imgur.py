import model
import upload
import json
import requests

#This should be moved to a class
SESSION = None
API_KEY = None
START_IMGUR_ENDPOINT = "https://api.imgur.com/3/gallery/hot/viral/1.json"
GALLERY_IMAGE_ENDPOINT = "https://api.imgur.com/3/gallery/image/{id}"
IMAGE_ENDPOINT = "https://api.imgur.com/3/image/{id}"
GALLERY_ALBUM_ENDPOINT = "https://api.imgur.com/3/gallery/album/{id}"
USER_ENDPOINT = "https://api.imgur.com/3/account/{url}"

def retrieve_api_key():
    with open("imgur.json") as input:
        j = json.load(input)
        return j["clientid"]

def init_session(API_KEY):
    session = requests.Session()
    session.headers.update({"Authorization":"Client-ID " + API_KEY})
    return session

def process_data_list(data_list):
    pass

def get_user(session, image):
    if "account_url" not in image or not image["account_url"]:
        return None
    print(image)
    user_url = image["account_url"]
    user = model.User("https://api.imgur.com/3/account/" + user_url)
    URI = USER_ENDPOINT.format(url=user_url)
    user_response = session.get(URI)
    user_data = json.loads(user_response.text)["data"]
    user.username = user_data["url"]
    user.description = user_data["bio"]
    return user

    #Works with pure imgur json
def upload_image(session, image, title):
    id = image["id"]
    URI = GALLERY_IMAGE_ENDPOINT.format(id=id)
    img_response = session.get(URI)
    if img_response.status_code != 200:
        URI = IMAGE_ENDPOINT.format(id=id)
        img_response = session.get(URI)
    img_data = json.loads(img_response.text)["data"]
    print(img_data)
    img_model = model.ImageItem(URI)
    if "title" in img_data:
        img_model.title = img_data["title"]
    if not img_model.title:
        img_model.title = title
    img_model.description = img_data["description"]
    img_model.datetime = img_data["datetime"]
    img_model.mime_type = img_data["type"]
    img_model.animated = img_data["animated"]
    img_model.width = img_data["width"]
    img_model.height = img_data["height"]
    img_model.size = img_data["size"]
    img_model.nsfw = img_data["nsfw"]
    img_model.creator = get_user(session, img_data)
    upload.upload_model(img_model)
    

#Works with pure imgur json
def upload_album(session, album):
    id = album["id"]
    album_response = session.get(GALLERY_ALBUM_ENDPOINT.format(id=id))
    if album_response.status_code != 200:
        print("Album {id} is fucked! SKIP HIM! Here is the excuse{msg}".format(id=id, msg=album_response.text))
        return
    album_data = json.loads(album_response.text)["data"]
    print(album_data)
    title = None
    if "title" in album_data:
        title = album_data["title"]
    for image in album_data["images"]:
        upload_image(session, image, title)

def main():
    API_KEY = retrieve_api_key()
    session = init_session(API_KEY)
    viral_galery = json.loads(session.get(START_IMGUR_ENDPOINT).text)
    # print(viral_galery)
    print(viral_galery)
    data_list = viral_galery["data"]
    for item in data_list:
        if item["is_album"]:
            upload_album(session, item)
        else:
            upload_image(session, item, None)

if __name__ == "__main__":
    main()
