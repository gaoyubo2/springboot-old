<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common-base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <title>壁纸设置</title>
        <script type="text/javascript">
            var basePath = '${pageContext.request.contextPath}';
        </script>
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
        <script src="${basePath}/static/js/webuploader-0.1.5/webuploader.min.js"></script>
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
                var uploader = WebUploader.create({
                    // 选完文件后，是否自动上传。
                    auto: true,
                    // swf文件路径
                    swf: '${basePath}/static/js/webuploader-0.1.5/Uploader.swf',
                    // 文件接收服务端。
                    server: basePath + '/systemSettingController/uploadPwallpaper.do',
                    // 选择文件的按钮。可选。
                    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                    pick: {
                        id: '#upload',
                        multiple: false
                    },
                    // 只允许选择图片文件。
                    accept: {
                        title: 'Images',
                        extensions: 'gif,jpg,jpeg,bmp,png',
                        mimeTypes: 'image/*'
                    }
                });
                uploader.on('beforeFileQueued', function (file) {

                    if ($(".view li").length >= 6) {
                        $.dialog({
                            content: "您已经上传满6张壁纸，可以删除之后再进行上传"
                        });
                        return false;
                    }
                    if (file.size / (1024 * 5) > 1000.4999999999999) {
                        $.dialog({
                            content: '文件大于5M，请压缩后再上传'
                        });
                        return false;
                    }
                });
                uploader.on('uploadSuccess', function (file, cb) {
                    //if(cb.state=="success"){
                    $('.wapppapercustom .view ul').append('<li id="' + cb.tbid + '" style="background:url(${basePath}/' + cb.surl + ')"><a href="javascript:;">删 除</a></li>');
                    window.parent.HROS.wallpaper.update(2, $('#wallpapertype').val(), cb.tbid);
                    uploader.removeFile(file);
                    /* 	}else{
                            $.dialog({
                                content : cb.state
                            });
                        } */
                });
                $('#wallpapertype').on('change', function () {
                    window.parent.HROS.wallpaper.update(0, $('#wallpapertype').val(), '');
                });
                $('.wapppapercustom .view').on('click', 'li', function () {
                    window.parent.HROS.wallpaper.update(2, $('#wallpapertype').val(), $(this).attr('id'));
                });
                $('.wapppapercustom .view').on('click', 'li a', function () {
                    var id = $(this).parent().attr('id');
                    $.ajax({
                        type: 'POST',
                        url: basePath + '/systemSettingController/delPwallpaper.do',
                        data: 'id=' + id,
                        dataType: "json",
                        success: function (data) {
                            var msg = data.code;
                            if (msg != 0) {
                                $('#' + id).remove();
                            } else {
                                ZENG.msgbox.show('当前壁纸正在使用，删除失败！', 5, 2000);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            ajaxErrorHandler(XMLHttpRequest);
                        }
                    });
                    return false;
                });
                $('.wapppaperwebsite button').on('click', function () {
                    if ($("#wallpaperurl").val() == null || $("#wallpaperurl").val() == '') {
                        $.dialog({
                            content: '请输入网络壁纸链接！'
                        });
                        return;
                    }
                    window.parent.HROS.wallpaper.update(3, $('#wallpapertype').val(), $('#wallpaperurl').val());
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
                <a class="btn" href="${basePath}/systemSettingController/goWallpaper.do">系统壁纸</a><a
                    class="btn disabled">自定义</a>
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
        <div class="wapppapercustom">
            <div class="tip">
                <a href="javascript:;" id="upload" class="btn btn-mini fr" style="position:relative">上传壁纸</a>
                <strong>自定义壁纸：</strong>最多上传6张，每张上传的壁纸大小不超过5M
            </div>
            <div class="view">
                <ul>
                    <c:forEach items="${pwallpaperModellist}" var="pwallpaper" varStatus="status">
                        <li id="${pwallpaper.tbid}" style="background:url(${basePath}/${pwallpaper.url})"><a
                                href="javascript:;">删 除</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <!-- <div class="wapppaperwebsite form-inline">
            <label>网络壁纸：</label>
            <div class="input-append">
                <input type="text" id="wallpaperurl" style="width:350px" placeholder="请输入一个URL地址（建议以 jpg, jpeg, png, gif, html, htm 结尾）" value=""><button type="button" class="btn">应用</button>
            </div>
        </div> -->
    </body>
</html>