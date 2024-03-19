<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="cn.cestc.os.desktop.model.MemberAppModel" %>
<%
    MemberAppModel memberAppModel = (MemberAppModel) request.getAttribute("memberAppModel");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <title>编辑私人应用</title>
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
        <script src="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.min.js"></script>
        <script>
            /* $(document).on('mousedown',function(Event){
                if (1 === Event.button) Event.preventDefault()
            }) */
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

                /* $("input[name='val_width']").blur(function(){
                      alert($("input[name='val_width']").val());
                        var B=$('.control-group');
                        var T = $('.help-inline');


                    }); */
            });


        </script>
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
                    if (file.size / 1024 > 300.49999999999999) {
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
                $('#form').Validform({
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
                        if (data.result == 'y') {
                            window.parent.HROS.app.get();
                            window.parent.$.dialog.list['editdialog'].close();
                        }
                    },
                    datatype: {
                        "nameexist": function (gets, obj, curform, regxp) {
                            //参数gets是获取到的表单元素值，obj为当前表单元素，curform为当前验证的表单，regxp为内置的一些正则表达式的引用。
                            return checkNameIsExist();
                        }
                    }
                });
                $('input[name="val_type"]').change(function () {
                    if ($(this).val() == 'pwindow') {
                        $('.input-label-isresize, .input-label-isopenmax, .input-label-isflash').slideDown();
                    } else {
                        $('input[name="val_isresize"]').each(function () {
                            if ($(this).val() == '1') {
                                $(this).prop('checked', true);
                            }
                        });
                        $('input[name="val_isopenmax"]').each(function () {
                            if ($(this).val() == '0') {
                                $(this).prop('checked', true);
                            }
                        });
                        $('input[name="val_isflash"]').each(function () {
                            if ($(this).val() == '0') {
                                $(this).prop('checked', true);
                            }
                        });
                        $('.input-label-isresize, .input-label-isopenmax, .input-label-isflash').slideUp();
                    }
                });
                //$('input[name="val_isresize"]').change(function(){
                //	if($(this).val() == '1'){
                //		$('.input-label-isopenmax').slideDown();
                //	}else{
                //		$('.input-label-isopenmax').slideUp();
                //	}
                //});

                $('input[name="val_isopenmax"]').change(function () {
                    if ($(this).val() == '0') {
                        $('.input-label-windowval').slideDown();
                    } else {
                        $('.input-label-windowval').slideUp();
                    }
                });


                //选择应用图片
                $('.shortcut-selicon a').click(function () {
                    $('.shortcutview img').remove();
                    $('.shortcutview').append($(this).html());
                    $('#val_icon').val($(this).children('img').attr('valsrc')).focusout();
                });

                $('#btn-submit').on('click', function () {
                    if ($("#icon_error").text()) {
                        $(".middle").animate({scrollTop: 0}, 'slow');
                    }
                });

                function checkNameIsExist() {
                    var memappIsExist = false;
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/desktopSettingController/memappIsExist.do",
                        data: "name=" + $("#val_name").val() + "&type=pwindow",
                        dataType: "json",
                        async: false,
                        success: function (data) {
                            if (data.result && data.result == 1 && $("#val_name").val() != $("#memberAppModelNameHidden").val()) {
                                memappIsExist = true;
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            ajaxErrorHandler(XMLHttpRequest);
                        }
                    })
                    if (memappIsExist) {
                        return "私人应用名称存在，请重新输出";
                    } else {
                        return true;
                    }
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
        <style type="text/css">
            .creatbox .middle {
                bottom: 47px
            }

            .bottom-bar {
                height: 48px
            }

            .bottom-bar .con {
                height: 28px;
                background: #fff
            }
        </style>
    </head>

    <body>
        <form action="${pageContext.request.contextPath}/appManageController/editdialog.do" method="post" name="form"
              id="form">
            <input type="hidden" name="id" value="${memberAppModel.tbid}">
            <input type="hidden" name="desk" value="${desk}">
            <div class="creatbox">
                <div class="middle">
                    <div class="input-label">
                        <label class="label-text">应用图片：</label>
                        <div class="label-box form-inline control-group">
                            <div class="shortcutview">
                                <%
                                    if (!memberAppModel.getIcon().equals(""))
                                    {
                                %>
                                <img src="${pageContext.request.contextPath}/${memberAppModel.icon}">
                                <%
                                    }
                                %>
                            </div>
                            <a href="javascript:;" id="upload" class="btn fl" style="position:relative">选择图片</a>
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
                            <input type="hidden" name="val_icon" id="val_icon" value="${memberAppModel.icon}"
                                   datatype="*" nullmsg="请选择或上传应用图片">
                            <span id="icon_error" class="help-inline" style="color:#b94a48"></span>
                        </div>
                    </div>
                    <div class="input-label">
                        <label class="label-text">应用名称：</label>
                        <div class="label-box form-inline control-group">
                            <input type="text" class="text" name="val_name" id="val_name" value="${memberAppModel.name}"
                                   datatype="s,nameexist" nullmsg="请输入应用名称">
                            <span id="checkremark" class="help-inline"></span>
                            <input id="memberAppModelNameHidden" type="hidden" value="${memberAppModel.name}"/>
                        </div>
                    </div>
                    <div class="input-label">
                        <label class="label-text">应用地址：</label>
                        <div class="label-box form-inline control-group">
                            <input type="text" name="val_url_temp" value="${memberAppModel.url}" style="width:300px"
                                   datatype="url" nullmsg="请输入应用地址">
                            <span class="help-inline"></span>
                            <input type="hidden" name="val_url" id="val_url" value="${memberAppModel.url}">
                        </div>
                    </div>
                    <div class="input-label input-label-windowval" ${memberAppModel.isopenmax==1?'style="display:none"':''}>
                        <label class="label-text">窗口大小：</label>
                        <div class="label-box form-inline control-group">
                            <div class="input-prepend input-append">
                                <span class="add-on">宽</span><input type="text" name="val_width"
                                                                    value="${memberAppModel.width==null?900:memberAppModel.width}"
                                                                    style="width:40px" datatype="n" nullmsg="请输入应用宽高"
                                                                    errormsg="宽高数值不规范"><span class="add-on">px</span>
                            </div>
                            <div class="input-prepend input-append" style="margin-left:10px">
                                <span class="add-on">高</span><input type="text" name="val_height"
                                                                    value="${memberAppModel.height==null?600:memberAppModel.height}"
                                                                    style="width:40px" datatype="n" nullmsg="请输入应用宽高"
                                                                    errormsg="宽高数值不规范"><span class="add-on">px</span>
                            </div>
                            <span class="help-inline"></span>
                        </div>
                    </div>
                    <%-- 	 <div class="input-label">
                            <label class="label-text">应用类型：</label>
                            <div class="label-box form-inline control-group">
                                <label class="radio" style="margin-right:10px"><input type="radio" name="val_type" value="pwindow" ${memberAppModel.type=='pwindow'?'checked':''}>窗口</label>
                            </div>
                        </div>  --%>
                    <input type="hidden" name="val_type"
                           value="pwindow" ${memberAppModel.type=='pwindow'?'checked':''} />
                    <div class="input-label input-label-isresize"  ${memberAppModel.type=='pwidget'?'style="display:none"':''}>
                        <label class="label-text">窗口是否拉伸：</label>
                        <div class="label-box form-inline control-group">
                            <label class="radio" style="margin-right:10px"><input type="radio" name="val_isresize"
                                                                                  value="1" ${memberAppModel.isresize==1?'checked':''}>是</label>
                            <label class="radio"><input type="radio" name="val_isresize"
                                                        value="0" ${memberAppModel.isresize==0?'checked':''}>否</label>
                        </div>
                    </div>
                    <div class="input-label input-label-isopenmax" ${memberAppModel.type=='pwidget'?'style="display:none"':memberAppModel.isresize=='0'?'style="display:none"':''}>
                        <label class="label-text">打开默认最大化：</label>
                        <div class="label-box form-inline control-group">
                            <label class="radio" style="margin-right:10px"><input type="radio" name="val_isopenmax"
                                                                                  value="1" ${memberAppModel.isopenmax==1?'checked':''}>是</label>
                            <label class="radio"><input type="radio" name="val_isopenmax"
                                                        value="0" ${memberAppModel.isopenmax==0?'checked':''}>否</label>
                        </div>
                    </div>
                    <%-- <div class="input-label input-label-isflash" ${memberAppModel.type=='pwidget'?'style="display:none"':''}>
                        <label class="label-text">是否为Flash：</label>
                        <div class="label-box form-inline control-group">
                            <label class="radio" style="margin-right:10px"><input type="radio" name="val_isflash" value="1" ${memberAppModel.isflash==1?'checked':''}>是</label>
                            <label class="radio" style="margin-right:10px"><input type="radio" name="val_isflash" value="0" ${memberAppModel.isflash==0?'checked':''}>否</label>
                            <span class="txt">[<a href="javascript:;" rel="tooltip" title="如果设置为Flash应用，当窗口非当前窗口时，会显示遮罩层">?</a>]</span>
                        </div>
                    </div> --%>
                </div>
            </div>
            <div class="bottom-bar">
                <div class="con">
                    <a class="btn fr" href="javascript:window.parent.$.dialog.list['editdialog'].close();"><i
                            class="icon-remove"></i> 关闭</a>
                    <a class="btn btn-primary fr" id="btn-submit" href="javascript:;" style="margin-right:10px"><i
                            class="icon-white icon-ok"></i> 确定</a>
                </div>
            </div>
        </form>
    </body>
</html>

