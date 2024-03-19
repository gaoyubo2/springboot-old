var version = '3.3.3';        //版本号
var ajaxUrl = {
    getMyApp: basePath + '/getMyApp.do',
    getMyAppById: basePath + '/getMyAppById.do',
    addMyApp: basePath + '/appManageController/addMyApp.do',
    delMyApp: basePath + '/appManageController/delMyApp.do',
    moveMyApp: basePath + '/moveMyApp.do',
    updateAppStar: basePath + '/appMarketController/updateAppStar.do',
    setWallpaper: basePath + '/systemSettingController/setWallpaper.do',
    getSidebarItems: basePath + '/appManageController/shortcutlist.do',
    updateSidebarItems: basePath + '/appManageController/shortcutsave.do',
    getWallpaper: basePath + '/systemSettingController/getWallpaper.do',
    logout: basePath + '/logout.do',
    addFolder: basePath + '/desktopSettingController/addFolder.do'
};
var HROS = {};

HROS.maskBoxCache = {};
HROS.templateCache = {};

HROS.CONFIG = {
    website: 'http://' + location.hostname + location.pathname, //网站地址，用于分享应用时调用。一般无需修改
    memberID: 0,         //用户id
    desk: 1,         //当前显示桌面
    dockPos: '',        //应用码头位置，参数有：top,left,right
    appXY: '',        //应用排列方式，参数有：x,y
    appSize: 48,        //图标显示尺寸
    appMarginTop: 20,        //快捷方式top初始位置
    appMarginLeft: 20,        //快捷方式left初始位置
    appVerticalSpacing: 50,        //图标垂直间距
    appHorizontalSpacing: 50,        //图标水平间距
    windowIndexid: 10000,     //窗口z-index初始值
    widgetIndexid: 1,         //挂件z-index初始值
    windowMinWidth: 215,       //窗口最小宽度
    windowMinHeight: 59,        //窗口最小高度
    wallpaperState: 1,         //1系统壁纸,2自定义壁纸,3网络壁纸
    wallpaper: '',        //壁纸
    wallpaperType: '',        //壁纸显示类型，参数有：tianchong,shiying,pingpu,lashen,juzhong
    wallpaperWidth: 0,         //壁纸宽度
    wallpaperHeight: 0          //壁纸高度
};

HROS.VAR = {
    zoomLevel: 1,
    isAppMoving: false,    //桌面应用是否正在移动中，也就是ajax操作是否正在执行中
    dock: [],
    desk1: [],
    desk2: [],
    desk3: [],
    desk4: [],
    desk5: [],
    folder: []
};