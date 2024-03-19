package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.SettingModel;

import java.util.List;


/**
 * Description:SettingService类
 *
 * @author bo.xu
 * 2015年7月20日 下午7:36:29
 */

public interface SettingService
{
    /**
     * Description: 新增
     *
     * @param settingModel
     * @return
     */
    Integer insert(SettingModel settingModel);

    /**
     * Description: 删除
     *
     * @param settingModel
     * @return
     */
    Integer delete(SettingModel settingModel);

    /**
     * Description: 通过tbid修改
     *
     * @param settingModel
     * @return
     */
    Integer updateById(SettingModel settingModel);

    /**
     * Description: 通过条件查询
     *
     * @param settingModel
     * @return
     */
    List<SettingModel> selectByCondition(SettingModel settingModel);
}