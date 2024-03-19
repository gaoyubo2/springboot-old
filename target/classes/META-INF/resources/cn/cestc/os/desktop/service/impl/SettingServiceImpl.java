package cn.cestc.os.desktop.service.impl;


import cn.cestc.os.desktop.mapper.SettingMapper;
import cn.cestc.os.desktop.model.SettingModel;
import cn.cestc.os.desktop.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Throwable.class)
public class SettingServiceImpl implements SettingService
{

    @Autowired
    private SettingMapper settingMapper;

    public Integer insert(SettingModel settingModel)
    {
        return settingMapper.insert(settingModel);
    }

    public Integer delete(SettingModel settingModel)
    {
        return settingMapper.delete(settingModel);
    }

    public Integer updateById(SettingModel settingModel)
    {
        return settingMapper.updateById(settingModel);
    }

    public List<SettingModel> selectByCondition(SettingModel settingModel)
    {
        return settingMapper.selectByCondition(settingModel);
    }

}