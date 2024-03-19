package cn.cestc.os.desktop.controller;


import cn.cestc.os.desktop.model.AppCategoryModel;
import cn.cestc.os.desktop.model.AppModel;
import cn.cestc.os.desktop.model.MemberAppModel;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.pojo.MemberAppVO;
import cn.cestc.os.desktop.service.AppCategoryService;
import cn.cestc.os.desktop.service.AppService;
import cn.cestc.os.desktop.service.MemberAppService;
import cn.cestc.os.desktop.service.MemberService;
import cn.cestc.os.desktop.utils.AjaxResult;
import cn.cestc.os.desktop.utils.DateUtils;
import cn.cestc.os.desktop.utils.ServletUtils;
import cn.cestc.os.desktop.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:应用管理
 *
 * @author 2015年7月28日 上午11:37:43
 */
@Slf4j
@Controller
@RequestMapping("/appManageController")
public class AppManageController
{


    @Autowired
    private AppService appService;
    @Autowired
    private MemberAppService memberAppService;
    @Autowired
    private AppCategoryService appCategoryService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model)
    {
        List<AppCategoryModel> appCategoryList = appCategoryService.selectByCondition(null);
        model.addAttribute("appCategoryList", appCategoryList);
        return "/appmanage/index";
    }

    /**
     * @param request
     * @param model
     * @return
     * @author tiger
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request, Model model)
    {
        int from = ServletRequestUtils.getIntParameter(request, "from", 0);
        int to = ServletRequestUtils.getIntParameter(request, "to", 7);
        String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");              // search_1
        String PARAM = "*%&.!;',|+-_/\\" + "//\\\\";
        if (keyword != null && !keyword.equals("") && PARAM.contains(keyword))
        {
            keyword = "$$";
        }
        int app_category_id = ServletRequestUtils.getIntParameter(request, "app_category_id", -1);     // search_4
        int verifytype = ServletRequestUtils.getIntParameter(request, "verifytype", 1);               //search_4

        log.debug("from=" + from);
        log.debug("to=" + to);
        log.debug("keyword=" + keyword);
        log.debug("app_category_id=" + app_category_id);
        log.debug("verifytype=" + verifytype);

        model.addAttribute("from", from);
        model.addAttribute("to", to);

        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);
        Integer memberId = member.getTbid();


        String appByUsername = StringUtil.toStrList(memberAppService.selectAppByUsername(userName, request));
        List<AppModel> appList = appService.selectByCondition(memberId, "tbid desc", keyword, app_category_id, verifytype, appByUsername);
        model.addAttribute("appList", appList);
        List<AppCategoryModel> appCategoryList = appCategoryService.selectByCondition(null);
        model.addAttribute("appCategoryList", appCategoryList);
        return "/appmanage/list";
    }

    /*
     * @Author 关玉珍
     * @Description 从市场安装应用到桌面
     * @Date 14:41 2022/1/17
     * @Param [request, res, model]
     * @return java.lang.String
     **/
    @RequestMapping("/addMyApp")
    public String addMyApp(HttpServletRequest request, HttpServletResponse res, Model model)
    {
        int realid = ServletRequestUtils.getIntParameter(request, "id", 0);
        int desk = ServletRequestUtils.getIntParameter(request, "desk", 0);
        log.info("realid=" + realid);
        log.info("desk=" + desk);

        String userName = ServletUtils.getUserName(request);

        MemberModel member = memberService.selectByUserName(userName);

        //检查应用是否已安装
        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setMemberId(member.getTbid());
        memberAppModel.setRealid(realid);
        List<MemberAppModel> memberAppList = memberAppService.selectByCondition(memberAppModel);
        if (memberAppList != null && memberAppList.size() > 0)
        {
            return null;
        }
        //查找应用信息
        AppModel appModel = new AppModel();
        appModel.setTbid(realid);
        List<AppModel> appList = appService.selectByCondition(appModel);
        AppModel app = appList.get(0);

        //在安装应用表里更新一条记录
        MemberAppModel memberApp = new MemberAppModel();
        memberApp.setRealid(app.getTbid());
        memberApp.setName(app.getName());
        memberApp.setIcon(app.getIcon());
        memberApp.setUrl(app.getUrl());
        memberApp.setWidth(app.getWidth());
        memberApp.setHeight(app.getHeight());
        memberApp.setIsresize(app.getIsresize());
        memberApp.setIsopenmax(app.getIsopenmax());
        memberApp.setIssetbar(app.getIssetbar());
        memberApp.setIsflash(app.getIsflash());
        memberApp.setDt(DateUtils.dateToStr(new Date()));
        memberApp.setLastdt(DateUtils.dateToStr(new Date()));
        memberApp.setMemberId(member.getTbid());
        memberApp.setType("window");
        memberApp.setFolderId(0);
        memberAppService.insert(memberApp);

        //更新使用人数
        app.setUsecount(app.getUsecount() + 1);
        appService.updateById(app);

        //增加到用户桌面
        if (desk == 1)
        {
            String desk_ = member.getDesk1();
            desk_ = (desk_ == null || desk_.equals("")) ? memberApp.getTbid() + "" : desk_ + "," + memberApp.getTbid();
            member.setDesk1(desk_);
        } else if (desk == 2)
        {
            String desk_ = member.getDesk2();
            desk_ = (desk_ == null || desk_.equals("")) ? memberApp.getTbid() + "" : desk_ + "," + memberApp.getTbid();
            member.setDesk2(desk_);
        } else if (desk == 3)
        {
            String desk_ = member.getDesk3();
            desk_ = (desk_ == null || desk_.equals("")) ? memberApp.getTbid() + "" : desk_ + "," + memberApp.getTbid();
            member.setDesk3(desk_);
        } else if (desk == 4)
        {
            String desk_ = member.getDesk4();
            desk_ = (desk_ == null || desk_.equals("")) ? memberApp.getTbid() + "" : desk_ + "," + memberApp.getTbid();
            member.setDesk4(desk_);
        } else if (desk == 5)
        {
            String desk_ = member.getDesk5();
            desk_ = (desk_ == null || desk_.equals("")) ? memberApp.getTbid() + "" : desk_ + "," + memberApp.getTbid();
            member.setDesk5(desk_);
        }
        memberService.updateById(member);
        return null;
    }


    /*
     * @Author 关玉珍
     * @Description  卸载桌面app
     * @Date 14:52 2022/1/17
     * @Param [request, id]
     * @return java.lang.Object
     **/
    @RequestMapping("/delMyApp")
    @ResponseBody
    public Object delMyApp(HttpServletRequest request, Integer id)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        if (memberAppService.memAppIsExist(id, userName).equals("THISAPPISNOWITHINUSER"))
        {
            return "THISAPPISNOWITHINUSER";
        } else
        {
            try
            {
                memberAppService.deleteMyApp(userName, id);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            result.setMsg("删除成功");
        }
        return result;
    }

    /**
     * Description:查看用户应用是否存在
     *
     * @author bo.xu
     */
    @RequestMapping("/memAppIsExist")
    @ResponseBody
    public Object memAppIsExist(HttpServletRequest request, Integer id)
    {
        String userName = ServletUtils.getUserName(request);
        AjaxResult<String> result = new AjaxResult<String>();
        result.setResult(memberAppService.memAppIsExist(id, userName));
        return result;
    }


    /**
     * Description: 跳转桌面应用编辑界面并初始化数据
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/toEditMyAppPage")
    public String toEditMyAppPage(Integer id, HttpServletRequest request)
    {
        MemberAppModel memberAppModel = new MemberAppModel();
        String userName = ServletUtils.getUserName(request);
        if (memberAppService.memAppIsExist(id, userName).equals("exist"))
        {
            memberAppModel.setTbid(id);
            request.setAttribute("memberAppModel", memberAppService.selectByCondition(memberAppModel).get(0));
        } else
        {
            return "redirect:/errorHadnlerController/thisappisnowithinuser.do";
        }
        return "appmanage/appmanage_editmyapp";
    }

    /**
     * Description:桌面应用编辑
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/editMyApp")
    @ResponseBody
    public Object editMyApp(HttpServletRequest request, MemberAppVO memberAppVO)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        MemberAppModel memberAppModel = new MemberAppModel();
        String userName = ServletUtils.getUserName(request);
        if (memberAppService.memAppIsExist(memberAppVO.getId(), userName).equals("exist"))
        {
            memberAppModel.setTbid(memberAppVO.getId());
            memberAppModel.setIcon(memberAppVO.getVal_icon());
            memberAppModel.setName(memberAppVO.getVal_name());
            memberAppModel.setWidth(memberAppVO.getVal_width());
            memberAppModel.setHeight(memberAppVO.getVal_height());
            memberAppModel.setIsresize(memberAppVO.getVal_isresize());
            memberAppModel.setIsopenmax(memberAppVO.getVal_isopenmax());
            memberAppModel.setIsflash(memberAppVO.getVal_isflash());
            memberAppService.updateById(memberAppModel);
            result.setResult("y");
        } else
        {
            return "THISAPPISNOWITHINUSER";
        }
        return result;
    }

    /**
     * Description:跳转新建或者编辑私人应用并初始化数据
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/toEditdialogPage")
    public String toEditdialogPage(HttpServletRequest request, MemberAppVO memberAppVO, Integer desk)
    {
        MemberAppModel memberAppModel = new MemberAppModel();
        String userName = ServletUtils.getUserName(request);
        //当id有值则编辑
        if (memberAppVO.getId() != null && !memberAppVO.getId().equals(""))
        {
            if (memberAppService.memAppIsExist(memberAppVO.getId(), userName).equals("exist"))
            {
                memberAppModel.setTbid(memberAppVO.getId());
                memberAppModel = memberAppService.selectByCondition(memberAppModel).get(0);
            } else
            {
                return "redirect:/errorHadnlerController/thisappisnowithinuser.do";
            }
            //当id无值则新建
        } else
        {
            memberAppModel.setIcon("static/img/ui/papp.png");
            memberAppModel.setType("pwindow");
            memberAppModel.setIsresize(1);
            memberAppModel.setIsopenmax(0);
            memberAppModel.setIsflash(0);
            request.setAttribute("desk", desk);
        }
        request.setAttribute("memberAppModel", memberAppModel);
        return "appmanage/appmanage_editdialog";
    }


    /**
     * Description:新建或者编辑私人应用
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/editdialog")
    @ResponseBody
    public Object editdialog(HttpServletRequest request, MemberAppVO memberAppVO)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        if (memberAppService.memAppIsExist(memberAppVO.getId(), userName).equals("exist"))
        {
            MemberAppModel memberAppModel = new MemberAppModel();
            memberAppModel.setTbid(memberAppVO.getId());
            memberAppModel.setType(memberAppVO.getVal_type());
            memberAppModel.setIcon(memberAppVO.getVal_icon());
            memberAppModel.setUrl(memberAppVO.getVal_url());
            memberAppModel.setName(memberAppVO.getVal_name());
            memberAppModel.setWidth(memberAppVO.getVal_width());
            memberAppModel.setHeight(memberAppVO.getVal_height());
            memberAppModel.setIsresize(memberAppVO.getVal_isresize());
            memberAppModel.setIsopenmax(memberAppVO.getVal_isopenmax());
            memberAppModel.setIsflash(memberAppVO.getVal_isflash());
            if (memberAppModel.getTbid() == null || memberAppModel.getTbid().equals(""))
            {
                memberAppService.builPwindowOrPwidgetApp(memberAppModel, memberAppVO.getDesk(), userName);
            } else
            {
                memberAppService.updateById(memberAppModel);
            }
            result.setResult("y");
        } else
        {
            return "THISAPPISNOWITHINUSER";
        }
        return result;
    }


    /**
     * Description: 上传桌面应用图片
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String, Object> uploadImg(HttpServletRequest request)
    {
        return memberAppService.uploadImg(request);
    }

    /**
     * @param request
     * @param res
     * @param model
     * @return
     * @author tiger
     */
    @RequestMapping("/delete")
    public String delete(HttpServletRequest request, HttpServletResponse res, Model model)
    {
        int tbid = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (tbid != 0)
        {
            AppModel am = new AppModel();
            am.setTbid(tbid);
            List<AppModel> list = appService.selectByCondition(am);
            if (list.size() > 0)
            {
                if (list.get(0).getIsrecommend() == 1)
                {
                    // 首先将全部推荐状态修改为未推荐，保证了今日推荐只有一个
                    String sql1 = "update tb_app set isrecommend=0 where isrecommend<>0";
                    // 修改推荐状态为“今日推荐”
                    String sql2 = "update tb_app set isrecommend=1 order by usecount desc limit 1";

                    appService.delete(am);
                    appService.update(sql1);
                    appService.update(sql2);
                } else
                {
                    this.appService.delete(am);
                }
            }
        }
        return null;
    }

    /**
     * @param request
     * @param res
     * @param model
     * @return
     * @author tiger
     */
    @RequestMapping("/recommend")
    public String recommend(HttpServletRequest request, HttpServletResponse res, Model model)
    {
        int tbid = ServletRequestUtils.getIntParameter(request, "id", 0);
        if (tbid != 0)
        {
            // 首先将全部推荐状态修改为未推荐，保证了今日推荐只有一个
            String sql1 = "update tb_app set isrecommend=0 where isrecommend<>0";
            // 修改推荐状态为“今日推荐”
            String sql2 = "update tb_app set isrecommend=1 where tbid=" + tbid;
            this.appService.update(sql1);
            this.appService.update(sql2);
        }
        return null;
    }

    /**
     * @param request
     * @param model
     * @return
     * @author tiger
     */
    @RequestMapping("/detail")
    public String detail(HttpServletRequest request, Model model)
    {
        int id = ServletRequestUtils.getIntParameter(request, "appid", 0);
        log.debug("id=" + id);

        AppModel appModel = new AppModel();
        if (id > 0)
        {
            appModel.setTbid(id);
            appModel = appService.selectByCondition(appModel).get(0);
        } else
        {
            appModel.setTbid(0);
            appModel.setIcon("");
            appModel.setName("");
            appModel.setApp_category_id(0);
            appModel.setUrl("");
            appModel.setWidth(900);
            appModel.setHeight(600);
            appModel.setIsresize(1);
            appModel.setIsopenmax(0);
            appModel.setRemark("");
            appModel.setVerifytype(0);
        }
        model.addAttribute("appModel", appModel);

        List<AppCategoryModel> appMyList = appCategoryService.selectByCondition(null);
        model.addAttribute("appMyList", appMyList);

        return "/appmanage/detail";
    }

    /**
     * 保存或更新
     *
     * @param request
     * @param model
     * @return
     * @author tiger
     */
    @RequestMapping("/save")
    public String saveOrUpdate(HttpServletRequest request, HttpServletResponse res, Model model)
    {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        String val_icon = ServletRequestUtils.getStringParameter(request, "val_icon", "");
        String val_name = ServletRequestUtils.getStringParameter(request, "val_name", "");
        int val_app_category_id = ServletRequestUtils.getIntParameter(request, "val_app_category_id", 0);
        String val_url = ServletRequestUtils.getStringParameter(request, "val_url", "");
        int val_width = ServletRequestUtils.getIntParameter(request, "val_width", 900);
        int val_height = ServletRequestUtils.getIntParameter(request, "val_height", 600);
        int val_isresize = ServletRequestUtils.getIntParameter(request, "val_isresize", 1);
        int val_isopenmax = ServletRequestUtils.getIntParameter(request, "val_isopenmax", 0);
        int val_isflash = ServletRequestUtils.getIntParameter(request, "val_isflash", 0);
        String val_remark = ServletRequestUtils.getStringParameter(request, "val_remark", "");
        log.debug("id=" + id);

        String userName = ServletUtils.getUserName(request);

        MemberModel member = memberService.selectByUserName(userName);

        AppModel appModel = new AppModel();
        if (id == 0)
        {
            appModel.setIssetbar(1);
            appModel.setUsecount(0);
            appModel.setStarnum(0.0);
            appModel.setIsrecommend(0);
            appModel.setVerifytype(1);
            appModel.setMember_id(member.getTbid());
        } else
        {
            appModel.setTbid(id);
            appModel = appService.selectByCondition(appModel).get(0);
        }

        appModel.setIcon(val_icon);
        appModel.setName(val_name);
        appModel.setApp_category_id(val_app_category_id);
        appModel.setUrl(val_url);
        appModel.setWidth(val_width);
        appModel.setHeight(val_height);
        appModel.setIsresize(val_isresize);
        appModel.setIsopenmax(val_isopenmax);
        appModel.setIsflash(val_isflash);
        appModel.setRemark(val_remark);
        appModel.setDt(DateUtils.dateToStr(new Date()));

        if (id == 0)
        {
            appService.insert(appModel);
        } else
        {
            appService.updateById(appModel);
        }

        AppMarketController.writeJson(res, "{\"info\":\"\",\"status\":\"y\"}");
        return null;
    }

    /**
     * 审核通过，修改状态
     *
     * @return
     * @author tiger
     */
    @RequestMapping("/pass")
    public String pass(HttpServletRequest request, HttpServletResponse res, Model model)
    {
        Integer tbid = ServletRequestUtils.getIntParameter(request, "appid", 0);
        AppModel am = new AppModel();
        am.setTbid(tbid);
        am.setVerifytype(1);
        this.appService.updateById(am);
        return null;
    }

    /**
     * 审核不通过，修改状态
     *
     * @author tiger
     */
    @RequestMapping("/unPass")
    public String unPass(HttpServletRequest request, HttpServletResponse res, Model model)
    {
        Integer tbid = ServletRequestUtils.getIntParameter(request, "appid", 0);
        String uinfo = ServletRequestUtils.getStringParameter(request, "info", "信息不完整");
        AppModel am = new AppModel();
        am.setVerifyinfo(uinfo);
        am.setTbid(tbid);
        am.setVerifytype(3);
        this.appService.updateById(am);
        return null;
    }


    /*
     * @Author 关玉珍
     * @Description  查找应用市场 应用
     * @Date 14:49 2022/1/17
     * @Param [realid, request]
     * @return cn.cestc.os.desktop.utils.AjaxResult<java.lang.String>
     **/
    @RequestMapping("/getappisexist")
    @ResponseBody
    public AjaxResult<String> getappisexist(Integer realid, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        //查找应用信息
        AppModel appModel = new AppModel();
        appModel.setTbid(realid);
        List<AppModel> appList = appService.selectByCondition(appModel);
        if (appList.size() == 0)
        {
            result.setCode(result.FAILURE);
            result.setMsg("应用不存在！");
        } else
        {
            // String userName = request.getRemoteUser();
            String userName = ServletUtils.getUserName(request);
            result.setCode(result.SUCCESS);
            result.setMsg("应用存在！");
        }
        return result;
    }

    @RequestMapping("/getmemberappisexist")
    @ResponseBody
    public AjaxResult<String> getmemberappisexist(Integer realid, HttpServletRequest request)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        //查找应用信息
        MemberAppModel memberappModel = new MemberAppModel();
        memberappModel.setTbid(realid);
        List<MemberAppModel> appList = memberAppService.selectByCondition(memberappModel);
        if (appList.size() == 0)
        {
            result.setCode(result.FAILURE);
            result.setMsg("应用不存在！");
        } else
        {
            String userName = ServletUtils.getUserName(request);
            result.setCode(result.SUCCESS);
            result.setMsg("应用存在！");
        }
        return result;
    }

    @RequestMapping("/shortcutlist")
    @ResponseBody
    public AjaxResult<Map<String, List<MemberAppModel>>> shortcutlist(HttpServletRequest request)
    {
        AjaxResult<Map<String, List<MemberAppModel>>> result = new AjaxResult<Map<String, List<MemberAppModel>>>();
        Map<String, List<MemberAppModel>> map = new HashMap<String, List<MemberAppModel>>();
        //查看用户信息
        // String username = request.getRemoteUser();
        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);
        String appByUsername = StringUtil.toStrList(memberAppService.selectAppByUsername(userName, request));
        //删除无权限的用户应用
        memberAppService.deletBynoAuthByUsernames(member.getTbid(), appByUsername);
        //查找应用信息
        MemberAppModel memberappModel = new MemberAppModel();
        memberappModel.setMemberId(member.getTbid());
        memberappModel.setIsshortcut(1);
        List<MemberAppModel> shortcutlist = memberAppService.selectByCondition(memberappModel);
        map.put("shortcutlist", shortcutlist);
        memberappModel.setIsshortcut(0);
        List<MemberAppModel> notshortcutlist = memberAppService.selectByCondition(memberappModel);
        map.put("notshortcutlist", notshortcutlist);
        result.setCode(result.SUCCESS);
        result.setResult(map);
        return result;
        // return null;
    }

    @RequestMapping("/shortcutsave")
    @ResponseBody
    public AjaxResult<List<MemberAppModel>> shortcutsave(HttpServletRequest request, String selectItemIds, String unselectItemIds)
    {
        AjaxResult<List<MemberAppModel>> result = new AjaxResult<List<MemberAppModel>>();
        //查看用户信息
        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);
        String[] selectItemIdslist = selectItemIds.split(",");
        String[] unselectItemIdslist = unselectItemIds.split(",");
        //处理快捷方式的
        for (String selectItemId : selectItemIdslist)
        {
            if (selectItemId != null && !selectItemId.equals(""))
            {
                MemberAppModel memberappModel = new MemberAppModel();
                memberappModel.setTbid(Integer.valueOf(selectItemId));
                memberappModel.setIsshortcut(1);
                memberAppService.updateById(memberappModel);
            }
        }
        //处理非快捷方式的
        for (String unselectItemId : unselectItemIdslist)
        {
            if (unselectItemId != null && !unselectItemId.equals(""))
            {
                MemberAppModel memberappModel = new MemberAppModel();
                memberappModel.setTbid(Integer.valueOf(unselectItemId));
                memberappModel.setIsshortcut(0);
                memberAppService.updateById(memberappModel);
            }
        }
        //查找应用信息
        MemberAppModel memberappModel = new MemberAppModel();
        memberappModel.setMemberId(member.getTbid());
        memberappModel.setIsshortcut(1);
        List<MemberAppModel> shortcutlist = memberAppService.selectByCondition(memberappModel);
        result.setCode(result.SUCCESS);
        result.setResult(shortcutlist);
        return result;
    }
}
