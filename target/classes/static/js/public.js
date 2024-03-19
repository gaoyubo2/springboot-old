//var NOTFOUNDUSERNAMEPAGEURL="http://172.18.1.67/cas-server-webapp-3.5.2/login?service="+ getRootPath() + "/bdos-cas";

var LOGOUTPATH = getRootPath() + "/logout";

var NOTFOUNDUSERNAMEPAGEURL = LOGOUTPATH;

var NOTFOUNDUSERNAMECODE = "USERNAMENOTFOUND";

var THISAPPISNOWITHINUSERCODE = "THISAPPISNOWITHINUSER";

var THISAPPISNOWITHINUSERURL = getRootPath();

//Ajax请求失败处理方式
function ajaxErrorHandler(XMLHttpRequest) {
    if (XMLHttpRequest.responseText == NOTFOUNDUSERNAMECODE) {
        window.top.location.href = NOTFOUNDUSERNAMEPAGEURL;
    } else if (XMLHttpRequest.responseText == THISAPPISNOWITHINUSERCODE) {
        window.top.location.href = THISAPPISNOWITHINUSERURL;
    } else if (XMLHttpRequest.status == 502) {
        ZENG.msgbox.show("网络连接中断，请检查网络！", 5, 3000);
    }
}

function getRootPath() {
    //获取当前网址，
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    if (pathName == "/") {
        pos = curWwwPath.lastIndexOf(pathName);
    }
    //获取主机地址
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：
    var projectName = "";
    if (pathName == "/" || pathName == "" || pathName.substring(0, "/errorHadnlerController".length) == "/errorHadnlerController") {
        projectName = "";
    } else {
        projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    }
    return (localhostPaht + projectName);
}