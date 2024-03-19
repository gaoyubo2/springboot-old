package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.AppCategoryModel;

import java.util.List;


/**
 * Description:AppCategoryServicel类
 *
 * @author bo.xu
 * 2015年7月20日 下午7:30:48
 */

public interface AppCategoryService
{
    /**
     * Description: 新增
     *
     * @param appCategoryModel
     * @return
     */
    Integer insert(AppCategoryModel appCategoryModel);

    /**
     * Description: 删除
     *
     * @return
     */
    Integer delete(AppCategoryModel appCategoryModel);

    /**
     * Description: 通过tbid修改
     *
     * @param appCategoryModel
     * @return
     */
    Integer updateById(AppCategoryModel appCategoryModel);

    /**
     * Description: 通过条件查询
     *
     * @param appCategoryModel
     * @return
     */
    List<AppCategoryModel> selectByCondition(AppCategoryModel appCategoryModel);

    Integer selectPageCount(String keyword);

    List<AppCategoryModel> selectPageByName(String keyword, Integer currentPage, Integer pageSize);

    Integer updateAppCategoryId(Integer appCategoryId, Integer tbid);
}