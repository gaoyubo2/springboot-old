package cn.cestc.os.desktop.controller;

import cn.cestc.os.desktop.model.AppModel;
import cn.cestc.os.desktop.service.DateManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 获取数据库中数据 接口
 */
@RestController
@RequestMapping("/data")
public class DateManageController {

    @Autowired
    private DateManageService dateManageService;

    //获取app相关数据
    @GetMapping("/apps")
    public List<AppModel> getApps(){
        List<AppModel> appModels = dateManageService.getApps();
        return appModels;
    }

}
