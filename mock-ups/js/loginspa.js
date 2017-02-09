//beautified with jsbeautifier from pip
(function() {

    function assert(v) {
        if (!v) {
            throw Error("assert error");
        }
    }

    function formPage(name, formNames) {
        this.name = name
        this.formNames = formNames
    }
    formPage.prototype = {
        submit: function() {
            var p = document.getElementsByClassName(this.name);
            assert(p.length == 1)
            p = p[0]
            r = {}
            for (var fname of this.formNames) {
                el = p.getElementsByClassName(fname);
                assert(el.length == 1)
                el = el[0]
                var v = el.value.trim()
                if (!v) {
                    this.print("Please fill out " + el.name + " field.");
                    return;
                }
                r[fname] = v;
            }
            alert(r);
        },
        print: function(x) {
            alert(x)
        },
    };

    for (var ev of document.getElementsByClassName("data-login-button")) {
        (function() {
            var x = new formPage("data-login", ["data-username", "data-password", ]);
            ev.addEventListener("click", function() {
                x.submit();
            });
        })();
    }

    for (var ev of document.getElementsByClassName("data-register-button")) {
        (function() {
            var x = new formPage("data-register", ["data-name", "data-email", "data-username", "data-password", "data-confirm-password", ]);
            ev.addEventListener("click", function() {
                x.submit();
            });
        })();
    }

})();