package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.PwallpaperModel;
import cn.cestc.os.desktop.pojo.PwallpaperUploadVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Description:PwallpaperService类
 *
 * @author bo.xu
 * 2015年7月20日 下午7:36:13
 */

public interface PwallpaperService
{
    /**
     * Description: 新增
     *
     * @param pwallpaperModel
     * @return
     */
    Integer insert(PwallpaperModel pwallpaperModel);

    /**
     * Description: 删除
     *
     * @param pwallpaperModel
     * @return
     */
    Integer delete(PwallpaperModel pwallpaperModel);

    /**
     * Description: 通过tbid修改
     *
     * @param pwallpaperModel
     * @return
     */
    Integer updateById(PwallpaperModel pwallpaperModel);

    /**
     * Description: 通过条件查询
     *
     * @param pwallpaperModel
     * @return
     */
    List<PwallpaperModel> selectByCondition(PwallpaperModel pwallpaperModel);

    /**
     * Description: 上传壁纸
     *
     * @param pwallpaperModel
     * @return
     */
    PwallpaperUploadVO uploadPwallpaper(HttpServletRequest request);
}