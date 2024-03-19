/**
 * 整理：胡尐睿丶
 * 联系：hooray@hoorayos.com
 */


/**
 * SWFObject
 * 2.3
 * http://github.com/swfobject/swfobject
 * demo:
 * var el = document.getElementById("my-target-element");
 * swfobject.embedSWF("myContent.swf", el, 300, 120, 10);
 */
var swfobject = function () {
    var D = "undefined", r = "object", T = "Shockwave Flash", Z = "ShockwaveFlash.ShockwaveFlash",
        q = "application/x-shockwave-flash", S = "SWFObjectExprInst", x = "onreadystatechange", Q = window,
        h = document, t = navigator, V = false, X = [], o = [], P = [], K = [], I, p, E, B, L = false, a = false, m, G,
        j = true, l = false, O = function () {
            var ad = typeof h.getElementById != D && typeof h.getElementsByTagName != D && typeof h.createElement != D,
                ak = t.userAgent.toLowerCase(), ab = t.platform.toLowerCase(), ah = ab ? /win/.test(ab) : /win/.test(ak),
                af = ab ? /mac/.test(ab) : /mac/.test(ak),
                ai = /webkit/.test(ak) ? parseFloat(ak.replace(/^.*webkit\/(\d+(\.\d+)?).*$/, "$1")) : false,
                aa = t.appName === "Microsoft Internet Explorer", aj = [0, 0, 0], ae = null;
            if (typeof t.plugins != D && typeof t.plugins[T] == r) {
                ae = t.plugins[T].description;
                if (ae && (typeof t.mimeTypes != D && t.mimeTypes[q] && t.mimeTypes[q].enabledPlugin)) {
                    V = true;
                    aa = false;
                    ae = ae.replace(/^.*\s+(\S+\s+\S+$)/, "$1");
                    aj[0] = n(ae.replace(/^(.*)\..*$/, "$1"));
                    aj[1] = n(ae.replace(/^.*\.(.*)\s.*$/, "$1"));
                    aj[2] = /[a-zA-Z]/.test(ae) ? n(ae.replace(/^.*[a-zA-Z]+(.*)$/, "$1")) : 0
                }
            } else {
                if (typeof Q.ActiveXObject != D) {
                    try {
                        var ag = new ActiveXObject(Z);
                        if (ag) {
                            ae = ag.GetVariable("$version");
                            if (ae) {
                                aa = true;
                                ae = ae.split(" ")[1].split(",");
                                aj = [n(ae[0]), n(ae[1]), n(ae[2])]
                            }
                        }
                    } catch (ac) {
                    }
                }
            }
            return {w3: ad, pv: aj, wk: ai, ie: aa, win: ah, mac: af}
        }(), i = function () {
            if (!O.w3) {
                return
            }
            if ((typeof h.readyState != D && (h.readyState === "complete" || h.readyState === "interactive")) || (typeof h.readyState == D && (h.getElementsByTagName("body")[0] || h.body))) {
                f()
            }
            if (!L) {
                if (typeof h.addEventListener != D) {
                    h.addEventListener("DOMContentLoaded", f, false)
                }
                if (O.ie) {
                    h.attachEvent(x, function aa() {
                        if (h.readyState == "complete") {
                            h.detachEvent(x, aa);
                            f()
                        }
                    });
                    if (Q == top) {
                        (function ac() {
                            if (L) {
                                return
                            }
                            try {
                                h.documentElement.doScroll("left")
                            } catch (ad) {
                                setTimeout(ac, 0);
                                return
                            }
                            f()
                        }())
                    }
                }
                if (O.wk) {
                    (function ab() {
                        if (L) {
                            return
                        }
                        if (!/loaded|complete/.test(h.readyState)) {
                            setTimeout(ab, 0);
                            return
                        }
                        f()
                    }())
                }
            }
        }();

    function f() {
        if (L || !document.getElementsByTagName("body")[0]) {
            return
        }
        try {
            var ac, ad = C("span");
            ad.style.display = "none";
            ac = h.getElementsByTagName("body")[0].appendChild(ad);
            ac.parentNode.removeChild(ac);
            ac = null;
            ad = null
        } catch (ae) {
            return
        }
        L = true;
        var aa = X.length;
        for (var ab = 0; ab < aa; ab++) {
            X[ab]()
        }
    }

    function M(aa) {
        if (L) {
            aa()
        } else {
            X[X.length] = aa
        }
    }

    function s(ab) {
        if (typeof Q.addEventListener != D) {
            Q.addEventListener("load", ab, false)
        } else {
            if (typeof h.addEventListener != D) {
                h.addEventListener("load", ab, false)
            } else {
                if (typeof Q.attachEvent != D) {
                    g(Q, "onload", ab)
                } else {
                    if (typeof Q.onload == "function") {
                        var aa = Q.onload;
                        Q.onload = function () {
                            aa();
                            ab()
                        }
                    } else {
                        Q.onload = ab
                    }
                }
            }
        }
    }

    function Y() {
        var aa = h.getElementsByTagName("body")[0];
        var ae = C(r);
        ae.setAttribute("style", "visibility: hidden;");
        ae.setAttribute("type", q);
        var ad = aa.appendChild(ae);
        if (ad) {
            var ac = 0;
            (function ab() {
                if (typeof ad.GetVariable != D) {
                    try {
                        var ag = ad.GetVariable("$version");
                        if (ag) {
                            ag = ag.split(" ")[1].split(",");
                            O.pv = [n(ag[0]), n(ag[1]), n(ag[2])]
                        }
                    } catch (af) {
                        O.pv = [8, 0, 0]
                    }
                } else {
                    if (ac < 10) {
                        ac++;
                        setTimeout(ab, 10);
                        return
                    }
                }
                aa.removeChild(ae);
                ad = null;
                H()
            }())
        } else {
            H()
        }
    }

    function H() {
        var aj = o.length;
        if (aj > 0) {
            for (var ai = 0; ai < aj; ai++) {
                var ab = o[ai].id;
                var ae = o[ai].callbackFn;
                var ad = {success: false, id: ab};
                if (O.pv[0] > 0) {
                    var ah = c(ab);
                    if (ah) {
                        if (F(o[ai].swfVersion) && !(O.wk && O.wk < 312)) {
                            w(ab, true);
                            if (ae) {
                                ad.success = true;
                                ad.ref = z(ab);
                                ad.id = ab;
                                ae(ad)
                            }
                        } else {
                            if (o[ai].expressInstall && A()) {
                                var al = {};
                                al.data = o[ai].expressInstall;
                                al.width = ah.getAttribute("width") || "0";
                                al.height = ah.getAttribute("height") || "0";
                                if (ah.getAttribute("class")) {
                                    al.styleclass = ah.getAttribute("class")
                                }
                                if (ah.getAttribute("align")) {
                                    al.align = ah.getAttribute("align")
                                }
                                var ak = {};
                                var aa = ah.getElementsByTagName("param");
                                var af = aa.length;
                                for (var ag = 0; ag < af; ag++) {
                                    if (aa[ag].getAttribute("name").toLowerCase() != "movie") {
                                        ak[aa[ag].getAttribute("name")] = aa[ag].getAttribute("value")
                                    }
                                }
                                R(al, ak, ab, ae)
                            } else {
                                b(ah);
                                if (ae) {
                                    ae(ad)
                                }
                            }
                        }
                    }
                } else {
                    w(ab, true);
                    if (ae) {
                        var ac = z(ab);
                        if (ac && typeof ac.SetVariable != D) {
                            ad.success = true;
                            ad.ref = ac;
                            ad.id = ac.id
                        }
                        ae(ad)
                    }
                }
            }
        }
    }

    X[0] = function () {
        if (V) {
            Y()
        } else {
            H()
        }
    };

    function z(ac) {
        var aa = null, ab = c(ac);
        if (ab && ab.nodeName.toUpperCase() === "OBJECT") {
            if (typeof ab.SetVariable !== D) {
                aa = ab
            } else {
                aa = ab.getElementsByTagName(r)[0] || ab
            }
        }
        return aa
    }

    function A() {
        return !a && F("6.0.65") && (O.win || O.mac) && !(O.wk && O.wk < 312)
    }

    function R(ad, ae, aa, ac) {
        var ah = c(aa);
        aa = W(aa);
        a = true;
        E = ac || null;
        B = {success: false, id: aa};
        if (ah) {
            if (ah.nodeName.toUpperCase() == "OBJECT") {
                I = J(ah);
                p = null
            } else {
                I = ah;
                p = aa
            }
            ad.id = S;
            if (typeof ad.width == D || (!/%$/.test(ad.width) && n(ad.width) < 310)) {
                ad.width = "310"
            }
            if (typeof ad.height == D || (!/%$/.test(ad.height) && n(ad.height) < 137)) {
                ad.height = "137"
            }
            var ag = O.ie ? "ActiveX" : "PlugIn",
                af = "MMredirectURL=" + encodeURIComponent(Q.location.toString().replace(/&/g, "%26")) + "&MMplayerType=" + ag + "&MMdoctitle=" + encodeURIComponent(h.title.slice(0, 47) + " - Flash Player Installation");
            if (typeof ae.flashvars != D) {
                ae.flashvars += "&" + af
            } else {
                ae.flashvars = af
            }
            if (O.ie && ah.readyState != 4) {
                var ab = C("div");
                aa += "SWFObjectNew";
                ab.setAttribute("id", aa);
                ah.parentNode.insertBefore(ab, ah);
                ah.style.display = "none";
                y(ah)
            }
            u(ad, ae, aa)
        }
    }

    function b(ab) {
        if (O.ie && ab.readyState != 4) {
            ab.style.display = "none";
            var aa = C("div");
            ab.parentNode.insertBefore(aa, ab);
            aa.parentNode.replaceChild(J(ab), aa);
            y(ab)
        } else {
            ab.parentNode.replaceChild(J(ab), ab)
        }
    }

    function J(af) {
        var ae = C("div");
        if (O.win && O.ie) {
            ae.innerHTML = af.innerHTML
        } else {
            var ab = af.getElementsByTagName(r)[0];
            if (ab) {
                var ag = ab.childNodes;
                if (ag) {
                    var aa = ag.length;
                    for (var ad = 0; ad < aa; ad++) {
                        if (!(ag[ad].nodeType == 1 && ag[ad].nodeName == "PARAM") && !(ag[ad].nodeType == 8)) {
                            ae.appendChild(ag[ad].cloneNode(true))
                        }
                    }
                }
            }
        }
        return ae
    }

    function k(aa, ab) {
        var ac = C("div");
        ac.innerHTML = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'><param name='movie' value='" + aa + "'>" + ab + "</object>";
        return ac.firstChild
    }

    function u(ai, ag, ab) {
        var aa, ad = c(ab);
        ab = W(ab);
        if (O.wk && O.wk < 312) {
            return aa
        }
        if (ad) {
            var ac = (O.ie) ? C("div") : C(r), af, ah, ae;
            if (typeof ai.id == D) {
                ai.id = ab
            }
            for (ae in ag) {
                if (ag.hasOwnProperty(ae) && ae.toLowerCase() !== "movie") {
                    e(ac, ae, ag[ae])
                }
            }
            if (O.ie) {
                ac = k(ai.data, ac.innerHTML)
            }
            for (af in ai) {
                if (ai.hasOwnProperty(af)) {
                    ah = af.toLowerCase();
                    if (ah === "styleclass") {
                        ac.setAttribute("class", ai[af])
                    } else {
                        if (ah !== "classid" && ah !== "data") {
                            ac.setAttribute(af, ai[af])
                        }
                    }
                }
            }
            if (O.ie) {
                P[P.length] = ai.id
            } else {
                ac.setAttribute("type", q);
                ac.setAttribute("data", ai.data)
            }
            ad.parentNode.replaceChild(ac, ad);
            aa = ac
        }
        return aa
    }

    function e(ac, aa, ab) {
        var ad = C("param");
        ad.setAttribute("name", aa);
        ad.setAttribute("value", ab);
        ac.appendChild(ad)
    }

    function y(ac) {
        var ab = c(ac);
        if (ab && ab.nodeName.toUpperCase() == "OBJECT") {
            if (O.ie) {
                ab.style.display = "none";
                (function aa() {
                    if (ab.readyState == 4) {
                        for (var ad in ab) {
                            if (typeof ab[ad] == "function") {
                                ab[ad] = null
                            }
                        }
                        ab.parentNode.removeChild(ab)
                    } else {
                        setTimeout(aa, 10)
                    }
                }())
            } else {
                ab.parentNode.removeChild(ab)
            }
        }
    }

    function U(aa) {
        return (aa && aa.nodeType && aa.nodeType === 1)
    }

    function W(aa) {
        return (U(aa)) ? aa.id : aa
    }

    function c(ac) {
        if (U(ac)) {
            return ac
        }
        var aa = null;
        try {
            aa = h.getElementById(ac)
        } catch (ab) {
        }
        return aa
    }

    function C(aa) {
        return h.createElement(aa)
    }

    function n(aa) {
        return parseInt(aa, 10)
    }

    function g(ac, aa, ab) {
        ac.attachEvent(aa, ab);
        K[K.length] = [ac, aa, ab]
    }

    function F(ac) {
        ac += "";
        var ab = O.pv, aa = ac.split(".");
        aa[0] = n(aa[0]);
        aa[1] = n(aa[1]) || 0;
        aa[2] = n(aa[2]) || 0;
        return (ab[0] > aa[0] || (ab[0] == aa[0] && ab[1] > aa[1]) || (ab[0] == aa[0] && ab[1] == aa[1] && ab[2] >= aa[2])) ? true : false
    }

    function v(af, ab, ag, ae) {
        var ad = h.getElementsByTagName("head")[0];
        if (!ad) {
            return
        }
        var aa = (typeof ag == "string") ? ag : "screen";
        if (ae) {
            m = null;
            G = null
        }
        if (!m || G != aa) {
            var ac = C("style");
            ac.setAttribute("type", "text/css");
            ac.setAttribute("media", aa);
            m = ad.appendChild(ac);
            if (O.ie && typeof h.styleSheets != D && h.styleSheets.length > 0) {
                m = h.styleSheets[h.styleSheets.length - 1]
            }
            G = aa
        }
        if (m) {
            if (typeof m.addRule != D) {
                m.addRule(af, ab)
            } else {
                if (typeof h.createTextNode != D) {
                    m.appendChild(h.createTextNode(af + " {" + ab + "}"))
                }
            }
        }
    }

    function w(ad, aa) {
        if (!j) {
            return
        }
        var ab = aa ? "visible" : "hidden", ac = c(ad);
        if (L && ac) {
            ac.style.visibility = ab
        } else {
            if (typeof ad === "string") {
                v("#" + ad, "visibility:" + ab)
            }
        }
    }

    function N(ab) {
        var ac = /[\\\"<>\.;]/;
        var aa = ac.exec(ab) != null;
        return aa && typeof encodeURIComponent != D ? encodeURIComponent(ab) : ab
    }

    var d = function () {
        if (O.ie) {
            window.attachEvent("onunload", function () {
                var af = K.length;
                for (var ae = 0; ae < af; ae++) {
                    K[ae][0].detachEvent(K[ae][1], K[ae][2])
                }
                var ac = P.length;
                for (var ad = 0; ad < ac; ad++) {
                    y(P[ad])
                }
                for (var ab in O) {
                    O[ab] = null
                }
                O = null;
                for (var aa in swfobject) {
                    swfobject[aa] = null
                }
                swfobject = null
            })
        }
    }();
    return {
        registerObject: function (ae, aa, ad, ac) {
            if (O.w3 && ae && aa) {
                var ab = {};
                ab.id = ae;
                ab.swfVersion = aa;
                ab.expressInstall = ad;
                ab.callbackFn = ac;
                o[o.length] = ab;
                w(ae, false)
            } else {
                if (ac) {
                    ac({success: false, id: ae})
                }
            }
        }, getObjectById: function (aa) {
            if (O.w3) {
                return z(aa)
            }
        }, embedSWF: function (af, al, ai, ak, ab, ae, ad, ah, aj, ag) {
            var ac = W(al), aa = {success: false, id: ac};
            if (O.w3 && !(O.wk && O.wk < 312) && af && al && ai && ak && ab) {
                w(ac, false);
                M(function () {
                    ai += "";
                    ak += "";
                    var an = {};
                    if (aj && typeof aj === r) {
                        for (var aq in aj) {
                            an[aq] = aj[aq]
                        }
                    }
                    an.data = af;
                    an.width = ai;
                    an.height = ak;
                    var ar = {};
                    if (ah && typeof ah === r) {
                        for (var ao in ah) {
                            ar[ao] = ah[ao]
                        }
                    }
                    if (ad && typeof ad === r) {
                        for (var am in ad) {
                            if (ad.hasOwnProperty(am)) {
                                var ap = (l) ? encodeURIComponent(am) : am,
                                    at = (l) ? encodeURIComponent(ad[am]) : ad[am];
                                if (typeof ar.flashvars != D) {
                                    ar.flashvars += "&" + ap + "=" + at
                                } else {
                                    ar.flashvars = ap + "=" + at
                                }
                            }
                        }
                    }
                    if (F(ab)) {
                        var au = u(an, ar, al);
                        if (an.id == ac) {
                            w(ac, true)
                        }
                        aa.success = true;
                        aa.ref = au;
                        aa.id = au.id
                    } else {
                        if (ae && A()) {
                            an.data = ae;
                            R(an, ar, al, ag);
                            return
                        } else {
                            w(ac, true)
                        }
                    }
                    if (ag) {
                        ag(aa)
                    }
                })
            } else {
                if (ag) {
                    ag(aa)
                }
            }
        }, switchOffAutoHideShow: function () {
            j = false
        }, enableUriEncoding: function (aa) {
            l = (typeof aa === D) ? true : aa
        }, ua: O, getFlashPlayerVersion: function () {
            return {major: O.pv[0], minor: O.pv[1], release: O.pv[2]}
        }, hasFlashPlayerVersion: F, createSWF: function (ac, ab, aa) {
            if (O.w3) {
                return u(ac, ab, aa)
            } else {
                return undefined
            }
        }, showExpressInstall: function (ac, ad, aa, ab) {
            if (O.w3 && A()) {
                R(ac, ad, aa, ab)
            }
        }, removeSWF: function (aa) {
            if (O.w3) {
                y(aa)
            }
        }, createCSS: function (ad, ac, ab, aa) {
            if (O.w3) {
                v(ad, ac, ab, aa)
            }
        }, addDomLoadEvent: M, addLoadEvent: s, getQueryParamValue: function (ad) {
            var ac = h.location.search || h.location.hash;
            if (ac) {
                if (/\?/.test(ac)) {
                    ac = ac.split("?")[1]
                }
                if (ad == null) {
                    return N(ac)
                }
                var ab = ac.split("&");
                for (var aa = 0; aa < ab.length; aa++) {
                    if (ab[aa].substring(0, ab[aa].indexOf("=")) == ad) {
                        return N(ab[aa].substring((ab[aa].indexOf("=") + 1)))
                    }
                }
            }
            return ""
        }, expressInstallCallback: function () {
            if (a) {
                var aa = c(S);
                if (aa && I) {
                    aa.parentNode.replaceChild(I, aa);
                    if (p) {
                        w(p, true);
                        if (O.ie) {
                            I.style.display = "block"
                        }
                    }
                    if (E) {
                        E(B)
                    }
                }
                a = false
            }
        }, version: "2.3"
    }
}();

/**
 * loadCSS
 * 1.3.1
 * https://github.com/filamentgroup/loadCSS
 */
(function (w) {
    var loadCSS = function (href, before, media) {
        var doc = w.document;
        var ss = doc.createElement("link");
        var ref;
        if (before) {
            ref = before
        } else {
            var refs = (doc.body || doc.getElementsByTagName("head")[0]).childNodes;
            ref = refs[refs.length - 1]
        }
        var sheets = doc.styleSheets;
        ss.rel = "stylesheet";
        ss.href = href;
        ss.media = "only x";

        function ready(cb) {
            if (doc.body) {
                return cb()
            }
            setTimeout(function () {
                ready(cb)
            })
        }

        ready(function () {
            ref.parentNode.insertBefore(ss, (before ? ref : ref.nextSibling))
        });
        var onloadcssdefined = function (cb) {
            var resolvedHref = ss.href;
            var i = sheets.length;
            while (i--) {
                if (sheets[i].href === resolvedHref) {
                    return cb()
                }
            }
            setTimeout(function () {
                onloadcssdefined(cb)
            })
        };

        function loadCB() {
            if (ss.addEventListener) {
                ss.removeEventListener("load", loadCB)
            }
            ss.media = media || "all"
        }

        if (ss.addEventListener) {
            ss.addEventListener("load", loadCB)
        }
        ss.onloadcssdefined = onloadcssdefined;
        onloadcssdefined(loadCB);
        return ss
    };
    if (typeof exports !== "undefined") {
        exports.loadCSS = loadCSS
    } else {
        w.loadCSS = loadCSS
    }
}(typeof global !== "undefined" ? global : this));

function onloadCSS(ss, callback) {
    var called;

    function newcb() {
        if (!called && callback) {
            called = true;
            callback.call(ss)
        }
    }

    if (ss.addEventListener) {
        ss.addEventListener("load", newcb)
    }
    if (ss.attachEvent) {
        ss.attachEvent("onload", newcb)
    }
    if ("isApplicationInstalled" in navigator && "onloadcssdefined" in ss) {
        ss.onloadcssdefined(newcb)
    }
};

/**
 * jquery-json
 * 2.6.0
 * https://github.com/Krinkle/jquery-json
 */
!function (a) {
    "function" == typeof define && define.amd ? define(["jquery"], a) : a("object" == typeof exports ? require("jquery") : jQuery)
}(function ($) {
    "use strict";
    var escape = /["\\\x00-\x1f\x7f-\x9f]/g,
        meta = {"\b": "\\b", "\t": "\\t", "\n": "\\n", "\f": "\\f", "\r": "\\r", '"': '\\"', "\\": "\\\\"},
        hasOwn = Object.prototype.hasOwnProperty;
    $.toJSON = "object" == typeof JSON && JSON.stringify ? JSON.stringify : function (a) {
        if (null === a) return "null";
        var b, c, d, e, f = $.type(a);
        if ("undefined" !== f) {
            if ("number" === f || "boolean" === f) return String(a);
            if ("string" === f) return $.quoteString(a);
            if ("function" == typeof a.toJSON) return $.toJSON(a.toJSON());
            if ("date" === f) {
                var g = a.getUTCMonth() + 1, h = a.getUTCDate(), i = a.getUTCFullYear(), j = a.getUTCHours(),
                    k = a.getUTCMinutes(), l = a.getUTCSeconds(), m = a.getUTCMilliseconds();
                return g < 10 && (g = "0" + g), h < 10 && (h = "0" + h), j < 10 && (j = "0" + j), k < 10 && (k = "0" + k), l < 10 && (l = "0" + l), m < 100 && (m = "0" + m), m < 10 && (m = "0" + m), '"' + i + "-" + g + "-" + h + "T" + j + ":" + k + ":" + l + "." + m + 'Z"'
            }
            if (b = [], $.isArray(a)) {
                for (c = 0; c < a.length; c++) b.push($.toJSON(a[c]) || "null");
                return "[" + b.join(",") + "]"
            }
            if ("object" == typeof a) {
                for (c in a) if (hasOwn.call(a, c)) {
                    if (f = typeof c, "number" === f) d = '"' + c + '"'; else {
                        if ("string" !== f) continue;
                        d = $.quoteString(c)
                    }
                    f = typeof a[c], "function" !== f && "undefined" !== f && (e = $.toJSON(a[c]), b.push(d + ":" + e))
                }
                return "{" + b.join(",") + "}"
            }
        }
    }, $.evalJSON = "object" == typeof JSON && JSON.parse ? JSON.parse : function (str) {
        return eval("(" + str + ")")
    }, $.secureEvalJSON = "object" == typeof JSON && JSON.parse ? JSON.parse : function (str) {
        var filtered = str.replace(/\\["\\\/bfnrtu]/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]").replace(/(?:^|:|,)(?:\s*\[)+/g, "");
        if (/^[\],:{}\s]*$/.test(filtered)) return eval("(" + str + ")");
        throw new SyntaxError("Error parsing JSON, source is not valid.")
    }, $.quoteString = function (a) {
        return a.match(escape) ? '"' + a.replace(escape, function (a) {
            var b = meta[a];
            return "string" == typeof b ? b : (b = a.charCodeAt(), "\\u00" + Math.floor(b / 16).toString(16) + (b % 16).toString(16))
        }) + '"' : '"' + a + '"'
    }
});

/**
 * jquery-zclip
 * 1.1.5
 * https://github.com/patricklodder/jquery-zclip
 */
(function (jQuery) {
    jQuery.fn.zclip = function (params) {
        if (typeof params == "object" && !params.length) {
            var settings = jQuery.extend({}, ZeroClipboard.defaults, params);
            return this.each(function () {
                var o = jQuery(this);
                if (o.is(':visible') && (typeof settings.copy == 'string' || jQuery.isFunction(settings.copy))) {
                    ZeroClipboard.setMoviePath(settings.path);
                    var clip = new ZeroClipboard.Client();
                    if (jQuery.isFunction(settings.copy)) {
                        o.bind('zClip_copy', settings.copy)
                    }
                    if (jQuery.isFunction(settings.beforeCopy)) {
                        o.bind('zClip_beforeCopy', settings.beforeCopy)
                    }
                    if (jQuery.isFunction(settings.afterCopy)) {
                        o.bind('zClip_afterCopy', settings.afterCopy)
                    }
                    clip.setHandCursor(settings.setHandCursor);
                    clip.setCSSEffects(settings.setCSSEffects);
                    clip.addEventListener('mouseOver', function (client) {
                        o.trigger('mouseenter')
                    });
                    clip.addEventListener('mouseOut', function (client) {
                        o.trigger('mouseleave')
                    });
                    clip.addEventListener('mouseDown', function (client) {
                        o.trigger('mousedown');
                        if (jQuery.isFunction(settings.beforeCopy)) {
                            o.trigger('zClip_beforeCopy')
                        }
                        if (!jQuery.isFunction(settings.copy)) {
                            clip.setText(settings.copy)
                        } else {
                            clip.setText(o.triggerHandler('zClip_copy'))
                        }
                    });
                    clip.addEventListener('complete', function (client, text) {
                        if (jQuery.isFunction(settings.afterCopy)) {
                            o.trigger('zClip_afterCopy')
                        } else {
                            if (text.length > 500) {
                                text = text.substr(0, 500) + "...\n\n(" + (text.length - 500) + " characters not shown)"
                            }
                            o.removeClass('hover');
                            alert("Copied text to clipboard:\n\n " + text)
                        }
                        if (settings.clickAfter) {
                            o.trigger('click')
                        }
                    });
                    clip.glue(o[0], o.parent()[0]);
                    jQuery(window).bind('load resize', function () {
                        clip.reposition()
                    })
                }
            })
        } else if (typeof params == "string") {
            return this.each(function () {
                var o = jQuery(this);
                params = params.toLowerCase();
                var zclipId = o.data('zclipId');
                var clipElm = jQuery('#' + zclipId + '.zclip');
                var clientId = clipElm.attr('id').replace(/^.*_/g, '') || null;
                if (params == "remove") {
                    clipElm.remove();
                    o.removeClass('active hover');
                    o.unbind('zClip_copy');
                    o.unbind('zClip_beforeCopy');
                    o.unbind('zClip_afterCopy');
                    ZeroClipboard.unregister(clientId)
                } else if (params == "hide") {
                    clipElm.hide();
                    o.removeClass('active hover')
                } else if (params == "show") {
                    clipElm.show()
                }
            })
        }
    }
})(jQuery);
var ZeroClipboard = {
    version: "1.0.7",
    clients: {},
    moviePath: 'ZeroClipboard.swf',
    nextId: 1,
    defaults: {
        path: 'ZeroClipboard.swf',
        clickAfter: true,
        setHandCursor: true,
        setCSSEffects: true,
        copy: null,
        beforeCopy: null,
        afterCopy: null
    },
    jQuery: function (thingy) {
        if (typeof (thingy) == 'string') thingy = document.getElementById(thingy);
        if (!thingy.addClass) {
            thingy.hide = function () {
                this.style.display = 'none'
            };
            thingy.show = function () {
                this.style.display = ''
            };
            thingy.addClass = function (name) {
                this.removeClass(name);
                this.className += ' ' + name
            };
            thingy.removeClass = function (name) {
                var classes = this.className.split(/\s+/);
                var idx = -1;
                for (var k = 0; k < classes.length; k++) {
                    if (classes[k] == name) {
                        idx = k;
                        k = classes.length
                    }
                }
                if (idx > -1) {
                    classes.splice(idx, 1);
                    this.className = classes.join(' ')
                }
                return this
            };
            thingy.hasClass = function (name) {
                return !!this.className.match(new RegExp("\\s*" + name + "\\s*"))
            }
        }
        return thingy
    },
    setMoviePath: function (path) {
        this.moviePath = path
    },
    dispatch: function (id, eventName, args) {
        var client = this.clients[id];
        if (client) {
            client.receiveEvent(eventName, args)
        }
    },
    register: function (id, client) {
        this.clients[id] = client
    },
    unregister: function (id) {
        if (typeof (id) === 'number' && this.clients.hasOwnProperty(id)) {
            delete this.clients[id]
        }
    },
    getDOMObjectPosition: function (obj, stopObj) {
        var info = {
            left: 0,
            top: 0,
            width: obj.width ? obj.width : obj.offsetWidth,
            height: obj.height ? obj.height : obj.offsetHeight
        };
        if (obj && (obj != stopObj)) {
            info.left += obj.offsetLeft;
            info.top += obj.offsetTop
        }
        return info
    },
    Client: function (elem) {
        this.handlers = {};
        this.id = ZeroClipboard.nextId++;
        this.movieId = 'ZeroClipboardMovie_' + this.id;
        ZeroClipboard.register(this.id, this);
        if (elem) this.glue(elem)
    }
};
ZeroClipboard.Client.prototype = {
    id: 0,
    ready: false,
    movie: null,
    clipText: '',
    handCursorEnabled: true,
    cssEffects: true,
    handlers: null,
    glue: function (elem, appendElem, stylesToAdd) {
        this.domElement = ZeroClipboard.jQuery(elem);
        var zIndex = 99;
        if (this.domElement.style.zIndex) {
            zIndex = parseInt(this.domElement.style.zIndex, 10) + 1
        }
        if (typeof (appendElem) == 'string') {
            appendElem = ZeroClipboard.jQuery(appendElem)
        } else if (typeof (appendElem) == 'undefined') {
            appendElem = document.getElementsByTagName('body')[0]
        }
        var box = ZeroClipboard.getDOMObjectPosition(this.domElement, appendElem);
        this.div = document.createElement('div');
        this.div.className = "zclip";
        this.div.id = "zclip-" + this.movieId;
        jQuery(this.domElement).data('zclipId', 'zclip-' + this.movieId);
        var style = this.div.style;
        style.position = 'absolute';
        style.left = '' + box.left + 'px';
        style.top = '' + box.top + 'px';
        style.width = '' + box.width + 'px';
        style.height = '' + box.height + 'px';
        style.zIndex = zIndex;
        if (typeof (stylesToAdd) == 'object') {
            for (var addedStyle in stylesToAdd) {
                style[addedStyle] = stylesToAdd[addedStyle]
            }
        }
        appendElem.appendChild(this.div);
        this.div.innerHTML = this.getHTML(box.width, box.height)
    },
    getHTML: function (width, height) {
        var html = '';
        var flashvars = 'id=' + this.id + '&width=' + width + '&height=' + height;
        if (navigator.userAgent.match(/MSIE/)) {
            var protocol = location.href.match(/^https/i) ? 'https://' : 'http://';
            html += '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="' + protocol + 'download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0" width="' + width + '" height="' + height + '" id="' + this.movieId + '" align="middle"><param name="allowScriptAccess" value="always" /><param name="allowFullScreen" value="false" /><param name="movie" value="' + ZeroClipboard.moviePath + '" /><param name="loop" value="false" /><param name="menu" value="false" /><param name="quality" value="best" /><param name="bgcolor" value="#ffffff" /><param name="flashvars" value="' + flashvars + '"/><param name="wmode" value="transparent"/></object>'
        } else {
            html += '<embed id="' + this.movieId + '" src="' + ZeroClipboard.moviePath + '" loop="false" menu="false" quality="best" bgcolor="#ffffff" width="' + width + '" height="' + height + '" name="' + this.movieId + '" align="middle" allowScriptAccess="always" allowFullScreen="false" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" flashvars="' + flashvars + '" wmode="transparent" />'
        }
        return html
    },
    hide: function () {
        if (this.div) {
            this.div.style.left = '-2000px'
        }
    },
    show: function () {
        this.reposition()
    },
    destroy: function () {
        if (this.domElement && this.div) {
            this.hide();
            this.div.innerHTML = '';
            var body = document.getElementsByTagName('body')[0];
            try {
                body.removeChild(this.div)
            } catch (e) {
            }
            this.domElement = null;
            this.div = null
        }
    },
    reposition: function (elem) {
        if (elem) {
            this.domElement = ZeroClipboard.jQuery(elem);
            if (!this.domElement) this.hide()
        }
        if (this.domElement && this.div) {
            var box = ZeroClipboard.getDOMObjectPosition(this.domElement);
            var style = this.div.style;
            style.left = '' + box.left + 'px';
            style.top = '' + box.top + 'px'
        }
    },
    setText: function (newText) {
        this.clipText = newText;
        if (this.ready) {
            this.movie.setText(newText)
        }
    },
    addEventListener: function (eventName, func) {
        eventName = eventName.toString().toLowerCase().replace(/^on/, '');
        if (!this.handlers[eventName]) {
            this.handlers[eventName] = []
        }
        this.handlers[eventName].push(func)
    },
    setHandCursor: function (enabled) {
        this.handCursorEnabled = enabled;
        if (this.ready) {
            this.movie.setHandCursor(enabled)
        }
    },
    setCSSEffects: function (enabled) {
        this.cssEffects = !!enabled
    },
    receiveEvent: function (eventName, args) {
        eventName = eventName.toString().toLowerCase().replace(/^on/, '');
        switch (eventName) {
            case'load':
                this.movie = document.getElementById(this.movieId);
                var self = this;
                if (!this.movie) {
                    setTimeout(function () {
                        self.receiveEvent('load', null)
                    }, 1);
                    return
                }
                if (!this.ready && navigator.userAgent.match(/Firefox/) && navigator.userAgent.match(/Windows/)) {
                    setTimeout(function () {
                        self.receiveEvent('load', null)
                    }, 100);
                    this.ready = true;
                    return
                }
                this.ready = true;
                try {
                    this.movie.setText(this.clipText)
                } catch (e) {
                }
                try {
                    this.movie.setHandCursor(this.handCursorEnabled)
                } catch (e) {
                }
                break;
            case'mouseover':
                if (this.domElement && this.cssEffects) {
                    this.domElement.addClass('hover');
                    if (this.recoverActive) {
                        this.domElement.addClass('active')
                    }
                }
                break;
            case'mouseout':
                if (this.domElement && this.cssEffects) {
                    this.recoverActive = false;
                    if (this.domElement.hasClass('active')) {
                        this.domElement.removeClass('active');
                        this.recoverActive = true
                    }
                    this.domElement.removeClass('hover')
                }
                break;
            case'mousedown':
                if (this.domElement && this.cssEffects) {
                    this.domElement.addClass('active')
                }
                break;
            case'mouseup':
                if (this.domElement && this.cssEffects) {
                    this.domElement.removeClass('active');
                    this.recoverActive = false
                }
                break
        }
        if (this.handlers[eventName]) {
            for (var idx = 0, len = this.handlers[eventName].length; idx < len; idx++) {
                var func = this.handlers[eventName][idx];
                if (jQuery.isFunction(func)) {
                    func(this, args)
                } else if ((typeof (func) == 'object') && (func.length == 2)) {
                    func[0][func[1]](this, args)
                } else if (typeof (func) == 'string') {
                    window[func](this, args)
                }
            }
        }
    }
};

/**
 * jQuery Easing
 * 1.4.1
 * https://github.com/gdsmith/jquery.easing
 * t: current time, b: begInnIng value, c: change In value, d: duration
 */
(function (factory) {
    if (typeof define === "function" && define.amd) {
        define(["jquery"], function ($) {
            return factory($)
        })
    } else if (typeof module === "object" && typeof module.exports === "object") {
        exports = factory(require("jquery"))
    } else {
        factory(jQuery)
    }
})(function ($) {
    $.easing.jswing = $.easing.swing;
    var pow = Math.pow, sqrt = Math.sqrt, sin = Math.sin, cos = Math.cos, PI = Math.PI, c1 = 1.70158, c2 = c1 * 1.525,
        c3 = c1 + 1, c4 = 2 * PI / 3, c5 = 2 * PI / 4.5;

    function bounceOut(x) {
        var n1 = 7.5625, d1 = 2.75;
        if (x < 1 / d1) {
            return n1 * x * x
        } else if (x < 2 / d1) {
            return n1 * (x -= 1.5 / d1) * x + .75
        } else if (x < 2.5 / d1) {
            return n1 * (x -= 2.25 / d1) * x + .9375
        } else {
            return n1 * (x -= 2.625 / d1) * x + .984375
        }
    }

    $.extend($.easing, {
        def: "easeOutQuad", swing: function (x) {
            return $.easing[$.easing.def](x)
        }, easeInQuad: function (x) {
            return x * x
        }, easeOutQuad: function (x) {
            return 1 - (1 - x) * (1 - x)
        }, easeInOutQuad: function (x) {
            return x < .5 ? 2 * x * x : 1 - pow(-2 * x + 2, 2) / 2
        }, easeInCubic: function (x) {
            return x * x * x
        }, easeOutCubic: function (x) {
            return 1 - pow(1 - x, 3)
        }, easeInOutCubic: function (x) {
            return x < .5 ? 4 * x * x * x : 1 - pow(-2 * x + 2, 3) / 2
        }, easeInQuart: function (x) {
            return x * x * x * x
        }, easeOutQuart: function (x) {
            return 1 - pow(1 - x, 4)
        }, easeInOutQuart: function (x) {
            return x < .5 ? 8 * x * x * x * x : 1 - pow(-2 * x + 2, 4) / 2
        }, easeInQuint: function (x) {
            return x * x * x * x * x
        }, easeOutQuint: function (x) {
            return 1 - pow(1 - x, 5)
        }, easeInOutQuint: function (x) {
            return x < .5 ? 16 * x * x * x * x * x : 1 - pow(-2 * x + 2, 5) / 2
        }, easeInSine: function (x) {
            return 1 - cos(x * PI / 2)
        }, easeOutSine: function (x) {
            return sin(x * PI / 2)
        }, easeInOutSine: function (x) {
            return -(cos(PI * x) - 1) / 2
        }, easeInExpo: function (x) {
            return x === 0 ? 0 : pow(2, 10 * x - 10)
        }, easeOutExpo: function (x) {
            return x === 1 ? 1 : 1 - pow(2, -10 * x)
        }, easeInOutExpo: function (x) {
            return x === 0 ? 0 : x === 1 ? 1 : x < .5 ? pow(2, 20 * x - 10) / 2 : (2 - pow(2, -20 * x + 10)) / 2
        }, easeInCirc: function (x) {
            return 1 - sqrt(1 - pow(x, 2))
        }, easeOutCirc: function (x) {
            return sqrt(1 - pow(x - 1, 2))
        }, easeInOutCirc: function (x) {
            return x < .5 ? (1 - sqrt(1 - pow(2 * x, 2))) / 2 : (sqrt(1 - pow(-2 * x + 2, 2)) + 1) / 2
        }, easeInElastic: function (x) {
            return x === 0 ? 0 : x === 1 ? 1 : -pow(2, 10 * x - 10) * sin((x * 10 - 10.75) * c4)
        }, easeOutElastic: function (x) {
            return x === 0 ? 0 : x === 1 ? 1 : pow(2, -10 * x) * sin((x * 10 - .75) * c4) + 1
        }, easeInOutElastic: function (x) {
            return x === 0 ? 0 : x === 1 ? 1 : x < .5 ? -(pow(2, 20 * x - 10) * sin((20 * x - 11.125) * c5)) / 2 : pow(2, -20 * x + 10) * sin((20 * x - 11.125) * c5) / 2 + 1
        }, easeInBack: function (x) {
            return c3 * x * x * x - c1 * x * x
        }, easeOutBack: function (x) {
            return 1 + c3 * pow(x - 1, 3) + c1 * pow(x - 1, 2)
        }, easeInOutBack: function (x) {
            return x < .5 ? pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2) / 2 : (pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2
        }, easeInBounce: function (x) {
            return 1 - bounceOut(1 - x)
        }, easeOutBounce: bounceOut, easeInOutBounce: function (x) {
            return x < .5 ? (1 - bounceOut(1 - 2 * x)) / 2 : (1 + bounceOut(2 * x - 1)) / 2
        }
    })
});

/**
 * jQuery Transit
 * 0.9.12
 * https://github.com/rstacruz/jquery.transit
 */
(function (t, e) {
    if (typeof define === "function" && define.amd) {
        define(["jquery"], e)
    } else if (typeof exports === "object") {
        module.exports = e(require("jquery"))
    } else {
        e(t.jQuery)
    }
})(this, function (t) {
    t.transit = {
        version: "0.9.12",
        propertyMap: {
            marginLeft: "margin",
            marginRight: "margin",
            marginBottom: "margin",
            marginTop: "margin",
            paddingLeft: "padding",
            paddingRight: "padding",
            paddingBottom: "padding",
            paddingTop: "padding"
        },
        enabled: true,
        useTransitionEnd: false
    };
    var e = document.createElement("div");
    var n = {};

    function i(t) {
        if (t in e.style) return t;
        var n = ["Moz", "Webkit", "O", "ms"];
        var i = t.charAt(0).toUpperCase() + t.substr(1);
        for (var r = 0; r < n.length; ++r) {
            var s = n[r] + i;
            if (s in e.style) {
                return s
            }
        }
    }

    function r() {
        e.style[n.transform] = "";
        e.style[n.transform] = "rotateY(90deg)";
        return e.style[n.transform] !== ""
    }

    var s = navigator.userAgent.toLowerCase().indexOf("chrome") > -1;
    n.transition = i("transition");
    n.transitionDelay = i("transitionDelay");
    n.transform = i("transform");
    n.transformOrigin = i("transformOrigin");
    n.filter = i("Filter");
    n.transform3d = r();
    var a = {
        transition: "transitionend",
        MozTransition: "transitionend",
        OTransition: "oTransitionEnd",
        WebkitTransition: "webkitTransitionEnd",
        msTransition: "MSTransitionEnd"
    };
    var o = n.transitionEnd = a[n.transition] || null;
    for (var u in n) {
        if (n.hasOwnProperty(u) && typeof t.support[u] === "undefined") {
            t.support[u] = n[u]
        }
    }
    e = null;
    t.cssEase = {
        _default: "ease",
        "in": "ease-in",
        out: "ease-out",
        "in-out": "ease-in-out",
        snap: "cubic-bezier(0,1,.5,1)",
        easeInCubic: "cubic-bezier(.550,.055,.675,.190)",
        easeOutCubic: "cubic-bezier(.215,.61,.355,1)",
        easeInOutCubic: "cubic-bezier(.645,.045,.355,1)",
        easeInCirc: "cubic-bezier(.6,.04,.98,.335)",
        easeOutCirc: "cubic-bezier(.075,.82,.165,1)",
        easeInOutCirc: "cubic-bezier(.785,.135,.15,.86)",
        easeInExpo: "cubic-bezier(.95,.05,.795,.035)",
        easeOutExpo: "cubic-bezier(.19,1,.22,1)",
        easeInOutExpo: "cubic-bezier(1,0,0,1)",
        easeInQuad: "cubic-bezier(.55,.085,.68,.53)",
        easeOutQuad: "cubic-bezier(.25,.46,.45,.94)",
        easeInOutQuad: "cubic-bezier(.455,.03,.515,.955)",
        easeInQuart: "cubic-bezier(.895,.03,.685,.22)",
        easeOutQuart: "cubic-bezier(.165,.84,.44,1)",
        easeInOutQuart: "cubic-bezier(.77,0,.175,1)",
        easeInQuint: "cubic-bezier(.755,.05,.855,.06)",
        easeOutQuint: "cubic-bezier(.23,1,.32,1)",
        easeInOutQuint: "cubic-bezier(.86,0,.07,1)",
        easeInSine: "cubic-bezier(.47,0,.745,.715)",
        easeOutSine: "cubic-bezier(.39,.575,.565,1)",
        easeInOutSine: "cubic-bezier(.445,.05,.55,.95)",
        easeInBack: "cubic-bezier(.6,-.28,.735,.045)",
        easeOutBack: "cubic-bezier(.175, .885,.32,1.275)",
        easeInOutBack: "cubic-bezier(.68,-.55,.265,1.55)"
    };
    t.cssHooks["transit:transform"] = {
        get: function (e) {
            return t(e).data("transform") || new f
        }, set: function (e, i) {
            var r = i;
            if (!(r instanceof f)) {
                r = new f(r)
            }
            if (n.transform === "WebkitTransform" && !s) {
                e.style[n.transform] = r.toString(true)
            } else {
                e.style[n.transform] = r.toString()
            }
            t(e).data("transform", r)
        }
    };
    t.cssHooks.transform = {set: t.cssHooks["transit:transform"].set};
    t.cssHooks.filter = {
        get: function (t) {
            return t.style[n.filter]
        }, set: function (t, e) {
            t.style[n.filter] = e
        }
    };
    if (t.fn.jquery < "1.8") {
        t.cssHooks.transformOrigin = {
            get: function (t) {
                return t.style[n.transformOrigin]
            }, set: function (t, e) {
                t.style[n.transformOrigin] = e
            }
        };
        t.cssHooks.transition = {
            get: function (t) {
                return t.style[n.transition]
            }, set: function (t, e) {
                t.style[n.transition] = e
            }
        }
    }
    p("scale");
    p("scaleX");
    p("scaleY");
    p("translate");
    p("rotate");
    p("rotateX");
    p("rotateY");
    p("rotate3d");
    p("perspective");
    p("skewX");
    p("skewY");
    p("x", true);
    p("y", true);

    function f(t) {
        if (typeof t === "string") {
            this.parse(t)
        }
        return this
    }

    f.prototype = {
        setFromString: function (t, e) {
            var n = typeof e === "string" ? e.split(",") : e.constructor === Array ? e : [e];
            n.unshift(t);
            f.prototype.set.apply(this, n)
        }, set: function (t) {
            var e = Array.prototype.slice.apply(arguments, [1]);
            if (this.setter[t]) {
                this.setter[t].apply(this, e)
            } else {
                this[t] = e.join(",")
            }
        }, get: function (t) {
            if (this.getter[t]) {
                return this.getter[t].apply(this)
            } else {
                return this[t] || 0
            }
        }, setter: {
            rotate: function (t) {
                this.rotate = b(t, "deg")
            }, rotateX: function (t) {
                this.rotateX = b(t, "deg")
            }, rotateY: function (t) {
                this.rotateY = b(t, "deg")
            }, scale: function (t, e) {
                if (e === undefined) {
                    e = t
                }
                this.scale = t + "," + e
            }, skewX: function (t) {
                this.skewX = b(t, "deg")
            }, skewY: function (t) {
                this.skewY = b(t, "deg")
            }, perspective: function (t) {
                this.perspective = b(t, "px")
            }, x: function (t) {
                this.set("translate", t, null)
            }, y: function (t) {
                this.set("translate", null, t)
            }, translate: function (t, e) {
                if (this._translateX === undefined) {
                    this._translateX = 0
                }
                if (this._translateY === undefined) {
                    this._translateY = 0
                }
                if (t !== null && t !== undefined) {
                    this._translateX = b(t, "px")
                }
                if (e !== null && e !== undefined) {
                    this._translateY = b(e, "px")
                }
                this.translate = this._translateX + "," + this._translateY
            }
        }, getter: {
            x: function () {
                return this._translateX || 0
            }, y: function () {
                return this._translateY || 0
            }, scale: function () {
                var t = (this.scale || "1,1").split(",");
                if (t[0]) {
                    t[0] = parseFloat(t[0])
                }
                if (t[1]) {
                    t[1] = parseFloat(t[1])
                }
                return t[0] === t[1] ? t[0] : t
            }, rotate3d: function () {
                var t = (this.rotate3d || "0,0,0,0deg").split(",");
                for (var e = 0; e <= 3; ++e) {
                    if (t[e]) {
                        t[e] = parseFloat(t[e])
                    }
                }
                if (t[3]) {
                    t[3] = b(t[3], "deg")
                }
                return t
            }
        }, parse: function (t) {
            var e = this;
            t.replace(/([a-zA-Z0-9]+)\((.*?)\)/g, function (t, n, i) {
                e.setFromString(n, i)
            })
        }, toString: function (t) {
            var e = [];
            for (var i in this) {
                if (this.hasOwnProperty(i)) {
                    if (!n.transform3d && (i === "rotateX" || i === "rotateY" || i === "perspective" || i === "transformOrigin")) {
                        continue
                    }
                    if (i[0] !== "_") {
                        if (t && i === "scale") {
                            e.push(i + "3d(" + this[i] + ",1)")
                        } else if (t && i === "translate") {
                            e.push(i + "3d(" + this[i] + ",0)")
                        } else {
                            e.push(i + "(" + this[i] + ")")
                        }
                    }
                }
            }
            return e.join(" ")
        }
    };

    function c(t, e, n) {
        if (e === true) {
            t.queue(n)
        } else if (e) {
            t.queue(e, n)
        } else {
            t.each(function () {
                n.call(this)
            })
        }
    }

    function l(e) {
        var i = [];
        t.each(e, function (e) {
            e = t.camelCase(e);
            e = t.transit.propertyMap[e] || t.cssProps[e] || e;
            e = h(e);
            if (n[e]) e = h(n[e]);
            if (t.inArray(e, i) === -1) {
                i.push(e)
            }
        });
        return i
    }

    function d(e, n, i, r) {
        var s = l(e);
        if (t.cssEase[i]) {
            i = t.cssEase[i]
        }
        var a = "" + y(n) + " " + i;
        if (parseInt(r, 10) > 0) {
            a += " " + y(r)
        }
        var o = [];
        t.each(s, function (t, e) {
            o.push(e + " " + a)
        });
        return o.join(", ")
    }

    t.fn.transition = t.fn.transit = function (e, i, r, s) {
        var a = this;
        var u = 0;
        var f = true;
        var l = t.extend(true, {}, e);
        if (typeof i === "function") {
            s = i;
            i = undefined
        }
        if (typeof i === "object") {
            r = i.easing;
            u = i.delay || 0;
            f = typeof i.queue === "undefined" ? true : i.queue;
            s = i.complete;
            i = i.duration
        }
        if (typeof r === "function") {
            s = r;
            r = undefined
        }
        if (typeof l.easing !== "undefined") {
            r = l.easing;
            delete l.easing
        }
        if (typeof l.duration !== "undefined") {
            i = l.duration;
            delete l.duration
        }
        if (typeof l.complete !== "undefined") {
            s = l.complete;
            delete l.complete
        }
        if (typeof l.queue !== "undefined") {
            f = l.queue;
            delete l.queue
        }
        if (typeof l.delay !== "undefined") {
            u = l.delay;
            delete l.delay
        }
        if (typeof i === "undefined") {
            i = t.fx.speeds._default
        }
        if (typeof r === "undefined") {
            r = t.cssEase._default
        }
        i = y(i);
        var p = d(l, i, r, u);
        var h = t.transit.enabled && n.transition;
        var b = h ? parseInt(i, 10) + parseInt(u, 10) : 0;
        if (b === 0) {
            var g = function (t) {
                a.css(l);
                if (s) {
                    s.apply(a)
                }
                if (t) {
                    t()
                }
            };
            c(a, f, g);
            return a
        }
        var m = {};
        var v = function (e) {
            var i = false;
            var r = function () {
                if (i) {
                    a.unbind(o, r)
                }
                if (b > 0) {
                    a.each(function () {
                        this.style[n.transition] = m[this] || null
                    })
                }
                if (typeof s === "function") {
                    s.apply(a)
                }
                if (typeof e === "function") {
                    e()
                }
            };
            if (b > 0 && o && t.transit.useTransitionEnd) {
                i = true;
                a.bind(o, r)
            } else {
                window.setTimeout(r, b)
            }
            a.each(function () {
                if (b > 0) {
                    this.style[n.transition] = p
                }
                t(this).css(l)
            })
        };
        var z = function (t) {
            this.offsetWidth;
            v(t)
        };
        c(a, f, z);
        return this
    };

    function p(e, i) {
        if (!i) {
            t.cssNumber[e] = true
        }
        t.transit.propertyMap[e] = n.transform;
        t.cssHooks[e] = {
            get: function (n) {
                var i = t(n).css("transit:transform");
                return i.get(e)
            }, set: function (n, i) {
                var r = t(n).css("transit:transform");
                r.setFromString(e, i);
                t(n).css({"transit:transform": r})
            }
        }
    }

    function h(t) {
        return t.replace(/([A-Z])/g, function (t) {
            return "-" + t.toLowerCase()
        })
    }

    function b(t, e) {
        if (typeof t === "string" && !t.match(/^[\-0-9\.]+$/)) {
            return t
        } else {
            return "" + t + e
        }
    }

    function y(e) {
        var n = e;
        if (typeof n === "string" && !n.match(/^[\-0-9\.]+/)) {
            n = t.fx.speeds[n] || t.fx.speeds._default
        }
        return b(n, "ms")
    }

    t.transit.getTransitionValue = d;
    return t
});

/**
 * js-cookie
 * 2.1.4
 * https://github.com/js-cookie/js-cookie
 */
!function (a) {
    var b = !1;
    if ("function" == typeof define && define.amd && (define(a), b = !0), "object" == typeof exports && (module.exports = a(), b = !0), !b) {
        var c = window.Cookies, d = window.Cookies = a();
        d.noConflict = function () {
            return window.Cookies = c, d
        }
    }
}(function () {
    function a() {
        for (var a = 0, b = {}; a < arguments.length; a++) {
            var c = arguments[a];
            for (var d in c) b[d] = c[d]
        }
        return b
    }

    function b(c) {
        function d(b, e, f) {
            var g;
            if ("undefined" != typeof document) {
                if (arguments.length > 1) {
                    if (f = a({path: "/"}, d.defaults, f), "number" == typeof f.expires) {
                        var h = new Date;
                        h.setMilliseconds(h.getMilliseconds() + 864e5 * f.expires), f.expires = h
                    }
                    f.expires = f.expires ? f.expires.toUTCString() : "";
                    try {
                        g = JSON.stringify(e), /^[\{\[]/.test(g) && (e = g)
                    } catch (p) {
                    }
                    e = c.write ? c.write(e, b) : encodeURIComponent(e + "").replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g, decodeURIComponent), b = encodeURIComponent(b + ""), b = b.replace(/%(23|24|26|2B|5E|60|7C)/g, decodeURIComponent), b = b.replace(/[\(\)]/g, escape);
                    var i = "";
                    for (var j in f) f[j] && (i += "; " + j, !0 !== f[j] && (i += "=" + f[j]));
                    return document.cookie = b + "=" + e + i
                }
                b || (g = {});
                for (var k = document.cookie ? document.cookie.split("; ") : [], l = 0; l < k.length; l++) {
                    var m = k[l].split("="), n = m.slice(1).join("=");
                    '"' === n.charAt(0) && (n = n.slice(1, -1));
                    try {
                        var o = m[0].replace(/(%[0-9A-Z]{2})+/g, decodeURIComponent);
                        if (n = c.read ? c.read(n, o) : c(n, o) || n.replace(/(%[0-9A-Z]{2})+/g, decodeURIComponent), this.json) try {
                            n = JSON.parse(n)
                        } catch (p) {
                        }
                        if (b === o) {
                            g = n;
                            break
                        }
                        b || (g[o] = n)
                    } catch (p) {
                    }
                }
                return g
            }
        }

        return d.set = d, d.get = function (a) {
            return d.call(d, a)
        }, d.getJSON = function () {
            return d.apply({json: !0}, [].slice.call(arguments))
        }, d.defaults = {}, d.remove = function (b, c) {
            d(b, "", a(c, {expires: -1}))
        }, d.withConverter = b, d
    }

    return b(function () {
    })
});

/**
 * mousetrap
 * 1.6.1
 * https://github.com/ccampbell/mousetrap
 */
(function (r, v, f) {
    function w(a, b, g) {
        a.addEventListener ? a.addEventListener(b, g, !1) : a.attachEvent("on" + b, g)
    }

    function A(a) {
        if ("keypress" == a.type) {
            var b = String.fromCharCode(a.which);
            a.shiftKey || (b = b.toLowerCase());
            return b
        }
        return p[a.which] ? p[a.which] : t[a.which] ? t[a.which] : String.fromCharCode(a.which).toLowerCase()
    }

    function F(a) {
        var b = [];
        a.shiftKey && b.push("shift");
        a.altKey && b.push("alt");
        a.ctrlKey && b.push("ctrl");
        a.metaKey && b.push("meta");
        return b
    }

    function x(a) {
        return "shift" == a || "ctrl" == a || "alt" == a || "meta" == a
    }

    function B(a, b) {
        var g, c, d, f = [];
        g = a;
        "+" === g ? g = ["+"] : (g = g.replace(/\+{2}/g, "+plus"), g = g.split("+"));
        for (d = 0; d < g.length; ++d) c = g[d], C[c] && (c = C[c]), b && "keypress" != b && D[c] && (c = D[c], f.push("shift")), x(c) && f.push(c);
        g = c;
        d = b;
        if (!d) {
            if (!n) {
                n = {};
                for (var q in p) 95 < q && 112 > q || p.hasOwnProperty(q) && (n[p[q]] = q)
            }
            d = n[g] ? "keydown" : "keypress"
        }
        "keypress" == d && f.length && (d = "keydown");
        return {key: c, modifiers: f, action: d}
    }

    function E(a, b) {
        return null === a || a === v ? !1 : a === b ? !0 : E(a.parentNode, b)
    }

    function c(a) {
        function b(a) {
            a = a || {};
            var b = !1, l;
            for (l in n) a[l] ? b = !0 : n[l] = 0;
            b || (y = !1)
        }

        function g(a, b, u, e, c, g) {
            var l, m, k = [], f = u.type;
            if (!h._callbacks[a]) return [];
            "keyup" == f && x(a) && (b = [a]);
            for (l = 0; l < h._callbacks[a].length; ++l) if (m = h._callbacks[a][l], (e || !m.seq || n[m.seq] == m.level) && f == m.action) {
                var d;
                (d = "keypress" == f && !u.metaKey && !u.ctrlKey) || (d = m.modifiers, d = b.sort().join(",") === d.sort().join(","));
                d && (d = e && m.seq == e && m.level == g, (!e && m.combo == c || d) && h._callbacks[a].splice(l, 1), k.push(m))
            }
            return k
        }

        function f(a, b, c, e) {
            h.stopCallback(b, b.target || b.srcElement, c, e) || !1 !== a(b, c) || (b.preventDefault ? b.preventDefault() : b.returnValue = !1, b.stopPropagation ? b.stopPropagation() : b.cancelBubble = !0)
        }

        function d(a) {
            "number" !== typeof a.which && (a.which = a.keyCode);
            var b = A(a);
            b && ("keyup" == a.type && z === b ? z = !1 : h.handleKey(b, F(a), a))
        }

        function p(a, c, u, e) {
            function l(c) {
                return function () {
                    y = c;
                    ++n[a];
                    clearTimeout(r);
                    r = setTimeout(b, 1E3)
                }
            }

            function g(c) {
                f(u, c, a);
                "keyup" !== e && (z = A(c));
                setTimeout(b, 10)
            }

            for (var d = n[a] = 0; d < c.length; ++d) {
                var m = d + 1 === c.length ? g : l(e || B(c[d + 1]).action);
                q(c[d], m, e, a, d)
            }
        }

        function q(a, b, c, e, d) {
            h._directMap[a + ":" + c] = b;
            a = a.replace(/\s+/g, " ");
            var f = a.split(" ");
            1 < f.length ? p(a, f, b, c) : (c = B(a, c), h._callbacks[c.key] = h._callbacks[c.key] || [], g(c.key, c.modifiers, {type: c.action}, e, a, d), h._callbacks[c.key][e ? "unshift" : "push"]({
                callback: b,
                modifiers: c.modifiers,
                action: c.action,
                seq: e,
                level: d,
                combo: a
            }))
        }

        var h = this;
        a = a || v;
        if (!(h instanceof c)) return new c(a);
        h.target = a;
        h._callbacks = {};
        h._directMap = {};
        var n = {}, r, z = !1, t = !1, y = !1;
        h._handleKey = function (a, c, d) {
            var e = g(a, c, d), k;
            c = {};
            var h = 0, l = !1;
            for (k = 0; k < e.length; ++k) e[k].seq && (h = Math.max(h, e[k].level));
            for (k = 0; k < e.length; ++k) e[k].seq ? e[k].level == h && (l = !0, c[e[k].seq] = 1, f(e[k].callback, d, e[k].combo, e[k].seq)) : l || f(e[k].callback, d, e[k].combo);
            e = "keypress" == d.type && t;
            d.type != y || x(a) || e || b(c);
            t = l && "keydown" == d.type
        };
        h._bindMultiple = function (a, b, c) {
            for (var d = 0; d < a.length; ++d) q(a[d], b, c)
        };
        w(a, "keypress", d);
        w(a, "keydown", d);
        w(a, "keyup", d)
    }

    if (r) {
        var p = {
            8: "backspace",
            9: "tab",
            13: "enter",
            16: "shift",
            17: "ctrl",
            18: "alt",
            20: "capslock",
            27: "esc",
            32: "space",
            33: "pageup",
            34: "pagedown",
            35: "end",
            36: "home",
            37: "left",
            38: "up",
            39: "right",
            40: "down",
            45: "ins",
            46: "del",
            91: "meta",
            93: "meta",
            224: "meta"
        }, t = {
            106: "*",
            107: "+",
            109: "-",
            110: ".",
            111: "/",
            186: ";",
            187: "=",
            188: ",",
            189: "-",
            190: ".",
            191: "/",
            192: "`",
            219: "[",
            220: "\\",
            221: "]",
            222: "'"
        }, D = {
            "~": "`",
            "!": "1",
            "@": "2",
            "#": "3",
            $: "4",
            "%": "5",
            "^": "6",
            "&": "7",
            "*": "8",
            "(": "9",
            ")": "0",
            _: "-",
            "+": "=",
            ":": ";",
            '"': "'",
            "<": ",",
            ">": ".",
            "?": "/",
            "|": "\\"
        }, C = {
            option: "alt",
            command: "meta",
            "return": "enter",
            escape: "esc",
            plus: "+",
            mod: /Mac|iPod|iPhone|iPad/.test(navigator.platform) ? "meta" : "ctrl"
        }, n;
        for (f = 1; 20 > f; ++f) p[111 + f] = "f" + f;
        for (f = 0; 9 >= f; ++f) p[f + 96] = f.toString();
        c.prototype.bind = function (a, b, c) {
            a = a instanceof Array ? a : [a];
            this._bindMultiple.call(this, a, b, c);
            return this
        };
        c.prototype.unbind = function (a, b) {
            return this.bind.call(this, a, function () {
            }, b)
        };
        c.prototype.trigger = function (a, b) {
            if (this._directMap[a + ":" + b]) this._directMap[a + ":" + b]({}, a);
            return this
        };
        c.prototype.reset = function () {
            this._callbacks = {};
            this._directMap = {};
            return this
        };
        c.prototype.stopCallback = function (a, b) {
            return -1 < (" " + b.className + " ").indexOf(" mousetrap ") || E(b, this.target) ? !1 : "INPUT" == b.tagName || "SELECT" == b.tagName || "TEXTAREA" == b.tagName || b.isContentEditable
        };
        c.prototype.handleKey = function () {
            return this._handleKey.apply(this, arguments)
        };
        c.addKeycodes = function (a) {
            for (var b in a) a.hasOwnProperty(b) && (p[b] = a[b]);
            n = null
        };
        c.init = function () {
            var a = c(v), b;
            for (b in a) "_" !== b.charAt(0) && (c[b] = function (b) {
                return function () {
                    return a[b].apply(a, arguments)
                }
            }(b))
        };
        c.init();
        r.Mousetrap = c;
        "undefined" !== typeof module && module.exports && (module.exports = c);
        "function" === typeof define && define.amd && define(function () {
            return c
        })
    }
})("undefined" !== typeof window ? window : null, "undefined" !== typeof window ? document : null);

/**
 * jquery.mousewheel
 * 3.1.13
 * https://github.com/brandonaaron/jquery-mousewheel/
 */
!function (a) {
    "function" == typeof define && define.amd ? define(["jquery"], a) : "object" == typeof exports ? module.exports = a : a(jQuery)
}(function (a) {
    function b(b) {
        var g = b || window.event, h = i.call(arguments, 1), j = 0, l = 0, m = 0, n = 0, o = 0, p = 0;
        if (b = a.event.fix(g), b.type = "mousewheel", "detail" in g && (m = -1 * g.detail), "wheelDelta" in g && (m = g.wheelDelta), "wheelDeltaY" in g && (m = g.wheelDeltaY), "wheelDeltaX" in g && (l = -1 * g.wheelDeltaX), "axis" in g && g.axis === g.HORIZONTAL_AXIS && (l = -1 * m, m = 0), j = 0 === m ? l : m, "deltaY" in g && (m = -1 * g.deltaY, j = m), "deltaX" in g && (l = g.deltaX, 0 === m && (j = -1 * l)), 0 !== m || 0 !== l) {
            if (1 === g.deltaMode) {
                var q = a.data(this, "mousewheel-line-height");
                j *= q, m *= q, l *= q
            } else if (2 === g.deltaMode) {
                var r = a.data(this, "mousewheel-page-height");
                j *= r, m *= r, l *= r
            }
            if (n = Math.max(Math.abs(m), Math.abs(l)), (!f || f > n) && (f = n, d(g, n) && (f /= 40)), d(g, n) && (j /= 40, l /= 40, m /= 40), j = Math[j >= 1 ? "floor" : "ceil"](j / f), l = Math[l >= 1 ? "floor" : "ceil"](l / f), m = Math[m >= 1 ? "floor" : "ceil"](m / f), k.settings.normalizeOffset && this.getBoundingClientRect) {
                var s = this.getBoundingClientRect();
                o = b.clientX - s.left, p = b.clientY - s.top
            }
            return b.deltaX = l, b.deltaY = m, b.deltaFactor = f, b.offsetX = o, b.offsetY = p, b.deltaMode = 0, h.unshift(b, j, l, m), e && clearTimeout(e), e = setTimeout(c, 200), (a.event.dispatch || a.event.handle).apply(this, h)
        }
    }

    function c() {
        f = null
    }

    function d(a, b) {
        return k.settings.adjustOldDeltas && "mousewheel" === a.type && b % 120 === 0
    }

    var e, f, g = ["wheel", "mousewheel", "DOMMouseScroll", "MozMousePixelScroll"],
        h = "onwheel" in document || document.documentMode >= 9 ? ["wheel"] : ["mousewheel", "DomMouseScroll", "MozMousePixelScroll"],
        i = Array.prototype.slice;
    if (a.event.fixHooks) for (var j = g.length; j;) a.event.fixHooks[g[--j]] = a.event.mouseHooks;
    var k = a.event.special.mousewheel = {
        version: "3.1.12", setup: function () {
            if (this.addEventListener) for (var c = h.length; c;) this.addEventListener(h[--c], b, !1); else this.onmousewheel = b;
            a.data(this, "mousewheel-line-height", k.getLineHeight(this)), a.data(this, "mousewheel-page-height", k.getPageHeight(this))
        }, teardown: function () {
            if (this.removeEventListener) for (var c = h.length; c;) this.removeEventListener(h[--c], b, !1); else this.onmousewheel = null;
            a.removeData(this, "mousewheel-line-height"), a.removeData(this, "mousewheel-page-height")
        }, getLineHeight: function (b) {
            var c = a(b), d = c["offsetParent" in a.fn ? "offsetParent" : "parent"]();
            return d.length || (d = a("body")), parseInt(d.css("fontSize"), 10) || parseInt(c.css("fontSize"), 10) || 16
        }, getPageHeight: function (b) {
            return a(b).height()
        }, settings: {adjustOldDeltas: !0, normalizeOffset: !0}
    };
    a.fn.extend({
        mousewheel: function (a) {
            return a ? this.bind("mousewheel", a) : this.trigger("mousewheel")
        }, unmousewheel: function (a) {
            return this.unbind("mousewheel", a)
        }
    })
});

/**
 * jquery_pagination 修改版
 * http://hooray.github.com/jquery.pagination/
 */
(function ($) {
    $.PaginationCalculator = function (maxentries, opts) {
        this.maxentries = maxentries;
        this.opts = opts
    };
    $.extend($.PaginationCalculator.prototype, {
        numPages: function () {
            return Math.ceil(this.maxentries / this.opts.items_per_page)
        }, getInterval: function (current_page) {
            var ne_half = Math.floor(this.opts.num_display_entries / 2);
            var np = this.numPages();
            var upper_limit = np - this.opts.num_display_entries;
            var start = current_page > ne_half ? Math.max(Math.min(current_page - ne_half, upper_limit), 0) : 0;
            var end = current_page > ne_half ? Math.min(current_page + ne_half + (this.opts.num_display_entries % 2), np) : Math.min(this.opts.num_display_entries, np);
            return {start: start, end: end}
        }
    });
    $.PaginationRenderers = {};
    $.PaginationRenderers.defaultRenderer = function (maxentries, opts) {
        this.maxentries = maxentries;
        this.opts = opts;
        this.pc = new $.PaginationCalculator(maxentries, opts)
    };
    $.extend($.PaginationRenderers.defaultRenderer.prototype, {
        createLink: function (page_id, current_page, appendopts) {
            var lnk, np = this.pc.numPages();
            page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1);
            appendopts = $.extend({text: page_id + 1, classes: ""}, appendopts || {});
            if (page_id == current_page) {
                if (isNaN(appendopts.text)) {
                    lnk = $("<li class='disabled'><a>" + appendopts.text + "</a></li>")
                } else {
                    lnk = $("<li class='active'><a>" + appendopts.text + "</a></li>")
                }
            } else {
                lnk = $("<li><a href='" + this.opts.link_to.replace(/__id__/, page_id) + "'>" + appendopts.text + "</a></li>")
            }
            if (appendopts.classes) {
                lnk.addClass(appendopts.classes)
            }
            lnk.data("page_id", page_id);
            return lnk
        }, appendRange: function (container, current_page, start, end, opts) {
            var i;
            for (i = start; i < end; i++) {
                this.createLink(i, current_page, opts).appendTo(container)
            }
        }, getLinks: function (current_page, eventHandler) {
            current_page = parseInt(current_page);
            var begin, end, interval = this.pc.getInterval(current_page), np = this.pc.numPages(),
                fragment = $("<ul class='pagination'></ul>");
            if (this.opts.prev_text && (current_page > 0 || this.opts.prev_show_always)) {
                fragment.append(this.createLink(current_page - 1, current_page, {
                    text: this.opts.prev_text,
                    classes: "prev"
                }))
            }
            if (interval.start > 0 && this.opts.num_edge_entries > 0) {
                end = Math.min(this.opts.num_edge_entries, interval.start);
                this.appendRange(fragment, current_page, 0, end, {classes: "sp"});
                if (this.opts.num_edge_entries < interval.start && this.opts.ellipse_text) {
                    $("<li class='disabled'><a>" + this.opts.ellipse_text + "</a></li>").appendTo(fragment)
                }
            }
            this.appendRange(fragment, current_page, interval.start, interval.end);
            if (interval.end < np && this.opts.num_edge_entries > 0) {
                if (np - this.opts.num_edge_entries > interval.end && this.opts.ellipse_text) {
                    $("<li class='disabled'><a>" + this.opts.ellipse_text + "</a></li>").appendTo(fragment)
                }
                begin = Math.max(np - this.opts.num_edge_entries, interval.end);
                this.appendRange(fragment, current_page, begin, np, {classes: "ep"})
            }
            if (this.opts.next_text && (current_page < np - 1 || this.opts.next_show_always)) {
                fragment.append(this.createLink(current_page + 1, current_page, {
                    text: this.opts.next_text,
                    classes: "next"
                }))
            }
            $("li:not(.disabled, .active) a", fragment).click(eventHandler);
            return fragment
        }
    });
    $.fn.pagination = function (maxentries, opts) {
        opts = $.extend({
            items_per_page: 10,
            num_display_entries: 11,
            current_page: 0,
            num_edge_entries: 0,
            link_to: "javascript:;",
            prev_text: "Prev",
            next_text: "Next",
            ellipse_text: "...",
            prev_show_always: true,
            next_show_always: true,
            renderer: "defaultRenderer",
            load_first_page: false,
            callback: function () {
                return false
            }
        }, opts || {});
        var containers = this, renderer, links, current_page;

        function paginationClickHandler(evt) {
            var links, new_current_page = $(evt.target).parent().data("page_id"),
                continuePropagation = selectPage(new_current_page);
            if (!continuePropagation) {
                evt.stopPropagation()
            }
            return continuePropagation
        }

        function selectPage(new_current_page) {
            containers.data("current_page", new_current_page);
            links = renderer.getLinks(new_current_page, paginationClickHandler);
            containers.empty();
            links.appendTo(containers);
            var continuePropagation = opts.callback(new_current_page, containers);
            return continuePropagation
        }

        current_page = opts.current_page;
        containers.data("current_page", current_page);
        maxentries = (!maxentries || maxentries < 0) ? 1 : maxentries;
        opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0) ? 1 : opts.items_per_page;
        if (!$.PaginationRenderers[opts.renderer]) {
            throw new ReferenceError("Pagination renderer '" + opts.renderer + "' was not found in jQuery.PaginationRenderers object.")
        }
        renderer = new $.PaginationRenderers[opts.renderer](maxentries, opts);
        var pc = new $.PaginationCalculator(maxentries, opts);
        var np = pc.numPages();
        containers.off("setPage").on("setPage", {numPages: np}, function (evt, page_id) {
            if (page_id >= 0 && page_id < evt.data.numPages) {
                selectPage(page_id);
                return false
            }
        });
        containers.off("prevPage").on("prevPage", function (evt) {
            var current_page = $(this).data("current_page");
            if (current_page > 0) {
                selectPage(current_page - 1)
            }
            return false
        });
        containers.off("nextPage").on("nextPage", {numPages: np}, function (evt) {
            var current_page = $(this).data("current_page");
            if (current_page < evt.data.numPages - 1) {
                selectPage(current_page + 1)
            }
            return false
        });
        containers.off("currentPage").on("currentPage", function () {
            var current_page = $(this).data("current_page");
            selectPage(current_page);
            return false
        });
        links = renderer.getLinks(current_page, paginationClickHandler);
        containers.empty();
        links.appendTo(containers);
        if (opts.load_first_page) {
            opts.callback(current_page, containers)
        }
    }
})(jQuery);

/**
 * 腾讯UED提示信息
 * ZENG.msgbox.show("服务器繁忙，请稍后再试。", 1, 2000);
 * ZENG.msgbox.show("设置成功！", 4, 2000);
 * ZENG.msgbox.show("数据拉取失败", 5, 2000);
 * ZENG.msgbox.show("正在加载中，请稍后...", 6,8000);
 */
window.ZENG = window.ZENG || {};
ZENG.dom = {
    getById: function (a) {
        return document.getElementById(a)
    }, get: function (a) {
        return (typeof (a) == "string") ? document.getElementById(a) : a
    }, createElementIn: function (d, f, e, c) {
        var a = (f = ZENG.dom.get(f) || document.body).ownerDocument.createElement(d || "div"), b;
        if (typeof (c) == "object") {
            for (b in c) {
                if (b == "class") {
                    a.className = c[b]
                } else {
                    if (b == "style") {
                        a.style.cssText = c[b]
                    } else {
                        a[b] = c[b]
                    }
                }
            }
        }
        e ? f.insertBefore(a, f.firstChild) : f.appendChild(a);
        return a
    }, getStyle: function (b, f) {
        b = ZENG.dom.get(b);
        if (!b || b.nodeType == 9) {
            return null
        }
        var a = document.defaultView && document.defaultView.getComputedStyle,
            c = !a ? null : document.defaultView.getComputedStyle(b, ""), d = "";
        switch (f) {
            case"float":
                f = a ? "cssFloat" : "styleFloat";
                break;
            case"opacity":
                if (!a) {
                    var h = 100;
                    try {
                        h = b.filters["DXImageTransform.Microsoft.Alpha"].opacity
                    } catch (g) {
                        try {
                            h = b.filters("alpha").opacity
                        } catch (g) {
                        }
                    }
                    return h / 100
                } else {
                    return parseFloat((c || b.style)[f])
                }
                break;
            case"backgroundPositionX":
                if (a) {
                    f = "backgroundPosition";
                    return ((c || b.style)[f]).split(" ")[0]
                }
                break;
            case"backgroundPositionY":
                if (a) {
                    f = "backgroundPosition";
                    return ((c || b.style)[f]).split(" ")[1]
                }
                break
        }
        if (a) {
            return (c || b.style)[f]
        } else {
            return (b.currentStyle[f] || b.style[f])
        }
    }, setStyle: function (c, g, h) {
        if (!(c = ZENG.dom.get(c)) || c.nodeType != 1) {
            return false
        }
        var e, b = true, d = (e = document.defaultView) && e.getComputedStyle,
            f = /z-?index|font-?weight|opacity|zoom|line-?height/i;
        if (typeof (g) == "string") {
            e = g;
            g = {};
            g[e] = h
        }
        for (var a in g) {
            h = g[a];
            if (a == "float") {
                a = d ? "cssFloat" : "styleFloat"
            } else {
                if (a == "opacity") {
                    if (!d) {
                        a = "filter";
                        h = h >= 1 ? "" : ("alpha(opacity=" + Math.round(h * 100) + ")")
                    }
                } else {
                    if (a == "backgroundPositionX" || a == "backgroundPositionY") {
                        e = a.slice(-1) == "X" ? "Y" : "X";
                        if (d) {
                            var i = ZENG.dom.getStyle(c, "backgroundPosition" + e);
                            a = "backgroundPosition";
                            typeof (h) == "number" && (h = h + "px");
                            h = e == "Y" ? (h + " " + (i || "top")) : ((i || "left") + " " + h)
                        }
                    }
                }
            }
            if (typeof c.style[a] != "undefined") {
                c.style[a] = h + (typeof h === "number" && !f.test(a) ? "px" : "");
                b = b && true
            } else {
                b = b && false
            }
        }
        return b
    }, getScrollTop: function (a) {
        var b = a || document;
        return Math.max(b.documentElement.scrollTop, b.body.scrollTop)
    }, getClientHeight: function (a) {
        var b = a || document;
        return b.compatMode == "CSS1Compat" ? b.documentElement.clientHeight : b.body.clientHeight
    }
};
ZENG.string = {
    RegExps: {
        trim: /^\s+|\s+$/g,
        ltrim: /^\s+/,
        rtrim: /\s+$/,
        nl2br: /\n/g,
        s2nb: /[\x20]{2}/g,
        URIencode: /[\x09\x0A\x0D\x20\x21-\x29\x2B\x2C\x2F\x3A-\x3F\x5B-\x5E\x60\x7B-\x7E]/g,
        escHTML: {re_amp: /&/g, re_lt: /</g, re_gt: />/g, re_apos: /\x27/g, re_quot: /\x22/g},
        escString: {bsls: /\\/g, sls: /\//g, nl: /\n/g, rt: /\r/g, tab: /\t/g},
        restXHTML: {re_amp: /&/g, re_lt: /</g, re_gt: />/g, re_apos: /&(?:apos|#0?39);/g, re_quot: /"/g},
        write: /\{(\d{1,2})(?:\:([xodQqb]))?\}/g,
        isURL: /^(?:ht|f)tp(?:s)?\:\/\/(?:[\w\-\.]+)\.\w+/i,
        cut: /[\x00-\xFF]/,
        getRealLen: {r0: /[^\x00-\xFF]/g, r1: /[\x00-\xFF]/g},
        format: /\{([\d\w\.]+)\}/g
    }, commonReplace: function (a, c, b) {
        return a.replace(c, b)
    }, format: function (c) {
        var b = Array.prototype.slice.call(arguments), a;
        c = String(b.shift());
        if (b.length == 1 && typeof (b[0]) == "object") {
            b = b[0]
        }
        ZENG.string.RegExps.format.lastIndex = 0;
        return c.replace(ZENG.string.RegExps.format, function (d, e) {
            a = ZENG.object.route(b, e);
            return a === undefined ? d : a
        })
    }
};
ZENG.object = {
    routeRE: /([\d\w_]+)/g, route: function (d, c) {
        d = d || {};
        c = String(c);
        var b = ZENG.object.routeRE, a;
        b.lastIndex = 0;
        while ((a = b.exec(c)) !== null) {
            d = d[a[0]];
            if (d === undefined || d === null) {
                break
            }
        }
        return d
    }
};
var ua = ZENG.userAgent = {}, agent = navigator.userAgent;
ua.ie = 9 - ((agent.indexOf("Trident/5.0") > -1) ? 0 : 1) - (window.XDomainRequest ? 0 : 1) - (window.XMLHttpRequest ? 0 : 1);
if (typeof (ZENG.msgbox) == "undefined") {
    ZENG.msgbox = {}
}
ZENG.msgbox._timer = null;
ZENG.msgbox.loadingAnimationPath = ZENG.msgbox.loadingAnimationPath || ("gb_tip_loading.gif");
ZENG.msgbox.show = function (c, g, h, a) {
    if (typeof (a) == "number") {
        a = {topPosition: a}
    }
    a = a || {};
    var j = ZENG.msgbox,
        i = '<span class="zeng_msgbox_layer" style="display:none;z-index:10000;" id="mode_tips_v2"><span class="gtl_ico_{type}"></span>{loadIcon}{msgHtml}<span class="gtl_end"></span></span>',
        d = '<span class="gtl_ico_loading"></span>', e = [0, 0, 0, 0, "succ", "fail", "clear"], b, f;
    j._loadCss && j._loadCss(a.cssPath);
    b = ZENG.dom.get("q_Msgbox") || ZENG.dom.createElementIn("div", document.body, false, {className: "zeng_msgbox_layer_wrap"});
    b.id = "q_Msgbox";
    b.style.display = "";
    b.innerHTML = ZENG.string.format(i, {type: e[g] || "hits", msgHtml: c || "", loadIcon: g == 6 ? d : ""});
    j._setPosition(b, h, a.topPosition)
};
ZENG.msgbox._setPosition = function (a, f, d) {
    f = f || 5000;
    var g = ZENG.msgbox, b = ZENG.dom.getScrollTop(), e = ZENG.dom.getClientHeight(), c = Math.floor(e / 2) - 40;
    ZENG.dom.setStyle(a, "top", ((document.compatMode == "BackCompat" || ZENG.userAgent.ie < 7) ? b : 0) + ((typeof (d) == "number") ? d : c) + "px");
    clearTimeout(g._timer);
    a.firstChild.style.display = "";
    f && (g._timer = setTimeout(g.hide, f))
};
ZENG.msgbox.hide = function (a) {
    var b = ZENG.msgbox;
    if (a) {
        clearTimeout(b._timer);
        b._timer = setTimeout(b._hide, a)
    } else {
        b._hide()
    }
};
ZENG.msgbox._hide = function () {
    var a = ZENG.dom.get("q_Msgbox"), b = ZENG.msgbox;
    clearTimeout(b._timer);
    if (a) {
        var c = a.firstChild;
        ZENG.dom.setStyle(a, "display", "none")
    }
};

/**
 * screenfull
 * 3.3.2
 * https://github.com/sindresorhus/screenfull.js
 */
!function () {
    "use strict";
    var a = "undefined" != typeof window && void 0 !== window.document ? window.document : {},
        b = "undefined" != typeof module && module.exports,
        c = "undefined" != typeof Element && "ALLOW_KEYBOARD_INPUT" in Element, d = function () {
            for (var b, c = [["requestFullscreen", "exitFullscreen", "fullscreenElement", "fullscreenEnabled", "fullscreenchange", "fullscreenerror"], ["webkitRequestFullscreen", "webkitExitFullscreen", "webkitFullscreenElement", "webkitFullscreenEnabled", "webkitfullscreenchange", "webkitfullscreenerror"], ["webkitRequestFullScreen", "webkitCancelFullScreen", "webkitCurrentFullScreenElement", "webkitCancelFullScreen", "webkitfullscreenchange", "webkitfullscreenerror"], ["mozRequestFullScreen", "mozCancelFullScreen", "mozFullScreenElement", "mozFullScreenEnabled", "mozfullscreenchange", "mozfullscreenerror"], ["msRequestFullscreen", "msExitFullscreen", "msFullscreenElement", "msFullscreenEnabled", "MSFullscreenChange", "MSFullscreenError"]], d = 0, e = c.length, f = {}; d < e; d++) if ((b = c[d]) && b[1] in a) {
                for (d = 0; d < b.length; d++) f[c[0][d]] = b[d];
                return f
            }
            return !1
        }(), e = {change: d.fullscreenchange, error: d.fullscreenerror}, f = {
            request: function (b) {
                var e = d.requestFullscreen;
                b = b || a.documentElement, / Version\/5\.1(?:\.\d+)? Safari\//.test(navigator.userAgent) ? b[e]() : b[e]()
            }, exit: function () {
                a[d.exitFullscreen]()
            }, toggle: function (a) {
                this.isFullscreen ? this.exit() : this.request(a)
            }, onchange: function (a) {
                this.on("change", a)
            }, onerror: function (a) {
                this.on("error", a)
            }, on: function (b, c) {
                var d = e[b];
                d && a.addEventListener(d, c, !1)
            }, off: function (b, c) {
                var d = e[b];
                d && a.removeEventListener(d, c, !1)
            }, raw: d
        };
    if (!d) return void (b ? module.exports = !1 : window.screenfull = !1);
    Object.defineProperties(f, {
        isFullscreen: {
            get: function () {
                return Boolean(a[d.fullscreenElement])
            }
        }, element: {
            enumerable: !0, get: function () {
                return a[d.fullscreenElement]
            }
        }, enabled: {
            enumerable: !0, get: function () {
                return Boolean(a[d.fullscreenEnabled])
            }
        }
    }), b ? module.exports = f : window.screenfull = f
}();

/**
 * artTemplate
 * 4.12.1
 * https://github.com/aui/artTemplate
 */
!function (e, t) {
    "object" == typeof exports && "object" == typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define([], t) : "object" == typeof exports ? exports.template = t() : e.template = t()
}(this, function () {
    return function (e) {
        function t(r) {
            if (n[r]) return n[r].exports;
            var i = n[r] = {i: r, l: !1, exports: {}};
            return e[r].call(i.exports, i, i.exports, t), i.l = !0, i.exports
        }

        var n = {};
        return t.m = e, t.c = n, t.d = function (e, n, r) {
            t.o(e, n) || Object.defineProperty(e, n, {configurable: !1, enumerable: !0, get: r})
        }, t.n = function (e) {
            var n = e && e.__esModule ? function () {
                return e["default"]
            } : function () {
                return e
            };
            return t.d(n, "a", n), n
        }, t.o = function (e, t) {
            return Object.prototype.hasOwnProperty.call(e, t)
        }, t.p = "", t(t.s = 6)
    }([function (e, t, n) {
        (function (t) {
            e.exports = !1;
            try {
                e.exports = "[object process]" === Object.prototype.toString.call(t.process)
            } catch (n) {
            }
        }).call(t, n(4))
    }, function (e, t, n) {
        "use strict";
        var r = n(8), i = n(3), o = n(23), s = function (e, t) {
            t.onerror(e, t);
            var n = function () {
                return "{Template Error}"
            };
            return n.mappings = [], n.sourcesContent = [], n
        }, a = function c(e) {
            var t = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
            "string" != typeof e ? t = e : t.source = e, t = i.$extend(t), e = t.source, !0 === t.debug && (t.cache = !1, t.minimize = !1, t.compileDebug = !0), t.compileDebug && (t.minimize = !1), t.filename && (t.filename = t.resolveFilename(t.filename, t));
            var n = t.filename, a = t.cache, u = t.caches;
            if (a && n) {
                var p = u.get(n);
                if (p) return p
            }
            if (!e) try {
                e = t.loader(n, t), t.source = e
            } catch (d) {
                var l = new o({
                    name: "CompileError",
                    path: n,
                    message: "template not found: " + d.message,
                    stack: d.stack
                });
                if (t.bail) throw l;
                return s(l, t)
            }
            var f = void 0, h = new r(t);
            try {
                f = h.build()
            } catch (l) {
                if (l = new o(l), t.bail) throw l;
                return s(l, t)
            }
            var m = function (e, n) {
                try {
                    return f(e, n)
                } catch (l) {
                    if (!t.compileDebug) return t.cache = !1, t.compileDebug = !0, c(t)(e, n);
                    if (l = new o(l), t.bail) throw l;
                    return s(l, t)()
                }
            };
            return m.mappings = f.mappings, m.sourcesContent = f.sourcesContent, m.toString = function () {
                return f.toString()
            }, a && n && u.set(n, m), m
        };
        a.Compiler = r, e.exports = a
    }, function (e, t) {
        Object.defineProperty(t, "__esModule", {value: !0}), t["default"] = /((['"])(?:(?!\2|\\).|\\(?:\r\n|[\s\S]))*(\2)?|`(?:[^`\\$]|\\[\s\S]|\$(?!\{)|\$\{(?:[^{}]|\{[^}]*\}?)*\}?)*(`)?)|(\/\/.*)|(\/\*(?:[^*]|\*(?!\/))*(\*\/)?)|(\/(?!\*)(?:\[(?:(?![\]\\]).|\\.)*\]|(?![\/\]\\]).|\\.)+\/(?:(?!\s*(?:\b|[\u0080-\uFFFF$\\'"~({]|[+\-!](?!=)|\.?\d))|[gmiyu]{1,5}\b(?![\u0080-\uFFFF$\\]|\s*(?:[+\-*%&|^<>!=?({]|\/(?![\/*])))))|(0[xX][\da-fA-F]+|0[oO][0-7]+|0[bB][01]+|(?:\d*\.\d+|\d+\.?)(?:[eE][+-]?\d+)?)|((?!\d)(?:(?!\s)[$\w\u0080-\uFFFF]|\\u[\da-fA-F]{4}|\\u\{[\da-fA-F]+\})+)|(--|\+\+|&&|\|\||=>|\.{3}|(?:[+\-\/%&|^]|\*{1,2}|<{1,2}|>{1,3}|!=?|={1,2})=?|[?~.,:;[\](){}])|(\s+)|(^$|[\s\S])/g, t.matchToToken = function (e) {
            var t = {type: "invalid", value: e[0]};
            return e[1] ? (t.type = "string", t.closed = !(!e[3] && !e[4])) : e[5] ? t.type = "comment" : e[6] ? (t.type = "comment", t.closed = !!e[7]) : e[8] ? t.type = "regex" : e[9] ? t.type = "number" : e[10] ? t.type = "name" : e[11] ? t.type = "punctuator" : e[12] && (t.type = "whitespace"), t
        }
    }, function (e, t, n) {
        "use strict";

        function r() {
            this.$extend = function (e) {
                return e = e || {}, s(e, e instanceof r ? e : this)
            }
        }

        var i = n(0), o = n(12), s = n(13), a = n(14), c = n(15), u = n(16), p = n(17), l = n(18), f = n(19), h = n(20),
            m = n(22), d = {
                source: null,
                filename: null,
                rules: [f, l],
                escape: !0,
                debug: !!i && "production" !== process.env.NODE_ENV,
                bail: !0,
                cache: !0,
                minimize: !0,
                compileDebug: !1,
                resolveFilename: m,
                include: a,
                htmlMinifier: h,
                htmlMinifierOptions: {collapseWhitespace: !0, minifyCSS: !0, minifyJS: !0, ignoreCustomFragments: []},
                onerror: c,
                loader: p,
                caches: u,
                root: "/",
                extname: ".art",
                ignore: [],
                imports: o
            };
        r.prototype = d, e.exports = new r
    }, function (e, t) {
        var n;
        n = function () {
            return this
        }();
        try {
            n = n || Function("return this")() || (0, eval)("this")
        } catch (r) {
            "object" == typeof window && (n = window)
        }
        e.exports = n
    }, function (e, t) {
    }, function (e, t, n) {
        "use strict";
        var r = n(7), i = n(1), o = n(24), s = function (e, t) {
            return t instanceof Object ? r({filename: e}, t) : i({filename: e, source: t})
        };
        s.render = r, s.compile = i, s.defaults = o, e.exports = s
    }, function (e, t, n) {
        "use strict";
        var r = n(1), i = function (e, t, n) {
            return r(e, n)(t)
        };
        e.exports = i
    }, function (e, t, n) {
        "use strict";

        function r(e, t) {
            if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
        }

        var i = n(9), o = n(11), s = "$data", a = "$imports", c = "print", u = "include", p = "extend", l = "block",
            f = "$$out", h = "$$line", m = "$$blocks", d = "$$slice", v = "$$from", g = "$$options",
            y = function (e, t) {
                return e.hasOwnProperty(t)
            }, b = JSON.stringify, x = function () {
                function e(t) {
                    var n, i, y = this;
                    r(this, e);
                    var b = t.source, x = t.minimize, w = t.htmlMinifier;
                    if (this.options = t, this.stacks = [], this.context = [], this.scripts = [], this.CONTEXT_MAP = {}, this.ignore = [s, a, g].concat(t.ignore), this.internal = (n = {}, n[f] = "''", n[h] = "[0,0]", n[m] = "arguments[1]||{}", n[v] = "null", n[c] = "function(){var s=''.concat.apply('',arguments);" + f + "+=s;return s}", n[u] = "function(src,data){var s=" + g + ".include(src,data||" + s + ",arguments[2]||" + m + "," + g + ");" + f + "+=s;return s}", n[p] = "function(from){" + v + "=from}", n[d] = "function(c,p,s){p=" + f + ";" + f + "='';c();s=" + f + ";" + f + "=p+s;return s}", n[l] = "function(){var a=arguments,s;if(typeof a[0]==='function'){return " + d + "(a[0])}else if(" + v + "){" + m + "[a[0]]=" + d + "(a[1])}else{s=" + m + "[a[0]];if(typeof s==='string'){" + f + "+=s}else{s=" + d + "(a[1])}return s}}", n), this.dependencies = (i = {}, i[c] = [f], i[u] = [f, g, s, m], i[p] = [v, u], i[l] = [d, v, f, m], i), this.importContext(f), t.compileDebug && this.importContext(h), x) try {
                        b = w(b, t)
                    } catch (E) {
                    }
                    this.source = b, this.getTplTokens(b, t.rules, this).forEach(function (e) {
                        e.type === o.TYPE_STRING ? y.parseString(e) : y.parseExpression(e)
                    })
                }

                return e.prototype.getTplTokens = function () {
                    return o.apply(undefined, arguments)
                }, e.prototype.getEsTokens = function (e) {
                    return i(e)
                }, e.prototype.getVariables = function (e) {
                    var t = !1;
                    return e.filter(function (e) {
                        return "whitespace" !== e.type && "comment" !== e.type
                    }).filter(function (e) {
                        return "name" === e.type && !t || (t = "punctuator" === e.type && "." === e.value, !1)
                    }).map(function (e) {
                        return e.value
                    })
                }, e.prototype.importContext = function (e) {
                    var t = this, n = "", r = this.internal, i = this.dependencies, o = this.ignore, c = this.context,
                        u = this.options, p = u.imports, l = this.CONTEXT_MAP;
                    y(l, e) || -1 !== o.indexOf(e) || (y(r, e) ? (n = r[e], y(i, e) && i[e].forEach(function (e) {
                        return t.importContext(e)
                    })) : n = "$escape" === e || "$each" === e || y(p, e) ? a + "." + e : s + "." + e, l[e] = n, c.push({
                        name: e,
                        value: n
                    }))
                }, e.prototype.parseString = function (e) {
                    var t = e.value;
                    if (t) {
                        var n = f + "+=" + b(t);
                        this.scripts.push({source: t, tplToken: e, code: n})
                    }
                }, e.prototype.parseExpression = function (e) {
                    var t = this, n = e.value, r = e.script, i = r.output, s = this.options.escape, a = r.code;
                    i && (a = !1 === s || i === o.TYPE_RAW ? f + "+=" + r.code : f + "+=$escape(" + r.code + ")");
                    var c = this.getEsTokens(a);
                    this.getVariables(c).forEach(function (e) {
                        return t.importContext(e)
                    }), this.scripts.push({source: n, tplToken: e, code: a})
                }, e.prototype.checkExpression = function (e) {
                    for (var t = [[/^\s*}[\w\W]*?{?[\s;]*$/, ""], [/(^[\w\W]*?\([\w\W]*?(?:=>|\([\w\W]*?\))\s*{[\s;]*$)/, "$1})"], [/(^[\w\W]*?\([\w\W]*?\)\s*{[\s;]*$)/, "$1}"]], n = 0; n < t.length;) {
                        if (t[n][0].test(e)) {
                            var r;
                            e = (r = e).replace.apply(r, t[n]);
                            break
                        }
                        n++
                    }
                    try {
                        return new Function(e), !0
                    } catch (i) {
                        return !1
                    }
                }, e.prototype.build = function () {
                    var e = this.options, t = this.context, n = this.scripts, r = this.stacks, i = this.source,
                        c = e.filename, l = e.imports, d = [], x = y(this.CONTEXT_MAP, p), w = 0, E = function (e, t) {
                            var n = t.line, i = t.start,
                                o = {generated: {line: r.length + w + 1, column: 1}, original: {line: n + 1, column: i + 1}};
                            return w += e.split(/\n/).length - 1, o
                        }, k = function (e) {
                            return e.replace(/^[\t ]+|[\t ]$/g, "")
                        };
                    r.push("function(" + s + "){"), r.push("'use strict'"), r.push(s + "=" + s + "||{}"), r.push("var " + t.map(function (e) {
                        return e.name + "=" + e.value
                    }).join(",")), e.compileDebug ? (r.push("try{"), n.forEach(function (e) {
                        e.tplToken.type === o.TYPE_EXPRESSION && r.push(h + "=[" + [e.tplToken.line, e.tplToken.start].join(",") + "]"), d.push(E(e.code, e.tplToken)), r.push(k(e.code))
                    }), r.push("}catch(error){"), r.push("throw {" + ["name:'RuntimeError'", "path:" + b(c), "message:error.message", "line:" + h + "[0]+1", "column:" + h + "[1]+1", "source:" + b(i), "stack:error.stack"].join(",") + "}"), r.push("}")) : n.forEach(function (e) {
                        d.push(E(e.code, e.tplToken)), r.push(k(e.code))
                    }), x && (r.push(f + "=''"), r.push(u + "(" + v + "," + s + "," + m + ")")), r.push("return " + f), r.push("}");
                    var T = r.join("\n");
                    try {
                        var O = new Function(a, g, "return " + T)(l, e);
                        return O.mappings = d, O.sourcesContent = [i], O
                    } catch (F) {
                        for (var $ = 0, j = 0, S = 0, _ = void 0; $ < n.length;) {
                            var C = n[$];
                            if (!this.checkExpression(C.code)) {
                                j = C.tplToken.line, S = C.tplToken.start, _ = C.code;
                                break
                            }
                            $++
                        }
                        throw{
                            name: "CompileError",
                            path: c,
                            message: F.message,
                            line: j + 1,
                            column: S + 1,
                            source: i,
                            generated: _,
                            stack: F.stack
                        }
                    }
                }, e
            }();
        x.CONSTS = {
            DATA: s,
            IMPORTS: a,
            PRINT: c,
            INCLUDE: u,
            EXTEND: p,
            BLOCK: l,
            OPTIONS: g,
            OUT: f,
            LINE: h,
            BLOCKS: m,
            SLICE: d,
            FROM: v,
            ESCAPE: "$escape",
            EACH: "$each"
        }, e.exports = x
    }, function (e, t, n) {
        "use strict";
        var r = n(10), i = n(2)["default"], o = n(2).matchToToken, s = function (e) {
            return e.match(i).map(function (e) {
                return i.lastIndex = 0, o(i.exec(e))
            }).map(function (e) {
                return "name" === e.type && r(e.value) && (e.type = "keyword"), e
            })
        };
        e.exports = s
    }, function (e, t, n) {
        "use strict";
        var r = {
            "abstract": !0,
            await: !0,
            "boolean": !0,
            "break": !0,
            "byte": !0,
            "case": !0,
            "catch": !0,
            "char": !0,
            "class": !0,
            "const": !0,
            "continue": !0,
            "debugger": !0,
            "default": !0,
            "delete": !0,
            "do": !0,
            "double": !0,
            "else": !0,
            "enum": !0,
            "export": !0,
            "extends": !0,
            "false": !0,
            "final": !0,
            "finally": !0,
            "float": !0,
            "for": !0,
            "function": !0,
            "goto": !0,
            "if": !0,
            "implements": !0,
            "import": !0,
            "in": !0,
            "instanceof": !0,
            "int": !0,
            "interface": !0,
            "let": !0,
            "long": !0,
            "native": !0,
            "new": !0,
            "null": !0,
            "package": !0,
            "private": !0,
            "protected": !0,
            "public": !0,
            "return": !0,
            "short": !0,
            "static": !0,
            "super": !0,
            "switch": !0,
            "synchronized": !0,
            "this": !0,
            "throw": !0,
            "transient": !0,
            "true": !0,
            "try": !0,
            "typeof": !0,
            "var": !0,
            "void": !0,
            "volatile": !0,
            "while": !0,
            "with": !0,
            "yield": !0
        };
        e.exports = function (e) {
            return r.hasOwnProperty(e)
        }
    }, function (e, t, n) {
        "use strict";

        function r(e, t, n, r) {
            var i = new String(e);
            return i.line = t, i.start = n, i.end = r, i
        }

        var i = function (e, t) {
            for (var n = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : {}, i = [{
                type: "string",
                value: e,
                line: 0,
                start: 0,
                end: e.length
            }], o = 0; o < t.length; o++) !function (e) {
                for (var t = e.test.ignoreCase ? "ig" : "g", o = e.test.source + "|^$|[\\w\\W]", s = new RegExp(o, t), a = 0; a < i.length; a++) if ("string" === i[a].type) {
                    for (var c = i[a].line, u = i[a].start, p = i[a].end, l = i[a].value.match(s), f = [], h = 0; h < l.length; h++) {
                        var m = l[h];
                        e.test.lastIndex = 0;
                        var d = e.test.exec(m), v = d ? "expression" : "string", g = f[f.length - 1], y = g || i[a],
                            b = y.value;
                        u = y.line === c ? g ? g.end : u : b.length - b.lastIndexOf("\n") - 1, p = u + m.length;
                        var x = {type: v, value: m, line: c, start: u, end: p};
                        if ("string" === v) g && "string" === g.type ? (g.value += m, g.end += m.length) : f.push(x); else {
                            d[0] = new r(d[0], c, u, p);
                            var w = e.use.apply(n, d);
                            x.script = w, f.push(x)
                        }
                        c += m.split(/\n/).length - 1
                    }
                    i.splice.apply(i, [a, 1].concat(f)), a += f.length - 1
                }
            }(t[o]);
            return i
        };
        i.TYPE_STRING = "string", i.TYPE_EXPRESSION = "expression", i.TYPE_RAW = "raw", i.TYPE_ESCAPE = "escape", e.exports = i
    }, function (e, t, n) {
        "use strict";
        (function (t) {
            function r(e) {
                return "string" != typeof e && (e = e === undefined || null === e ? "" : "function" == typeof e ? r(e.call(e)) : JSON.stringify(e)), e
            }

            function i(e) {
                var t = "" + e, n = a.exec(t);
                if (!n) return e;
                var r = "", i = void 0, o = void 0, s = void 0;
                for (i = n.index, o = 0; i < t.length; i++) {
                    switch (t.charCodeAt(i)) {
                        case 34:
                            s = "&#34;";
                            break;
                        case 38:
                            s = "&#38;";
                            break;
                        case 39:
                            s = "&#39;";
                            break;
                        case 60:
                            s = "&#60;";
                            break;
                        case 62:
                            s = "&#62;";
                            break;
                        default:
                            continue
                    }
                    o !== i && (r += t.substring(o, i)), o = i + 1, r += s
                }
                return o !== i ? r + t.substring(o, i) : r
            }

            var o = n(0), s = Object.create(o ? t : window), a = /["&'<>]/;
            s.$escape = function (e) {
                return i(r(e))
            }, s.$each = function (e, t) {
                if (Array.isArray(e)) for (var n = 0, r = e.length; n < r; n++) t(e[n], n); else for (var i in e) t(e[i], i)
            }, e.exports = s
        }).call(t, n(4))
    }, function (e, t, n) {
        "use strict";
        var r = Object.prototype.toString, i = function (e) {
            return null === e ? "Null" : r.call(e).slice(8, -1)
        }, o = function s(e, t) {
            var n = void 0, r = i(e);
            if ("Object" === r ? n = Object.create(t || {}) : "Array" === r && (n = [].concat(t || [])), n) {
                for (var o in e) e.hasOwnProperty(o) && (n[o] = s(e[o], n[o]));
                return n
            }
            return e
        };
        e.exports = o
    }, function (e, t, n) {
        "use strict";
        var r = function (e, t, r, i) {
            var o = n(1);
            return i = i.$extend({filename: i.resolveFilename(e, i), bail: !0, source: null}), o(i)(t, r)
        };
        e.exports = r
    }, function (e, t, n) {
        "use strict";
        var r = function (e) {
            console.error(e.name, e.message)
        };
        e.exports = r
    }, function (e, t, n) {
        "use strict";
        var r = {
            __data: Object.create(null), set: function (e, t) {
                this.__data[e] = t
            }, get: function (e) {
                return this.__data[e]
            }, reset: function () {
                this.__data = {}
            }
        };
        e.exports = r
    }, function (e, t, n) {
        "use strict";
        var r = n(0), i = function (e) {
            if (r) {
                return n(5).readFileSync(e, "utf8")
            }
            var t = document.getElementById(e);
            return t.value || t.innerHTML
        };
        e.exports = i
    }, function (e, t, n) {
        "use strict";
        var r = {
            test: /{{([@#]?)[ \t]*(\/?)([\w\W]*?)[ \t]*}}/, use: function (e, t, n, i) {
                var o = this, s = o.options, a = o.getEsTokens(i), c = a.map(function (e) {
                    return e.value
                }), u = {}, p = void 0, l = !!t && "raw", f = n + c.shift(), h = function (t, n) {
                    console.warn((s.filename || "anonymous") + ":" + (e.line + 1) + ":" + (e.start + 1) + "\nTemplate upgrade: {{" + t + "}} -> {{" + n + "}}")
                };
                switch ("#" === t && h("#value", "@value"), f) {
                    case"set":
                        i = "var " + c.join("").trim();
                        break;
                    case"if":
                        i = "if(" + c.join("").trim() + "){";
                        break;
                    case"else":
                        var m = c.indexOf("if");
                        ~m ? (c.splice(0, m + 1), i = "}else if(" + c.join("").trim() + "){") : i = "}else{";
                        break;
                    case"/if":
                        i = "}";
                        break;
                    case"each":
                        p = r._split(a), p.shift(), "as" === p[1] && (h("each object as value index", "each object value index"), p.splice(1, 1));
                        i = "$each(" + (p[0] || "$data") + ",function(" + (p[1] || "$value") + "," + (p[2] || "$index") + "){";
                        break;
                    case"/each":
                        i = "})";
                        break;
                    case"block":
                        p = r._split(a), p.shift(), i = "block(" + p.join(",").trim() + ",function(){";
                        break;
                    case"/block":
                        i = "})";
                        break;
                    case"echo":
                        f = "print", h("echo value", "value");
                    case"print":
                    case"include":
                    case"extend":
                        if (0 !== c.join("").trim().indexOf("(")) {
                            p = r._split(a), p.shift(), i = f + "(" + p.join(",") + ")";
                            break
                        }
                    default:
                        if (~c.indexOf("|")) {
                            var d = a.reduce(function (e, t) {
                                var n = t.value, r = t.type;
                                return "|" === n ? e.push([]) : "whitespace" !== r && "comment" !== r && (e.length || e.push([]), ":" === n && 1 === e[e.length - 1].length ? h("value | filter: argv", "value | filter argv") : e[e.length - 1].push(t)), e
                            }, []).map(function (e) {
                                return r._split(e)
                            });
                            i = d.reduce(function (e, t) {
                                var n = t.shift();
                                return t.unshift(e), "$imports." + n + "(" + t.join(",") + ")"
                            }, d.shift().join(" ").trim())
                        }
                        l = l || "escape"
                }
                return u.code = i, u.output = l, u
            }, _split: function (e) {
                e = e.filter(function (e) {
                    var t = e.type;
                    return "whitespace" !== t && "comment" !== t
                });
                for (var t = 0, n = e.shift(), r = /\]|\)/, i = [[n]]; t < e.length;) {
                    var o = e[t];
                    "punctuator" === o.type || "punctuator" === n.type && !r.test(n.value) ? i[i.length - 1].push(o) : i.push([o]), n = o, t++
                }
                return i.map(function (e) {
                    return e.map(function (e) {
                        return e.value
                    }).join("")
                })
            }
        };
        e.exports = r
    }, function (e, t, n) {
        "use strict";
        var r = {
            test: /<%(#?)((?:==|=#|[=-])?)[ \t]*([\w\W]*?)[ \t]*(-?)%>/, use: function (e, t, n, r) {
                return n = {
                    "-": "raw",
                    "=": "escape",
                    "": !1,
                    "==": "raw",
                    "=#": "raw"
                }[n], t && (r = "/*" + r + "*/", n = !1), {code: r, output: n}
            }
        };
        e.exports = r
    }, function (e, t, n) {
        "use strict";
        var r = n(0), i = function (e, t) {
            if (r) {
                var i, o = n(21).minify, s = t.htmlMinifierOptions, a = t.rules.map(function (e) {
                    return e.test
                });
                (i = s.ignoreCustomFragments).push.apply(i, a), e = o(e, s)
            }
            return e
        };
        e.exports = i
    }, function (e, t) {
        !function (e) {
            e.noop = function () {
            }
        }("object" == typeof e && "object" == typeof e.exports ? e.exports : window)
    }, function (e, t, n) {
        "use strict";
        var r = n(0), i = /^\.+\//, o = function (e, t) {
            if (r) {
                var o = n(5), s = t.root, a = t.extname;
                if (i.test(e)) {
                    var c = t.filename, u = !c || e === c, p = u ? s : o.dirname(c);
                    e = o.resolve(p, e)
                } else e = o.resolve(s, e);
                o.extname(e) || (e += a)
            }
            return e
        };
        e.exports = o
    }, function (e, t, n) {
        "use strict";

        function r(e, t) {
            if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
        }

        function i(e, t) {
            if (!e) throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
            return !t || "object" != typeof t && "function" != typeof t ? e : t
        }

        function o(e, t) {
            if ("function" != typeof t && null !== t) throw new TypeError("Super expression must either be null or a function, not " + typeof t);
            e.prototype = Object.create(t && t.prototype, {
                constructor: {
                    value: e,
                    enumerable: !1,
                    writable: !0,
                    configurable: !0
                }
            }), t && (Object.setPrototypeOf ? Object.setPrototypeOf(e, t) : e.__proto__ = t)
        }

        function s(e) {
            var t = e.name, n = e.source, r = e.path, i = e.line, o = e.column, s = e.generated, a = e.message;
            if (!n) return a;
            var c = n.split(/\n/), u = Math.max(i - 3, 0), p = Math.min(c.length, i + 3),
                l = c.slice(u, p).map(function (e, t) {
                    var n = t + u + 1;
                    return (n === i ? " >> " : "    ") + n + "| " + e
                }).join("\n");
            return (r || "anonymous") + ":" + i + ":" + o + "\n" + l + "\n\n" + t + ": " + a + (s ? "\n   generated: " + s : "")
        }

        var a = function (e) {
            function t(n) {
                r(this, t);
                var o = i(this, e.call(this, n.message));
                return o.name = "TemplateError", o.message = s(n), Error.captureStackTrace && Error.captureStackTrace(o, o.constructor), o
            }

            return o(t, e), t
        }(Error);
        e.exports = a
    }, function (e, t, n) {
        "use strict";
        e.exports = n(3)
    }])
});

//https://github.com/h5bp/html5-boilerplate
(function () {
    var method;
    var noop = function () {
    };
    var methods = ["assert", "clear", "count", "debug", "dir", "dirxml", "error", "exception", "group", "groupCollapsed", "groupEnd", "info", "log", "markTimeline", "profile", "profileEnd", "table", "time", "timeEnd", "timeline", "timelineEnd", "timeStamp", "trace", "warn"];
    var length = methods.length;
    var console = (window.console = window.console || {});
    while (length--) {
        method = methods[length];
        if (!console[method]) {
            console[method] = noop
        }
    }
}());