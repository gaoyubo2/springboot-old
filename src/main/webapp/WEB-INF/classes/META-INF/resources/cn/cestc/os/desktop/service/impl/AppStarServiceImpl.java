package cn.cestc.os.desktop.service.impl;


import cn.cestc.os.desktop.mapper.AppStarMapper;
import cn.cestc.os.desktop.model.AppStarModel;
import cn.cestc.os.desktop.service.AppStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AppStarServiceImpl implements AppStarService
{

    @Autowired
    private AppStarMapper appStarMapper;

    public Integer insert(AppStarModel appStarModel)
    {
        return appStarMapper.insert(appStarModel);
    }

    public Integer delete(AppStarModel appStarModel)
    {
        return appStarMapper.delete(appStarModel);
    }

    public Integer updateById(AppStarModel appStarModel)
    {
        return appStarMapper.updateById(appStarModel);
    }

    public List<AppStarModel> selectByCondition(AppStarModel appStarModel)
    {
        return appStarMapper.selectByCondition(appStarModel);
    }

    public double getAvgStarNum(int app_id)
    {
        return appStarMapper.getAvgStarNum(app_id);
    }
}