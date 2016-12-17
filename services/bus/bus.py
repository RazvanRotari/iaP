#/usr/bin/env python

from klein import Klein
from twisted.internet.defer import DeferredQueue, inlineCallbacks, returnValue

pipe = DeferredQueue() 
app = Klein()
 
#http://tavendo.com/blog/post/going-asynchronous-from-flask-to-twisted-klein/ 
@app.route('/media', methods=['GET'])
@inlineCallbacks
def put_slaves_to_work(request):
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
