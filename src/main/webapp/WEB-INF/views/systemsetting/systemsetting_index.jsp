<%@ page language="java" pageEncoding="UTF-8" %>
<%
    Integer desk = (Integer) request.getAttribute("desk");
    String xy = (String) request.getAttribute("xy");
    Integer size = (Integer) request.getAttribute("size");
    String pos = (String) request.getAttribute("pos");
    Integer vertical = (Integer) request.getAttribute("vertical");
    Integer horizontal = (Integer) request.getAttribute("horizontal");
%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <title>桌面设置</title>
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
                $('input[name="desk"]').change(function () {
                    var desk = $('input[name="desk"]:checked').val();
                    window.parent.HROS.deskTop.updateDefaultDesk(desk);
                });
                $('input[name="appxy"]').change(function () {
                    var xy = $('input[name="appxy"]:checked').val();
                    window.parent.HROS.app.updateXY(xy);
                });
                var updateSize = function (size) {
                    if (size < 32) {
                        $.dialog({
                            content: "图标尺寸不能小于32，请检查！",
                            icon: "warning"
                        });
                        size = 32;
                    } else if (size > 64) {
                        $.dialog({
                            content: "图标尺寸不能大于64，请检查！",
                            icon: "warning"
                        });
                        size = 64;
                    } else if (isNaN(size)) {
                        if (!size || size == '') {
                            return;
                        }
                        $.dialog({
                            content: "图标尺寸只能为数字，请检查！",
                            icon: "warning"
                        });
                        size = window.parent.HROS.CONFIG.appSize;
                    }
                    $('input[name="appsize"]').val(size);
                    window.parent.HROS.app.updateSize(size);
                };

                $('.appsize-minus, .appsize-plus').click(function () {
                    var size = parseInt($('input[name="appsize"]').val());
                    if ($(this).hasClass('appsize-minus')) {
                        size = size - 1;
                    } else {
                        size = size + 1;
                    }
                    updateSize(size);
                });

                var si;

                $('.appsize-minus, .appsize-plus').mousedown(function () {
                    var size = parseInt($('input[name="appsize"]').val());
                    if ($(this).hasClass('appsize-minus')) {
                        si = setInterval(function () {
                            size = size - 1;
                            updateSize(size);
                            if (size < 32) {
                                clearInterval(si);//停止
                            }
                        }, 200);
                    } else {
                        si = setInterval(function () {
                            size = size + 1;
                            updateSize(size);
                            if (size > 64) {
                                clearInterval(si);//停止
                            }
                        }, 200);
                    }
                    $(document).mouseup(function () {
                        clearInterval(si);//停止
                    });
                });

                var last;

                $('input[name="appsize"]').keyup(function (event) {
                    var size = parseInt($('input[name="appsize"]').val());
                    last = event.timeStamp;
                    setTimeout(function () {
                        if (last - event.timeStamp == 0) { //设置的1.5秒延时期间操作不执行，比如输入35 第一个3不执行
                            updateSize(size);
                        }
                    }, 1500);

                });
                $('input[name="appsize"]').blur(function (event) {
                    var size = parseInt($('input[name="appsize"]').val());
                    if (!size || size == '') {
                        $('input[name="appsize"]').val(window.parent.HROS.CONFIG.appSize);
                    }

                });


                var updateVertical = function (vertical) {
                    if (vertical < 0) {
                        $.dialog({
                            content: "图标垂直间距不能小于0，请检查！",
                            icon: "warning"
                        });
                        vertical = 0;
                    } else if (vertical > 100) {
                        $.dialog({
                            content: "图标垂直间距不能大于100，请检查！",
                            icon: "warning"
                        });
                        vertical = 100;
                    } else if (isNaN(vertical)) {
                        if (!vertical || vertical == '') {
                            return;
                        }
                        $.dialog({
                            content: "图标垂直间距只能为数字，请检查！",
                            icon: "warning"
                        });
                        vertical = window.parent.HROS.CONFIG.appVerticalSpacing;
                    }
                    $('input[name="appverticalspacing"]').val(vertical);
                    window.parent.HROS.app.updateVertical(vertical);
                };
                $('.appverticalspacing-minus, .appverticalspacing-plus').click(function () {
                    var vertical = parseInt($('input[name="appverticalspacing"]').val());
                    if ($(this).hasClass('appverticalspacing-minus')) {
                        vertical = vertical - 1;
                    } else {
                        vertical = vertical + 1;
                    }
                    updateVertical(vertical);
                });


                $('.appverticalspacing-minus, .appverticalspacing-plus').mousedown(function () {
                    var vertical = parseInt($('input[name="appverticalspacing"]').val());
                    if ($(this).hasClass('appverticalspacing-minus')) {
                        si = setInterval(function () {
                            vertical = vertical - 1;
                            updateVertical(vertical);
                            if (vertical < 0) {
                                clearInterval(si);//停止
                            }
                        }, 200);
                    } else {
                        si = setInterval(function () {
                            vertical = vertical + 1;
                            updateVertical(vertical);
                            if (vertical > 100) {
                                clearInterval(si);//停止
                            }
                        }, 200);
                    }
                    $(document).mouseup(function () {
                        clearInterval(si);//停止
                    });
                });


                $('input[name="appverticalspacing"]').keyup(function (event) {
                    var vertical = parseInt($('input[name="appverticalspacing"]').val());
                    last = event.timeStamp;
                    setTimeout(function () {
                        if (last - event.timeStamp == 0) { //设置的1.5秒延时期间操作不执行，比如输入35 第一个3不执行
                            updateVertical(vertical);
                        }
                    }, 1500);
                });
                $('input[name="appverticalspacing"]').blur(function (event) {
                    var vertical = parseInt($('input[name="appverticalspacing"]').val());
                    if (!vertical || vertical == '') {
                        $('input[name="appverticalspacing"]').val(window.parent.HROS.CONFIG.appVerticalSpacing);
                    }

                });
                var updateHorizontal = function (horizontal) {
                    if (horizontal < 0) {
                        $.dialog({
                            content: "图标水平间距不能小于0，请检查！",
                            icon: "warning"
                        });
                        horizontal = 0;
                    } else if (horizontal > 100) {
                        $.dialog({
                            content: "图标水平间距不能大于100，请检查！",
                            icon: "warning"
                        });
                        horizontal = 100;
                    } else if (isNaN(horizontal)) {
                        if (!horizontal || horizontal == '') {
                            return;
                        }
                        $.dialog({
                            content: "图标水平间距只能为数字，请检查！",
                            icon: "warning"
                        });
                        horizontal = window.parent.HROS.CONFIG.appHorizontalSpacing;
                    }
                    $('input[name="apphorizontalspacing"]').val(horizontal);
                    window.parent.HROS.app.updateHorizontal(horizontal);
                };

                $('.apphorizontalspacing-minus, .apphorizontalspacing-plus').click(function () {
                    var horizontal = parseInt($('input[name="apphorizontalspacing"]').val());
                    if ($(this).hasClass('apphorizontalspacing-minus')) {
                        horizontal = horizontal - 1;
                    } else {
                        horizontal = horizontal + 1;
                    }
                    updateHorizontal(horizontal);
                });


                $('.apphorizontalspacing-minus, .apphorizontalspacing-plus').mousedown(function () {
                    var horizontal = parseInt($('input[name="apphorizontalspacing"]').val());
                    if ($(this).hasClass('apphorizontalspacing-minus')) {
                        si = setInterval(function () {
                            horizontal = horizontal - 1;
                            updateHorizontal(horizontal);
                            if (horizontal < 0) {
                                clearInterval(si);//停止
                            }
                        }, 200);
                    } else {
                        si = setInterval(function () {
                            horizontal = horizontal + 1;
                            updateHorizontal(horizontal);
                            if (horizontal > 100) {
                                clearInterval(si);//停止
                            }
                        }, 200);
                    }
                    $(document).mouseup(function () {
                        clearInterval(si);//停止
                    });
                });


                $('input[name="apphorizontalspacing"]').keyup(function (event) {
                    var horizontal = parseInt($('input[name="apphorizontalspacing"]').val());
                    last = event.timeStamp;
                    setTimeout(function () {
                        if (last - event.timeStamp == 0) { //设置的1.5秒延时期间操作不执行，比如输入35 第一个3不执行
                            updateHorizontal(horizontal);
                        }
                    }, 1500);
                });
                $('input[name="apphorizontalspacing"]').blur(function (event) {
                    var horizontal = parseInt($('input[name="apphorizontalspacing"]').val());
                    if (!horizontal || horizontal == '') {
                        $('input[name="apphorizontalspacing"]').val(window.parent.HROS.CONFIG.appHorizontalSpacing);
                    }

                });

                $('input[name="dockpos"]').change(function () {
                    var pos = $('input[name="dockpos"]:checked').val();
                    $('.set_view').removeClass('set_view_top set_view_left set_view_right set_view_none');
                    $('.set_view').addClass('set_view_' + pos);
                    window.parent.HROS.dock.updatePos(pos);

                });
            });
        </script>
    </head>

    <body>
        <div class="title">
            <ul>
                <li class="focus">桌面设置</li>
                <li><a href="${pageContext.request.contextPath}/systemSettingController/goWallpaper.do">壁纸设置</a></li>
                <li><a href="${pageContext.request.contextPath}/systemSettingController/skinindex.do">皮肤设置</a></li>
            </ul>
        </div>
        <div class="input-label">
            <label class="label-text">默认桌面：</label>
            <div class="label-box form-inline control-group">
                <label class="radio" style="margin-right:10px"><input type="radio" name="desk"
                                                                      value="1" <%=desk==1?"checked":"" %>>第1屏</label>
                <label class="radio" style="margin-right:10px"><input type="radio" name="desk"
                                                                      value="2" <%=desk==2?"checked":"" %>>第2屏</label>
                <label class="radio" style="margin-right:10px"><input type="radio" name="desk"
                                                                      value="3" <%=desk==3?"checked":"" %>>第3屏</label>
                <label class="radio" style="margin-right:10px"><input type="radio" name="desk"
                                                                      value="4" <%=desk==4?"checked":"" %>>第4屏</label>
                <label class="radio"><input type="radio" name="desk" value="5" <%=desk==5?"checked":"" %>>第5屏</label>
            </div>
        </div>
        <div class="input-label">
            <label class="label-text">桌面图标排列方式：</label>
            <div class="label-box form-inline control-group">
                <label class="radio" style="margin-right:10px"><input type="radio" name="appxy"
                                                                      value="x" <%=xy.equals("x")?"checked":"" %>>横向排列</label>
                <label class="radio"><input type="radio" name="appxy" value="y"  <%=xy.equals("y")?"checked":"" %>>纵向排列</label>
            </div>
        </div>
        <div class="input-label">
            <label class="label-text">桌面图标显示尺寸：</label>
            <div class="label-box form-inline control-group">
                <div class="input-prepend input-append">
                    <button class="btn appsize-minus" type="button"><i class="icon-minus"></i></button>
                    <input type="text" name="appsize" class="text-center span1" value="<%=size%>">
                    <button class="btn appsize-plus" type="button"><i class="icon-plus"></i></button>
                </div>
            </div>
        </div>
        <div class="input-label">
            <label class="label-text">桌面图标垂直间距：</label>
            <div class="label-box form-inline control-group">
                <div class="input-prepend input-append">
                    <button class="btn appverticalspacing-minus" type="button"><i class="icon-minus"></i></button>
                    <input type="text" name="appverticalspacing" class="text-center span1" value="<%=vertical%>">
                    <button class="btn appverticalspacing-plus" type="button"><i class="icon-plus"></i></button>
                </div>
            </div>
        </div>
        <div class="input-label">
            <label class="label-text">桌面图标水平间距：</label>
            <div class="label-box form-inline control-group">
                <div class="input-prepend input-append">
                    <button class="btn apphorizontalspacing-minus" type="button"><i class="icon-minus"></i></button>
                    <input type="text" name="apphorizontalspacing" class="text-center span1" value="<%=horizontal%>">
                    <button class="btn apphorizontalspacing-plus" type="button"><i class="icon-plus"></i></button>
                </div>
            </div>
        </div>
        <div class="input-label">
            <label class="label-text">应用码头位置：</label>
            <div class="label-box form-inline control-group">
                <label class="radio" style="margin-right:10px"><input type="radio" name="dockpos"
                                                                      value="top"  <%=pos.equals("top")?"checked":"" %>>顶部</label>
                <%--<label class="radio" style="margin-right:10px"><input type="radio" name="dockpos" value="bottom" <%=pos.equals("bottom")?"checked":"" %>>底部</label>--%>
                <label class="radio" style="margin-right:10px"><input type="radio" name="dockpos"
                                                                      value="left" <%=pos.equals("left")?"checked":"" %>>左侧</label>
                <label class="radio" style="margin-right:10px"><input type="radio" name="dockpos"
                                                                      value="right" <%=pos.equals("right")?"checked":"" %>>右侧</label>
            </div>
        </div>
    </body>
</html>

