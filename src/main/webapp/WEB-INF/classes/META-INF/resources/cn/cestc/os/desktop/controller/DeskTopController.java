package cn.cestc.os.desktop.controller;


import cn.cestc.os.desktop.model.MemberAppModel;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.model.SettingModel;
import cn.cestc.os.desktop.pojo.DesktopVO;
import cn.cestc.os.desktop.pojo.MoveAppVO;
import cn.cestc.os.desktop.service.MemberAppService;
import cn.cestc.os.desktop.service.MemberService;
import cn.cestc.os.desktop.service.SettingService;
import cn.cestc.os.desktop.utils.AjaxResult;
import cn.cestc.os.desktop.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class DeskTopController
{

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberAppService memberAppService;
    @Autowired
    private SettingService settingService;

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

        String userName = ServletUtils.getUserName(request);
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
    @RequestMapping("/getMyAppById")
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
     * Description: 通过当前用户名得到主页显示的应用
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/getMyApp")
    @ResponseBody
    public DesktopVO getMyApp(HttpServletRequest request)
    {
        //得到username，当member中的username不存在则新建
        String userName = ServletUtils.getUserName(request);
        memberService.saveMemberOnMemberIsNotExist(userName);
        //返回桌面数据 通过用户名查询应用
        return
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
