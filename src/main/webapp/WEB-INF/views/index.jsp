<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="cn.cestc.os.desktop.utils.ServletUtils" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            var basePath = '${pageContext.request.contextPath}';
        </script>

        <title>${settingModel.title} -- ${memberModel.username}</title>
        <meta name="description" content="${settingModel.description}">
        <meta name="keywords" content="${settingModel.keywords}">

        <link rel="icon" href="${pageContext.request.contextPath}/static/img/favicon.ico">

        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link href="${pageContext.request.contextPath}/static/css/all.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/static/css/sweetalert.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/static/css/font_2dkt72ag040io1or.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/HoorayLibs/hooraylibs.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/libs/system/clicaptcha/css/captcha.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/index.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/default.css" id="window-skin">
        <script type="text/javascript">
            //cookie前缀，避免重名
            var cookie_prefix = '';
        </script>

    </head>

    <body>

        <div class="loading" style="display: none;"></div>
        <!-- 登录&注册 -->
        <!-- 桌面 -->
        <div id="desktop" style="display: block;">
            <div id="desk">
                <div id="desk-1" class="desktop-container">
                    <div class="desktop-apps-container"></div>
                    <div class="scrollbar scrollbar-x"></div>
                    <div class="scrollbar scrollbar-y"></div>
                </div>
                <div id="desk-2" class="desktop-container">
                    <div class="desktop-apps-container"></div>
                    <div class="scrollbar scrollbar-x"></div>
                    <div class="scrollbar scrollbar-y"></div>
                </div>
                <div id="desk-3" class="desktop-container">
                    <div class="desktop-apps-container"></div>
                    <div class="scrollbar scrollbar-x"></div>
                    <div class="scrollbar scrollbar-y"></div>
                </div>
                <div id="desk-4" class="desktop-container">
                    <div class="desktop-apps-container"></div>
                    <div class="scrollbar scrollbar-x"></div>
                    <div class="scrollbar scrollbar-y"></div>
                </div>
                <div id="desk-5" class="desktop-container">
                    <div class="desktop-apps-container"></div>
                    <div class="scrollbar scrollbar-x"></div>
                    <div class="scrollbar scrollbar-y"></div>
                </div>
                <div id="dock-bar">
                    <div id="dock-container">
                        <div class="dock-middle">
                            <div class="dock-applist"></div>
                            <div class="dock-tools-container">
                                <div class="dock-tools">
                                    <a href="javascript:;" class="dock-tool-setting" title="桌面设置">
                                        <i class="hrosfont hros-config"></i>
                                    </a>
                                    <a href="javascript:;" class="dock-tool-style" title="主题设置">
                                        <i class="hrosfont hros-style"></i>
                                    </a>
                                </div>
                                <div class="dock-tools">
                                    <a href="javascript:;" class="dock-tool-appmanage" title="全局视图，快捷键：Ctrl + ↑">
                                        <i class="hrosfont hros-grid"></i>
                                    </a>
                                    <a href="javascript:;" class="dock-tool-search" title="搜索，快捷键：Ctrl + F">
                                        <i class="hrosfont hros-search"></i>
                                    </a>
                                </div>
                                <div class="dock-startbtn">
                                    <a href="javascript:;" class="dock-tool-start" title="点击这里开始">
                                        <i class="hrosfont hros-box"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="dock-pagination">
                                <a class="pagination pagination-1" href="javascript:;" index="1"
                                   title="切换至桌面1，快捷键：Ctrl + 1">
                                    <i class="hrosfont hros-dot"></i>
                                    <i class="hrosfont hros-num1"></i>
                                </a>
                                <a class="pagination pagination-2" href="javascript:;" index="2"
                                   title="切换至桌面2，快捷键：Ctrl + 2">
                                    <i class="hrosfont hros-dot"></i>
                                    <i class="hrosfont hros-num2"></i>
                                </a>
                                <a class="pagination pagination-3" href="javascript:;" index="3"
                                   title="切换至桌面3，快捷键：Ctrl + 3">
                                    <i class="hrosfont hros-dot"></i>
                                    <i class="hrosfont hros-num3"></i>
                                </a>
                                <a class="pagination pagination-4" href="javascript:;" index="4"
                                   title="切换至桌面4，快捷键：Ctrl + 4">
                                    <i class="hrosfont hros-dot"></i>
                                    <i class="hrosfont hros-num4"></i>
                                </a>
                                <a class="pagination pagination-5" href="javascript:;" index="5"
                                   title="切换至桌面5，快捷键：Ctrl + 5">
                                    <i class="hrosfont hros-dot"></i>
                                    <i class="hrosfont hros-num5"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="startmenu-container">

                <ul class="startmenu">
                    <li><a href="javascript:;" class="about1"><%=ServletUtils.getUserName(request)%>
                    </a></li>
                </ul>


                <ul class="startmenu">
                    <li>
                        <a href="javascript:;" class="about1"><%=ServletUtils.getName(request)%>
                        </a>
                    </li>

                </ul>
                <ul class="startmenu">
                    <li><a href="javascript:;" class="about">关于我们</a></li>
                </ul>

                <div class="startmenu-exit"><a href="javascript:;" title="注销当前用户"></a></div>
            </div>
            <div id="task-bar-bg1"></div>
            <div id="task-bar-bg2"></div>
            <div id="task-bar">
                <div id="task-prev"><a href="javascript:;" id="task-prev-btn" hidefocus="true"></a></div>
                <div id="task-content">
                    <div id="task-content-inner"></div>
                </div>
                <div id="task-next"><a href="javascript:;" id="task-next-btn" hidefocus="true"></a></div>
            </div>
            <div id="search-bar">
                <input id="pageletSearchInput" class="mousetrap" placeholder="搜索应用...">
                <button type="button" id="pageletSearchButton" title="搜索"><i class="fas fa-search"></i></button>
            </div>
            <div id="search-suggest">
                <ul class="resultBox"></ul>
                <div class="resultList openAppMarket"><a href="javascript:;">
                    <div>去应用市场搜搜...</div>
                </a></div>
            </div>
        </div>
        <!-- 全局视图 -->
        <div id="appmanage">
            <a class="amg_close" href="javascript:;"></a>
            <div id="amg_dock_container"></div>
            <div id="amg_folder_container">
                <div class="folderItem">
                    <div class="folder_bg folder_bg1"></div>
                    <div class="folderOuter">
                        <div class="folderInner" desk="1"></div>
                        <div class="scrollBar"></div>
                    </div>
                </div>
                <div class="folderItem">
                    <div class="folder_bg folder_bg2"></div>
                    <div class="folderOuter">
                        <div class="folderInner" desk="2"></div>
                        <div class="scrollBar"></div>
                    </div>
                </div>
                <div class="folderItem">
                    <div class="folder_bg folder_bg3"></div>
                    <div class="folderOuter">
                        <div class="folderInner" desk="3"></div>
                        <div class="scrollBar"></div>
                    </div>
                </div>
                <div class="folderItem">
                    <div class="folder_bg folder_bg4"></div>
                    <div class="folderOuter">
                        <div class="folderInner" desk="4"></div>
                        <div class="scrollBar"></div>
                    </div>
                </div>
                <div class="folderItem">
                    <div class="folder_bg folder_bg5"></div>
                    <div class="folderOuter">
                        <div class="folderInner" desk="5"></div>
                        <div class="scrollBar"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="copyright">
            <a href="javascript:;" class="close" title="关闭"></a>
            <div class="title">${settingModel.title}</div>
            <div class="body">
                <p>${settingModel.keywords}</p>
                <p></p>
                <p>${settingModel.description}</p>

                <p>官网：<a href="http://hoorayos.com/" target="_blank">http://hoorayos.com</a></p>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/static/libs/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/libs/sweetalert.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/libs/dialog-plus.js"></script>
        <script src="${pageContext.request.contextPath}/static/libs/sugar.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/plugins/HoorayLibs/hooraylibs.js"></script>
        <script src="${pageContext.request.contextPath}/static/plugins/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
        <script src="${pageContext.request.contextPath}/static/libs/system/clicaptcha/clicaptcha.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.core.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.app.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.appmanage.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.base.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.copyright.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.desktop.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.dock.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.folderView.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.folderThumbnail.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.grid.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.hotkey.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.lock.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.maskBox.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.popupMenu.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.searchBar.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.startMenu.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.taskBar.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.templates.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.wallpaper.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.widget.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/hros.window.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/html2canvas.js"></script>
        <script>
            var childWindow, interval;

        </script>

        <script>
            $(function () {
                //IE下禁止选中
                document.body.onselectstart = document.body.ondrag = function () {
                    return false;
                }
                //隐藏加载遮罩层
                $('.loading').hide();
                if ($('#lrbox').data('isforcedlogin') == 0 || Cookies.get(cookie_prefix + 'memberID') != 0) {
                    $('#desktop').show();
                    //初始化一些桌面信息
                    //HROS.CONFIG.sinaweiboAppkey = '';
                    //HROS.CONFIG.tweiboAppkey = '';
                    var wallInitData = '${wallInitData}';
                    var wallInitDatalist = wallInitData.split("<{|}>");
                    var wallState = parseInt(wallInitDatalist[0]);
                    HROS.CONFIG.wallpaperState = wallState;

                    if (wallState == 1 || wallState == 2) {
                        HROS.CONFIG.wallpaper = "${pageContext.request.contextPath}/" + wallInitDatalist[1];
                        HROS.CONFIG.wallpaperType = wallInitDatalist[2];
                        HROS.CONFIG.wallpaperWidth = parseInt(wallInitDatalist[3]);
                        HROS.CONFIG.wallpaperHeight = parseInt(wallInitDatalist[4]);
                    } else if (wallState == 3) {
                        HROS.CONFIG.wallpaper = "${pageContext.request.contextPath}/" + wallInitDatalist[1];
                    }


                    HROS.CONFIG.dockPos = '${memberModel.dockpos}';
                    HROS.CONFIG.appXY = '${memberModel.appxy}';
                    HROS.CONFIG.appSize = ${memberModel.appsize};
                    HROS.CONFIG.appVerticalSpacing =  ${memberModel.appverticalspacing};
                    HROS.CONFIG.appHorizontalSpacing = ${memberModel.apphorizontalspacing};
                    HROS.CONFIG.desk = ${memberModel.desk};

                    //加载桌面
                    HROS.base.init();
                }
            });
        </script>

    </body>
    <style>
        html, body {
            overscroll-behavior: contain;
        }
    </style>
</html>