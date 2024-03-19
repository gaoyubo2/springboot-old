package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.AppStarModel;

import java.util.List;

/**
 * Description:AppStarService类
 *
 * @author bo.xu
 * 2015年7月20日 下午7:34:37
 */

public interface AppStarService
{
    /**
     * Description: 新增
     *
     * @param appStarModel
     * @return
     */
    Integer insert(AppStarModel appStarModel);

    /**
     * Description: 删除
     *
     * @param appStarModel
     * @return
     */
    Integer delete(AppStarModel appStarModel);

    /**
     * Description: 通过tbid修改
     *
     * @param appStarModel
     * @return
     */
    Integer updateById(AppStarModel appStarModel);

    /**
     * Description: 通过条件查询
     *
     * @param appStarModel
     * @return
     */
    List<AppStarModel> selectByCondition(AppStarModel appStarModel);

    /**
     * 查询平均值
     */
    double getAvgStarNum(int app_id);
}