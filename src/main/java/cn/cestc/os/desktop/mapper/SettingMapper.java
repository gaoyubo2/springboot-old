package cn.cestc.os.desktop.mapper;


import cn.cestc.os.desktop.model.SettingModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:SettingMapper类
 * <p>
 * <p>
 * 2015年7月20日 下午7:33:11
 */

@Repository
public interface SettingMapper
{
    /**
     * Description: 新增
     *
     * @param settingModel
     */
    Integer insert(SettingModel settingModel);

    /**
     * Description: 删除
     *
     * @param settingModel
     */
    Integer delete(SettingModel settingModel);

    /**
     * Description: 通过tbid修改
     *
     * @param settingModel
     */
    Integer updateById(SettingModel settingModel);

    /**
     * Description: 通过条件查询
     *
     * @param settingModel
     */
    List<SettingModel> selectByCondition(SettingModel settingModel);
}