package cn.cestc.os.desktop.controller;


import cn.cestc.os.desktop.model.MemberAppModel;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.service.MemberAppService;
import cn.cestc.os.desktop.service.MemberService;
import cn.cestc.os.desktop.utils.AjaxResult;
import cn.cestc.os.desktop.utils.FileOperationUtils;
import cn.cestc.os.desktop.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Description:桌面设置
 *
 * @author bo.xu
 * 2015年7月28日 上午11:39:32
 */

@Controller
@RequestMapping("/desktopSettingController")
public class DesktopSettingController
{

    @Autowired
    private MemberAppService memberAppService;

    @Autowired
    private MemberService memberService;


    /**
     * Description: 上传文件主界面跳转
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/uploadindex")
    public String uploadindex(HttpServletRequest request)
    {
        // String userName = request.getRemoteUser();
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        //通过配置文件得到单个文件上传大小，单位MB
        request.setAttribute("uploadFileSingleSize", 4);
        //通过配置文件得到总文件上传大小，单位MB
        request.setAttribute("uploadFileSize", 20);
        //上传文件类型信息
        request.setAttribute("filetypesBydunhao", FileOperationUtils.getUploadFileTypesByProperties("、"));
        request.setAttribute("filetypesBydouhao", FileOperationUtils.getUploadFileTypesByProperties(","));
        return "desktopsetting/desktopsetting_upload";
    }

    /**
     * Description: 上传文件
     *
     * @return
     * @author bo.xu
     */

    @RequestMapping("/uploadfile")
    @ResponseBody
    public AjaxResult<String> uploadfile(HttpServletRequest request, Integer desk)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        //当desk符合条件时，上传并创建应用
        if (desk != null && desk >= 1 && desk <= 5)
        {
            memberAppService.uploadfileAndBuildApp(request, desk);
            result.setCode(result.SUCCESS);
        }
        return result;
    }

    /**
     * Description: 新建文件夹
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/addFolder")
    @ResponseBody
    public AjaxResult<String> addFolder(HttpServletRequest request, String name, String icon, Integer desk)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        String userName = ServletUtils.getUserName(request);
        //当desk符合条件时，上传并创建应用
        if (desk != null && desk >= 1 && desk <= 5)
        {
            memberAppService.builFolderApp(userName, name, icon, desk);
            result.setCode(result.SUCCESS);
        }
        return result;
    }

    /**
     * Description: 更新文件夹
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/updateFolder")
    @ResponseBody
    public AjaxResult<String> updateFolder(HttpServletRequest request, String name, String icon, Integer id)
    {
        AjaxResult<String> result = new AjaxResult<String>();
        //更新
        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setName(name);
        memberAppModel.setIcon(icon);
        memberAppModel.setTbid(id);
        result.setCode(result.SUCCESS);
        memberAppService.updateById(memberAppModel);
        return result;
    }

    /**
     * Description: 判断用户应用名称是否存在
     *
     * @return
     * @author bo.xu
     */
    @RequestMapping("/memappIsExist")
    @ResponseBody
    public AjaxResult<String> memappIsExist(HttpServletRequest request, String name, String type)
    {
        String userName = ServletUtils.getUserName(request);
        MemberModel member = memberService.selectByUserName(userName);

        AjaxResult<String> result = new AjaxResult<String>();
        //更新
        MemberAppModel memberAppModel = new MemberAppModel();
        memberAppModel.setName(name);
        memberAppModel.setType(type);
        memberAppModel.setMemberId(member.getTbid());
        List<MemberAppModel> list = memberAppService.selectByCondition(memberAppModel);
        if (list.size() > 0)
        {
            result.setResult("1");
        } else
        {
            result.setResult("0");
        }
        result.setCode(result.SUCCESS);
        return result;
    }

}
