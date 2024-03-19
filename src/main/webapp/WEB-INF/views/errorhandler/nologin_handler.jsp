<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common-base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <script>
            if (window.parent == window) {
                window.location.href = NOTFOUNDUSERNAMEPAGEURL;
            } else {
                window.top.location.href = NOTFOUNDUSERNAMEPAGEURL;
            }

        </script>
    </head>

    <body>
    </body>
</html>