package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.pojo.SkinVO;

import java.io.File;
import java.util.List;

/**
 * Description:皮肤设置service
 *
 * @author bo.xu
 * 2015年8月4日 下午3:59:14
 */

public interface SkinService
{

    /**
     * Description:通过文件夹查询所有皮肤信息
     *
     * @param
     * @return
     */
    List<SkinVO> selectAllSkinVOs(File dir);

}
