//beautified with jsbeautifier from pip
(function() {

    function assert(v) {
        if (!v) {
            throw Error("assert error");
        }
    }
    
    function isTextElement(el) {
        return el.nodeType==document.TEXT_NODE;
    }
    
    function getAncestorByClassName(child,cl) { //inclusive
        for(;child!=null;child=child.parentNode) { 
            if((" "+child.className+" ").indexOf(" "+cl.trim()+" ")>=0) {
                return child;
            }
        }
        return child;
    }
    
    function getTextChild(el){
        for(var i of el.childNodes) {
            if(isTextElement(i))
                return i;
        }
        return null;
    }
    
    var pages = {
        search: ["searchbar", ],
        tags: ["tagsbar"],
        articles: ["searchbar", "articles"],
        login: ["login"],
        register: ["register"],

    }

    function selectPage(visibleSlides) {
        for (var slide of document.getElementsByClassName("data-slide")) {
            var arr = []
            for (var cl of slide.getAttribute("class").split(" "))
                if (cl != "hidden")
                    arr.push(cl)
            if (visibleSlides.indexOf(slide.getAttribute("data-slide-name")) < 0)
                arr.push("hidden");
            slide.setAttribute("class", arr.join(" "));
        }
    }

    function formPage(name, formNames) {
        this.name = name
        this.formNames = formNames
    }
    formPage.prototype = {
        collectValues: function(ev) {
            p = getAncestorByClassName(ev.target,this.name);
            r = {}
            for (var fname of this.formNames) {
                el = p.getElementsByClassName(fname);
                assert(el.length == 1)
                el = el[0]
                var v = el.value.trim()
                if (!v) {
                    this.print("Please fill out " + el.name + " field.");
                    //return;
                }
                else {
                    r[fname] = v;
                }
            }
            return r;
        },
        submit: function(ev) { 
            var x = this.collectValues(ev);
            for(var i of this.formNames) {
                if(!x.hasOwnProperty(i)) { 
                    return;
                }
            }
            alert(x);
        },
        collectElements: function(ev) {
            p = getAncestorByClassName(ev.target,this.name);
            r = {}
            for (var fname of this.formNames) {
                el = p.getElementsByClassName(fname);
                assert(el.length == 1)
                el = el[0]
                r[fname] = el;
            }
            return r;
        },
        print: function(x) { 
            alert(x)
        },
    };

    selectPage(pages.search);

    for (var ev of document.getElementsByClassName("data-link")) {
        (function() {
            var p = ev.getAttribute("data-link-name");
            ev.addEventListener("click", function() {
                selectPage(pages[p]);
            });
        })();
    }

    (function() {
        var x = new formPage("data-login", ["data-username", "data-password",]);
        for (var ev of document.getElementsByClassName("data-login-button")) {
            ev.addEventListener("click", function(ev) {
                x.submit(ev);
            });
        }
    })();

    (function() {
        var x = new formPage("data-register", ["data-name", "data-email", "data-username", "data-password", "data-confirm-password", ]);
        for (var ev of document.getElementsByClassName("data-register-button")) {
            ev.addEventListener("click", function(ev) {
                x.submit(ev);
            });
        }
    })();
    
    (function() {
        var radioConst = {
            radios:["data-document-awful","data-document-bad","data-document-neutral","data-document-good","data-document-great","data-author-awful","data-author-bad","data-author-neutral","data-author-good","data-author-great",],
            reset:["data-document-reset","data-author-reset",],
            other:[],
            
        };
        var allForms = new formPage("data-article-card",radioConst.radios.concat(radioConst.other));
        var radioForms = new formPage("data-article-card",radioConst.radios);
        var resetForms = new formPage("data-article-card",radioConst.reset);
        
        function resetRadios(ev){
            var e = resetForms.collectElements(ev);
            for(var i in e){
                    e[i].dispatchEvent(new Event("click",{bubbles:true}));
            }
        }
        function closeCallback(ev) {
            resetRadios(ev);
        }
        function saveCallback(ev) {
                var e = allForms.collectElements(ev);
                var r = {}
                for(var i of radioConst.radios) {
                    r[i] = e[i].checked;
                    //alert(r[i]);
                }
                for(var i of radioConst.other) {
                    r[i] = e[i].value;
                }
                resetRadios(ev);
                alert(r);
        }

        for (var el of document.getElementsByClassName("data-radio-save")) {
            el.addEventListener("click",saveCallback);
        }
        for (var el of document.getElementsByClassName("data-radio-close")) {
            el.addEventListener("click",closeCallback);
        }        
    })();

})();