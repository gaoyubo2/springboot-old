package cn.cestc.os.desktop.controller;


import cn.cestc.os.desktop.model.SettingModel;
import cn.cestc.os.desktop.service.SettingService;
import cn.cestc.os.desktop.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Description:网站设置
 *
 * @author bo.xu
 * 2015年7月28日 上午11:40:53
 */

@Controller
@RequestMapping("/webSettingController")
public class WebSettingController
{

    @Autowired
    private SettingService settingService;

    /**
     * Description: 网站设置主界面跳转并初始化数据
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/index")
    public String toWebsettingIndex(HttpServletRequest request)
    {
        SettingModel settingModel = new SettingModel();
        //系统设置
        request.setAttribute("settingModel", settingService.selectByCondition(settingModel).get(0));
        return "websetting/websetting_index";
    }


    /**
     * Description: 网站设置数据更新
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/websettingUpdate")
    @ResponseBody
    public AjaxResult<String> websettingUpdate(HttpServletRequest request, String val_title,
                                               String val_keywords, String val_description)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        SettingModel settingModel = new SettingModel();
        settingModel = settingService.selectByCondition(settingModel).get(0);
        //更新
        settingModel.setTitle(val_title);
        settingModel.setKeywords(val_keywords);
        settingModel.setDescription(val_description);
        settingService.updateById(settingModel);
        result.setMsg("y");
        return result;
    }

}
