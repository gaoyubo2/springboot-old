<%@page import="cn.cestc.os.desktop.model.AppCategoryModel" %>
<%@ page language="java" import="java.util.List" pageEncoding="UTF-8" %>
<%
    List<AppCategoryModel> appCategoryList = (List<AppCategoryModel>) request.getAttribute("appCategoryList");
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
        <style>
            body {
                margin: 10px 10px 0
            }

            .appicon {
                width: 32px;
                height: 32px
            }

            .appname {
                margin-left: 10px
            }
        </style>
    </head>

    <body>
        <div class="well well-small" style="margin-bottom:10px;padding-bottom:0">
            <div class="form-inline">
                <div class="control-group">
                    <label>名称：</label>
                    <input type="text" name="search_1" id="search_1" class="span5">
                    <a class="btn" menu="search" href="javascript:;" style="margin-left:10px"><i
                            class="icon-search"></i> 搜索</a>
                    <a class="btn btn-primary fr"
                       href="javascript:openDetailIframe('${pageContext.request.contextPath}/appManageController/detail.do');"><i
                            class="icon-white icon-plus"></i> 添加新应用</a>
                </div>
                <div class="control-group">
                    <label>分类：</label>
                    <select name="search_2" id="search_2" style="width:140px">
                        <option value="-1">全部</option>
                        <% for (AppCategoryModel vo : appCategoryList)
                        {%>
                        <option value="<%=vo.getTbid()%>"><%=vo.getName() %>
                        </option>
                        <%}%>
                        <option value="0">未分类</option>
                    </select>
                    <label style="margin-left:10px">状态：</label>
                    <select name="search_4" id="search_4" style="width:140px">
                        <option value="1">已上线应用</option>
                        <option value="2">待审核应用</option>
                        <option value="3">审核不通过</option>
                    </select>
                </div>
            </div>
        </div>
        <table class="list-table">
            <thead>
                <tr class="col-name">
                    <th style="width:50px">应用ID</th>
                    <th>应用名称</th>
                    <th style="width:100px">类型</th>
                    <th style="width:100px">使用人数</th>
                    <th style="width:220px" colspan="3">操作</th>
                </tr>
                <tr class="sep-row">
                    <td colspan="100"></td>
                </tr>
                <tr class="toolbar">
                    <td colspan="100">
                        <b style="margin:0 10px">符合条件的记录</b>有<font class="list-count">0</font>条
                    </td>
                </tr>
                <tr class="sep-row">
                    <td colspan="100"></td>
                </tr>
            </thead>
            <tbody class="list-con"></tbody>
            <tfoot>
            <tr>
                <td colspan="100">
                    <div class="pagination pagination-centered">
                        <div id="pagination"></div>
                    </div>
                    <input id="pagination_setting" type="hidden" per="7">
                </td>
            </tr>
            </tfoot>
        </table>
        <div id="detailIframe"
             style="background:#fff;position:fixed;z-index:1;top:-100px;left:0;width:100%;height:100%;display:none">
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
                    getPageList(0);
                };
            });
        </script>
        <script>
            $(function () {
                //加载列表
                getPageList(0);
                //删除，推荐
                $('.list-con').on('click', '.do-del', function () {
                    var appid = $(this).attr('appid');
                    var appname = $(this).parents('tr').children('td:nth-child(2)').text();
                    $.dialog({
                        id: 'del',
                        content: '确定要删除 “' + appname + '” 该应用么？',
                        ok: function () {
                            $.ajax({
                                type: 'POST',
                                url: '${pageContext.request.contextPath}/appManageController/delete.do',
                                data: 'id=' + appid
                            }).done(function () {
                                $('#pagination').trigger('currentPage');
                            });
                        },
                        cancel: true
                    });
                }).on('click', '.do-recommend', function () {
                    var appid = $(this).attr('appid');
                    $.ajax({
                        type: 'POST',
                        url: '${pageContext.request.contextPath}/appManageController/recommend.do',
                        data: 'id=' + appid
                    }).done(function () {
                        $('#pagination').trigger('currentPage');
                    });
                });
                //搜索
                $('a[menu=search]').click(function () {
                    getPageList(0);
                });
            });

            function initPagination(current_page) {
                $('#pagination').pagination(parseInt($('#pagination_setting').attr('count')), {
                    current_page: current_page,
                    items_per_page: parseInt($('#pagination_setting').attr('per')),
                    num_display_entries: 9,
                    num_edge_entries: 2,
                    callback: getPageList,
                    prev_text: '上一页',
                    next_text: '下一页'
                });
            }

            function getPageList(current_page) {
                ZENG.msgbox.show('正在加载中，请稍后...', 6, 100000);
                var from = current_page * parseInt($('#pagination_setting').attr('per')),
                    to = parseInt($('#pagination_setting').attr('per'));
                var keyword = $('#search_1').val();
                if (keyword.indexOf("%") > -1 || keyword.indexOf("&") > -1) {
                    keyword = "%25";
                }
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/appManageController/list.do',
                    data: 'from=' + from + '&to=' + to + '&app_category_id=' + $('#search_2').val() + '&verifytype=' + $('#search_4').val() + '&keyword=' + keyword,
                    success: function (msg) {
                        var arr = msg.split('<{|*|}>');
                        $('#pagination_setting').attr('count', arr[0]);
                        $('.list-count').text(arr[0]);
                        $('.list-con').html(arr[1]);
                        initPagination(current_page);
                        ZENG.msgbox._hide();
                    }
                });
            }
        </script>
    </body>
</html>