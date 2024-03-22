package cn.cestc.os.desktop.controller;


import cn.cestc.os.desktop.model.MemberAppModel;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.model.Result;
import cn.cestc.os.desktop.model.SettingModel;
import cn.cestc.os.desktop.model.manage.MemberNameVO;
import cn.cestc.os.desktop.model.manage.User;
import cn.cestc.os.desktop.pojo.DesktopVO;
import cn.cestc.os.desktop.pojo.MoveAppVO;
import cn.cestc.os.desktop.service.MemberAppService;
import cn.cestc.os.desktop.service.MemberService;
import cn.cestc.os.desktop.service.SettingService;
import cn.cestc.os.desktop.service.SsoService;
import cn.cestc.os.desktop.utils.AjaxResult;
import cn.cestc.os.desktop.utils.ServletUtils;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Description:桌面基本处理
 *
 * @author bo.xu
 * 2015年7月28日 上午11:39:52
 */

@Slf4j
@Controller
@RequestMapping()
@CrossOrigin
public class DeskTopController
{

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberAppService memberAppService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private SsoService ssoService;

    @Value("${cas.casLogoutUrl}")
    private String casLogoutUrl;


    @PostMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response)
    {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies)
        {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        session.invalidate();
        return casLogoutUrl;
    }


    /*
     * @Author 关玉珍
     * @Description  跳转桌面主界面并初始化数据
     * @Date 11:55 2022/1/17
     * @Param [request]
     * @return java.lang.String
     **/
    @RequestMapping("/")
    public String toMainPage(
            HttpServletRequest request,
            HttpServletResponse response)
    {
        //String userName = ServletUtils.getUserName(request);
        //更改：获取用户名satoken
        Integer uid = StpUtil.getLoginIdAsInt();
        String userName = ssoService.getUser(uid).getUsername();
//        String userName = "用户3";
        //通过用户名去新建或者更新用户表
        memberService.saveMemberOnMemberIsNotExist(userName);

        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        // 通过条件查询 tb_member 用户表
        memberModel = memberService.selectByCondition(memberModel).get(0);
        SettingModel settingModel = new SettingModel();
        //系统设置
        request.setAttribute("settingModel", settingService.selectByCondition(settingModel).get(0));
        //当前用户壁纸信息
        request.setAttribute("wallInitData", memberService.getWallpaperByUsername(userName));
        //当前用户信息
        request.setAttribute("memberModel", memberModel);
        return "index";
    }


    /**
     * Description: 通过应用id得到应用
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/getMyAppByIdOlde")
    @ResponseBody
    public Object getMyAppById(Integer id, String type, HttpServletRequest request, HttpServletResponse response)
    {
        String userName = ServletUtils.getUserName(request);
        MemberAppModel memberAppModel;
        try
        {
            memberAppModel = memberAppService.getAppByMemberappidAndTypeAndUsername(id, type, userName);
            if (memberAppModel.getError() != null && memberAppModel.getError().equals("ERROR_NOT_INSTALLED"))
            {
                return "THISAPPISNOWITHINUSER";
            }
        } catch (Exception e)
        {
            return "THISAPPISNOWITHINUSER";
        }
        return memberAppModel;
    }
    /**
     * 郜宇博
     * Description: 通过应用id得到应用
     * 2024.3.21
     */
    @RequestMapping("/getMyAppById")
    @ResponseBody
    public Object getAppByAppId(Integer id) {
        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setTbid(id);
        return memberAppService.selectByCondition(memberAppModel).get(0);
    }

    /**
     * Description: 通过应用id下载file类型应用内容
     *
     * @return
     * @throws Exception
     * @author bo.xu
     */
    @RequestMapping("/filedownloadMyAppById")
    public ModelAndView filedownloadMyAppById(Integer appid, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        memberAppService.downloadfileappByid(appid, request, response);
        return null;
    }

    /**
     * 通过角色Id获取Desk
     * date: 2024.3.20
     */
    @RequestMapping("/getMyApp")
    @ResponseBody
    public DesktopVO getMyApp(HttpServletRequest request)
    {
        //获取用户id
        Integer uid = StpUtil.getLoginIdAsInt();
        User user = ssoService.getUser(uid);
        String username = user.getUsername();
        Integer roleId = user.getRoleId();
//        Integer uid = 10;
//        String username = "用户3";
//        Integer roleId = 21;
        return memberAppService.getDeskByRoleId(username, roleId, request,uid);
    }
    @RequestMapping("/getMyAppOld")
    @ResponseBody
    public DesktopVO getMyAppOld(HttpServletRequest request)
    {
        //得到username，当member中的username不存在则新建
        String userName = ServletUtils.getUserName(request);
        memberService.saveMemberOnMemberIsNotExist(userName);
        //返回桌面数据 通过用户名查询应用
        return memberAppService.selectByUsername(userName, request);
    }

    /**
     * Description: 应用移动
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/moveMyApp")
    @ResponseBody
    public AjaxResult<String> moveMyApp(HttpServletRequest request, MoveAppVO moveappvo)
    {
        //获取用户信息
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberService.selectByCondition(memberModel).get(0);
        //应用移动
        if (moveappvo.getMovetype() != null && !moveappvo.getMovetype().equals(""))
        {
            try
            {
                memberAppService.moveMyApp(memberModel, moveappvo);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        result.setResult("sucess");
        return result;
    }



}
