<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8" %>
<%
    String startDate = "";
    if (request.getAttribute("startDate") != null)
    {
        startDate = (String) request.getAttribute("startDate");
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="utf-8">
        <script type="text/javascript">
            function goprepage() {
                history.go(-1);
            }
        </script>
        <title>提示</title>

        <style>
            body {
                height: 100%;
                overflow: hidden;
                margin: 0px;
                padding: 0px;
            }

            .logoTitle {
                border: 1px solid #314562;
                width: 100%;
                height: 60px;
                background: #314562;
            }

            .content {
                height: 100%;
                width: 100%;
                position: absolute;
                background-image: url("/static/error/lisence-bg.jpg");
                background-attachment: fixed;
                background-repeat: no-repeat;
                background-size: cover;
                -moz-background-size: cover;
                -webkit-background-size: cover;
            }

            .container {
                height: 198px;
                width: 440px;
                /*border:1px solid #314562;*/
                margin: 0 auto;
                vertical-align: middle;
                margin-top: 200px;
            }

            .fontImageStyle {
                margin: 0 auto;
                width: 77px;
                height: 73px;
            }

            .fontBigStyle {
                font-size: 21px;
                color: #314562;
                margin-top: 22px;
                margin-left: 35px;
                font-weight: bold;
            }

            .fontDateStyle {
                font-size: 14px;
                color: #576369;
                margin-top: 10px;
                margin-left: 141px;
                font-family: "微软雅黑"
            }


        </style>
        <title>提示</title>
    <body>
        <div class="logoTitle">
            <img src="/static/error/logo.png" style="margin-top: 15px;margin-left: 10px;">
        </div>
        <div class="content">
            <div class="container">
                <div class="fontImageStyle">
                    <img class="fontImageStyle" src="/static/error/license-search.png">
                </div>
                <div class="fontBigStyle">${license_msg}</div>

                <%
                    if (!"".equals(startDate))
                    {
                %>
                <div class="fontDateStyle" style="margin-top: 25px;">
                    生效日期： <span>${startDate}</span>
                </div>
                <div class="fontDateStyle">
                    失效日期： <span style="font-weight: bold;">${endDate}</span>
                </div>
                <% }
                %>
            </div>
        </div>
    </body>
    <%-- <body>
        <center>
          <div style="width: 90%;border:1px solid #314562;">

              <img src="/static/error/img/top.png" style="width:100%;"  />
                        <font>${license_msg}</font>
              <font>生效日期：${startDate}</font>
              <font>失效日期：${endDate}</font>
            <!-- <img src="/static/error/img/404.png" style="padding-top:5%;padding-bottom:15%;"  />  -->
          </div>
        </center>


    </body> --%>
</html>