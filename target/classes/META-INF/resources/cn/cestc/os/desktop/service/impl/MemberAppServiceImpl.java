package cn.cestc.os.desktop.service.impl;


import cn.cestc.os.desktop.mapper.AppMapper;
import cn.cestc.os.desktop.mapper.MemberAppMapper;
import cn.cestc.os.desktop.mapper.MemberMapper;
import cn.cestc.os.desktop.model.AppModel;
import cn.cestc.os.desktop.model.MemberAppModel;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.model.Result;
import cn.cestc.os.desktop.pojo.DeskTopFolerAppVO;
import cn.cestc.os.desktop.pojo.DesktopVO;
import cn.cestc.os.desktop.pojo.MenuDO;
import cn.cestc.os.desktop.pojo.MoveAppVO;
import cn.cestc.os.desktop.service.AppService;
import cn.cestc.os.desktop.service.MemberAppService;
import cn.cestc.os.desktop.service.MemberService;
import cn.cestc.os.desktop.service.TenantFeignService;
import cn.cestc.os.desktop.utils.DateUtils;
import cn.cestc.os.desktop.utils.FileOperationUtils;
import cn.cestc.os.desktop.utils.ServletUtils;
import cn.cestc.os.desktop.utils.StringUtil;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class MemberAppServiceImpl implements MemberAppService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private MemberAppMapper memberAppMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private TenantFeignService tenantFeignService;
    @Autowired
    private AppService appService;


    public Integer insert(MemberAppModel memberAppModel) {
        if (memberAppModel.getIsshortcut() == null || memberAppModel.getIsshortcut().equals("")) {
            memberAppModel.setIsshortcut(0);
        }
        return memberAppMapper.insert(memberAppModel);
    }

    public Integer delete(MemberAppModel memberAppModel) {
        return memberAppMapper.delete(memberAppModel);
    }

    public Integer updateById(MemberAppModel memberAppModel) {
        return memberAppMapper.updateById(memberAppModel);
    }

    public List<MemberAppModel> selectByCondition(MemberAppModel memberAppModel) {
        return memberAppMapper.selectByCondition(memberAppModel);
    }

    public List<MemberAppModel> selectByMemberId(int member_id) {
        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setMemberId(member_id);
        return memberAppMapper.selectByCondition(memberAppModel);
    }

    @Override
    public DesktopVO selectByUsername(String username, HttpServletRequest request) {
        DesktopVO desktopVO = new DesktopVO();
        //通过username得到用户全部信息
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(username);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);
        // log.info("memberModel: {}	", memberModel.toString());
        String appByUsername = StringUtil.toStrList(selectAppByUsername(username, request));

        //删除无权限的用户应用
        memberAppMapper.deletBynoAuthByUsernames(memberModel.getTbid(), appByUsername);

        //获取桌面文件夹名
        Integer tbid = memberModel.getTbid();
        MemberAppModel memberAppModel1 = new MemberAppModel();
        memberAppModel1.setMemberId(tbid);
        memberAppModel1.setType("folder");
        List<MemberAppModel> memberAppModels = memberAppMapper.selectByCondition(memberAppModel1);
        //TODO 此处为何有，
        //        StringBuilder sb = new StringBuilder(",");
        StringBuilder sb = new StringBuilder();
        memberAppModels.forEach(memberAppModel -> {
            sb.append("'").append(memberAppModel.getName()).append("',");
        });

        String s = sb.length()==0?sb.toString():sb.substring(0, sb.length() - 1);
        appByUsername = appByUsername + s;
        System.out.println(memberModel);

        List<Integer> folderids = new ArrayList<Integer>();
        if (memberModel.getDock() != null && !memberModel.getDock().equals("")) {
            List<MemberAppModel> list = memberAppMapper.selectByAppids(memberModel.getDock(), appByUsername);

            System.out.println(list);
            handlerDeskList(list, folderids);
            desktopVO.setDock(list);
        }
        if (memberModel.getDesk1() != null && !memberModel.getDesk1().equals("")) {
            List<MemberAppModel> list = memberAppMapper.selectByAppids(memberModel.getDesk1(), appByUsername);
            handlerDeskList(list, folderids);
            desktopVO.setDesk1(list);
        }
        if (memberModel.getDesk2() != null && !memberModel.getDesk2().equals("")) {
            List<MemberAppModel> list = memberAppMapper.selectByAppids(memberModel.getDesk2(), appByUsername);
            handlerDeskList(list, folderids);
            desktopVO.setDesk2(list);
        }
        if (memberModel.getDesk3() != null && !memberModel.getDesk3().equals("")) {
            List<MemberAppModel> list = memberAppMapper.selectByAppids(memberModel.getDesk3(), appByUsername);
            handlerDeskList(list, folderids);
            desktopVO.setDesk3(list);
        }
        if (memberModel.getDesk4() != null && !memberModel.getDesk4().equals("")) {
            List<MemberAppModel> list = memberAppMapper.selectByAppids(memberModel.getDesk4(), appByUsername);
            handlerDeskList(list, folderids);
            desktopVO.setDesk4(list);
        }
        if (memberModel.getDesk5() != null && !memberModel.getDesk5().equals("")) {
            List<MemberAppModel> list = memberAppMapper.selectByAppids(memberModel.getDesk5(), appByUsername);
            handlerDeskList(list, folderids);
            desktopVO.setDesk5(list);
        }

        if (folderids.size() > 0) {
            List<DeskTopFolerAppVO> deskTopFolerAppVOs = new ArrayList<DeskTopFolerAppVO>();
            for (Integer folderid : folderids) {
                DeskTopFolerAppVO deskTopFolerAppVO = new DeskTopFolerAppVO();
                deskTopFolerAppVO.setAppid(folderid);

                MemberAppModel memberAppModel = new MemberAppModel();
                memberAppModel.setFolderId(folderid);
                memberAppModel.setMemberId(memberModel.getTbid());
                List<MemberAppModel> list = memberAppMapper.selectByCondition(memberAppModel);
                for (MemberAppModel memberAppModeltemp : list) {
                    if (memberAppModeltemp.getType().equals("file")) {
                        memberAppModeltemp.setName(memberAppModeltemp.getName() + "." + memberAppModeltemp.getExt());
                    }
                    memberAppModeltemp.setAppid(memberAppModeltemp.getTbid());
                    memberAppModeltemp.setRealappid(memberAppModeltemp.getRealid());
                }
                deskTopFolerAppVO.setApps(list);
                deskTopFolerAppVOs.add(deskTopFolerAppVO);
            }
            desktopVO.setFolder(deskTopFolerAppVOs);
        }

        return desktopVO;
    }


    /*
     * @Author 关玉珍
     * @Description  查询有权限的应用
     * @Date 12:32 2022/1/14
     * @Param [username, request]
     * @return java.lang.String
     **/
    public List<String> selectAppByUsername(String username, HttpServletRequest request) {

        MemberModel member = memberService.selectByUserName(username);
        List<String> appLocalList = appService.selectByUserName(member.getTbid());


        //获取租户系统的应用列表
//        Integer tenantId = ServletUtils.getTenantId(request);
//        Result<List<MenuDO>> menuDOResult = tenantFeignService.moduleList(tenantId);
//
        List<String> appList = new ArrayList<>();
//        if (menuDOResult != null && menuDOResult.getCode() == 200) {
//            if (menuDOResult.getData().size() > 0) {
//                for (MenuDO resultData : menuDOResult.getData()) {
//                    if (resultData.getMenuName() != null && !resultData.getMenuName().equals("")) {
//                        appList.add(resultData.getMenuName());
//                    }
//                }
//            }
//        }
//        log.info("租户系统获取的租户app列表 : {}", appList);
//
        appList.addAll(appLocalList);

        String userType = ServletUtils.getUserType(request);
//        String userType = "系统管理员";
        switch (userType) {
            case "系统管理员":   //当为系统管理员的时候 对应四个默认应用
                appList.add("应用管理");
                appList.add("类目管理");
                appList.add("网站设置");
                appList.add("租户用户管理");
                break;
            case "租户管理员":
                appList.add("租户用户管理");
                break;
        }

        return appList;
    }

    public void handlerDeskList(List<MemberAppModel> list, List<Integer> folderid) {
        for (MemberAppModel memberAppModel : list) {
            if (memberAppModel.getType().equals("folder")) {
                folderid.add(memberAppModel.getTbid());
            } else if (memberAppModel.getType().equals("file")) {
                memberAppModel.setName(memberAppModel.getName() + "." + memberAppModel.getExt());
            }
            memberAppModel.setAppid(memberAppModel.getTbid());
            memberAppModel.setRealappid(memberAppModel.getRealid());
        }
    }

    @Override
    public MemberAppModel getAppByMemberappidAndTypeAndUsername(Integer Memberappid, String type, String username) {
        MemberAppModel memberAppModel = new MemberAppModel();
        //通过username得到用户全部信息
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(username);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);

        //通过用户id和用户应用id查询用户应用
        MemberAppModel memberAppModeltemp = new MemberAppModel();
        memberAppModeltemp.setTbid(Memberappid);
        memberAppModeltemp.setMemberId(memberModel.getTbid());
        List<MemberAppModel> list = memberAppMapper.selectByCondition(memberAppModeltemp);
        //当对应用户应用表不为空的时候
        if (list.size() > 0) {
            memberAppModel = list.get(0);
            //通过主键查询应用详细信息
            if (type.equals("window") || type.equals("widget")) {
                AppModel appModeltemp = new AppModel();
                appModeltemp.setTbid(list.get(0).getRealid());
                List<AppModel> appModellist = appMapper.selectByCondition(appModeltemp);
                //当查询的应用不为空的时候
                if (appModellist.size() > 0) {
                    memberAppModel.setUrl(appModellist.get(0).getUrl());
                } else {
                    memberAppModel.setError("ERROR_NOT_FOUND");
                }
            } else if (memberAppModel.getType().equals("file")) {
                memberAppModel.setUrl("");
            }
            memberAppModel.setAppid(memberAppModel.getTbid());
            memberAppModel.setRealappid(memberAppModel.getRealid());
        } else {
            if (type.equals("window") || type.equals("widget")) {
                memberAppModel.setError("ERROR_NOT_INSTALLED");
            } else {
                memberAppModel.setError("ERROR_NOT_FOUND");
            }
        }
        return memberAppModel;
    }

    @Override
    public void downloadfileappByid(Integer appid, HttpServletRequest request,
                                    HttpServletResponse response) {
        //得到用户信息
        String userName = request.getRemoteUser();
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);
        //得到该id 该用户对应的用户应用
        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setTbid(appid);
        memberAppModel.setMemberId(memberModel.getTbid());
        memberAppModel = memberAppMapper.selectByCondition(memberAppModel).get(0);
        //得到项目根路径
        String basepath = request.getSession().getServletContext().getRealPath("/");
        //下载
        try {
            FileOperationUtils.download(request, response, basepath + "/" + memberAppModel.getUrl(), memberAppModel.getName() + "." + memberAppModel.getExt());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Integer uploadfileAndBuildApp(HttpServletRequest request, Integer desk) {
        //得到用户信息
        String userName = request.getRemoteUser();
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);

        //上传
        String basepath = request.getSession().getServletContext().getRealPath("/");
        String filepath = "static/uploads/member/" + memberModel.getTbid() + "/file/" + DateUtils.getDateByFormae("yyyyMMdd") + "/";
        String filename = new Date().getTime() + "" + new Random().nextInt(9);
        Map<String, Object> fliemessage = FileOperationUtils.fileupload(request, basepath + "/" + filepath, filename);

        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setMemberId(memberModel.getTbid());
        //根据配置文件得到相应文件类型对应图片
        memberAppModel.setIcon(FileOperationUtils.getUploadFileTypesMessageByProperties().get(fliemessage.get("flietype")));
        memberAppModel.setName((String) fliemessage.get("filename"));
        memberAppModel.setUrl(filepath + filename + "." + (String) fliemessage.get("flietype"));
        memberAppModel.setExt((String) fliemessage.get("flietype"));
        memberAppModel.setSize(((Long) fliemessage.get("size")).intValue());
        memberAppModel.setType("file");
        memberAppModel.setWidth(600);
        memberAppModel.setHeight(400);
        memberAppModel.setDt(DateUtils.dateToStr(new Date()));
        memberAppModel.setLastdt(DateUtils.dateToStr(new Date()));
        this.insert(memberAppModel);

        memberAppModel = selectByCondition(memberAppModel).get(0);
        //利用反射将对应的desk的id添加进数据库
        memberService.updateSomedeskByMemberidAndMemapid(memberModel, memberAppModel.getTbid(), desk);
        return 1;
    }

    @Override
    public Integer builFolderApp(String userName, String name, String icon, Integer desk) {
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);

        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setName(name);
        memberAppModel.setIcon("static/img/folder.png");
        memberAppModel.setMemberId(memberModel.getTbid());
        memberAppModel.setType("folder");
        memberAppModel.setWidth(600);
        memberAppModel.setHeight(400);
        memberAppModel.setDt(DateUtils.dateToStr(new Date()));
        memberAppModel.setLastdt(DateUtils.dateToStr(new Date()));
        memberAppModel.setUrl("");
        memberAppModel.setRealid(0);
        memberAppModel.setFolderId(0);
        memberAppModel.setIsresize(0);
        memberAppModel.setIsopenmax(0);
        memberAppModel.setIssetbar(0);
        memberAppModel.setIsflash(0);


        this.insert(memberAppModel);

        memberAppModel = selectByCondition(memberAppModel).get(0);
        //利用反射将对应的desk的id添加进数据库
        memberService.updateSomedeskByMemberidAndMemapid(memberModel, memberAppModel.getTbid(), desk);
        return 1;
    }

    @Override
    public Integer builPwindowOrPwidgetApp(MemberAppModel memberAppModel, Integer desk, String userName) {
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);
        //将参数加入
        MemberAppModel memberAppModeltemp = new MemberAppModel();
        memberAppModeltemp.setName(memberAppModel.getName());
        memberAppModeltemp.setIcon(memberAppModel.getIcon());
        memberAppModeltemp.setUrl(memberAppModel.getUrl());
        memberAppModeltemp.setType(memberAppModel.getType());
        memberAppModeltemp.setWidth(memberAppModel.getWidth());
        memberAppModeltemp.setHeight(memberAppModel.getHeight());
        memberAppModeltemp.setIsresize(memberAppModel.getIsresize());
        memberAppModeltemp.setIsopenmax(memberAppModel.getIsopenmax());
        memberAppModeltemp.setIsflash(memberAppModel.getIsflash());
        memberAppModeltemp.setDt(DateUtils.dateToStr(new Date()));
        memberAppModeltemp.setLastdt(DateUtils.dateToStr(new Date()));
        memberAppModeltemp.setMemberId(memberModel.getTbid());
        this.insert(memberAppModeltemp);

        memberAppModeltemp = selectByCondition(memberAppModeltemp).get(0);
        //利用反射将对应的desk的id添加进数据库
        memberService.updateSomedeskByMemberidAndMemapid(memberModel, memberAppModeltemp.getTbid(), desk);
        return 1;
    }

    @Override
    public Integer deleteMyApp(String username, Integer appid) throws Exception {
        //查询用户信息
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(username);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);
        //查询用户应用信息
        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setTbid(appid);
        memberAppModel = memberAppMapper.selectByCondition(memberAppModel).get(0);
        //如果是文件夹，则先删除文件夹内的应用
        if (memberAppModel.getType().equals("folder")) {
            MemberAppModel memberAppModeltemp = new MemberAppModel();
            memberAppModeltemp.setFolderId(appid);
            List<MemberAppModel> memberapplist = memberAppMapper.selectByCondition(memberAppModeltemp);
            for (MemberAppModel delmemberAppModel : memberapplist) {
                memberAppMapper.delete(delmemberAppModel);
            }
            //如果是系统应用，则更新应用的安装人数
        } else if (memberAppModel.getType().equals("window") || memberAppModel.getType().equals("widget")) {
            AppModel appModel = new AppModel();
            appModel.setTbid(memberAppModel.getRealid());
            List<AppModel> list = appMapper.selectByCondition(appModel);
            if (list.size() > 0) {
                appModel = list.get(0);
                appModel.setUsecount(appModel.getUsecount() - 1);
                appMapper.updateById(appModel);
            }
        }
        //查询用户应用码头以及5个桌面的数据去掉要删除应用id的关联
        if (memberModel.getDock() != null && !memberModel.getDock().equals("") && !memberModel.getDock().toLowerCase().equals("null")) {
            String[] str = memberModel.getDock().split(",");
            List<String> strlist = new ArrayList<String>();
            for (String str0 : str) {
                strlist.add(str0);
            }

            if (strlist.size() > 0) {
                Iterator<String> strlistIterator = strlist.iterator();
                while (strlistIterator.hasNext()) {
                    String e = strlistIterator.next();
                    MemberAppModel appModel = new MemberAppModel();
                    appModel.setTbid(Integer.valueOf(e));
                    List listtemp = memberAppMapper.selectByCondition(appModel);
                    if (e.equals(appid + "") || listtemp.size() == 0) {
                        strlistIterator.remove();
                    }
                }
            }
            String strResult = "";
            for (String resstr : strlist) {
                strResult += resstr + ",";
            }
            if (strlist.size() > 0) {
                memberModel.setDock(strResult.substring(0, strResult.length() - 1));
            } else {
                memberModel.setDock("");
            }
        }
        for (int i = 1; i <= 5; i++) {
            String desk = (String) memberModel.getClass().getDeclaredMethod("getDesk" + i).invoke(memberModel);
            if (desk != null && !desk.equals("") && !desk.equals("null")) {
                String[] str = desk.split(",");
                List<String> strlist = new ArrayList<String>();
                for (String str0 : str) {
                    strlist.add(str0);
                }

                if (strlist.size() > 0) {
                    Iterator<String> strlistIterator = strlist.iterator();
                    while (strlistIterator.hasNext()) {
                        String e = strlistIterator.next();
                        MemberAppModel appModel = new MemberAppModel();
                        appModel.setTbid(Integer.valueOf(e));
                        List listtemp = memberAppMapper.selectByCondition(appModel);
                        if (e.equals(appid + "") || listtemp.size() == 0) {
                            strlistIterator.remove();
                        }
                    }
                }
                String strResult = "";
                for (String resstr : strlist) {
                    strResult += resstr + ",";
                }
                if (strlist.size() > 0) {
                    memberModel.getClass().getDeclaredMethod("setDesk" + i, String.class).invoke(
                            memberModel, strResult.substring(0, strResult.length() - 1));
                } else {
                    memberModel.getClass().getDeclaredMethod("setDesk" + i, String.class).invoke(
                            memberModel, "");
                }
            }
        }

        //更新用户表删除用户应用表
        memberMapper.updateById(memberModel);
        memberAppMapper.delete(memberAppModel);
        return 1;
    }

    @Override
    public Map<String, Object> uploadImg(HttpServletRequest request) {
        //得到用户信息
        String userName = request.getRemoteUser();
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);
        //上传
        String basepath = request.getSession().getServletContext().getRealPath("/");
        String filepath = "static/uploads/member/" + memberModel.getTbid() + "/shortcut/" + DateUtils.getDateByFormae("yyyyMMdd") + "/";
        String filename = new Date().getTime() + "" + new Random().nextInt(9);
        Map<String, Object> fliemessage = FileOperationUtils.fileupload(request, basepath + "/" + filepath, filename);
        fliemessage.put("url", filepath + filename + "." + (String) fliemessage.get("flietype"));
        fliemessage.put("fileType", (String) fliemessage.get("flietype"));
        fliemessage.put("original", "");
        fliemessage.put("state", "sucess");
        return fliemessage;
    }


    @Override
    public Integer moveMyApp(MemberModel memberModel, MoveAppVO moveAppVO) throws Exception {
        //因为要split，所以提前处理值为null 的
        if (memberModel.getDock() == null) {
            memberModel.setDock("");
        }
        for (int i = 1; i <= 5; i++) {
            try {
                if (memberModel.getClass().getDeclaredMethod("getDesk" + i).invoke(memberModel) == null) {
                    memberModel.getClass().getDeclaredMethod("setDesk" + i, String.class).invoke(
                            memberModel, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //dock-folder
        if (moveAppVO.getMovetype().equals("dock-folder")) {
            //删除原dock里面id
            String delid = moveAppVO.getId() + "";
            String olddock = memberModel.getDock();
            String newdock = StringUtil.listToString(StringUtil.deleStringlistByValue(StringUtil.StringToList(olddock, ","), delid), ",");
            memberModel.setDock(newdock);
            memberMapper.updateById(memberModel);
            //将id更新应用对应的folder字段
            MemberAppModel memberAppModel = new MemberAppModel();
            memberAppModel.setFolderId(moveAppVO.getTo());
            memberAppModel.setTbid(moveAppVO.getId());
            memberAppMapper.updateById(memberAppModel);

            //dock-dock
        } else if (moveAppVO.getMovetype().equals("dock-dock")) {
            String[] strlist = memberModel.getDock().split(",");
            //判断传入的应用id和数据库里的id是否吻合
            if (strlist[moveAppVO.getFrom()].equals(moveAppVO.getId() + "")) {
                //交换顺序
                if (moveAppVO.getTo() >= strlist.length) {
                    moveAppVO.setTo(strlist.length - 1);
                }
                String temp = strlist[moveAppVO.getFrom()];
                strlist[moveAppVO.getFrom()] = strlist[moveAppVO.getTo()];
                strlist[moveAppVO.getTo()] = temp;
                memberModel.setDock(StringUtil.stringlistToString(strlist, ","));
                memberMapper.updateById(memberModel);
            }
            //dock-desk
        } else if (moveAppVO.getMovetype().equals("dock-desk")) {
            List<String> deskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getDesk()).invoke(memberModel), ",");
            List<String> dockarr = StringUtil.StringToList(memberModel.getDock(), ",");
            //当处理的id和应用id相符的时候才能进行处理
            if (dockarr.get(moveAppVO.getFrom()).equals(moveAppVO.getId() + "")) {
                //删掉原dock的
                StringUtil.deleStringlistByValue(dockarr, dockarr.get(moveAppVO.getFrom()));
                //将应用加入desk
                if (deskarr.size() == 0) {
                    deskarr.add(moveAppVO.getId() + "");
                } else {
                    if (deskarr.get(0).equals("")) {
                        deskarr.set(0, moveAppVO.getId() + "");
                    } else {
                        if (moveAppVO.getTo() >= deskarr.size()) {
                            deskarr.add(moveAppVO.getId() + "");
                        } else {
                            deskarr.add(moveAppVO.getTo(), moveAppVO.getId() + "");
                        }
                    }
                }
                memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getDesk(), String.class).invoke(
                        memberModel, StringUtil.listToString(deskarr, ","));
                memberModel.setDock(StringUtil.listToString(dockarr, ","));
                memberMapper.updateById(memberModel);
            }
            //dock-otherdesk
        } else if (moveAppVO.getMovetype().equals("dock-otherdesk")) {
            List<String> dockarr = StringUtil.StringToList(memberModel.getDock(), ",");
            List<String> todeskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getTodesk()).invoke(memberModel), ",");
            //当处理的id和应用id相符的时候才能进行处理
            if (dockarr.get(moveAppVO.getFrom()).equals(moveAppVO.getId() + "")) {
                //删掉原dock里的
                StringUtil.deleStringlistByValue(dockarr, dockarr.get(moveAppVO.getFrom()));
                //将应用加入dock
                if (todeskarr.size() == 0) {
                    todeskarr.add(moveAppVO.getId() + "");
                } else {
                    if (todeskarr.get(0).equals("")) {
                        todeskarr.set(0, moveAppVO.getId() + "");
                    } else {
                        todeskarr.add(moveAppVO.getId() + "");
                    }
                }

                memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getTodesk(), String.class).invoke(
                        memberModel, StringUtil.listToString(todeskarr, ","));
                memberModel.setDock(StringUtil.listToString(dockarr, ","));
                memberMapper.updateById(memberModel);
            }
            //desk-folder
        } else if (moveAppVO.getMovetype().equals("desk-folder")) {
            List<String> deskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getDesk()).invoke(memberModel), ",");
            //删掉原桌面的
            StringUtil.deleStringlistByValue(deskarr, moveAppVO.getId() + "");
            //更新原桌面数据
            memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getDesk(), String.class).invoke(
                    memberModel, StringUtil.listToString(deskarr, ","));
            memberMapper.updateById(memberModel);
            //更新要移动的应用所属floder
            MemberAppModel memberAppModel = new MemberAppModel();
            memberAppModel.setFolderId(moveAppVO.getTo());
            memberAppModel.setTbid(moveAppVO.getId());
            memberAppMapper.updateById(memberAppModel);
            //desk-dock
        } else if (moveAppVO.getMovetype().equals("desk-dock")) {
            List<String> deskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getDesk()).invoke(memberModel), ",");
            List<String> dockarr = StringUtil.StringToList(memberModel.getDock(), ",");
            Integer from = moveAppVO.getFrom();
            String s = deskarr.get(moveAppVO.getFrom());
            String s1 = moveAppVO.getId().toString();
            if (!s.equals(s1)) {
                log.info("码头应用位置 获取失败: {}", s + ":" + s1);
            }
            if (deskarr.get(moveAppVO.getFrom()).equals(moveAppVO.getId() + "")) {
                //删掉原桌面的
                StringUtil.deleStringlistByValue(deskarr, deskarr.get(moveAppVO.getFrom()));
                //将应用加入dock
                if (dockarr.size() == 0) {
                    dockarr.add(moveAppVO.getId() + "");
                } else {
                    if (dockarr.get(0).equals("")) {
                        dockarr.set(0, moveAppVO.getId() + "");
                    } else {
                        if (moveAppVO.getTo() >= dockarr.size()) {
                            dockarr.add(moveAppVO.getId() + "");
                        } else {
                            dockarr.add(moveAppVO.getTo(), moveAppVO.getId() + "");
                        }
                    }
                }
                //如果dock大于7 则将第八个应用挪向桌面
                if (dockarr.size() > 7) {
                    deskarr.add(dockarr.get(7));
                    dockarr.remove(7);
                }
                memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getDesk(), String.class).invoke(
                        memberModel, StringUtil.listToString(deskarr, ","));
                memberModel.setDock(StringUtil.listToString(dockarr, ","));
                memberMapper.updateById(memberModel);
            }

            //desk-desk
        } else if (moveAppVO.getMovetype().equals("desk-desk")) {
            String[] strlist = ((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getDesk()).invoke(memberModel)).split(",");
            //判断传入的应用id和数据库里的id是否吻合
            if (strlist[moveAppVO.getFrom()].equals(moveAppVO.getId() + "")) {
                //交换顺序
                if (moveAppVO.getTo() >= strlist.length) {
                    moveAppVO.setTo(strlist.length - 1);
                }
                String temp = strlist[moveAppVO.getFrom()];
                strlist[moveAppVO.getFrom()] = strlist[moveAppVO.getTo()];
                strlist[moveAppVO.getTo()] = temp;
                memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getDesk(), String.class).invoke(
                        memberModel, StringUtil.stringlistToString(strlist, ","));
                memberMapper.updateById(memberModel);
            }

            //desk-otherdesk
        } else if (moveAppVO.getMovetype().equals("desk-otherdesk")) {
            List<String> fromdeskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getFromdesk()).invoke(memberModel), ",");
            List<String> todeskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getTodesk()).invoke(memberModel), ",");

            if (fromdeskarr.get(moveAppVO.getFrom()).equals(moveAppVO.getId() + "")) {
                //删掉原desk的
                StringUtil.deleStringlistByValue(fromdeskarr, fromdeskarr.get(moveAppVO.getFrom()));
                //将应用加入desk
                if (todeskarr.size() == 0) {
                    todeskarr.add(moveAppVO.getId() + "");
                } else {
                    if (todeskarr.get(0).equals("")) {
                        todeskarr.set(0, moveAppVO.getId() + "");
                    } else {
                        if (moveAppVO.getTo() >= todeskarr.size()) {
                            todeskarr.add(moveAppVO.getId() + "");
                        } else {
                            todeskarr.add(moveAppVO.getTo(), moveAppVO.getId() + "");
                        }
                    }
                }
                memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getFromdesk(), String.class).invoke(
                        memberModel, StringUtil.listToString(fromdeskarr, ","));
                memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getTodesk(), String.class).invoke(
                        memberModel, StringUtil.listToString(todeskarr, ","));
                memberMapper.updateById(memberModel);
            }
            //folder-folder
        } else if (moveAppVO.getMovetype().equals("folder-folder")) {
            //直接更新应用所属文件夹id即可
            MemberAppModel memberAppModel = new MemberAppModel();
            memberAppModel.setFolderId(moveAppVO.getTo());
            memberAppModel.setTbid(moveAppVO.getId());
            memberAppMapper.updateById(memberAppModel);
            //folder-dock
        } else if (moveAppVO.getMovetype().equals("folder-dock")) {
            List<String> deskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getDesk()).invoke(memberModel), ",");
            List<String> dockarr = StringUtil.StringToList(memberModel.getDock(), ",");
            //将应用加入dock
            if (dockarr.size() == 0) {
                dockarr.add(moveAppVO.getId() + "");
            } else {
                if (dockarr.get(0).equals("")) {
                    dockarr.set(0, moveAppVO.getId() + "");
                } else {
                    if (moveAppVO.getTo() >= dockarr.size()) {
                        dockarr.add(moveAppVO.getId() + "");
                    } else {
                        dockarr.add(moveAppVO.getTo(), moveAppVO.getId() + "");
                    }
                }
            }
            //如果dock大于7 则将第八个应用挪向桌面
            if (dockarr.size() > 7) {
                deskarr.add(dockarr.get(7));
                dockarr.remove(7);
            }
            //更新用户桌面、dock数据
            memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getDesk(), String.class).invoke(
                    memberModel, StringUtil.listToString(deskarr, ","));
            memberModel.setDock(StringUtil.listToString(dockarr, ","));
            memberMapper.updateById(memberModel);

            //将应用从文件夹中删除
            MemberAppModel memberAppModel = new MemberAppModel();
            memberAppModel.setTbid(moveAppVO.getId());
            memberAppModel.setFolderId(0);
            memberAppMapper.updateById(memberAppModel);
            //folder-desk
        } else if (moveAppVO.getMovetype().equals("folder-desk")) {
            List<String> deskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getDesk()).invoke(memberModel), ",");
            //将应用加入desk
            if (deskarr.size() == 0) {
                deskarr.add(moveAppVO.getId() + "");
            } else {
                if (deskarr.get(0).equals("")) {
                    deskarr.set(0, moveAppVO.getId() + "");
                } else {
                    if (moveAppVO.getTo() >= deskarr.size()) {
                        deskarr.add(moveAppVO.getId() + "");
                    } else {
                        deskarr.add(moveAppVO.getTo(), moveAppVO.getId() + "");
                    }
                }
            }
            memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getDesk(), String.class).invoke(
                    memberModel, StringUtil.listToString(deskarr, ","));
            memberMapper.updateById(memberModel);
            //将应用从文件夹中删除
            MemberAppModel memberAppModel = new MemberAppModel();
            memberAppModel.setTbid(moveAppVO.getId());
            memberAppModel.setFolderId(0);
            memberAppMapper.updateById(memberAppModel);
            //folder-otherdesk
        } else if (moveAppVO.getMovetype().equals("folder-otherdesk")) {
            List<String> todeskarr = StringUtil.StringToList((String) memberModel.getClass().getDeclaredMethod("getDesk" + moveAppVO.getTodesk()).invoke(memberModel), ",");
            //将应用加入desk
            if (todeskarr.size() == 0) {
                todeskarr.add(moveAppVO.getId() + "");
            } else {
                if (todeskarr.get(0).equals("")) {
                    todeskarr.set(0, moveAppVO.getId() + "");
                } else {
                    todeskarr.add(moveAppVO.getId() + "");
                }
            }
            memberModel.getClass().getDeclaredMethod("setDesk" + moveAppVO.getTodesk(), String.class).invoke(
                    memberModel, StringUtil.listToString(todeskarr, ","));
            memberMapper.updateById(memberModel);
            //将应用从文件夹中删除
            MemberAppModel memberAppModel = new MemberAppModel();
            memberAppModel.setTbid(moveAppVO.getId());
            memberAppModel.setFolderId(0);
            memberAppMapper.updateById(memberAppModel);
        }
        return 1;
    }

    @Override
    public String memAppIsExist(Integer memAppId, String username) {
        //通过username得到用户全部信息
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(username);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);
        //通过用户id和用户应用id查询用户应用
        MemberAppModel memberAppModeltemp = new MemberAppModel();
        memberAppModeltemp.setTbid(memAppId);
        memberAppModeltemp.setMemberId(memberModel.getTbid());
        List<MemberAppModel> memapplist = memberAppMapper.selectByCondition(memberAppModeltemp);
        //当对应用户应用表不为空的时候
        if (memapplist.size() == 0) {
            return "THISAPPISNOWITHINUSER";
        } else if (memapplist.size() > 0) {
            AppModel app = new AppModel();
            app.setTbid(memapplist.get(0).getRealid());
            List<AppModel> applist = appMapper.selectByCondition(app);
            if (applist.size() == 0) {
                return "ERROR_NOT_FOUND";
            }
        }
        return "exist";
    }

    @Override
    public Integer deletBynoAuthByUsernames(Integer memberId,
                                            String appByUsername) {
        return memberAppMapper.deletBynoAuthByUsernames(memberId, appByUsername);
    }

}