package cn.cestc.os.desktop.mapper;


import cn.cestc.os.desktop.model.PwallpaperModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:PwallpaperMapper类
 * <p>
 * 2015年7月20日 下午7:32:58
 */

@Repository
public interface PwallpaperMapper
{
    /**
     * Description: 新增
     *
     * @param pwallpaperModel
     */
    Integer insert(PwallpaperModel pwallpaperModel);

    /**
     * Description: 删除
     *
     * @param pwallpaperModel
     */
    Integer delete(PwallpaperModel pwallpaperModel);

    /**
     * Description: 通过tbid修改
     *
     * @param pwallpaperModel
     */
    Integer updateById(PwallpaperModel pwallpaperModel);

    /**
     * Description: 通过条件查询
     *
     * @param pwallpaperModel
     */
    List<PwallpaperModel> selectByCondition(PwallpaperModel pwallpaperModel);
}