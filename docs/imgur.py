
api = """id 	string 	The ID for the image
title 	string 	The title of the image.
description 	string 	Description of the image.
datetime 	integer 	Time inserted into the gallery, epoch time
type 	string 	Image MIME type.
animated 	boolean 	is the image animated
width 	integer 	The width of the image in pixels
height 	integer 	The height of the image in pixels
size 	integer 	The size of the image in bytes
views 	integer 	The number of image views
bandwidth 	integer 	Bandwidth consumed by the image in bytes
deletehash 	string 	OPTIONAL, the deletehash, if you're logged in as the image owner
link 	string 	The direct link to the the image. (Note: if fetching an animated GIF that was over 20MB in original size, a .gif thumbnail will be returned)
gifv 	string 	OPTIONAL, The .gifv link. Only available if the image is animated and type is 'image/gif'.
mp4 	string 	OPTIONAL, The direct link to the .mp4. Only available if the image is animated and type is 'image/gif'.
mp4_size 	integer 	OPTIONAL, The Content-Length of the .mp4. Only available if the image is animated and type is 'image/gif'. Note that a zero value (0) is possible if the video has not yet been generated
looping 	boolean 	OPTIONAL, Whether the image has a looping animation. Only available if the image is animated and type is 'image/gif'.
vote 	string 	The current user's vote on the album. null if not signed in or if the user hasn't voted on it.
favorite 	boolean 	Indicates if the current user favorited the image. Defaults to false if not signed in.
nsfw 	boolean 	Indicates if the image has been marked as nsfw or not. Defaults to null if information is not available.
comment_count 	int 	Number of comments on the gallery image.
topic 	string 	Topic of the gallery image.
topic_id 	integer 	Topic ID of the gallery image.
section 	string 	If the image has been categorized by our backend then this will contain the section the image belongs in. (funny, cats, adviceanimals, wtf, etc)
account_url 	string 	The username of the account that uploaded it, or null.
account_id 	integer 	The account ID of the account that uploaded it, or null.
ups 	integer 	Upvotes for the image
downs 	integer 	Number of downvotes for the image
points 	integer 	Upvotes minus downvotes
score 	integer 	Imgur popularity score
is_album 	boolean 	If it's an album or not"""

for line in api.split("\n"):
    data = line.split(" ")
    name = data[0]
    type = data[1]
    description = data[2]
    if "OPTIONAL" in description:
        description = " ".join(data[3:])
    else:
        description = " ".join(data[2:])

    print("""     {name}:
       type: {type}
       description: {description}""".format(name=name, type=type.strip(), description=description.strip()))
