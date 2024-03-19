<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common-base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <title>皮肤设置</title>
        <link rel="stylesheet" href="${basePath}/static/img/ui/globle.css">
        <link rel="stylesheet" href="${basePath}/static/js/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${basePath}/static/js/HoorayLibs/hooraylibs.css">
        <link rel="stylesheet" href="${basePath}/static/js/webuploader-0.1.5/webuploader.css">
        <link rel="stylesheet" href="${basePath}/static/img/ui/sys.css">
        <script src="${basePath}/static/js/jquery-1.8.3.min.js"></script>
        <script src="${basePath}/static/js/bootstrap/js/bootstrap.min.js"></script>
        <script src="${basePath}/static/js/HoorayLibs/hooraylibs.js"></script>
        <script src="${basePath}/static/js/artDialog4.1.7/jquery.artDialog.js?skin=simple"></script>
        <script src="${basePath}/static/js/artDialog4.1.7/plugins/iframeTools.js"></script>
        <script src="${basePath}/static/js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
        <script>
            $(function () {
                //配置artDialog全局默认参数
                (function (config) {
                    config['lock'] = true;
                    config['fixed'] = true;
                    config['resize'] = false;
                    config['background'] = '#000';
                    config['opacity'] = 0.5;
                })($.dialog.defaults);
                //toolTip
                $('[rel="tooltip"]').tooltip();
                //表单提示
                $("[datatype]").focusin(function () {
                    $(this).parent().addClass('info').children('.infomsg').show().siblings('.help-inline').hide();
                }).focusout(function () {
                    $(this).parent().removeClass('info').children('.infomsg').hide().siblings('.help-inline').show();
                });
                //detailIframe
                openDetailIframe = function (url) {
                    ZENG.msgbox.show('正在载入中，请稍后...', 6, 100000);
                    $('#detailIframe iframe').attr('src', url).load(function () {
                        $('body').css('overflow', 'hidden');
                        ZENG.msgbox._hide();
                        $('#detailIframe').animate({
                            'top': 0,
                            'opacity': 'show'
                        }, 500);
                    });
                };
                closeDetailIframe = function (callback) {
                    $('body').css('overflow', 'auto');
                    $('#detailIframe').animate({
                        'top': '-100px',
                        'opacity': 'hide'
                    }, 500, function () {
                        callback && callback();
                    });
                };
            });
        </script>
        <script>
            $(function () {
                $('.skin li').on('click', function () {
                    $('.skin li').removeClass('selected');
                    $(this).addClass('selected');
                    var skin = $(this).attr('skin');
                    $.ajax({
                        type: 'POST',
                        url: basePath + '/systemSettingController/updateSkin.do',
                        data: 'skin=' + skin,
                        dataType: "json",
                        success: function (data) {
                            window.parent.ZENG.msgbox.show("设置成功，正在切换皮肤，如果长时间没更新，请刷新页面", 4, 5000);
                            window.parent.HROS.base.setSkin(skin, function () {
                                window.parent.ZENG.msgbox._hide();
                            });
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            ajaxErrorHandler(XMLHttpRequest);
                        }
                    });
                });
            });
        </script>
    </head>

    <body>
        <div class="title">
            <ul>
                <li><a href="${basePath}/systemSettingController/index.do">桌面设置</a></li>
                <li><a href="${basePath}/systemSettingController/goWallpaper.do">壁纸设置</a></li>
                <li class="focus">皮肤设置</li>
            </ul>
        </div>
        <ul class="skin">
            <c:forEach items="${skinlist}" var="skin" varStatus="status">
                <c:choose>
                    <c:when test="${skin.name==memberModel.skin}">
                        <li class="selected" skin="${skin.name}"><img src="${basePath}/${skin.img}"
                                                                      style="width:256px;height:156px">
                            <div></div>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li skin="${skin.name}"><img src="${basePath}/${skin.img}" style="width:256px;height:156px">
                            <div></div>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>

    </body>
</html>