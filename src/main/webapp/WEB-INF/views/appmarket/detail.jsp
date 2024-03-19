<%@page import="cn.cestc.os.desktop.model.AppCategoryModel" %>
<%@ page language="java" import="cn.cestc.os.desktop.model.AppModel" pageEncoding="UTF-8" %>
<%@page import="cn.cestc.os.desktop.model.MemberAppModel" %>
<%@page import="java.util.List" %>
<%
    List<MemberAppModel> memberAppList = (List<MemberAppModel>) request.getAttribute("memberAppList");
    List<AppCategoryModel> appCategoryList = (List<AppCategoryModel>) request.getAttribute("appCategoryList");
    AppModel app = (AppModel) request.getAttribute("appModel");
    String memberName = (String) request.getAttribute("memberName");
%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="utf-8">
        <title>应用市场</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/globle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/sys.css">
    </head>

    <body>
        <div class="detail-wrap">
            <div class="app-contents">
                <div class="mbox">
                    <div class="title">
                        <a href="javascript:window.parent.closeDetailIframe2();" class="btn-back">返回</a>
                    </div>
                    <div class="app-title">
                        <img src="${pageContext.request.contextPath}/<%=app.getIcon()%>" alt="tableau">
                        <span class="app-name"><%=app.getName()%></span>
                        <span class="app-desc"><i><%=app.getUsecount()%></i> 人在使用</span>
                        <%
                            boolean flag = false;
                            for (MemberAppModel mApp : memberAppList)
                            {
                                if (mApp.getRealid() != null && app.getTbid() != null && mApp.getRealid().equals(app.getTbid()))
                                {
                        %><a href="javascript:;" app_id="<%=mApp.getTbid()%>" real_app_id="<%=app.getTbid()%>"
                             app_type="window" class="btn-run">打开应用</a><%
                                flag = true;
                                break;
                            }
                        }
                        if (!flag)
                        {
                    %><a href="javascript:;" real_app_id="<%=app.getTbid()%>" app_type="window" class="btn-add">添加应用</a><%
                        }%>
                        <div class="grade-box">
                            <div class="star-num"><%=app.getStarnum()%>
                            </div>
                            <div class="star-box"><i style="width:<%=app.getStarnum() * 20%>%"></i>
                                <ul>
                                    <li class="grade-1" num="1"><a href="javascript:;"><em>很不好用</em></a></li>
                                    <li class="grade-2" num="2"><a href="javascript:;"><em>体验一般般</em></a></li>
                                    <li class="grade-3" num="3"><a href="javascript:;"><em>比较好用</em></a></li>
                                    <li class="grade-4" num="4"><a href="javascript:;"><em>很好用</em></a></li>
                                    <li class="grade-5" num="5"><a href="javascript:;"><em>棒极了，推荐</em></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <h4>应用介绍</h4>
                    <h5>
                        <em>开发者：</em><b style="color:red"><%=memberName%>
                    </b>
                        <em style="margin-left:10px">所属分类：</em>
                        <%
                            if (app.getApp_category_id() == 0)
                            {
                                out.print("未分类");
                            } else
                            {
                                for (AppCategoryModel cat : appCategoryList)
                                {
                                    if (app.getApp_category_id() == cat.getTbid())
                                    {
                                        out.print(cat.getName());
                                        break;
                                    }
                                }
                            }
                        %>
                        <em style="margin-left:10px">发布时间：</em><%=app.getDt().substring(0, 10)%>
                    </h5>
                    <div class="app-text breakword"><%=app.getRemark()%>
                    </div>
                </div>
            </div>
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
                //添加应用
                $('.btn-add').click(function () {
                    if (window.parent.parent.HROS.base.checkLogin()) {
                        if (!handlerappnotexist($(this).attr('real_app_id'))) {
                            return;
                        }
                        var appid = $(this).attr('real_app_id');
                        window.parent.parent.HROS.app.add(appid, function () {
                            window.parent.parent.HROS.app.get();
                            location.reload();
                        });
                    } else {
                        window.parent.$.dialog({
                            title: '温馨提示',
                            icon: 'warning',
                            content: '您尚未登录，赶快登录去添加您喜爱的应用吧！',
                            ok: function () {
                                window.parent.parent.HROS.base.login();
                            }
                        });
                    }
                });
                //打开应用
                $('.btn-run').click(function () {
                    if ($(this).attr('app_type') == 'window') {
                        window.parent.parent.HROS.window.create($(this).attr('app_id'));
                    } else {
                        window.parent.parent.HROS.widget.create($(this).attr('app_id'));
                    }
                });
                //评分
                $('.grade-box ul li').click(function () {
                    var num = $(this).attr('num');
                    if (!isNaN(num) && /^[1-5]$/.test(num)) {
                        if (window.parent.parent.HROS.base.checkLogin()) {
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/appMarketController/updateAppStar.do',
                                data: 'id=<%=app.getTbid()%>&starnum=' + num,
                                success: function (msg) {
                                    if (msg) {
                                        ZENG.msgbox.show("打分成功！", 4, 2000);
                                        setTimeout(function () {
                                            location.reload();
                                        }, 2000);
                                    } else {
                                        ZENG.msgbox.show("你已经打过分了！", 1, 2000);
                                    }
                                }
                            });
                        } else {
                            window.parent.parent.HROS.base.login();
                        }
                    }
                });
            });

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
                                    window.parent.closeDetailIframe2();
                                }
                            });
                        }
                    }
                });
                return appnotexist;
            }
        </script>
    </body>
</html>