<%@ page language="java" import="cn.cestc.os.desktop.model.AppCategoryModel" pageEncoding="UTF-8" %>
<%@page import="cn.cestc.os.desktop.model.AppModel" %>
<%@page import="java.util.List" %>
<%
    AppModel app = (AppModel) request.getAttribute("appModel");
    List<AppCategoryModel> appEditList = (List<AppCategoryModel>) request.getAttribute("appMyList");
%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8">
        <title>应用管理</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/globle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/sys.css">
    </head>

    <body>
        <form action="${pageContext.request.contextPath}/appManageController/save.do" method="post" name="form"
              id="form">
            <input type="hidden" name="id" value="<%=app.getTbid()%>">
            <div class="creatbox">
                <div class="middle">
                    <p class="detile-title">编辑应用</p>
                    <div class="input-label">
                        <label class="label-text">应用图片：</label>
                        <div class="label-box form-inline control-group">
                            <div class="shortcutview">
                                <%
                                    if (app.getTbid() > 0)
                                    {
                                %><img
                                    src="${pageContext.request.contextPath}/<%=app.getIcon()%>"><%}%>
                            </div>
                            <a href="javascript:;" id="upload" class="btn fl"
                               style="position: relative">选择图片</a>
                            <div class="shortcut-selicon">
                                <div class="title">系统推荐的图标：</div>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-gear.png"
                                        valsrc="static/img/ui/system-gear.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-users.png"
                                        valsrc="static/img/ui/system-users.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-wrench.png"
                                        valsrc="static/img/ui/system-wrench.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-star.png"
                                        valsrc="static/img/ui/system-star.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-shapes.png"
                                        valsrc="static/img/ui/system-shapes.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-chart-bar.png"
                                        valsrc="static/img/ui/system-chart-bar.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-document-edit.png"
                                        valsrc="static/img/ui/system-document-edit.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-documents.png"
                                        valsrc="static/img/ui/system-documents.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-mail.png"
                                        valsrc="static/img/ui/system-mail.png"></a>
                                <a href="javascript:;"><img
                                        src="${pageContext.request.contextPath}/static/img/ui/system-puzzle.png"
                                        valsrc="static/img/ui/system-puzzle.png"></a>
                            </div>
                            <input type="hidden" name="val_icon" id="val_icon" value="<%=app.getIcon()%>" datatype="*"
                                   nullmsg="请选择或上传应用图片">
                            <span id="icon_error" class="help-inline" style="color:#b94a48"></span>
                        </div>
                    </div>
                    <div class="input-label">
                        <label class="label-text">应用名称：</label>
                        <div class="label-box form-inline control-group">
                            <input id="val_name" type="text" class="text" name="val_name" value="<%=app.getName()%>"
                                   datatype="s" nullmsg="请输入应用名称">
                            <span id="name_error" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="input-label">
                        <label class="label-text">应用分类：</label>
                        <div class="label-box form-inline control-group">
                            <select name="val_app_category_id" datatype="*" nullmsg="请选择应用分类">
                                <option value="">--请选择--</option>
                                <%
                                    for (AppCategoryModel vo2 : appEditList)
                                    {
                                        if (app.getApp_category_id() == vo2.getTbid())
                                        {%>
                                <option value="<%=vo2.getTbid()%>" selected="selected"><%=vo2.getName() %>
                                </option>
                                <% } else
                                {%>
                                <option value="<%=vo2.getTbid()%>"><%=vo2.getName() %>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="input-label">
                        <label class="label-text">应用地址：</label>
                        <div class="label-box form-inline control-group">
                            <input type="text" name="val_url_temp" maxlength="1000" value="<%=app.getUrl()%>"
                                   style="width:300px" datatype="url" nullmsg="请输入应用地址">
                            <span class="help-inline"></span>
                            <input type="hidden" name="val_url" id="val_url" value="<%=app.getUrl()%>">
                        </div>
                    </div>
                    <div class="input-label input-label-windowval" ${appModel.isopenmax==1?'style="display:none"':''}>
                        <label class="label-text">窗口大小：</label>
                        <div class="label-box form-inline control-group">
                            <div class="input-prepend input-append">
                                <span class="add-on">宽</span><input id="val_width" maxlength="4" type="text"
                                                                    name="val_width" value="<%=app.getWidth()%>"
                                                                    style="width:40px" datatype="n"
                                                                    nullmsg="请输入应用宽高"><span class="add-on">px</span>
                            </div>
                            <div class="input-prepend input-append" style="margin-left:10px">
                                <span class="add-on">高</span><input id="val_height" maxlength="4" type="text"
                                                                    name="val_height" value="<%=app.getHeight()%>"
                                                                    style="width:40px" datatype="n"
                                                                    nullmsg="请输入应用宽高"><span class="add-on">px</span>
                            </div>
                            <span id="checkNum" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="input-label input-label-isresize">
                        <label class="label-text">窗口是否拉伸：</label>
                        <div class="label-box form-inline control-group">
                            <label class="radio" style="margin-right:10px"><input type="radio" name="val_isresize"
                                                                                  value="1"
                                                                                  <%if(app.getIsresize() == 1){%>checked<%}%>>是</label>
                            <label class="radio"><input type="radio" name="val_isresize" value="0"
                                                        <%if(app.getIsresize() == 0){%>checked<%}%>>否</label>
                        </div>
                    </div>
                    <div class="input-label input-label-isopenmax">
                        <label class="label-text">打开默认最大化：</label>
                        <div class="label-box form-inline control-group">
                            <label class="radio" style="margin-right:10px"><input type="radio" name="val_isopenmax"
                                                                                  value="1"
                                                                                  <%if(app.getIsopenmax() != null && app.getIsopenmax() == 1){%>checked<%}%>>是</label>
                            <label class="radio"><input type="radio" name="val_isopenmax" value="0"
                                                        <%if(app.getIsopenmax() == 0){%>checked<%}%>>否</label>
                        </div>
                    </div>
                    <div class="input-label">
                        <label class="label-text">应用介绍：</label>
                        <div class="label-box form-inline control-group">
                            <textarea id="val_remark" class="textarea" name="val_remark"
                                      style="width:300px;height:100px;margin-bottom:10px"><%=app.getRemark()%></textarea>
                            <span id="checkremark" class="help-inline" style="color:#B94A48;"></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="bottom-bar">
                <div class="con">
                    <%
                        if (app.getVerifytype() == 2 || app.getVerifytype() == 3)
                        {
                    %>
                    <a class="btn btn-success fl" id="btn-pass" href="javascript:;" appid="<%=app.getTbid()%>"><i
                            class="icon-white icon-ok"></i>审核通过</a>
                    <a class="btn fl" id="btn-unpass" href="javascript:;" appid="<%=app.getTbid()%>"
                       style="margin-left: 10px"><i class="icon-remove"></i> 审核不通过</a>
                    <a class="btn" id="btn-preview" href="javascript:;" style="margin-left: 10px"><i
                            class="icon-eye-open"></i> 预览应用</a>
                    <%
                    } else
                    {
                    %>
                    <a class="btn" id="btn-preview" href="javascript:;"><i class="icon-eye-open"></i> 预览应用</a>
                    <%}%>
                    <a class="btn btn-primary fr" id="btn-submit" href="javascript:;">
                        <i class="icon-white icon-ok"></i> 确定</a>
                    <a class="btn fr" href="javascript:window.parent.closeDetailIframe();" style="margin-right: 10px">
                        <i class="icon-chevron-up"></i> 返回应用列表</a>
                </div>
            </div>
        </form>
        <div id="unpassinfo" class="form-inline"
             style="display: none; width: 300px">
            <div>拒绝审核通过理由：</div>
            <label class="radio" style="margin-right: 10px">
                <input type="radio" name="unpassinfo" value="1" class="unpass" checked="checked">信息不完整</label>
            <label class="radio" style="margin-right: 10px"><input type="radio" name="unpassinfo" value="2"
                                                                   class="unpass">应用已存在</label>
            <label class="radio" style="margin-right: 10px"><input type="radio" name="unpassinfo" value="3"
                                                                   class="unpass">内容低俗</label>
        </div>
        <script src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/artDialog4.1.7/jquery.artDialog.js?skin=simple"></script>
        <script src="${pageContext.request.contextPath}/static/js/artDialog4.1.7/plugins/iframeTools.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
        <script>
            /* $(document).on('mousedown',function(Event){
                if (1 === Event.button) Event.preventDefault()
            })
             */
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
        <script src="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.min.js"></script>
        <script>
            $(function () {
                var uploader = WebUploader.create({
                    // 选完文件后，是否自动上传。
                    auto: true,
                    // swf文件路径
                    swf: '${pageContext.request.contextPath}/static/js/webuploader-0.1.5/Uploader.swf',
                    // 文件接收服务端。
                    server: '${pageContext.request.contextPath}/appManageController/uploadImg.do',
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
                    if (file.size / 1024 > 300.499999999999) {
                        $.dialog({
                            content: '文件大于300Kb，请压缩后再上传'
                        });
                        return false;
                    } else {
                        $('.shortcutview img').remove();
                        $('#val_icon').val('');
                    }
                });
                uploader.on('fileQueued', function (file) {
                    var $img = $('<img>');
                    $('.shortcutview').append($img);
                    // 创建缩略图
                    uploader.makeThumb(file, function (error, src) {
                        if (error) {
                            $img.replaceWith('');
                            return;
                        }
                        $img.attr('src', src);
                    }, 48, 48);
                });
                uploader.on('uploadSuccess', function (file, cb) {
                    $('.shortcutview img').attr('src', '${pageContext.request.contextPath}/' + cb.url);
                    $('#val_icon').val(cb.url);
                    uploader.removeFile(file);
                });
                var form = $('#form').Validform({
                    btnSubmit: '#btn-submit',
                    postonce: false,
                    showAllError: true,
                    //msg：提示信息;
                    //o:{obj:*,type:*,curform:*}, obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4， 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
                    //cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
                    tiptype: function (msg, o) {
                        if (!o.obj.is('form')) {//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
                            var B = o.obj.parents('.control-group');
                            var T = B.children('.help-inline');
                            if (o.type == 2) {
                                B.removeClass('error');
                                T.text('');
                            } else {
                                B.addClass('error');
                                T.text(msg);
                            }

                            if (o.obj.attr("name") == "val_name") {
                                if (!T.text()) {
                                    var s = $("#val_name").val();
                                    if (s.indexOf(" ") >= 0) {
                                        s = s.replace(/\s+/g, "");
                                        $("#val_name").val(s);
                                    }
                                    if (getByteLen(s) > 20) {
                                        B.addClass('warning');
                                        $("#val_name").val(getShortForm(s, 20));
                                        T.text("输入的字节总长度不能超过20");
                                    } else {
                                        B.removeClass('warning');
                                        T.text('');
                                    }
                                }
                            }

                            if (o.obj.attr("name") == "val_width") {
                                if (!T.text()) {
                                    //var width=window.screen.availWidth;
                                    var width = 1366;
                                    if ($("input[name='val_width']").val() > width) {
                                        B.addClass('warning');
                                        $("input[name='val_width']").val(width);
                                        T.text("宽度不能大于" + width);
                                    } else {
                                        B.removeClass('warning');
                                        T.text('');
                                    }
                                }
                            }

                            if (o.obj.attr("name") == "val_height") {
                                if (!T.text()) {
                                    //var height=window.screen.availHeight-60;
                                    var height = 668;
                                    if ($("input[name='val_height']").val() > height) {
                                        B.addClass('warning');
                                        $("input[name='val_height']").val(height);
                                        T.text("高度不能大于" + height);
                                    } else {
                                        B.removeClass('warning');
                                        T.text('');
                                    }
                                }
                            }

                            if (o.obj.attr("name") == "val_url_temp") {
                                var val_url_temp_value = $("input[name='val_url_temp']").val();
                                if (val_url_temp_value.indexOf("http://") != 0 && val_url_temp_value.indexOf("https://") != 0) {
                                    $("#val_url").val("http://" + val_url_temp_value);
                                } else {
                                    $("#val_url").val(val_url_temp_value);
                                }
                            }
                        }
                    },
                    ajaxPost: true,
                    callback: function (data) {
                        if ($('input[name="id"]').val() != '0') {
                            if (data.status == 'y') {
                                $.dialog({
                                    id: 'ajaxedit',
                                    content: '修改成功，是否继续修改？',
                                    okVal: '是',
                                    ok: function () {
                                        $.dialog.list['ajaxedit'].close();
                                    },
                                    cancel: function () {
                                        window.parent.closeDetailIframe(function () {
                                            window.parent.$('#pagination').trigger('currentPage');
                                        });
                                    }
                                });
                            }
                        } else {
                            if (data.status == 'y') {
                                $.dialog({
                                    id: 'ajaxedit',
                                    content: '添加成功，是否继续添加？',
                                    okVal: '是',
                                    ok: function () {
                                        location.reload();
                                        return false;
                                    },
                                    cancel: function () {
                                        window.parent.closeDetailIframe(function () {
                                            window.parent.$('#pagination').trigger('currentPage');
                                        });
                                    }
                                });
                            }
                        }
                    }
                });
                //$('input[name="val_isresize"]').change(function(){
                //	if($(this).val() == '1'){
                //		$('.input-label-isopenmax').slideDown();
                //	}else{
                //		$('.input-label-isopenmax').slideUp();
                //	}
                //});
                //选择应用图片
                $('.shortcut-selicon a').click(function () {
                    $('.shortcutview img').remove();
                    $('.shortcutview').append($(this).html());
                    $('#val_icon').val($(this).children('img').attr('valsrc'));
                });
                $('#btn-pass').on('click', function () {
                    var appid = $(this).attr('appid');
                    $.dialog({
                        id: 'del',
                        content: '确认审核通过该应用？',
                        ok: function () {
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/appManageController/pass.do',
                                data: 'appid=' + appid
                            }).done(function () {
                                window.parent.closeDetailIframe(function () {
                                    window.parent.$('#pagination').trigger('currentPage');
                                });
                            });
                        },
                        cancel: true
                    });
                });
                $('#btn-unpass').on('click', function () {
                    var appid = $(this).attr('appid');
                    var uinfo = '信息不完整';
                    $('.unpass').change(function () {
                        var $selectedvalue = $("input[name='unpassinfo']:checked").val();
                        // alert($selectedvalue);
                        if ($selectedvalue == 1) {
                            uinfo = "信息不完整";
                        } else if ($selectedvalue == 2) {
                            uinfo = "应用已存在";
                        } else {
                            uinfo = "内容低俗";
                        }
                    });
                    $.dialog({
                        id: 'del',
                        content: document.getElementById('unpassinfo'),
                        ok: function () {
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/appManageController/unPass.do',
                                data: 'appid=' + appid + '&info=' + uinfo
                            }).done(function () {
                                window.parent.closeDetailIframe(function () {
                                    window.parent.$('#pagination').trigger('currentPage');
                                });
                            });
                        },
                        cancel: true
                    });
                });
                $('#btn-preview').on('click', function () {
                    if (form.check()) {
                        window.top.HROS.window.createTemp({
                            title: $('input[name="val_name"]').val(),
                            url: $('input[name="val_url"]').val(),
                            width: $('input[name="val_width"]').val(),
                            height: $('input[name="val_height"]').val(),
                            isresize: $('input[name="val_isresize"]:checked').val() == 1 ? true : false,
                            isopenmax: $('input[name="val_isopenmax"]:checked').val() == 1 ? true : false
                        });
                    } else {
                        $.dialog({
                            icon: 'error',
                            content: '应用无法预览，请将内容填写完整后再尝试预览'
                        });
                    }
                });

                $('#btn-submit').on('click', function () {
                    if ($("#icon_error").text()) {
                        $(".middle").animate({scrollTop: 0}, 'slow');
                    }
                });
            });

            $("#val_width").blur(function () {
                var s = $("#val_width").val();
                var reg = new RegExp("^[0-9]*$");
                if (!reg.test(s)) {
                    $("#val_width").val("");
                    return;
                }
            });

            $("#val_height").blur(function () {
                var s = $("#val_height").val();
                var reg = new RegExp("^[0-9]*$");
                if (!reg.test(s)) {
                    $("#val_height").val("");
                    return;
                }
            });

            $("#val_remark").blur(function () {
                if ($("#val_remark").val().length > 256) {
                    var subs = $("#val_remark").val().substring(0, 253);
                    $("#val_remark").val(subs + "...");
                    $("#checkremark").html("最多256个字符");
                } else {
                    $("#checkremark").html("");
                }
            });

            $('input[name="val_isopenmax"]').change(function () {
                if ($(this).val() == '0') {
                    $('.input-label-windowval').slideDown();
                } else {
                    $('.input-label-windowval').slideUp();
                }
            });


            /**
             * description : 得到字符串的字节长度;
             * @version 0.2;
             * @return 返回字符串的字节长度(eg:"一二12"的字节长度是6);
             */
            function getByteLen(str) {
                var text = str.replace(/[^\x00-\xff]/g, "**");
                return text.length;
            }

            /**
             * description : 按字节长度截取字符串,并添加后缀.
             * @param len 需要截取的长度,字符串长度不足返回本身;
             * @return 返回截取后的字符串;
             * @requires getLength;
             */
            function getShortForm(str, len) {
                var tempStr = str;
                if (getByteLen(str) > len) {
                    var i = 0;
                    for (var z = 0; z < len; z++) {
                        if (tempStr.charCodeAt(z) > 255) {
                            i = i + 2;
                        } else {
                            i = i + 1;
                        }
                        if (i >= len) {
                            tempStr = tempStr.slice(0, (z + 1));
                            break;
                        }
                    }
                    return tempStr;
                } else {
                    return str;
                }
            }
        </script>
    </body>
</html>