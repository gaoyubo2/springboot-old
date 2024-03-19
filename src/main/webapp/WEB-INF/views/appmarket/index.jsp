<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="cn.cestc.os.desktop.model.AppCategoryModel" %>
<%@page import="cn.cestc.os.desktop.model.AppModel" %>
<%@page import="cn.cestc.os.desktop.model.MemberAppModel" %>
<%@page import="cn.cestc.os.desktop.model.MemberModel" %>
<%@page import="java.util.List" %>
<%
    List<MemberAppModel> memberAppList = (List<MemberAppModel>) request.getAttribute("memberAppList");
    AppModel app = (AppModel) request.getAttribute("appModel");
    MemberModel member = (MemberModel) request.getAttribute("member");
    List<AppCategoryModel> appCategoryList = (List<AppCategoryModel>) request.getAttribute("appCategoryList");
    Integer kf = (Integer) request.getAttribute("kf");
    Integer wfb = (Integer) request.getAttribute("wfb");
%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <title>应用市场</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/globle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/sys.css">
        <script type="text/javascript">var basePath = '${pageContext.request.contextPath}';</script>
    </head>

    <body>
        <div class="sub-nav tabbable tabs-left">
            <ul class="nav nav-tabs">
                <li class="all active" value="-1"><a href="javascript:;" title="全部">全部</a></li>
                <% for (AppCategoryModel vo : appCategoryList)
                {%>
                <li value="<%=vo.getTbid() %>"><a href="javascript:;" title="<%=vo.getName() %>"><%=vo.getName() %>
                </a></li>
                <%}%>
                <li value="0"><a href="javascript:;" title="未分类">未分类</a></li>
                <li class="myapps" value="-2"><a href="javascript:;" title="我的应用">我的<br>应用</a></li>
            </ul>
            <input type="hidden" name="search_1" id="search_1" value="">
        </div>
        <div class="wrap">
            <div class="col-sub">
                <div class="search-box">
                    <div class="input-append">
                        <input type="text" name="search_3" id="search_3" placeholder="请输入搜索关键字" style="width:150px"
                               value="${keyword}">
                        <button id="search_3_remove" class="btn" style="padding:4px 5px"><i class="icon-remove"></i>
                        </button>
                        <button id="search_3_do" class="btn" style="padding:4px 5px"><i class="icon-search"></i>
                        </button>
                    </div>
                </div>
                <div class="mbox commend-day">
                    <h3>今日推荐</h3>
                    <div class="commend-container">
                        <a href="javascript:beforeopenDetailIframe2('${pageContext.request.contextPath}/appMarketController/detail.do?id=<%=app.getTbid()%>',<%=app.getTbid()%>);">
                            <img src="${pageContext.request.contextPath}/<%=app.getIcon()%>" alt="<%=app.getName()%>">
                        </a>
                    </div>
                    <div class="commend-text">
                        <h4>
                            <strong><%=app.getName()%>
                            </strong>
                            <span><%=app.getUsecount()%>人在用</span>
                        </h4>
                        <div class="con" title=""></div>
                        <%
                            boolean flag = false;
                            for (MemberAppModel mApp : memberAppList)
                            {
                                if (mApp.getRealid() != null && app.getTbid() != null && mApp.getRealid().equals(app.getTbid()))
                                {
                                    flag = true;
                        %><a href="javascript:;" app_id="<%=mApp.getTbid()%>" real_app_id="<%=app.getTbid()%>"
                             app_type="window" class="btn-run">打开应用</a><%
                                break;
                            }
                        }
                        if (!flag)
                        {
                    %><a href="javascript:;" real_app_id="<%=app.getTbid()%>" class="btn-add">添加应用</a><%
                        }%>
                    </div>
                    <span class="star-box"><i style="width:<%=app.getStarnum() * 20%>%"></i></span>
                </div>
                <div class="mbox develop">
                    <h3>我是开发者</h3>
                    <div class="developer">
                        <p>开发者：<%=member.getUsername()%>
                        </p>
                        <p>我开发的应用：<font style="font-weight:bold"><%=kf%>
                        </font> 个</p>
                        <p>未发布的应用：<font style="font-weight:bold"><%=wfb%>
                        </font> 个</p>
                        <div class="text-center"><a href="javascript:void(0)" class="btn btn-primary">管理我的应用</a>
                            <a href="javascript:openDetailIframe2('${pageContext.request.contextPath}/appMarketController/manage.do?add=1');"
                               class="btn btn-danger">开发新应用</a></div>
                    </div>
                </div>
            </div>
            <div class="col-main">
                <div class="mbox app-list-box">
                    <div class="title">
                        <ul>
                            <li class="focus" value="1"><a href="javascript:;">最新应用</a></li>
                            <li value="2"><a href="javascript:;">最热门</a></li>
                            <li value="3"><a href="javascript:;">最高评价</a></li>
                            <input type="hidden" name="search_2" id="search_2" value="1">
                        </ul>
                    </div>
                    <ul class="app-list"></ul>
                    <div class="pagination pagination-centered" style="margin-top:6px" id="pagination"></div>
                    <input id="pagination_setting" type="hidden" per="5"/>
                </div>
            </div>
        </div>
        <div id="detailIframe"
             style="background:#fff;position:fixed;z-index:1;top:0;left:140px;right:0;height:100%;display:none">
            <iframe frameborder="0" style="width:100%;height:100%"></iframe>
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
            });
        </script>
        <script>
            $(function () {
                //加载列表
                getPageList(0);
                //detailIframe
                openDetailIframe2 = function (url) {
                    ZENG.msgbox.show('正在载入中，请稍后...', 6, 100000);
                    $('#detailIframe iframe').attr('src', url).load(function () {
                        ZENG.msgbox._hide();
                        $('#detailIframe').animate({
                            'left': '60px',
                            'opacity': 'show'
                        }, 500);
                    });
                };
                closeDetailIframe2 = function (callback) {
                    $('#detailIframe').animate({
                        'left': 0,
                        'opacity': 'hide'
                    }, 500, function () {
                        $('#detailIframe').css('left', '140px');
                        callback && callback();
                    });
                    getPageList(0);
                };
                closeDetailIframe3 = function (callback) {
                    window.location.reload();
                };
                $('.sub-nav ul li').click(function () {
                    $('#search_1').val($(this).attr('value'));
                    closeDetailIframe2();
                    $('.sub-nav ul li').removeClass('active');
                    $(this).addClass('active');
                    $('.app-list-box .title li').removeClass('active').eq(0).addClass('active');
                    $('#search_2').val(1);
                    getPageList(0);
                });
                $('.app-list-box .title li').click(function () {
                    $('.app-list-box .title li').removeClass('focus');
                    $(this).addClass('focus');
                    $('#search_2').val($(this).attr('value'));
                    getPageList(0);
                });
                //搜索
                $('#search_3').on('keydown', function (e) {
                    if (e.keyCode == '13') {
                        $('#search_3_do').click();
                    }
                });
                $('#search_3_do').click(function () {
                    $('.app-list-box .title li').removeClass('focus').eq(0).addClass('focus');
                    $('.sub-nav ul li').removeClass('active').eq(0).addClass('active');
                    $('#search_1').val(-1);
                    $('#search_2').val(1);
                    getPageList(0);
                });
                $('#search_3_remove').click(function () {
                    $('#search_3').val('');
                    getPageList(0);
                });
                //添加，删除，打开应用
                $('.app-list').on('click', '.btn-add-s', function () {
                    if (window.parent.HROS.base.checkLogin()) {
                        if (!handlerappnotexist($(this).attr('real_app_id'))) {
                            return;
                        }
                        $(this).removeClass().addClass('btn-loading-s');
                        window.parent.HROS.app.add($(this).attr('real_app_id'), function () {
                            $('#pagination').trigger('currentPage');
                            window.parent.HROS.app.get();
                        });
                    } else {
                        window.parent.$.dialog({
                            title: '温馨提示',
                            icon: 'warning',
                            content: '您尚未登录，赶快登录去添加您喜爱的应用吧！',
                            ok: function () {
                                window.parent.HROS.base.login();
                            }
                        });
                    }
                }).on('click', '.btn-remove-s', function () {
                    if (window.parent.HROS.base.checkLogin()) {
                        if (!getmemberappisexist($(this).attr('app_id'))) {
                            return;
                        }
                        
                        var realappid = $(this).attr('real_app_id'), type = $(this).attr('app_type');
                        let appid = $(this).attr('app_id');

                        $(this).removeClass().addClass('btn-loading-s');
                        window.parent.HROS.app.remove(appid, function () {
                            $('#pagination').trigger('currentPage');
                            window.parent.HROS.widget.removeCookie(realappid, type);
                            window.parent.HROS.app.get();
                        });
                    } else {
                        window.parent.HROS.base.login();
                    }
                }).on('click', '.btn-run-s', function () {
                    if (!getmemberappisexist($(this).attr('app_id'))) {
                        return;
                    }
                    if ($(this).attr('app_type') == 'window') {
                        window.parent.HROS.window.create($(this).attr('app_id'));
                    } else {
                        window.parent.HROS.widget.create($(this).attr('app_id'));
                    }
                });
                $('.commend-day').on('click', '.btn-add', function () {
                    if (window.parent.HROS.base.checkLogin()) {
                        var appid = $(this).attr('real_app_id');
                        if (!handlerappnotexist(appid)) {
                            return;
                        }
                        window.parent.HROS.app.add(appid, function () {
                            window.parent.HROS.app.get();
                            location.reload();
                        });
                    } else {
                        window.parent.$.dialog({
                            title: '温馨提示',
                            icon: 'warning',
                            content: '您尚未登录，赶快登录去添加您喜爱的应用吧！',
                            ok: function () {
                                window.parent.HROS.base.login();
                            }
                        });
                    }
                }).on('click', '.btn-run', function () {
                    if (!getmemberappisexist($(this).attr('app_id'))) {
                        return;
                    }
                    if ($(this).attr('app_type') == 'window') {
                        window.parent.HROS.window.create($(this).attr('app_id'), 'window', $(this).attr('real_app_id'));
                    } else {
                        window.parent.HROS.widget.create($(this).attr('app_id'), 'widget', $(this).attr('real_app_id'));
                    }
                });


                $('.btn-primary').on('click', function () {
                    openDetailIframe2('${pageContext.request.contextPath}/appMarketController/manage.do?type=' + $('#search_1').val());
                });
            });

            function initPagination(current_page) {
                $('#pagination').pagination(parseInt($('#pagination_setting').attr('count')), {
                    current_page: current_page,
                    items_per_page: parseInt($('#pagination_setting').attr('per')),
                    num_display_entries: 5,
                    num_edge_entries: 1,
                    callback: getPageList,
                    prev_text: '上一页',
                    next_text: '下一页'
                });
            }

            function getPageList(current_page) {
                ZENG.msgbox.show('正在加载中，请稍后...', 6, 100000);
                var from = current_page * parseInt($('#pagination_setting').attr('per')),
                    to = parseInt($('#pagination_setting').attr('per'));
                var keyword = $('#search_3').val();
                if (keyword.indexOf("%") > -1 || keyword.indexOf("&") > -1) {
                    keyword = "%25";
                }
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/appMarketController/list.do',
                    data: 'from=' + from + '&to=' + to + '&type=' + $('#search_1').val() + '&new_hot_top=' + $('#search_2').val() + '&keyword=' + keyword,
                    success: function (msg) {
                        var arr = msg.split('<{|*|}>');
                        $('#pagination_setting').attr('count', arr[0]);
                        $('.app-list').html(arr[1]);
                        initPagination(current_page);
                        ZENG.msgbox._hide();
                    }
                });
            }

            function beforeopenDetailIframe2(url, realid) {
                if (!handlerappnotexist(realid)) {
                    return;
                }
                openDetailIframe2(url);
            }

            function handlerappnotexist(realid) {
                var appnotexist = true;
                $.ajax({
                    async: false,
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/appManageController/getappisexist.do',
                    data: 'realid=' + realid,
                    success: function (data) {
                        if (data.code == 0) {
                            appnotexist = false;
                            $.dialog({
                                content: '应用不存在！',
                                okVal: '确定',
                                ok: function () {
                                    location.reload();
                                    //closeDetailIframe2();
                                }
                            });
                            $("a.aui_close").css('display', 'none');
                        }
                    }
                });
                return appnotexist;
            }

            function getmemberappisexist(realid) {
                var appnotexist = true;
                $.ajax({
                    async: false,
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/appManageController/getmemberappisexist.do',
                    data: 'realid=' + realid,
                    success: function (data) {
                        if (data.code == 0) {
                            appnotexist = false;
                            $.dialog({
                                content: '应用不存在！',
                                okVal: '确定',
                                ok: function () {
                                    location.reload();
                                    //closeDetailIframe2();
                                }
                            });
                            $("a.aui_close").css('display', 'none');
                        }
                    }
                });
                return appnotexist;
            }
        </script>
    </body>
</html>
