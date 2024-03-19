package cn.cestc.os.desktop.mapper;


import cn.cestc.os.desktop.model.AppStarModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:AppStarMapper类
 * 2015年7月20日 下午7:31:38
 */

@Repository
public interface AppStarMapper
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

    double getAvgStarNum(int app_id);
}