/*
**  一个不属于其他模块的模块
*/
HROS.base = (function () {
    return {
        /*
        **	系统初始化
        */
        init: function () {
            //ajax默认设置
            $.ajaxSetup({
                url: ajaxUrl,
                type: 'POST',
                dataType: 'json'
            });
            //绑定ajax全局验证
            $(document).ajaxSuccess(function (event, request, settings) {
                if ($.trim(request.responseText) == 'ERROR_NOT_LOGGED_IN' && HROS.CONFIG.memberID != 0) {
                    HROS.base.loginDialog();
                }
            });
            //更新当前用户ID
            HROS.CONFIG.memberID = Cookies.get(cookie_prefix + 'memberID');
            //阻止弹出浏览器默认右键菜单
            $('body').on('contextmenu', function () {
                return false;
            });
            //版权信息初始化并显示
            HROS.copyright.init();
            //桌面(容器)初始化
            HROS.deskTop.init();
            //初始化壁纸
            HROS.wallpaper.init();
            //初始化搜索栏
            HROS.searchBar.init();
            //初始化开始菜单
            HROS.startMenu.init();
            //初始化任务栏
            HROS.taskBar.init();
            /*
            **      当dockPos为top时          当dockPos为left时         当dockPos为right时
            **  -----------------------   -----------------------   -----------------------
            **  | o o o        [dock] |   | o | o               |   | o               | o |
            **  -----------------------   | o | o               |   | o               | o |
            **  | o o                 |   | o | o               |   | o               | o |
            **  | o +                 |   |   | o               |   | o               |   |
            **  | o            [desk] |   |   | o        [desk] |   | o        [desk] |   |
            **  | o                   |   |   | +               |   | +               |   |
            **  -----------------------   -----------------------   -----------------------
            **  因为desk区域的尺寸和定位受dock位置的影响，所以加载应用前必须先定位好dock的位置
            */
            //初始化应用码头
            HROS.dock.init();
            //初始化桌面应用
            HROS.app.init();
            //初始化widget模块
            HROS.widget.init();
            //初始化窗口模块
            HROS.window.init();
            //初始化文件夹预览
            HROS.folderView.init();
            //初始化缩略图
            HROS.folderThumbnail.init();
            //初始化全局视图
            HROS.appmanage.init();
            //初始化右键菜单
            HROS.popupMenu.init();
            //初始化锁屏
            HROS.lock.init();
            //初始化快捷键
            HROS.hotkey.init();
        },
        loginDialog: function (text) {
            text = typeof text === 'undefined' ? '系统检测到您尚未登录，或者长时间未操作已登出<br>为了更好的操作，是否登录？' : text;
            HROS.CONFIG.memberID = 0;
            swal({
                type: 'warning',
                title: '温馨提示',
                text: text,
                html: true,
                showCancelButton: true,
                confirmButtonText: '走，去登录！',
                cancelButtonText: '我再逛逛…'
            }, function () {
                HROS.base.login();
            });
        },
        login: function () {
            changeTabindex();
            $('#lrbox').transition({
                scale: 1,
                opacity: 1,
                visibility: 'visible'
            }, 300);
            $('#lrbox .lrbox').transition({
                y: 0,
                rotateX: '0deg'
            }, 500);
        },
        logout: function () {

            $.ajax({
                url: ajaxUrl.logout,

            }).error(function () {
                location.reload();
            });
        },
        checkLogin: function () {
            return HROS.CONFIG.memberID != 0 ? true : false;
        },
        setSkin: function (skin, callback) {
            //将原样式修改id，并载入新样式
            $('#window-skin').attr('id', 'window-skin-ready2remove');
            var stylesheet = loadCSS('static/css/skins/' + skin + '.css?' + version);
            onloadCSS(stylesheet, function () {
                $('body link').last().attr('id', 'window-skin');
                $('#window-skin-ready2remove').remove();
                HROS.CONFIG.skin = skin;
                callback && callback();
            });
        },
        getParameter: function () {
            var url = location.search;
            var parameter = new Object();
            if (url.indexOf('?') != -1) {
                var str = url.substr(1);
                strs = str.split('&');
                for (var i = 0; i < strs.length; i++) {
                    parameter[strs[i].split('=')[0]] = decodeURIComponent(strs[i].split('=')[1]);
                }
            }
            return parameter;
        }
    }
})();