package cn.cestc.os.desktop.service.impl;


import cn.cestc.os.desktop.mapper.WallpaperMapper;
import cn.cestc.os.desktop.model.WallpaperModel;
import cn.cestc.os.desktop.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Throwable.class)
public class WallpaperServiceImpl implements WallpaperService
{

    @Autowired
    private WallpaperMapper wallpaperMapper;

    public Integer insert(WallpaperModel wallpaperModel)
    {
        return wallpaperMapper.insert(wallpaperModel);
    }

    public Integer delete(WallpaperModel wallpaperModel)
    {
        return wallpaperMapper.delete(wallpaperModel);
    }

    public Integer updateById(WallpaperModel wallpaperModel)
    {
        return wallpaperMapper.updateById(wallpaperModel);
    }

    public List<WallpaperModel> selectByCondition(WallpaperModel wallpaperModel)
    {
        return wallpaperMapper.selectByCondition(wallpaperModel);
    }

}