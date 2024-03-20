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
    public List<AppModel> getApps(){
        List<AppModel> appModels = dateManageService.getApps();
        return appModels;
    }
    //"/app?tbid=2"  根据id查app信息
    @GetMapping("/app")
    public List<AppModel> getAppById(@RequestParam Integer tbid){
        AppModel appModel = new AppModel();
        appModel.setTbid(tbid);
        List<AppModel> appList = appService.selectByCondition(appModel);
        return appList;
    }

    //插入member app model
    @PostMapping("/memberapp")
    public Boolean insertMemberApp(MemberAppModel memberAppModel){
        Integer insert = memberAppService.insert(memberAppModel);
        if(insert > 0 )
            return true;
        return false;
    }

    //获取roles相关数据
//    @GetMapping("/roles")
//    public List<MemberModel> getRoles(){
//        return dateManageService.getRoles();
//    }

}
