package cn.cestc.os.desktop.controller;


import cn.cestc.os.desktop.model.*;
import cn.cestc.os.desktop.model.manage.User;
import cn.cestc.os.desktop.service.*;
import cn.cestc.os.desktop.utils.DateUtils;
import cn.cestc.os.desktop.utils.ServletUtils;
import cn.cestc.os.desktop.utils.StringUtil;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * Description:应用市场
 *
 * @author 2015年7月28日 上午11:38:28
 */
@Slf4j
@Controller
@RequestMapping("/appMarketController")
public class AppMarketController {


    @Autowired
    private MemberAppService memberAppService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AppService appService;
    @Autowired
    private AppStarService appStarService;
    @Autowired
    private AppCategoryService appCategoryService;
    @Autowired
    private TenantFeignService tenantFeignService;
    @Autowired
    private ErrorHadnlerController errorHadnlerController;
    @Autowired
    private SsoService ssoService;

    public static final void writeJson(HttpServletResponse response, String json) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request, Model model) {
        // String userName = request.getRemoteUser();
        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);
        model.addAttribute("member", member);

        // 查询我已被分配的应用
        //获取用户角色
//        Integer uid = StpUtil.getLoginIdAsInt();
//        User user = ssoService.getUser(uid).getData();
//        Integer roleId = user.getRoleId();
        Integer roleId = 19;
        List<MemberAppModel> memberAppList = memberAppService.selectByMemberId(roleId);
        model.addAttribute("memberAppList", memberAppList);

        String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
        try {
            model.addAttribute("keyword", java.net.URLDecoder.decode(keyword, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String appByUsername = StringUtil.toStrList(memberAppService.selectAppByUsername(userName, request));

        // 今日推荐
        List<AppModel> appModelList = appService.selectByCondition(0, "isrecommend desc, tbid desc", null, -1, 1, appByUsername);
        AppModel appModel = new AppModel();
        appModel.setStarnum(0d);
        if (appModelList != null && appModelList.size() > 0) {
            appModel = appModelList.get(0);
        }
        model.addAttribute("appModel", appModel);

        // 类目类型
        List<AppCategoryModel> appCategoryList = appCategoryService.selectByCondition(null);
        model.addAttribute("appCategoryList", appCategoryList);

        // 我开发的应用数量
        int kf = appService.selectByCondition(member.getTbid(), "tbid desc", null, -1, -1, appByUsername).size();
        // 已上线的应用数量
        int sx = appService.selectByCondition(member.getTbid(), "tbid desc", null, -1, 1, appByUsername).size();

        model.addAttribute("kf", kf);
        model.addAttribute("wfb", (kf - sx));
        return "/appmarket/index";
    }
    @RequestMapping("/indexOld")
    public String indexOld(HttpServletRequest request, Model model) {
        // String userName = request.getRemoteUser();
        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);
        model.addAttribute("member", member);

        // 查询我已被分配的应用
        List<MemberAppModel> memberAppList = memberAppService.selectByMemberId(member.getTbid());
        model.addAttribute("memberAppList", memberAppList);

        String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
        try {
            model.addAttribute("keyword", java.net.URLDecoder.decode(keyword, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String appByUsername = StringUtil.toStrList(memberAppService.selectAppByUsername(userName, request));

        // 今日推荐
        List<AppModel> appModelList = appService.selectByCondition(0, "isrecommend desc, tbid desc", null, -1, 1, appByUsername);
        AppModel appModel = new AppModel();
        appModel.setStarnum(0d);
        if (appModelList != null && appModelList.size() > 0) {
            appModel = appModelList.get(0);
        }
        model.addAttribute("appModel", appModel);

        // 类目类型
        List<AppCategoryModel> appCategoryList = appCategoryService.selectByCondition(null);
        model.addAttribute("appCategoryList", appCategoryList);

        // 我开发的应用数量
        int kf = appService.selectByCondition(member.getTbid(), "tbid desc", null, -1, -1, appByUsername).size();
        // 已上线的应用数量
        int sx = appService.selectByCondition(member.getTbid(), "tbid desc", null, -1, 1, appByUsername).size();

        model.addAttribute("kf", kf);
        model.addAttribute("wfb", (kf - sx));
        return "/appmarket/index";
    }

    /*
     * @Author 关玉珍
     * @Description  获取应用列表
     * @Date 11:54 2022/1/14
     * @Param [request, model]
     * @return java.lang.String
     **/
    @RequestMapping("/list")
    public String list(HttpServletRequest request, Model model) {
        int from = ServletRequestUtils.getIntParameter(request, "from", 0);
        int to = ServletRequestUtils.getIntParameter(request, "to", 5);
        int app_category_id = ServletRequestUtils.getIntParameter(request, "type", -1);    // search_1
        int new_hot_top = ServletRequestUtils.getIntParameter(request, "new_hot_top", 1);  // search_2
        String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");   // search_3
        String PARAM = "*%&.!;',|+-_/\\" + "//\\\\";
        if (keyword != null && !keyword.equals("") && PARAM.contains(keyword)) {
            keyword = "$$";
        }
        // app_category_id = app_category_id == 0 ? -1 : app_category_id;

        model.addAttribute("app_category_id", app_category_id);
        model.addAttribute("from", from);
        model.addAttribute("to", to);


        String userName = ServletUtils.getUserName(request);

        //查询 tb_member表
        MemberModel member = memberService.selectByUserName(userName);

        //查询我已经安装的应用
        List<MemberAppModel> memberAppList = memberAppService.selectByMemberId(member.getTbid());
        model.addAttribute("memberAppList", memberAppList);
        log.debug(JSONArray.fromObject(memberAppList).toString());
        //查询全部应用(没权限的不列出)
        String orderBy = " dt DESC";
        orderBy = new_hot_top == 2 ? " usecount DESC" : orderBy;
        orderBy = new_hot_top == 3 ? " starnum DESC" : orderBy;

        // 返回有权限的应用
        String appByUsername = StringUtil.toStrList(memberAppService.selectAppByUsername(userName, request));


        List<AppModel> appList = appService
                .selectByCondition(0, orderBy, keyword,
                        (app_category_id == -2 ? -1 : app_category_id), 1, appByUsername);

        //只显示我已安装的应用
        if (app_category_id == -2) {
            Map<Integer, Integer> mMap = new HashMap<Integer, Integer>();
            for (MemberAppModel mApp : memberAppList) {
                mMap.put(mApp.getRealid(), mApp.getRealid());
            }
            for (int i = appList.size() - 1; i >= 0; i--) {
                if (!mMap.containsKey(appList.get(i).getTbid())) {
                    appList.remove(i);
                }
            }
        }
        model.addAttribute("appList", appList);
        StringBuilder sb = new StringBuilder();
        appList.forEach(value -> {
            sb.append(value.getName()).append("|,");
        });

        log.info("app应用列表: {}", sb);

        // log.info("app应用列表: {}", JSONArray.fromObject(appList.get(0)).toString());

        return "/appmarket/list";
    }

    @RequestMapping("/detail")
    public String detail(HttpServletRequest request, Model model) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        log.debug("id=" + id);


        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);

        AppModel appModel = new AppModel();
        appModel.setTbid(id);
        appModel = appService.selectByCondition(appModel).get(0);
        appModel.setMember_id(1);
        model.addAttribute("appModel", appModel);

        model.addAttribute("memberName", userName);
        if (appModel.getMember_id() != 0) {
            MemberModel memberModel = new MemberModel();
            memberModel.setTbid(appModel.getMember_id());
            List<MemberModel> mmList = memberService.selectByCondition(memberModel);
            if (mmList != null && mmList.size() > 0) {
                model.addAttribute("memberName", mmList.get(0).getUsername());
            }
        }

        List<MemberAppModel> memberAppList = memberAppService.selectByMemberId(member.getTbid());
        model.addAttribute("memberAppList", memberAppList);

        // 类目类型
        List<AppCategoryModel> appCategoryList = appCategoryService.selectByCondition(null);
        model.addAttribute("appCategoryList", appCategoryList);
        return "/appmarket/detail";
    }

    @RequestMapping("/updateAppStar")
    public String updateAppStar(HttpServletRequest request, HttpServletResponse res, Model model) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        int starnum = ServletRequestUtils.getIntParameter(request, "starnum", 0);
        log.debug("id=" + id);
        log.debug("starnum" + starnum);

        // String userName = request.getRemoteUser();
        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);

        AppStarModel appStarModel = new AppStarModel();
        appStarModel.setMember_id(member.getTbid());
        appStarModel.setApp_id(id);
        List<AppStarModel> appStatList = appStarService.selectByCondition(appStarModel);
        if (appStatList != null && appStatList.size() > 0) {
            return null;
        }

        appStarModel.setStarnum(starnum);
        appStarModel.setDt(DateUtils.dateToStr(new Date()));
        appStarService.insert(appStarModel);

        // 更新平均值
        double avg = appStarService.getAvgStarNum(id);
        AppModel appModel = new AppModel();
        appModel.setTbid(id);
        appModel = appService.selectByCondition(appModel).get(0);
        appModel.setStarnum((double) Math.round(avg * 10) / 10);
        appService.updateById(appModel);

        writeJson(res, "1");
        return null;
    }

    /*
     * @Author 关玉珍
     * @Description  添加应用窗口模板
     * @Date 11:41 2022/1/14
     * @Param [request, res, model]
     * @return java.lang.String
     **/
    @RequestMapping("/manage")
    public String manage(HttpServletRequest request, HttpServletResponse res, Model model) {
        int add = ServletRequestUtils.getIntParameter(request, "add", 0);
        int app_category_id = ServletRequestUtils.getIntParameter(request, "type", -1);
        model.addAttribute("app_category_id", app_category_id);
        model.addAttribute("add", add);
        List<AppCategoryModel> appManagerList = appCategoryService.selectByCondition(null);
        model.addAttribute("appManagerList", appManagerList);
        return "/appmarket/manage";
    }

    @RequestMapping("/myAppList")
    public String myAppList(HttpServletRequest request, HttpServletResponse res, Model model) {
        int from = ServletRequestUtils.getIntParameter(request, "from", 0);
        int to = ServletRequestUtils.getIntParameter(request, "to", 5);
        String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");            // search_1
        int app_category_id = ServletRequestUtils.getIntParameter(request, "app_category_id", -1);  // search_2
        int verifytype = ServletRequestUtils.getIntParameter(request, "verifytype", 1);             // search_4

        String PARAM = "*%&.!;',|+-_/\\" + "//\\\\";
        if (keyword != null && !keyword.equals("") && PARAM.contains(keyword)) {
            keyword = "$$";
        }

        log.debug("from=" + from);
        log.debug("to=" + to);
        log.debug("keyword=" + keyword);
        log.debug("app_category_id=" + app_category_id);
        log.debug("verifytype=" + verifytype);

        model.addAttribute("from", from);
        model.addAttribute("to", to);

        // String userName = request.getRemoteUser();
        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);

        String appByUsername = StringUtil.toStrList(memberAppService.selectAppByUsername(userName, request));

        List<AppModel> appList = appService.selectByCondition(member.getTbid(), "tbid desc", keyword, app_category_id, verifytype, appByUsername);
        if (appList == null) {
            appList = new ArrayList<AppModel>();
        }
        model.addAttribute("appList", appList);

        List<AppCategoryModel> appMyList = appCategoryService.selectByCondition(null);
        model.addAttribute("appMyList", appMyList);

        return "/appmarket/myAppList";
    }

    /*
     * @Author 关玉珍
     * @Description  编辑应用
     * @Date 14:33 2022/1/17
     * @Param [request, res, model]
     * @return java.lang.String
     **/
    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpServletResponse res, Model model) {
        int id = ServletRequestUtils.getIntParameter(request, "id", 0);
        // int id = 27;
        log.debug("id=" + id);
        String userName = ServletUtils.getUserName(request);
        AppModel appModel = new AppModel();
        if (id > 0) {
            MemberModel member = memberService.selectByUserName(userName);
            appModel.setTbid(id);
            appModel.setMember_id(member.getTbid());
            appModel = appService.selectByCondition(appModel).get(0);
        } else {
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

        List<String> byUsername = memberAppService.selectAppByUsername(userName, request);
        model.addAttribute("byUsername", byUsername);

        model.addAttribute("appModel", appModel);


        List<AppCategoryModel> appEditList = appCategoryService.selectByCondition(null);
        model.addAttribute("appEditList", appEditList);
        return "/appmarket/edit";
    }

    /*
     * @Author 关玉珍
     * @Description  保存添加的应用
     * @Date 11:43 2022/1/14
     * @Param [request, res, model]
     * @return java.lang.String
     **/
    @RequestMapping("/save")
    public String save(HttpServletRequest request, HttpServletResponse res, Model model) {
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
        int val_verifytype = ServletRequestUtils.getIntParameter(request, "val_verifytype", 0);

        String userName = ServletUtils.getUserName(request);

        MemberModel member = memberService.selectByUserName(userName);

        AppModel appModel = new AppModel();
        if (id == 0) {
            appModel.setIssetbar(1);
            appModel.setUsecount(0);
            appModel.setStarnum(0.0);
            appModel.setIsrecommend(0);
            appModel.setMember_id(member.getTbid());
        } else {
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
        appModel.setVerifytype(val_verifytype);
        appModel.setDt(DateUtils.dateToStr(new Date()));
        appModel.setVerifytype(1);

        if (id == 0) {
            appService.insert(appModel);
        } else {
            appService.updateById(appModel);
        }

        writeJson(res, "{\"info\":\"\",\"status\":\"y\"}");
        return null;
    }

}
