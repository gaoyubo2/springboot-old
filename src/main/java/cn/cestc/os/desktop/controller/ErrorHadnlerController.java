package cn.cestc.os.desktop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * Description:错误处理
 *
 * @author 2015年7月28日 上午11:38:28
 */

@Controller
@RequestMapping("/errorHadnlerController")
public class ErrorHadnlerController
{

    /**
     * Description: 没有登录处理
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/nologin")
    public String nologin(HttpServletRequest request)
    {
        return "errorhandler/nologin_handler";
    }

    /**
     * Description: 用户应用不存在处理
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/thisappisnowithinuser")
    public String thisappisnowithinuser(HttpServletRequest request)
    {
        return "errorhandler/thisappisnowithinuser_handler";
    }
}
