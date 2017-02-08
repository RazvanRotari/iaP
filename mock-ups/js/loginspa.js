(function(){
    for(var ev of document.getElementsByClassName("login-button"))
        ev.addEventListener("click",function(){
            username=document.getElementsByName("username")[0].value;
            password=document.getElementsByName("password")[0].value;
            if(username==="")
                alert("Please enter a username");
            else if(password==="")
                alert("Please enter a password");
            else 
                alert("Your username is: "+username+" and your password is: "+password);
        });
})();