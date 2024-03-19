package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.WallpaperModel;

import java.util.List;


/**
 * Description:WallpaperService类
 *
 * @author bo.xu
 * 2015年7月20日 下午7:36:46
 */

public interface WallpaperService
{
    /**
     * Description: 新增
     *
     * @param wallpaperModel
     * @return
     */
    Integer insert(WallpaperModel wallpaperModel);

    /**
     * Description: 删除
     *
     * @param wallpaperModel
     * @return
     */
    Integer delete(WallpaperModel wallpaperModel);

    /**
     * Description: 通过tbid修改
     *
     * @param wallpaperModel
     * @return
     */
    Integer updateById(WallpaperModel wallpaperModel);

    /**
     * Description: 通过条件查询
     *
     * @param wallpaperModel
     * @return
     */
    List<WallpaperModel> selectByCondition(WallpaperModel wallpaperModel);

}