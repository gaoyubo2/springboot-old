package cn.cestc.os.desktop.mapper;


import cn.cestc.os.desktop.model.AppCategoryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:AppCategoryMapper类
 */

@Repository
public interface AppCategoryMapper
{
    /**
     * Description: 新增应用分类
     *
     * @param appCategoryModel
     */
    Integer insert(AppCategoryModel appCategoryModel);

    /**
     * Description: 删除应用分类
     *
     * @param appCategoryModel
     */
    Integer delete(AppCategoryModel appCategoryModel);

    /**
     * Description: 通过tbid修改应用分类
     *
     * @param appCategoryModel
     */
    Integer updateById(AppCategoryModel appCategoryModel);

    /**
     * Description: 通过条件查询应用分类
     *
     * @param appCategoryModel
     */
    List<AppCategoryModel> selectByCondition(AppCategoryModel appCategoryModel);

    /**
     * Description:查找应用分类总条数
     */
    Integer selectPageCount(@Param("keyword") String keyword);

    /**
     * Description:用于分页查询应用分类
     */
    List<AppCategoryModel> selectPageByName(@Param("keyword") String keyword,
                                            @Param("currentPage") Integer currentPage,
                                            @Param("pageSize") Integer pageSize);

    /*
     * @Author 关玉珍
     * @Description  更新app应用分类id
     * @Date 11:21 2022/2/24
     * @Param [appCategoryId, tbid]
     * @return java.lang.Integer
     **/
    Integer updateAppCategoryId(@Param("appCategoryId") Integer appCategoryId,
                                @Param("tbid") Integer tbid);
}