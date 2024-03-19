/*
**  应用
*/
HROS.app = (function () {
    return {
        /*
        **  初始化桌面应用
        */
        init: function () {
            //绑定'应用市场'点击事件
            $('#desk').on('click', '.add', function () {
                HROS.window.createTemp({
                    appid: 'hoorayos-yysc',
                    title: '应用市场',
                    url: 'appMarketController/index.do',
                    width: 880,
                    height: 590,
                    isflash: false
                });
            });
            //绑定应用拖动事件
            HROS.app.move();
            //绑定滚动条拖动事件
            HROS.app.moveScrollbar();
            //绑定应用右击事件
            $('body').on('contextmenu', '.appbtn:not(.add)', function (e) {
                HROS.popupMenu.hide();
                switch ($(this).attr('type')) {
                    case 'window':
                    case 'widget':
                        HROS.popupMenu.app(e, $(this));
                        break;
                    case 'pwindow':
                    case 'pwidget':
                        HROS.popupMenu.papp(e, $(this));
                        break;
                    case 'folder':
                        HROS.popupMenu.folder(e, $(this));
                        break;
                    case 'file':
                        HROS.popupMenu.file(e, $(this));
                        break;
                }
                return false;
            });
            //绑定桌面缩放事件
            $(document).on('mousewheel', function (event, delta) {
                if (event.ctrlKey) {
                    event.preventDefault();
                    var size;
                    if (delta < 0) {
                        size = HROS.CONFIG.appSize - 8 < 32 ? 32 : HROS.CONFIG.appSize - 8;
                    } else {
                        size = HROS.CONFIG.appSize + 8 > 128 ? 128 : HROS.CONFIG.appSize + 8;
                    }
                    HROS.app.updateSize(size);
                }
            });
            //获取桌面应用数据
            HROS.app.get();
        },
        /*
        **  更新应用排列方式
        */
        updateXY: function (i) {
            if (HROS.CONFIG.appXY != i) {
                HROS.CONFIG.appXY = i;
                HROS.deskTop.resize();
                if (HROS.base.checkLogin()) {
                    $.ajax({
                        data: {
                            ac: 'setAppXY',
                            appxy: i
                        }
                    });
                }
            }
        },
        /*
        **  更新应用显示尺寸
        */
        updateSize: function (i) {
            if (HROS.CONFIG.appSize != i) {
                HROS.CONFIG.appSize = i;
                HROS.deskTop.resize();
                HROS.app.set()
                HROS.dock.setPos();
                HROS.window.setPos();
                if (HROS.base.checkLogin()) {
                    $.ajax({
                        data: {
                            ac: 'setAppSize',
                            appsize: i
                        },
                        url: basePath + "/systemSettingController/setAppSize.do"
                    }).done(function () {

                    });
                }
            }
        },
        /*
        **  更新应用垂直间距
        */
        updateVertical: function (i) {
            if (HROS.CONFIG.appVerticalSpacing != i) {
                HROS.CONFIG.appVerticalSpacing = i;
                HROS.deskTop.resize();
                if (HROS.base.checkLogin()) {
                    $.ajax({
                        data: {
                            ac: 'setAppVerticalSpacing',
                            appverticalspacing: i
                        }
                    });
                }
            }
        },
        /*
        **  更新应用水平间距
        */
        updateHorizontal: function (i) {
            if (HROS.CONFIG.appHorizontalSpacing != i) {
                HROS.CONFIG.appHorizontalSpacing = i;
                HROS.deskTop.resize();
                if (HROS.base.checkLogin()) {
                    $.ajax({
                        data: {
                            ac: 'setAppHorizontalSpacing',
                            apphorizontalspacing: i
                        }
                    });
                }
            }
        },
        /*
        **  获取桌面应用数据
        */
        get: function () {
            //获取json数组并循环输出每个应用
            $.ajax({

                data: {ac: 'getMyApp'},
                dataType: "json",
                beforeSend: function () {
                    HROS.VAR.isAppMoving = true;
                },
                url: ajaxUrl.getMyApp

            }).done(function (sc) {
                HROS.VAR.isAppMoving = false;
                if (typeof sc == 'object') {
                    if (typeof sc['dock'] == 'object') {
                        HROS.VAR.dock = sc['dock'];
                    }
                    if (typeof sc['desk1'] == 'object') {
                        HROS.VAR.desk1 = sc['desk1'];
                    }
                    if (typeof sc['desk2'] == 'object') {
                        HROS.VAR.desk2 = sc['desk2'];
                    }
                    if (typeof sc['desk3'] == 'object') {
                        HROS.VAR.desk3 = sc['desk3'];
                    }
                    if (typeof sc['desk4'] == 'object') {
                        HROS.VAR.desk4 = sc['desk4'];
                    }
                    if (typeof sc['desk5'] == 'object') {
                        HROS.VAR.desk5 = sc['desk5'];
                    }
                    if (typeof sc['folder'] == 'object') {
                        HROS.VAR.folder = sc['folder'];
                    }
                }
                //输出桌面应用
                HROS.app.set();
            });
        },
        /*
        **  渲染桌面，输出应用
        */
        set: function () {
            //加载应用码头应用
            var dock_append = '';
            if (HROS.VAR.dock != '') {
                $(HROS.VAR.dock).each(function () {
                    if (this.type == 'window') {
                        dock_append += HROS.template.app({
                            'title': this.name,
                            'type': this.type,
                            'id': 'd_' + this.appid,
                            'appid': this.appid,
                            'realappid': this.realappid == 0 ? this.appid : this.realappid,
                            'imgsrc': this.icon,
                            'appsize': HROS.CONFIG.appSize
                        });
                    } else if (this.type == 'folder') {
                        let folderArray = HROS.VAR.folder.filter(item => item.appid == this.appid)
                        dock_append += HROS.template.appFolder({
                            'title': this.name,
                            'type': this.type,
                            'id': 'd_' + this.appid,
                            'folderArray': folderArray[0].apps,
                            'appid': this.appid,
                            'realappid': this.realappid == 0 ? this.appid : this.realappid,
                            'imgsrc': this.icon,
                            'appsize': HROS.CONFIG.appSize
                        });
                    }

                });
            }
            $('#dock-bar .dock-applist .appbtn').remove();
            $('#dock-bar .dock-applist').append(dock_append);
            //加载桌面应用
            for (var j = 1; j <= 5; j++) {
                var desk_append = '';
                var desk = eval('HROS.VAR.desk' + j);
                if (desk != '') {
                    $(desk).each(function () {
                        console.log("this.ddddd", this)
                        if (this.type == 'window') {
                            desk_append += HROS.template.app({
                                'title': this.name,
                                'type': this.type,
                                'id': 'd_' + this.appid,
                                'appid': this.appid,
                                'realappid': this.realappid == 0 ? this.appid : this.realappid,
                                'imgsrc': this.icon,
                                'appsize': HROS.CONFIG.appSize
                            });
                        } else if (this.type == 'folder') {
                            let folderArray = HROS.VAR.folder.filter(item => item.appid == this.appid)
                            console.log("floderarray", folderArray[0].apps)
                            desk_append += HROS.template.appFolder({
                                'title': this.name,
                                'type': this.type,
                                'id': 'd_' + this.appid,
                                'folderArray': folderArray[0].apps,
                                'appid': this.appid,
                                'realappid': this.realappid == 0 ? this.appid : this.realappid,
                                'imgsrc': this.icon,
                                'appsize': HROS.CONFIG.appSize
                            });
                        }
                    });
                }
                desk_append += HROS.template.add({
                    'appsize': HROS.CONFIG.appSize
                });
                $('#desk-' + j + ' .desktop-apps-container .appbtn').remove();
                $('#desk-' + j + ' .desktop-apps-container').append(desk_append);
            }
            HROS.app.setPos(false);
            //如果文件夹预览面板为显示状态，则进行更新
            $('body .quick_view_container').each(function () {
                HROS.folderView.get($('#d_' + $(this).attr('appid')));
            });
            //如果文件夹窗口为显示状态，则进行更新
            $('#desk .folder-window').each(function () {
                HROS.window.updateFolder($(this).attr('appid'));
            });
            //加载滚动条
            HROS.app.getScrollbar();
        },
        setPos: function (isAnimate) {
            $('#desk .desktop-container .appbtn img').width(HROS.CONFIG.appSize).height(HROS.CONFIG.appSize);
            $('#desk .desktop-container .appbtn .appFolder img').width(20).height(20);
            $('#desk .desktop-container .appbtn span').width(Number(HROS.CONFIG.appSize) + 10);
            isAnimate = isAnimate == null ? true : isAnimate;
            var grid = HROS.grid.getAppGrid(), dockGrid = HROS.grid.getDockAppGrid();
            $('#dock-bar .dock-applist .appbtn').each(function (i) {
                $(this).css({
                    'top': HROS.CONFIG.dockPos == 'top' ? dockGrid[i]['startY'] : dockGrid[i]['startY'] + 5,
                    'left': HROS.CONFIG.dockPos == 'top' ? dockGrid[i]['startX'] + 5 : dockGrid[i]['startX']
                }).attr('top', $(this).offset().top).attr('left', $(this).offset().left);
            });
            for (var j = 1; j <= 5; j++) {
                $('#desk-' + j + ' .appbtn').each(function (i) {
                    var offsetTop = HROS.CONFIG.appVerticalSpacing / 2;
                    var offsetLeft = HROS.CONFIG.appHorizontalSpacing / 2;
                    var top = grid[i]['startY'] + offsetTop;
                    var left = grid[i]['startX'] + offsetLeft;
                    $(this).stop(true, false).animate({
                        'top': top,
                        'left': left
                    }, isAnimate ? 500 : 0);
                    switch (HROS.CONFIG.dockPos) {
                        case 'top':
                            $(this).attr('left', left).attr('top', top + $('#dock-bar').height());
                            break;
                        case 'left':
                            $(this).attr('left', left + $('#dock-bar').width()).attr('top', top);
                            break;
                        default:
                            $(this).attr('left', left).attr('top', top);
                    }
                });
            }
            //更新滚动条
            HROS.app.getScrollbar();
        },
        /*
        **  添加应用
        */
        add: function (id, callback) {
            function done() {
                callback && callback();
            }

            if (HROS.base.checkLogin()) {
                $.ajax({
                    data: {
                        ac: 'addMyApp',
                        id: id,
                        desk: HROS.CONFIG.desk
                    },
                    url: ajaxUrl.addMyApp
                }).done(function (responseText) {
                    done();
                }).error(function (error1) {
                    done();
                })

            } else {
                done();
            }
        },
        /*
        **  删除应用
        */
        remove: function (id, callback) {
            function done() {
                HROS.widget.removeCookie(id);
                callback && callback();
            }

            if (HROS.base.checkLogin()) {
                $.ajax({
                    data: {
                        ac: 'delMyApp',
                        id: id
                    },
                    url: ajaxUrl.delMyApp

                }).done(function (responseText) {
                    done();
                });
            } else {
                done();
            }
        },
        /*
        **  应用拖动、打开
        **  这块代码略多，主要处理了9种情况下的拖动，分别是：
        **  桌面拖动到应用码头、桌面拖动到文件夹内、当前桌面上拖动(排序)
        **  应用码头拖动到桌面、应用码头拖动到文件夹内、应用码头上拖动(排序)
        **  文件夹内拖动到桌面、文件夹内拖动到应用码头、不同文件夹之间拖动
        */
        move: function () {
            //应用码头应用拖动
            $('#dock-bar .dock-applist').on('mousedown touchstart', '.appbtn', function (e) {
                e.preventDefault();
                e.stopPropagation();
                if (e.which == 0 || e.which == 1) {
                    var oldobj = $(this);
                    var obj = $('<div id="shortcut_shadow">' + oldobj.html() + '</div>');
                    var dx = e.type == 'mousedown' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                    var dy = e.type == 'mousedown' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                    var cx = e.type == 'mousedown' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                    var cy = e.type == 'mousedown' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                    var x = dx - oldobj.offset().left;
                    var y = dy - oldobj.offset().top;
                    var lay = HROS.maskBox.desk();
                    //绑定鼠标移动事件
                    $(document).on('mousemove touchmove', function (e) {
                        var dcx = e.type == 'mousemove' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                        var dcy = e.type == 'mousemove' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                        $('body').append(obj);
                        cx = dcx <= 0 ? 0 : dcx >= $(window).width() ? $(window).width() : dcx;
                        cy = dcy <= 0 ? 0 : dcy >= $(window).height() ? $(window).height() : dcy;
                        if (dx != cx || dy != cy) {
                            obj.css({
                                left: cx - x,
                                top: cy - y,
                            }).show();
                            $(obj).find('.appFolder').css({
                                display: 'flex',
                                'flex-wrap': 'wrap',
                                overflow: 'hidden'
                            })
                            $(obj).find('.apptitle').css({
                                width: HROS.CONFIG.appSize
                            })
                            $(obj).find('.folderAppImg').css({
                                width: '12px',
                                height: '12px'
                            })
                            $(obj).find('.folderAppContainer').css({
                                margin: '2px',
                                'margin-bottom': '3px',
                                'margin-top': '2px'
                            })
                        }
                    }).on('mouseup touchend', function () {
                        $(document).off('mousemove touchmove mouseup touchend');
                        obj.remove();
                        lay.hide();
                        //判断是否移动应用，如果没有则判断为click事件
                        if (dx == cx && dy == cy) {
                            switch (oldobj.attr('type')) {
                                case 'window':
                                case 'pwindow':
                                case 'file':
                                    HROS.window.create(oldobj.attr('appid'), oldobj.attr('type'));
                                    break;
                                case 'widget':
                                case 'pwidget':
                                    HROS.widget.create(oldobj.attr('appid'), oldobj.attr('type'));
                                    break;
                                case 'folder':
                                    HROS.folderView.get(oldobj);
                                    break;
                            }
                            return false;
                        }
                        var movegrid = HROS.grid.searchFolderGrid(cx, cy);
                        if (movegrid != null) {
                            if (oldobj.hasClass('folder') == false) {
                                var id = oldobj.attr('appid');
                                var from = oldobj.index();
                                var to = movegrid;
                                if (HROS.base.checkLogin()) {
                                    if (!HROS.app.checkIsMoving()) {
                                        if (HROS.app.dataDockToFolder(id, from, to)) {
                                            $.ajax({
                                                data: {
                                                    movetype: 'dock-folder',
                                                    id: id,
                                                    from: from,
                                                    to: to
                                                },
                                                type: "POST",
                                                dataType: "json",
                                                url: ajaxUrl.moveMyApp,
                                                success: function (e) {
                                                    HROS.VAR.isAppMoving = false;
                                                    // classtippedtipper();
                                                },
                                                error: function (XMLHttpRequest, textStatus, errorThrown) {
                                                    ajaxErrorHandler(XMLHttpRequest);
                                                }

                                            }).done(function (responseText) {
                                                HROS.VAR.isAppMoving = false;
                                            });
                                        } else {

                                        }
                                    }
                                } else {
                                    HROS.app.dataDockToFolder(id, from, to);
                                }
                            }
                        } else {
                            var dock_w = HROS.CONFIG.dockPos == 'left' ? 0 : HROS.CONFIG.dockPos == 'top' ? ($(window).width() - $('#dock-container').width() + 20) / 2 : $(window).width() - $('#dock-container').width();
                            var dock_h = HROS.CONFIG.dockPos == 'top' ? 0 : ($(window).height() - $('#dock-container').height() + 20) / 2;
                            var movegrid = HROS.grid.searchDockAppGrid(cx - dock_w, cy - dock_h);
                            if (movegrid != null) {
                                if (movegrid != oldobj.index()) {
                                    var movegrid2 = HROS.grid.searchDockAppGrid2(cx - dock_w, cy - dock_h);
                                    var id = oldobj.attr('appid');
                                    var from = oldobj.index();
                                    var to = movegrid;
                                    var boa = movegrid2 % 2 == 0 ? 'b' : 'a';
                                    // console.log("aasss", HROS.VAR.dock[to].type != 'folder')


                                    if (HROS.base.checkLogin()) {
                                        if (!HROS.app.checkIsMoving()) {
                                            if ((to < HROS.VAR.dock.length || HROS.VAR.dock[to].type == 'folder') && HROS.VAR.dock[from].type != 'folder') {
                                                let toid = HROS.VAR.dock[to].appid
                                                //判断目标应用是文件夹
                                                if (HROS.app.dataDockToFolder(id, from, toid)) {
                                                    $.ajax({
                                                        data: {
                                                            movetype: 'dock-folder',
                                                            id: id,
                                                            from: from,
                                                            to: toid
                                                        },
                                                        type: "POST",
                                                        dataType: "json",
                                                        url: ajaxUrl.moveMyApp,
                                                        success: function (e) {
                                                            HROS.VAR.isAppMoving = false;
                                                            // HROS.app.get()
                                                        },
                                                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                                                            ajaxErrorHandler(XMLHttpRequest);
                                                        }

                                                    }).done(function (responseText) {
                                                        HROS.VAR.isAppMoving = false;
                                                    });
                                                }

                                            } else {
                                                if (HROS.app.dataDockToDock(id, from, to, boa)) {
                                                    $.ajax({
                                                        data: {
                                                            ac: 'moveMyApp',
                                                            movetype: 'dock-dock',
                                                            id: id,
                                                            from: from,
                                                            to: to,
                                                            boa: boa
                                                        },
                                                        url: ajaxUrl.moveMyApp,

                                                    }).done(function (responseText) {
                                                        HROS.VAR.isAppMoving = false;
                                                    });
                                                }
                                            }
                                        }
                                    } else {
                                        HROS.app.dataDockToDock(id, from, to, boa);
                                    }
                                }
                            } else {
                                var dock_w = HROS.CONFIG.dockPos == 'left' ? $('#dock-bar').width() : 0;
                                var dock_h = HROS.CONFIG.dockPos == 'top' ? $('#dock-bar').height() : 0;
                                var deskScrollLeft = $('#desk-' + HROS.CONFIG.desk + ' .desktop-apps-container').scrollLeft();
                                var deskScrollTop = $('#desk-' + HROS.CONFIG.desk + ' .desktop-apps-container').scrollTop();
                                var movegrid = HROS.grid.searchAppGrid(cx - dock_w + deskScrollLeft, cy - dock_h + deskScrollTop);
                                if (movegrid != null) {
                                    var movegrid2 = HROS.grid.searchAppGrid2(cx - dock_w + deskScrollLeft, cy - dock_h + deskScrollTop);
                                    var id = oldobj.attr('appid');
                                    var from = oldobj.index();
                                    var to = movegrid;
                                    var boa = movegrid2 % 2 == 0 ? 'b' : 'a';
                                    var desk = HROS.CONFIG.desk;
                                    let deskArray = 'desk' + desk
                                    if (HROS.base.checkLogin()) {
                                        if (!HROS.app.checkIsMoving()) {
                                            if (to < HROS.VAR[deskArray].length && HROS.VAR[deskArray][to].type == 'folder' && HROS.VAR[deskArray][from].type != 'folder') {
                                                let toid = HROS.VAR[deskArray][to].appid
                                                if (HROS.app.dataDockToFolder(id, from, toid, desk)) {
                                                    $.ajax({
                                                        data: {
                                                            movetype: 'dock-folder',
                                                            id: id,
                                                            from: from,
                                                            to: toid,
                                                            desk: desk
                                                        },
                                                        type: "POST",
                                                        dataType: "json",
                                                        url: ajaxUrl.moveMyApp,
                                                        success: function (e) {
                                                            HROS.VAR.isAppMoving = false;
                                                            // classtippedtipper();
                                                        },
                                                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                                                            ajaxErrorHandler(XMLHttpRequest);
                                                        }

                                                    }).done(function (responseText) {
                                                        HROS.VAR.isAppMoving = false;
                                                    });
                                                }
                                            } else {
                                                if (HROS.app.dataDockToDesk(id, from, to, boa, desk)) {
                                                    $.ajax({
                                                        data: {
                                                            ac: 'moveMyApp',
                                                            movetype: 'dock-desk',
                                                            id: id,
                                                            from: from,
                                                            to: to,
                                                            boa: boa,
                                                            desk: desk
                                                        },
                                                        url: ajaxUrl.moveMyApp,
                                                    }).done(function (responseText) {
                                                        HROS.VAR.isAppMoving = false;
                                                    });
                                                }

                                            }
                                        }


                                    } else {
                                        HROS.app.dataDockToDesk(id, from, to, boa, desk);
                                    }
                                }


                            }
                        }
                    });
                }
            });
            //桌面应用拖动
            $('#desktop .desktop-apps-container').on('mousedown touchstart', '.appbtn:not(.add)', function (e) {
                e.preventDefault();
                e.stopPropagation();
                if (e.which == 0 || e.which == 1) {
                    var oldobj = $(this);
                    var obj = $('<div id="shortcut_shadow">' + oldobj.html() + '</div>');
                    var dx = e.type == 'mousedown' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                    var dy = e.type == 'mousedown' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                    var cx = e.type == 'mousedown' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                    var cy = e.type == 'mousedown' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                    var x = dx - oldobj.offset().left;
                    var y = dy - oldobj.offset().top;
                    var lay = HROS.maskBox.desk();
                    //绑定鼠标移动事件
                    $(document).on('mousemove touchmove', function (e) {
                        var dcx = e.type == 'mousemove' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                        var dcy = e.type == 'mousemove' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                        $('body').append(obj);
                        lay.show();
                        cx = dcx <= 0 ? 0 : dcx >= $(window).width() ? $(window).width() : dcx;
                        cy = dcy <= 0 ? 0 : dcy >= $(window).height() ? $(window).height() : dcy;
                        if (dx != cx || dy != cy) {
                            obj.css({
                                left: cx - x,
                                top: cy - y
                            }).show();
                            $(obj).find('.appFolder').css({
                                display: 'flex',
                                'flex-wrap': 'wrap',
                                overflow: 'hidden'
                            })
                            $(obj).find('.folderAppImg').css({
                                width: '12px',
                                height: '12px'
                            })
                            $(obj).find('.folderAppContainer').css({
                                margin: '2px',
                                'margin-bottom': '3px',
                                'margin-top': '2px'
                            })
                        }
                    }).on('mouseup touchend', function () {
                        $(document).off('mousemove touchmove mouseup touchend');
                        obj.remove();
                        lay.hide();
                        //判断是否移动应用，如果没有则判断为click事件
                        if (dx == cx && dy == cy) {
                            switch (oldobj.attr('type')) {
                                case 'window':
                                case 'pwindow':
                                case 'file':
                                    HROS.window.create(oldobj.attr('appid'), oldobj.attr('type'));
                                    break;
                                case 'widget':
                                case 'pwidget':
                                    HROS.widget.create(oldobj.attr('appid'), oldobj.attr('type'));
                                    break;
                                case 'folder':
                                    HROS.folderView.get(oldobj);
                                    break;
                            }
                            return false;
                        }
                        var movegrid = HROS.grid.searchFolderGrid(cx, cy);
                        if (movegrid != null) {
                            if (oldobj.attr('type') != 'folder') {
                                var id = oldobj.attr('appid');
                                var from = oldobj.index();
                                var to = movegrid;
                                var desk = HROS.CONFIG.desk;
                                if (HROS.base.checkLogin()) {
                                    if (!HROS.app.checkIsMoving()) {
                                        if (HROS.app.dataDeskToFolder(id, from, to, desk)) {
                                            $.ajax({
                                                data: {
                                                    ac: 'moveMyApp',
                                                    movetype: 'desk-folder',
                                                    id: id,
                                                    from: from,
                                                    to: to,
                                                    desk: desk
                                                },
                                                url: ajaxUrl.moveMyApp,
                                            }).done(function (responseText) {
                                                HROS.VAR.isAppMoving = false;
                                            });
                                        }
                                    }
                                } else {
                                    HROS.app.dataDeskToFolder(id, from, to, desk);
                                }
                            }
                        } else {
                            var dock_w = HROS.CONFIG.dockPos == 'left' ? 0 : HROS.CONFIG.dockPos == 'top' ? ($(window).width() - $('#dock-container').width() + 20) / 2 : $(window).width() - $('#dock-container').width();
                            var dock_h = HROS.CONFIG.dockPos == 'top' ? 0 : ($(window).height() - $('#dock-container').height() + 20) / 2;
                            var movegrid = HROS.grid.searchDockAppGrid(cx - dock_w, cy - dock_h);
                            if (movegrid != null) {
                                var movegrid2 = HROS.grid.searchDockAppGrid2(cx - dock_w, cy - dock_h);
                                var id = oldobj.attr('appid');
                                var from = oldobj.index();
                                var to = movegrid;
                                var boa = movegrid2 % 2 == 0 ? 'b' : 'a';
                                var desk = HROS.CONFIG.desk;
                                let deskArray = 'desk' + desk
                                if (HROS.base.checkLogin()) {
                                    if (!HROS.app.checkIsMoving()) {
                                        if (HROS.VAR.dock != null && to < HROS.VAR.dock.length && HROS.VAR.dock[to].type == 'folder' && HROS.VAR[deskArray][from].type != 'folder') {
                                            let toid = HROS.VAR.dock[to].appid
                                            if (HROS.app.dataDeskToFolder(id, from, toid, desk)) {
                                                $.ajax({
                                                    data: {
                                                        movetype: 'desk-folder',
                                                        id: id,
                                                        from: from,
                                                        to: toid,
                                                        desk: desk
                                                    },
                                                    type: "POST",
                                                    dataType: "json",
                                                    url: ajaxUrl.moveMyApp,
                                                    success: function (e) {
                                                        HROS.VAR.isAppMoving = false;
                                                        // HROS.app.get()
                                                    },
                                                    error: function (XMLHttpRequest, textStatus, errorThrown) {

                                                    }

                                                }).done(function (responseText) {
                                                    HROS.VAR.isAppMoving = false;
                                                });
                                            }

                                        } else {
                                            if (HROS.app.dataDeskToDock(id, from, to, boa, desk)) {
                                                $.ajax({
                                                    type: "POST",
                                                    data: {
                                                        ac: 'moveMyApp',
                                                        movetype: 'desk-dock',
                                                        id: id,
                                                        from: from,
                                                        to: to,
                                                        boa: boa,
                                                        desk: desk
                                                    },
                                                    dataType: "json",
                                                    url: ajaxUrl.moveMyApp,
                                                }).done(function (responseText) {
                                                    HROS.VAR.isAppMoving = false;
                                                    // HROS.app.get()
                                                });
                                            }
                                        }
                                    }
                                } else {
                                    HROS.app.dataDeskToDock(id, from, to, boa, desk);
                                }
                            } else {
                                var dock_w = HROS.CONFIG.dockPos == 'left' ? $('#dock-bar').width() : 0;
                                var dock_h = HROS.CONFIG.dockPos == 'top' ? $('#dock-bar').height() : 0;
                                var deskScrollLeft = $('#desk-' + HROS.CONFIG.desk + ' .desktop-apps-container').scrollLeft();
                                var deskScrollTop = $('#desk-' + HROS.CONFIG.desk + ' .desktop-apps-container').scrollTop();
                                var movegrid = HROS.grid.searchAppGrid(cx - dock_w + deskScrollLeft, cy - dock_h + deskScrollTop);
                                if (movegrid != null && movegrid != oldobj.index()) {
                                    var movegrid2 = HROS.grid.searchAppGrid2(cx - dock_w + deskScrollLeft, cy - dock_h + deskScrollTop);
                                    var id = oldobj.attr('appid');
                                    var from = oldobj.index();
                                    var to = movegrid;
                                    var boa = movegrid2 % 2 == 0 ? 'b' : 'a';
                                    var desk = HROS.CONFIG.desk;
                                    let deskArray = 'desk' + desk
                                    if (HROS.base.checkLogin()) {
                                        if (!HROS.app.checkIsMoving()) {
                                            // if ((to<HROS.VAR[deskArray].length ||  (to >= HROS.VAR[deskArray].length &&HROS.VAR[deskArray][to].type != 'folder')) || HROS.VAR[deskArray][from].type == 'folder')
                                            if (to < HROS.VAR[deskArray].length && HROS.VAR[deskArray][to].type == 'folder' && HROS.VAR[deskArray][from].type != 'folder') {
                                                let toid = HROS.VAR[deskArray][to].appid
                                                if (HROS.app.dataDeskToFolder(id, from, toid, desk)) {
                                                    $.ajax({
                                                        data: {
                                                            ac: 'moveMyApp',
                                                            movetype: 'desk-folder',
                                                            id: id,
                                                            from: from,
                                                            to: toid,
                                                            desk: desk
                                                        },
                                                        url: ajaxUrl.moveMyApp,
                                                    }).done(function (responseText) {
                                                        HROS.VAR.isAppMoving = false;
                                                        // HROS.app.get()
                                                    });
                                                }
                                            } else {
                                                if (HROS.app.dataDeskToDesk(id, from, to, boa, desk)) {
                                                    $.ajax({
                                                        data: {
                                                            ac: 'moveMyApp',
                                                            movetype: 'desk-desk',
                                                            id: id,
                                                            from: from,
                                                            to: to,
                                                            boa: boa,
                                                            desk: desk
                                                        },
                                                        url: ajaxUrl.moveMyApp,
                                                    }).done(function (responseText) {
                                                        HROS.VAR.isAppMoving = false;
                                                    });
                                                }

                                            }
                                        }
                                    } else {
                                        HROS.app.dataDeskToDesk(id, from, to, boa, desk);
                                    }
                                }
                            }
                        }
                    });
                }
            });
            //文件夹内应用拖动
            $('body').on('mousedown touchstart', '.folder_body .appbtn, .quick_view_container .appbtn', function (e) {
                e.preventDefault();
                e.stopPropagation();
                if (e.which == 0 || e.which == 1) {
                    var oldobj = $(this);
                    var obj = $('<div id="shortcut_shadow">' + oldobj.html() + '</div>');
                    var dx = e.type == 'mousedown' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                    var dy = e.type == 'mousedown' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                    var cx = e.type == 'mousedown' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                    var cy = e.type == 'mousedown' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                    var x = dx - oldobj.offset().left;
                    var y = dy - oldobj.offset().top;
                    var lay = HROS.maskBox.desk();
                    //绑定鼠标移动事件
                    $(document).on('mousemove touchmove', function (e) {
                        var dcx = e.type == 'mousemove' ? e.clientX : e.originalEvent.targetTouches[0].clientX;
                        var dcy = e.type == 'mousemove' ? e.clientY : e.originalEvent.targetTouches[0].clientY;
                        $('body').append(obj);
                        lay.show();
                        cx = dcx <= 0 ? 0 : dcx >= $(window).width() ? $(window).width() : dcx;
                        cy = dcy <= 0 ? 0 : dcy >= $(window).height() ? $(window).height() : dcy;
                        if (dx != cx || dy != cy) {
                            obj.css({
                                left: cx - x,
                                top: cy - y
                            }).show();
                        }
                    }).on('mouseup touchend', function () {
                        $(document).off('mousemove touchmove mouseup touchend');
                        obj.remove();
                        lay.hide();
                        //判断是否移动应用，如果没有则判断为click事件
                        if (dx == cx && dy == cy) {
                            switch (oldobj.attr('type')) {
                                case 'window':
                                case 'pwindow':
                                case 'file':
                                    HROS.window.create(oldobj.attr('appid'), oldobj.attr('type'));
                                    break;
                                case 'widget':
                                case 'pwidget':
                                    HROS.widget.create(oldobj.attr('appid'), oldobj.attr('type'));
                                    break;
                            }
                            return false;
                        }
                        var movegrid = HROS.grid.searchFolderGrid(cx, cy);
                        if (movegrid != null) {
                            if ((oldobj.parents('.folder-window').attr('appid') || oldobj.parents('.quick_view_container').attr('appid')) != movegrid) {
                                var id = oldobj.attr('appid');
                                var from = oldobj.index();
                                var to = movegrid;
                                var fromFolderId = oldobj.parents('.folder-window').attr('appid') || oldobj.parents('.quick_view_container').attr('appid');
                                if (HROS.base.checkLogin()) {
                                    if (!HROS.app.checkIsMoving()) {
                                        if (HROS.app.dataFolderToFolder(id, from, to, fromFolderId)) {
                                            $.ajax({
                                                data: {
                                                    ac: 'moveMyApp',
                                                    movetype: 'folder-folder',
                                                    id: id,
                                                    from: from,
                                                    to: to
                                                },
                                                url: ajaxUrl.moveMyApp,
                                            }).done(function (responseText) {
                                                HROS.VAR.isAppMoving = false;
                                            });
                                        }
                                    }
                                } else {
                                    HROS.app.dataFolderToFolder(id, from, to, fromFolderId);
                                }
                            }
                        } else {
                            var dock_w = HROS.CONFIG.dockPos == 'left' ? 0 : HROS.CONFIG.dockPos == 'top' ? ($(window).width() - $('#dock-container').width() + 20) / 2 : $(window).width() - $('#dock-container').width();
                            var dock_h = HROS.CONFIG.dockPos == 'top' ? 0 : ($(window).height() - $('#dock-container').height() + 20) / 2;
                            var movegrid = HROS.grid.searchDockAppGrid(cx - dock_w, cy - dock_h);
                            if (movegrid != null) {
                                var movegrid2 = HROS.grid.searchDockAppGrid2(cx - dock_w, cy - dock_h);
                                var id = oldobj.attr('appid');
                                var from = oldobj.index();
                                var to = movegrid;
                                var fromFolderId = oldobj.parents('.folder-window').attr('appid') || oldobj.parents('.quick_view_container').attr('appid');
                                var boa = movegrid2 % 2 == 0 ? 'b' : 'a';
                                var desk = HROS.CONFIG.desk;
                                if (HROS.base.checkLogin()) {
                                    if (!HROS.app.checkIsMoving()) {
                                        if (HROS.app.dataFolderToDock(id, from, to, fromFolderId, boa, desk)) {
                                            $.ajax({
                                                data: {
                                                    ac: 'moveMyApp',
                                                    movetype: 'folder-dock',
                                                    id: id,
                                                    to: to,
                                                    boa: boa,
                                                    desk: desk
                                                },
                                                url: ajaxUrl.moveMyApp,
                                            }).done(function (responseText) {
                                                HROS.VAR.isAppMoving = false;
                                            });
                                        }
                                    }
                                } else {
                                    HROS.app.dataFolderToDock(id, from, to, fromFolderId, boa, desk);
                                }
                            } else {
                                var dock_w = HROS.CONFIG.dockPos == 'left' ? $('#dock-bar').width() : 0;
                                var dock_h = HROS.CONFIG.dockPos == 'top' ? $('#dock-bar').height() : 0;
                                var deskScrollLeft = $('#desk-' + HROS.CONFIG.desk + ' .desktop-apps-container').scrollLeft();
                                var deskScrollTop = $('#desk-' + HROS.CONFIG.desk + ' .desktop-apps-container').scrollTop();
                                var movegrid = HROS.grid.searchAppGrid(cx - dock_w + deskScrollLeft, cy - dock_h + deskScrollTop);
                                if (movegrid != null) {
                                    var movegrid2 = HROS.grid.searchAppGrid2(cx - dock_w + deskScrollLeft, cy - dock_h + deskScrollTop);
                                    var id = oldobj.attr('appid');
                                    var from = oldobj.index();
                                    var to = movegrid;
                                    var fromFolderId = oldobj.parents('.folder-window').attr('appid') || oldobj.parents('.quick_view_container').attr('appid');
                                    var boa = movegrid2 % 2 == 0 ? 'b' : 'a';
                                    var desk = HROS.CONFIG.desk;
                                    if (HROS.base.checkLogin()) {
                                        if (!HROS.app.checkIsMoving()) {
                                            if (HROS.app.dataFolderToDesk(id, from, to, fromFolderId, boa, desk)) {
                                                $.ajax({
                                                    data: {
                                                        ac: 'moveMyApp',
                                                        movetype: 'folder-desk',
                                                        id: id,
                                                        to: to,
                                                        boa: boa,
                                                        desk: desk
                                                    },
                                                    url: ajaxUrl.moveMyApp,
                                                }).done(function (responseText) {
                                                    HROS.VAR.isAppMoving = false;
                                                });
                                            }
                                        }
                                    } else {
                                        HROS.app.dataFolderToDesk(id, from, to, fromFolderId, boa, desk);
                                    }
                                }
                            }
                        }
                    });
                }
            });
        },
        /*
        **  加载滚动条
        */
        getScrollbar: function () {
            setTimeout(function () {
                $('#desk .desktop-container').each(function () {
                    var desk = $(this).children('.desktop-apps-container'), scrollbar = $(this).children('.scrollbar');
                    var scrollbarLeft = desk.nextAll('.scrollbar-x').position().left,
                        scrollbarTop = desk.nextAll('.scrollbar-y').position().top;
                    //先清空所有附加样式
                    scrollbar.hide();
                    desk.scrollLeft(0).scrollTop(0);
                    /*
                    **  判断应用排列方式
                    **  横向排列超出屏幕则出现纵向滚动条，纵向排列超出屏幕则出现横向滚动条
                    */
                    if (HROS.CONFIG.appXY == 'x') {
                        /*
                        **  获得桌面应用定位好后的实际高度
                        **  因为显示的高度是固定的，而实际的高度是根据应用个数会变化
                        */
                        var deskH = parseInt(desk.children('.add').css('top')) + parseInt(desk.children('.add').outerHeight());
                        /*
                        **  计算滚动条高度
                        **  高度公式（应用纵向排列计算滚动条宽度以此类推）：
                        **  滚动条实际高度 = 桌面显示高度 / 桌面实际高度 * 滚动条总高度(桌面显示高度)
                        **  如果“桌面显示高度 < 桌面实际高度”，则出现滚动条
                        */
                        if (desk.height() < deskH) {
                            desk.nextAll('.scrollbar-y').height(desk.height() / deskH * desk.height());
                            scrollbarTop = scrollbarTop + desk.nextAll('.scrollbar-y').height() > desk.height() ? desk.height() - desk.nextAll('.scrollbar-y').height() : scrollbarTop;
                            desk.nextAll('.scrollbar-y').css('top', scrollbarTop).show();
                            desk.scrollTop(scrollbarTop / desk.height() * deskH);
                        }
                    } else {
                        var deskW = parseInt(desk.children('.add').css('left')) + parseInt(desk.children('.add').outerWidth());
                        if (desk.width() < deskW) {
                            desk.nextAll('.scrollbar-x').width(desk.width() / deskW * desk.width());
                            scrollbarLeft = scrollbarLeft + desk.nextAll('.scrollbar-x').width() > desk.width() ? desk.width() - desk.nextAll('.scrollbar-w').width() : scrollbarLeft;
                            desk.nextAll('.scrollbar-x').css('left', scrollbarLeft).show();
                            desk.scrollLeft(scrollbarLeft / desk.width() * deskW);
                        }
                    }
                });
            }, 500);
        },
        /*
        **  移动滚动条
        */
        moveScrollbar: function () {
            /*
            **  手动拖动
            */
            $('#desk .scrollbar').on('mousedown', function (e) {
                var x, y, cx, cy, deskrealw, deskrealh, movew, moveh;
                var scrollbar = $(this), desk = scrollbar.prevAll('.desktop-apps-container');
                deskrealw = parseInt(desk.children('.add').css('left')) + parseInt(desk.children('.add').outerWidth());
                deskrealh = parseInt(desk.children('.add').css('top')) + parseInt(desk.children('.add').outerHeight());
                movew = desk.width() - scrollbar.width();
                moveh = desk.height() - scrollbar.height();
                if (scrollbar.hasClass('scrollbar-x')) {
                    x = e.clientX - scrollbar.position().left;
                } else {
                    y = e.clientY - scrollbar.position().top;
                }
                $(document).on('mousemove', function (e) {
                    if (scrollbar.hasClass('scrollbar-x')) {
                        cx = e.clientX - x < 0 ? 0 : e.clientX - x > movew ? movew : e.clientX - x;
                        scrollbar.css('left', cx);
                        desk.scrollLeft(cx / desk.width() * deskrealw);
                    } else {
                        cy = e.clientY - y < 0 ? 0 : e.clientY - y > moveh ? moveh : e.clientY - y;
                        scrollbar.css('top', cy);
                        desk.scrollTop(cy / desk.height() * deskrealh);
                    }
                }).on('mouseup', function () {
                    $(this).off('mousemove').off('mouseup');
                });
            });
            /*
            **  鼠标滚动
            */
            for (var i = 1; i <= 5; i++) {
                $('#desk-' + i).on('mousewheel', function (event, delta) {
                    var desk = $(this).find('.desktop-apps-container');
                    if (HROS.CONFIG.appXY == 'x') {
                        var deskrealh = parseInt(desk.find('.add').css('top')) + parseInt(desk.children('.add').outerHeight()),
                            scrollupdown;
                        /*
                        **  delta == -1   往下
                        **  delta == 1    往上
                        **  200px 是鼠标滚轮每滚一次的距离
                        */
                        if (delta < 0) {
                            scrollupdown = desk.scrollTop() + 200 > deskrealh - desk.height() ? deskrealh - desk.height() : desk.scrollTop() + 200;
                        } else {
                            scrollupdown = desk.scrollTop() - 200 < 0 ? 0 : desk.scrollTop() - 200;
                        }
                        desk.stop(false, true).animate({scrollTop: scrollupdown}, 300);
                        desk.nextAll('.scrollbar-y').stop(false, true).animate({
                            top: scrollupdown / deskrealh * desk.height()
                        }, 300);
                    } else {
                        var deskrealw = parseInt(desk.find('.add').css('left')) + parseInt(desk.children('.add').outerWidth()),
                            scrollleftright;
                        if (delta < 0) {
                            scrollleftright = desk.scrollLeft() + 200 > deskrealw - desk.width() ? deskrealw - desk.width() : desk.scrollLeft() + 200;
                        } else {
                            scrollleftright = desk.scrollLeft() - 200 < 0 ? 0 : desk.scrollLeft() - 200;
                        }
                        desk.stop(false, true).animate({scrollLeft: scrollleftright}, 300);
                        desk.nextAll('.scrollbar-x').stop(false, true).animate({
                            left: scrollleftright / deskrealw * desk.width()
                        }, 300);
                    }
                });
            }
        },
        checkIsMoving: function () {
            var rtn = false;
            if (HROS.VAR.isAppMoving) {
                swal({
                    type: 'warning',
                    title: '温馨提示',
                    text: '数据正在处理中，请稍后…',
                    timer: 2000,
                    showConfirmButton: false
                });
                rtn = true;
            } else {
                HROS.VAR.isAppMoving = true;
            }
            return rtn;
        },
        dataWarning: function () {
            swal({
                type: 'error',
                title: '温馨提示',
                text: '数据错误，请刷新后重试',
                timer: 2000,
                showConfirmButton: false
            });
        },
        dataDockToFolder: function (id, from, to) {
            var rtn = false;
            $(HROS.VAR.dock).each(function (i) {
                if (this.appid == id) {
                    $(HROS.VAR.folder).each(function (j) {
                        if (this.appid == to) {
                            HROS.VAR.folder[j].apps.push(HROS.VAR.dock[i]);
                            HROS.VAR.folder[j].apps = Sugar.Array(HROS.VAR.folder[j].apps).sortBy(function (n) {
                                return n.appid;
                            }, true).raw;
                            HROS.VAR.dock.splice(i, 1);
                            rtn = true;
                            return false;
                        }
                    });
                    return false;
                }
            });
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataDockToDock: function (id, from, to, boa) {
            var rtn = false;
            if (HROS.VAR.dock[from] != null) {
                if (to == 0) {
                    if (boa == 'b') {
                        HROS.VAR.dock.splice(0, 0, HROS.VAR.dock[from]);
                    } else {
                        HROS.VAR.dock.splice(1, 0, HROS.VAR.dock[from]);
                    }
                } else {
                    if (boa == 'b') {
                        HROS.VAR.dock.splice(to, 0, HROS.VAR.dock[from]);
                    } else {
                        HROS.VAR.dock.splice(to + 1, 0, HROS.VAR.dock[from]);
                    }
                }
                if (from > to) {
                    HROS.VAR.dock.splice(from + 1, 1);
                } else {
                    HROS.VAR.dock.splice(from, 1);
                }
                rtn = true;
            }
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataDockToDesk: function (id, from, to, boa, desk) {
            var rtn = false;
            desk = eval('HROS.VAR.desk' + desk);
            if (HROS.VAR.dock[from] != null) {
                if (to == 0) {
                    if (boa == 'b') {
                        desk.splice(0, 0, HROS.VAR.dock[from]);
                    } else {
                        desk.splice(1, 0, HROS.VAR.dock[from]);
                    }
                } else {
                    if (boa == 'b') {
                        desk.splice(to, 0, HROS.VAR.dock[from]);
                    } else {
                        desk.splice(to + 1, 0, HROS.VAR.dock[from]);
                    }
                }
                HROS.VAR.dock.splice(from, 1);
                rtn = true;
            }
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataDockToOtherdesk: function (id, from, todesk) {
            var rtn = false;
            todesk = eval('HROS.VAR.desk' + todesk);
            if (HROS.VAR.dock[from] != null) {
                todesk.push(HROS.VAR.dock[from]);
                HROS.VAR.dock.splice(from, 1);
                rtn = true;
            }
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataDockToDelete: function (id, from) {
            var rtn = false;
            if (HROS.VAR.dock[from] != null) {
                HROS.VAR.dock.splice(from, 1);
                rtn = true;
            }
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataDeskToFolder: function (id, from, to, desk) {
            var rtn = false;
            desk = eval('HROS.VAR.desk' + desk);
            $(HROS.VAR.folder).each(function (i) {
                if (this.appid == to && desk[from] != null) {
                    HROS.VAR.folder[i].apps.push(desk[from]);
                    HROS.VAR.folder[i].apps = Sugar.Array(HROS.VAR.folder[i].apps).sortBy(function (n) {
                        return n.appid;
                    }, true).raw;
                    desk.splice(from, 1);
                    rtn = true;
                    return false;
                }
            });
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataDeskToDock: function (id, from, to, boa, desk) {
            var rtn = false;
            desk = eval('HROS.VAR.desk' + desk);
            if (desk[from] != null) {
                if (HROS.VAR.dock == null) {
                    HROS.VAR.dock = [];
                }
                if (to == 0) {
                    if (boa == 'b') {
                        HROS.VAR.dock.splice(0, 0, desk[from]);
                    } else {
                        HROS.VAR.dock.splice(1, 0, desk[from]);
                    }
                } else {
                    if (boa == 'b') {
                        HROS.VAR.dock.splice(to, 0, desk[from]);
                    } else {
                        HROS.VAR.dock.splice(to + 1, 0, desk[from]);
                    }
                }
                desk.splice(from, 1);
                if (HROS.VAR.dock.length > 7) {
                    desk.push(HROS.VAR.dock[7]);
                    HROS.VAR.dock.splice(7, 1);
                }
                rtn = true;
            }
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataDeskToDesk: function (id, from, to, boa, desk) {
            var rtn = false;
            desk = eval('HROS.VAR.desk' + desk);
            if (desk[from] != null) {
                if (to == 0) {
                    if (boa == 'b') {
                        desk.splice(0, 0, desk[from]);
                    } else {
                        desk.splice(1, 0, desk[from]);
                    }
                } else {
                    if (boa == 'b') {
                        desk.splice(to, 0, desk[from]);
                    } else {
                        desk.splice(to + 1, 0, desk[from]);
                    }
                }
                if (from > to) {
                    desk.splice(from + 1, 1);
                } else {
                    desk.splice(from, 1);
                }
                rtn = true;
            }
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        //--------------------------------------------
        dataDeskToOtherdesk: function (id, from, to, boa, todesk, fromdesk) {
            var rtn = false;
            fromdesk = eval('HROS.VAR.desk' + fromdesk);
            todesk = eval('HROS.VAR.desk' + todesk);
            if (!todesk) {
                todesk = [];
            }
            if (fromdesk[from] != null) {
                if (to == 0) {
                    if (boa == 'b') {
                        todesk.splice(0, 0, fromdesk[from]);
                    } else {
                        todesk.splice(1, 0, fromdesk[from]);
                    }
                } else {
                    if (boa == 'b') {
                        todesk.splice(to, 0, fromdesk[from]);
                    } else {
                        todesk.splice(to + 1, 0, fromdesk[from]);
                    }
                }
                fromdesk.splice(from, 1);
                rtn = true;
            }
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataFolderToFolder: function (id, from, to, fromFolderId) {
            var rtn = false, flags = 0, fromKey, toKey;
            $(HROS.VAR.folder).each(function (i) {
                if (this.appid == fromFolderId && HROS.VAR.folder[i].apps[from] != null) {
                    fromKey = i;
                    flags += 1;
                }
                if (this.appid == to) {
                    toKey = i;
                    flags += 1;
                }
            });
            if (flags == 2) {
                HROS.VAR.folder[toKey].apps.push(HROS.VAR.folder[fromKey].apps[from]);
                HROS.VAR.folder[toKey].apps = Sugar.Array(HROS.VAR.folder[toKey].apps).sortBy(function (n) {
                    return n.appid;
                }, true).raw;
                HROS.VAR.folder[fromKey].apps.splice(from, 1);
                rtn = true;
            }
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataFolderToDock: function (id, from, to, fromFolderId, boa, desk) {
            var rtn = false;
            desk = eval('HROS.VAR.desk' + desk);
            $(HROS.VAR.folder).each(function (i) {
                if (this.appid == fromFolderId && HROS.VAR.folder[i].apps[from] != null) {
                    if (to == 0) {
                        if (boa == 'b') {
                            HROS.VAR.dock.splice(0, 0, HROS.VAR.folder[i].apps[from]);
                        } else {
                            HROS.VAR.dock.splice(1, 0, HROS.VAR.folder[i].apps[from]);
                        }
                    } else {
                        if (boa == 'b') {
                            HROS.VAR.dock.splice(to, 0, HROS.VAR.folder[i].apps[from]);
                        } else {
                            HROS.VAR.dock.splice(to + 1, 0, HROS.VAR.folder[i].apps[from]);
                        }
                    }
                    HROS.VAR.folder[i].apps.splice(from, 1);
                    if (HROS.VAR.dock.length > 7) {
                        desk.push(HROS.VAR.dock[7]);
                        HROS.VAR.dock.splice(7, 1);
                    }
                    rtn = true;
                    return false;
                }
            });
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataFolderToDesk: function (id, from, to, fromFolderId, boa, desk) {
            var rtn = false;
            desk = eval('HROS.VAR.desk' + desk);
            $(HROS.VAR.folder).each(function (i) {
                if (this.appid == fromFolderId && HROS.VAR.folder[i].apps[from] != null) {
                    if (to == 0) {
                        if (boa == 'b') {
                            desk.splice(0, 0, HROS.VAR.folder[i].apps[from]);
                        } else {
                            desk.splice(1, 0, HROS.VAR.folder[i].apps[from]);
                        }
                    } else {
                        if (boa == 'b') {
                            desk.splice(to, 0, HROS.VAR.folder[i].apps[from]);
                        } else {
                            desk.splice(to + 1, 0, HROS.VAR.folder[i].apps[from]);
                        }
                    }
                    HROS.VAR.folder[i].apps.splice(from, 1);
                    rtn = true;
                    return false;
                }
            });
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataFolderToOtherdesk: function (id, from, todesk, fromFolderId) {
            var rtn = false;
            todesk = eval('HROS.VAR.desk' + todesk);
            $(HROS.VAR.folder).each(function (i) {
                if (this.appid == fromFolderId && HROS.VAR.folder[i].apps[from] != null) {
                    todesk.push(HROS.VAR.folder[i].apps[from]);
                    HROS.VAR.folder[i].apps.splice(from, 1);
                    rtn = true;
                    return false;
                }
            });
            if (rtn) {
                if ($('#desktop').is(':visible')) {
                    HROS.app.set();
                } else {
                    HROS.appmanage.set();
                }
            } else {
                HROS.app.dataWarning();
            }
            return rtn;
        },
        dataAllDockToDesk: function (desk) {
            desk = eval('HROS.VAR.desk' + desk);
            $(HROS.VAR.dock).each(function (i) {
                desk.push(HROS.VAR.dock[i]);
            });
            HROS.VAR.dock.splice(0, HROS.VAR.dock.length);
        },
        dataDeleteByAppid: function (appid) {
            $(HROS.VAR.dock).each(function (i) {
                if (this.appid == appid) {
                    HROS.VAR.dock.splice(i, 1);
                    return false;
                }
            });
            for (var i = 1; i <= 5; i++) {
                var desk = eval('HROS.VAR.desk' + i);
                $(desk).each(function (j) {
                    if (this.appid == appid) {
                        desk.splice(j, 1);
                        if (this.type == 'folder') {
                            $(HROS.VAR.folder).each(function (k) {
                                if (this.appid == appid) {
                                    HROS.VAR.folder.splice(k, 1);
                                    return false;
                                }
                            });
                        }
                        return false;
                    }
                });
            }
            $(HROS.VAR.folder).each(function (i) {
                $(this.apps).each(function (j) {
                    if (this.appid == appid) {
                        HROS.VAR.folder[i].apps.splice(j, 1);
                        return false;
                    }
                });
            });
        }
    }
})();