#/usr/bin/env python

from klein import Klein
from twisted.internet.defer import DeferredQueue, inlineCallbacks, returnValue

pipe = DeferredQueue() 
app = Klein()

UNDER_TEST=False
TEST_DATA = """{"class": "AudioItem", "URI": "test.com/4554", "data": {"description": {"value": null, "link": ["http://razvanrotari.me/terms/", "description", "rr"]}, "creator": {"value": null, "link": ["http://razvanrotari.me/terms/", "creator", "rr"]}, "uploaded_at": {"value": null, "link": ["http://razvanrotari.me/terms/", "uploaded_at", "rr"]}, "external_id": {"value": null, "link": ["http://razvanrotari.me/terms/", "external_id", "rr"]}, "external_category_id": {"value": null, "link": ["http://razvanrotari.me/terms/", "external_category_id", "rr"]}, "audio_links": {"value": null, "link": ["http://razvanrotari.me/terms/", "audio_links", "rr"]}, "recorded_at": {"value": null, "link": ["http://razvanrotari.me/terms/", "recorded_at", "rr"]}, "title": {"value": "cal", "link": ["http://razvanrotari.me/terms/", "title", "rr"]}, "provider": {"value": null, "link": ["http://purl.org/dc/terms/", "accrualPeriodicity", "dc"]}, "duration": {"value": null, "link": ["http://razvanrotari.me/terms/", "duration", "rr"]}, "supports_comments": {"value": null, "link": ["http://razvanrotari.me/terms/", "supports_comments", "rr"]}, "tags": {"value": null, "link": ["http://razvanrotari.me/terms/", "tags", "rr"]}}}"""

 
#http://tavendo.com/blog/post/going-asynchronous-from-flask-to-twisted-klein/ 
@app.route('/media', methods=['GET'])
@inlineCallbacks
def put_slaves_to_work(request):
    if UNDER_TEST:
        returnValue(TEST_DATA)
    else:
        data = yield pipe.get()
    print(data)
    returnValue(data)

@app.route('/media/<item>', methods=["POST"])
def media_insert(request, item):
    data = request.content.read()
    print(data)
    pipe.put(data)
    return

 
 
if __name__ == "__main__":
   app.run("localhost", 8080)
