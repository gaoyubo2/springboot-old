package cn.cestc.os.desktop.controller;


import cn.cestc.os.desktop.model.AppCategoryModel;
import cn.cestc.os.desktop.service.AppCategoryService;
import cn.cestc.os.desktop.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description:类目管理
 *
 * @author bo.xu
 * 2015年7月28日 上午11:38:48
 */

@Controller
@RequestMapping("/categoryManageController")

public class CategoryManageController
{

    @Autowired
    private AppCategoryService appCategoryService;

    @RequestMapping("/index")
    public String showCategoryPage(Model model)
    {
        return "category/index";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult<Integer> deleteCategory(Integer categoryid, HttpServletRequest request)
    {
        AjaxResult<Integer> result = new AjaxResult<Integer>();

        AppCategoryModel appCategoryModel = new AppCategoryModel();
        appCategoryModel.setTbid(categoryid);
        result.setResult(appCategoryService.delete(appCategoryModel));

        appCategoryService.updateAppCategoryId(0, appCategoryModel.getTbid());
        result.setMsg("删除成功!");
        result.setCode(result.SUCCESS);
        return result;
    }

    @RequestMapping("/list")
    @ResponseBody
    public AjaxResult<String> pageListCategory(HttpServletRequest request)
    {
        Integer from = Integer.parseInt(request.getParameter("from"));
        Integer pageSize = Integer.parseInt(request.getParameter("to"));
        String keyword = request.getParameter("search_1");
        String PARAM = "*%&.!;',|+-_/\\" + "//\\\\";
        if (keyword != null && !keyword.equals("") && PARAM.contains(keyword))
        {
            keyword = "$$";
        }
        Integer totalCount = appCategoryService.selectPageCount(keyword);
        AjaxResult<String> result = new AjaxResult<String>();
        StringBuilder sb = new StringBuilder(totalCount + "<{|*|}>");
        List<AppCategoryModel> appCategory = appCategoryService.selectPageByName(keyword, from, pageSize);
        for (AppCategoryModel list : appCategory)
        {
            sb.append(
                    "<tr class=\"list-bd\">"
                            + "<td style=\"text-align:left;padding-left:15px\">" + list.getName() + "</td>"
                            + "<td>"
                            + "<a href=\"javascript:openDetailIframe('edit.do?categoryid=" + list.getTbid() + "');\" class=\"btn btn-link\">编辑</a>"
                            + "<a href=\"javascript:;\" class=\"btn btn-link do-del\" categoryid=" + list.getTbid() + ">删除</a>"
                            + "</td></tr><br/>");
        }
        result.setMsg(sb.toString());
        result.setCode(result.SUCCESS);
        return result;
    }

    @RequestMapping("/edit")
    public String editorCategory(HttpServletRequest request, Model model)
    {
        int id = ServletRequestUtils.getIntParameter(request, "categoryid", 0);
        AppCategoryModel appCategoryModel = new AppCategoryModel();
        if (id != 0)
        {
            appCategoryModel.setTbid(id);
            appCategoryModel = appCategoryService.selectByCondition(appCategoryModel).get(0);
        } else
        {
            appCategoryModel.setTbid(0);
            appCategoryModel.setName("");
        }
        request.setAttribute("AppEditor", appCategoryModel);
        return "category/edit";
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResult<String> insertCategory(Model model, Integer id, String val_name)
    {
        AppCategoryModel appCategoryModel = new AppCategoryModel();
        AjaxResult<String> result = new AjaxResult<String>();
        List<AppCategoryModel> list = appCategoryService.selectByCondition(null);
        boolean isRepeat = false;
        for (AppCategoryModel appCategory : list)
        {
            if (appCategory.getName().equals(val_name.trim()))
            {
                isRepeat = true;
                break;
            }
        }
        System.out.println(isRepeat + "  isisisisi");
        if (isRepeat)
        {
            result.setMsg("r");
            result.setResult("命名重复，请重新输入！");
        } else
        {

            if (id != 0)
            {
                appCategoryModel.setTbid(id);
                appCategoryModel.setName(val_name);
                appCategoryService.updateById(appCategoryModel);
                result.setMsg("y");
            } else
            {
                appCategoryModel.setName(val_name);
                appCategoryModel.setIssystem(0);
                appCategoryService.insert(appCategoryModel);
                result.setMsg("x");
            }
        }
        return result;
    }
}
