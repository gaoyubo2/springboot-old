package cn.cestc.os.desktop.mapper;


import cn.cestc.os.desktop.model.WallpaperModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:WallpaperMapper类
 * <p>
 * 2015年7月20日 下午7:33:24
 */

@Repository
public interface WallpaperMapper
{
    /**
     * Description: 新增
     *
     * @param wallpaperModel
     */
    Integer insert(WallpaperModel wallpaperModel);

    /**
     * Description: 删除
     *
     * @param wallpaperModel
     */
    Integer delete(WallpaperModel wallpaperModel);

    /**
     * Description: 通过tbid修改
     *
     * @param wallpaperModel
     */
    Integer updateById(WallpaperModel wallpaperModel);

    /**
     * Description: 通过条件查询
     *
     * @param wallpaperModel
     */
    List<WallpaperModel> selectByCondition(WallpaperModel wallpaperModel);
}