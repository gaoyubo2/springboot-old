<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <title>网站设置</title>
        <script type="text/javascript">
            var basePath = '${pageContext.request.contextPath}';
        </script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/globle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/sys.css">
        <script src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/artDialog4.1.7/jquery.artDialog.js?skin=simple"></script>
        <script src="${pageContext.request.contextPath}/static/js/artDialog4.1.7/plugins/iframeTools.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
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
                $('#form').Validform({
                    btnSubmit: '#form-submit',
                    postonce: false,
                    showAllError: true,
                    //msg：提示信息;
                    //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
                    //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
                    tiptype: function (msg, o) {
                        if (!o.obj.is('form')) {//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
                            var B = o.obj.parents('.control-group');
                            var T = B.children('.errormsg');
                            if (o.type == 2) {
                                B.removeClass('error');
                                T.text('');
                            } else {
                                B.addClass('error');
                                T.text(msg);
                            }
                        }
                    },
                    ajaxPost: true,
                    callback: function (data) {
                        if (data.msg == 'y') {
                            ZENG.msgbox.show('设置已保存，页面刷新后生效！', 4, 2000);
                        }
                    }
                });
            });
        </script>

    </head>

    <body>
        <div class="title">
            <ul>
                <li class="focus">网站设置</li>
            </ul>
        </div>
        <form action="${pageContext.request.contextPath}/webSettingController/websettingUpdate.do" method="post"
              name="form" id="form">
            <input type="hidden" name="ac" value="edit">
            <div class="input-label">
                <div class="label-text">网站标题：</div>
                <div class="label-box form-inline control-group">
                    <input type="text" name="val_title" style="width:250px" value="${settingModel.title}" datatype="*"
                           nullmsg="请填写网站标题">
                    <span class="help-inline errormsg"></span>
                </div>
            </div>
            <div class="input-label">
                <div class="label-text">SEO关键词：</div>
                <div class="label-box form-inline control-group">
                    <input type="text" name="val_keywords" style="width:250px" value="${settingModel.keywords}"
                           datatype="*" nullmsg="请填写SEO关键字">
                    <p class="help-inline infomsg" style="display:none">推荐写法：“关键词1,关键词2,关键词3”，必须为英文逗号，不超过100字符</p>
                    <p class="help-inline errormsg"></p>
                </div>
            </div>
            <div class="input-label">
                <div class="label-text">SEO描述信息：</div>
                <div class="label-box form-inline control-group">
                    <input type="text" name="val_description" style="width:250px" value="${settingModel.description}"
                           datatype="*" nullmsg="请填写SEO描述信息">
                    <p class="help-inline infomsg" style="display:none">推荐写法：尽量把关键词重复2-3次</p>
                    <p class="help-inline errormsg"></p>
                </div>
            </div>
            <div class="input-label" style="background:none;padding-left:0;text-align:center">
                <a class="btn" id="form-submit" href="javascript:;">应用</a>
            </div>
        </form>
    </body>
</html>