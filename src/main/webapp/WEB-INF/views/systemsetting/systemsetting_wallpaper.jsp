<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common-base.jsp" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>壁纸设置</title>
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
                $("#wallpapertype").on('change', function () {
                    window.parent.HROS.wallpaper.update(0, $('#wallpapertype').val(), '');
                });
                $('.wallpaper li').on('click', function () {
                    window.parent.HROS.wallpaper.update(1, $('#wallpapertype').val(), $(this).attr('wpid'));
                });
            });
        </script>

    </head>

    <body>
        <div class="title">
            <ul>
                <li><a href="${basePath}/systemSettingController/index.do">桌面设置</a></li>
                <li class="focus">壁纸设置</li>
                <li><a href="${basePath}/systemSettingController/skinindex.do">皮肤设置</a></li>
            </ul>
        </div>
        <div class="wallpapertype form-inline">
            <div class="btn-group fl">
                <a class="btn disabled">系统壁纸</a><a class="btn"
                                                   href="${basePath}/systemSettingController/customindex.do">自定义</a>
            </div>
            <div class="fr">
                <label>显示方式：</label>
                <select name="wallpapertype" id="wallpapertype" style="width:100px">
                    <%-- <option value="tianchong" ${memberModel.wallpapertype == "tianchong"? "selected":""} >填充</option> --%>
                    <option value="shiying" ${memberModel.wallpapertype == "shiying"? "selected":""}>适应</option>
                    <option value="pingpu" ${memberModel.wallpapertype == "pingpu"? "selected":""}>平铺</option>
                    <option value="lashen" ${memberModel.wallpapertype == "lashen"? "selected":""}>拉伸</option>
                    <option value="juzhong" ${memberModel.wallpapertype == "juzhong"? "selected":""}>居中</option>
                </select>
            </div>
        </div>
        <ul class="wallpaper">
            <c:forEach items="${wallpaperList}" var="wallpaper" varStatus="status">
                <c:choose>
                    <c:when test="${status.count%3==2}">
                        <li class="three" wpid="${wallpaper.tbid}">
                    </c:when>
                    <c:otherwise>
                        <li wpid="${wallpaper.tbid}">
                    </c:otherwise>
                </c:choose>
                <img src="${basePath}/${wallpaper.url}">
                <div>${wallpaper.title}</div>
                </li>
            </c:forEach>
        </ul>
    </body>
</html>