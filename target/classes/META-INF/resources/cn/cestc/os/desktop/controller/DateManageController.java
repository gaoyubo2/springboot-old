package cn.cestc.os.desktop.controller;

import cn.cestc.os.desktop.model.AppModel;
import cn.cestc.os.desktop.model.MemberAppModel;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.service.AppService;
import cn.cestc.os.desktop.service.DateManageService;
import cn.cestc.os.desktop.service.MemberAppService;
import cn.cestc.os.desktop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 获取数据库中数据 接口
 */
@RestController
@RequestMapping("/data")
public class DateManageController {

    @Autowired
    private DateManageService dateManageService;
    @Autowired
    private AppService appService;
    @Autowired
    private MemberAppService memberAppService;

    //获取app相关数据
    @GetMapping("/apps")
    public AppModel getApps(){
        AppModel appModel = dateManageService.getApps().get(0);
        return appModel;
    }
    //"/app?tbid=2"  根据id查app信息
    @GetMapping("/app")
    public AppModel getAppById(@RequestParam Integer appId){
        AppModel appModel = new AppModel();
        appModel.setTbid(appId);
        AppModel app = appService.selectByCondition(appModel).get(0);
        return app;
    }

    //插入member app model
    @PostMapping("/memberApp")
    public Boolean insertMemberApp(@RequestBody MemberAppModel memberAppModel){
        System.out.println(memberAppModel);
        Integer insert = memberAppService.insert(memberAppModel);
        return insert > 0;
    }

    //获取roles相关数据
//    @GetMapping("/roles")
//    public List<MemberModel> getRoles(){
//        return dateManageService.getRoles();
//    }

}