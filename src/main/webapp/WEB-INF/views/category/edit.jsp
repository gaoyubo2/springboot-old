<%@page import="cn.cestc.os.desktop.model.AppCategoryModel" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<% AppCategoryModel appEditor = (AppCategoryModel) request.getAttribute("AppEditor");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>用户管理</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/globle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/sys.css">
    </head>

    <body>
        <form action="${pageContext.request.contextPath}/categoryManageController/saveOrUpdate.do" method="post"
              name="form" id="form">
            <input type="hidden" name="ac" value="edit">
            <input type="hidden" name="id" value="<%=appEditor.getTbid()%>">
            <div class="creatbox">
                <div class="middle">
                    <p class="detile-title">
                        <strong><%=appEditor.getName() == null ? "添加" : appEditor.getName().equals("") ? "添加" : "编辑" %>
                            类目</strong>
                    </p>
                    <div class="input-label">
                        <label class="label-text">类目名称：</label>
                        <div class="label-box form-inline control-group">
                            <input type="text" id="val_name" name="val_name" value="<%=appEditor.getName() %>"
                                   maxlength="20" datatype="s,fourbyte" nullmsg="请输入类目名称"
                                   errormsg="长度不允许超过4字节（汉字2个，字母4个），且不允许输入特殊字符！">
                            <span id="name_error" class="help-inline"></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="bottom-bar">
                <div class="con">
                    <a class="btn btn-primary fr" id="btn-submit" href="javascript:;"><i class="icon-white icon-ok"></i>
                        确定</a>
                    <a class="btn fr" href="javascript:window.parent.location.reload();" style="margin-right:10px"><i
                            class="icon-chevron-up"></i> 返回类目列表</a>
                </div>
            </div>
        </form>
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

                        }
                    },
                    datatype: {
                        "fourbyte": function (gets, obj, curform, regxp) {
                            //参数gets是获取到的表单元素值，
                            //obj为当前表单元素，
                            //curform为当前验证的表单，
                            //regxp为内置的一些正则表达式的引用。
                            //return false表示验证出错，没有return或者return true表示验证通过。
                            if (obj.attr("name") == "val_name") {
                                var s = $("#val_name").val();
                                if (s.indexOf(" ") >= 0) {
                                    s = s.replace(/\s+/g, "");
                                    $("#val_name").val(s);
                                }
                                if (getByteLen(s) > 4) {
                                    return false;
                                }
                            }
                        }
                    },
                    ajaxPost: true,
                    callback: function (data) {
                        if (!$('input[name="id"]').val() || $('input[name="id"]').val() == 0) {
                            if (data.msg == 'y' || data.msg == 'x') {
                                var str = data.msg == "y" ? "修改" : "添加";
                                $.dialog({
                                    id: 'ajaxedit',
                                    content: str + '成功，是否继续' + str + '？',
                                    okVal: '是',
                                    ok: function () {
                                        $.dialog.list['ajaxedit'].close();
                                        $("input[name='val_name']").val("");
                                    },
                                    cancel: function () {
                                        window.parent.closeDetailIframe(function () {
                                            window.parent.location.reload();
                                            //window.parent.$('#pagination').trigger('currentPage');
                                        });
                                    }
                                });
                            }

                            if (data.msg == 'r') {
                                ZENG.msgbox.show(data.result, 5, 2000);
                            }

                        } else {
                            if (data.msg == 'y' || data.msg == 'x') {
                                var str = data.msg == "y" ? "修改" : "添加";
                                $.dialog({
                                    id: 'ajaxedit',
                                    content: str + '成功，是否继续' + str + '？',
                                    okVal: '取消',
                                    cancelVal: '是',
                                    ok: function () {
                                        window.parent.closeDetailIframe(function () {
                                            window.parent.$('#pagination').trigger('currentPage');
                                        });

                                    },
                                    cancel: function () {
                                        location.reload();
                                        return false;
                                    }
                                });
                            } else if (data.msg == 'r') {
                                ZENG.msgbox.show(data.result, 5, 2000);
                            }
                        }
                    }
                });
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