package cn.cestc.os.desktop.pojo;


import cn.cestc.os.desktop.model.MemberAppModel;

import java.io.Serializable;
import java.util.List;


/**
 * Description:桌面文件夹应用vo
 *
 * @author bo.xu
 * 2015年7月29日 下午1:28:57
 */

public class DeskTopFolerAppVO implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer appid;

    private List<MemberAppModel> apps;

    public Integer getAppid()
    {
        return appid;
    }

    public void setAppid(Integer appid)
    {
        this.appid = appid;
    }

    public List<MemberAppModel> getApps()
    {
        return apps;
    }

    public void setApps(List<MemberAppModel> apps)
    {
        this.apps = apps;
    }


}
