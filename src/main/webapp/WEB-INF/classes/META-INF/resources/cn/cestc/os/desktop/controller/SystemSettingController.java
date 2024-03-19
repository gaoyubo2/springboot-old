package cn.cestc.os.desktop.controller;


import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.model.PwallpaperModel;
import cn.cestc.os.desktop.model.WallpaperModel;
import cn.cestc.os.desktop.pojo.PwallpaperUploadVO;
import cn.cestc.os.desktop.service.MemberService;
import cn.cestc.os.desktop.service.PwallpaperService;
import cn.cestc.os.desktop.service.SkinService;
import cn.cestc.os.desktop.service.WallpaperService;
import cn.cestc.os.desktop.utils.AjaxResult;
import cn.cestc.os.desktop.utils.OSinfoUtils;
import cn.cestc.os.desktop.utils.ParamDataHandlerUtils;
import cn.cestc.os.desktop.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Description:系统设置
 *
 * @author bo.xu
 * 2015年7月28日 上午11:40:36
 */
@Slf4j
@Controller
@RequestMapping("/systemSettingController")
public class SystemSettingController
{

    @Autowired
    private MemberService memberService;
    @Autowired
    private WallpaperService wallpaperService;
    @Autowired
    private PwallpaperService pwallpaperService;
    @Autowired
    private SkinService skinService;

    /**
     * Description: 系统设置界面跳转并初始化数据
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/index")
    public String toSystemsetting(HttpServletRequest request)
    {
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberService.selectByCondition(memberModel).get(0);
        request.setAttribute("desk", memberModel.getDesk());
        request.setAttribute("xy", memberModel.getAppxy());
        request.setAttribute("size", memberModel.getAppsize());
        request.setAttribute("pos", memberModel.getDockpos());
        request.setAttribute("vertical", memberModel.getAppverticalspacing());
        request.setAttribute("horizontal", memberModel.getApphorizontalspacing());
        return "systemsetting/systemsetting_index";
    }

    /**
     * Description: 设置默认桌面
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/setDesk")
    @ResponseBody
    public AjaxResult<String> setDesk(Integer desk, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        if (desk != null && desk >= 1 && desk <= 5)
        {
            String userName = ServletUtils.getUserName(request);
            MemberModel memberModel = new MemberModel();
            memberModel.setUsername(userName);
            memberModel.setDesk(desk);
            memberService.updateByUsername(memberModel);
            result.setCode(result.SUCCESS);
            result.setMsg("设置成功！");
        }
        return result;
    }

    /**
     * Description: 设置桌面图标排列方式
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/setAppXY")
    @ResponseBody
    public AjaxResult<String> setAppXY(String appxy, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel.setAppxy(appxy);
        memberService.updateByUsername(memberModel);
        result.setCode(result.SUCCESS);
        result.setMsg("设置成功！");
        return result;
    }

    /**
     * Description: 设置桌面图标显示尺寸
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/setAppSize")
    @ResponseBody
    public AjaxResult<String> setAppSize(Integer appsize, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel.setAppsize(appsize);
        memberService.updateByUsername(memberModel);
        result.setCode(result.SUCCESS);
        result.setMsg("设置成功！");
        return result;
    }

    /**
     * Description: 设置桌面图标垂直间距
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/setAppVerticalSpacing")
    @ResponseBody
    public AjaxResult<String> setAppVerticalSpacing(Integer appverticalspacing, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        StringBuffer url = request.getRequestURL();
        log.info("url********** : {}", url);

        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel.setAppverticalspacing(appverticalspacing);
        memberService.updateByUsername(memberModel);
        result.setCode(result.SUCCESS);
        result.setMsg("设置成功！");
        return result;
    }


    /**
     * Description: 设置桌面图标水平间距
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/setAppHorizontalSpacing")
    @ResponseBody
    public AjaxResult<String> setAppHorizontalSpacing(Integer apphorizontalspacing, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel.setApphorizontalspacing(apphorizontalspacing);
        memberService.updateByUsername(memberModel);
        result.setCode(result.SUCCESS);
        result.setMsg("设置成功！");
        return result;
    }

    /**
     * Description: 设置应用码头位置
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/setDockPos")
    @ResponseBody
    public AjaxResult<String> setDockPos(String dock, Integer desk, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel.setDockpos(dock);
        memberService.updateByUsername(memberModel);
        result.setCode(result.SUCCESS);
        result.setMsg("设置成功！");
        return result;
    }

    /**
     * Description: 跳转壁纸设置界面
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/goWallpaper")
    public String goWallpaper(String dock, Integer desk, HttpServletRequest request)
    {
        WallpaperModel wallpaperModel = new WallpaperModel();
        //将可选的桌面列表list查出,将并将对象中的url处理
        List<WallpaperModel> wallpaperModellist = wallpaperService.selectByCondition(wallpaperModel);
        for (WallpaperModel wallpaperModeltemp : wallpaperModellist)
        {
            wallpaperModeltemp.setUrl(ParamDataHandlerUtils.getFileInfo(wallpaperModeltemp.getUrl(), "simg"));
        }
        request.setAttribute("wallpaperList", wallpaperModellist);

        //将壁纸显示方式初始化数据传前台
        MemberModel memberModel = new MemberModel();
        String userName = ServletUtils.getUserName(request);
        memberModel.setUsername(userName);
        memberModel = memberService.selectByCondition(memberModel).get(0);
        request.setAttribute("memberModel", memberModel);
        return "systemsetting/systemsetting_wallpaper";
    }

    /**
     * Description: 设置壁纸
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/setWallpaper")
    @ResponseBody
    public AjaxResult<String> setWallpaper(Integer wpstate, String wptype, String wp, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        // String username = req.getRemoteUser();
        String userName = ServletUtils.getUserName(request);
        memberService.setWallpaperByUsername(wpstate, wptype, wp, userName);
        result.setCode(result.SUCCESS);
        result.setMsg("设置成功！");
        return result;
    }


    /**
     * Description: 设置壁纸
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/getWallpaper")
    @ResponseBody
    public AjaxResult<String> getWallpaper(HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        result.setResult(memberService.getWallpaperByUsername(userName));
        result.setCode(result.SUCCESS);
        result.setMsg("设置成功！");
        return result;
    }

    /**
     * Description: 自定义壁纸界面跳转并初始化数据
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/customindex")
    public String tosettingcustom(HttpServletRequest request)
    {
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberService.selectByCondition(memberModel).get(0);

        PwallpaperModel pwallpaperModel = new PwallpaperModel();
        pwallpaperModel.setMemberId(memberModel.getTbid());
        List<PwallpaperModel> pwallpaperModellist = pwallpaperService.selectByCondition(pwallpaperModel);
        for (PwallpaperModel pwallpaperModeltemp : pwallpaperModellist)
        {
            pwallpaperModeltemp.setUrl(ParamDataHandlerUtils.getFileInfo(pwallpaperModeltemp.getUrl(), "simg"));
        }
        //将当前用户自定义壁纸传像前台
        request.setAttribute("pwallpaperModellist", pwallpaperModellist);
        //将壁纸显示方式初始化数据传前台
        request.setAttribute("memberModel", memberModel);
        return "systemsetting/systemsetting_custom";
    }


    /**
     * Description: 删除自定义壁纸
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/delPwallpaper")
    @ResponseBody
    public AjaxResult<String> delPwallpaper(Integer id, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberService.selectByCondition(memberModel).get(0);

        MemberModel memberModeltemp = new MemberModel();
        memberModeltemp.setWallpaperId(id);
        memberModeltemp.setWallpaperstate(2);
        List<MemberModel> memberModellist = memberService.selectByCondition(memberModeltemp);
        //检查当前准备删除的壁纸是否在使用
        if (memberModellist.size() > 0)
        {
            result.setCode(result.FAILURE);
        } else
        {
            PwallpaperModel pwallpaperModel = new PwallpaperModel();
            pwallpaperModel.setTbid(id);
            pwallpaperModel.setMemberId(memberModel.getTbid());
            pwallpaperService.delete(pwallpaperModel);
            result.setCode(result.SUCCESS);
        }
        return result;
    }


    /**
     * Description: 上传壁纸
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/uploadPwallpaper")
    @ResponseBody
    public PwallpaperUploadVO uploadPwallpaper(HttpServletRequest request)
    {

        return pwallpaperService.uploadPwallpaper(request);
    }


    /**
     * Description: 皮肤设置界面跳转并初始化数据
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/skinindex")
    public String skinindex(HttpServletRequest request)
    {
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberService.selectByCondition(memberModel).get(0);
        String basepath = request.getSession().getServletContext().getRealPath("/");
        File file = null;

        if (OSinfoUtils.isWindows())
        {
            basepath = basepath.replaceAll("webapp\\\\", "resources\\\\");
            file = new File(basepath + "/" + "static/img/skins/");
        }

        if (OSinfoUtils.isLinux())
        {
            file = new File(basepath + "/" + "WEB-INF/classes/static/img/skins/");
        }

        log.debug("皮肤全限定路径: {}", file.getPath());
        request.setAttribute("skinlist", skinService.selectAllSkinVOs(file));
        request.setAttribute("memberModel", memberModel);
        return "systemsetting/systemsetting_skin";
    }

    /**
     * Description: 设置皮肤
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/updateSkin")
    @ResponseBody
    public AjaxResult<String> updateSkin(String skin, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel.setSkin(skin);
        memberService.updateByUsername(memberModel);
        result.setCode(result.SUCCESS);
        result.setMsg("设置成功！");
        return result;
    }


}
