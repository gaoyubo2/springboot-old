package cn.cestc.os.desktop.model;

import java.io.Serializable;


/**
 * Description:tb_app_star表
 *
 * @author bo.xu
 * 2015年7月20日 上午10:20:32
 */

public class AppStarModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer tbid;//主键

    private Integer app_id;//应用id

    private Integer member_id;//用户id

    private Integer starnum;//评分

    private String dt;//操作时间


    public Integer getTbid()
    {
        return tbid;
    }

    public void setTbid(Integer tbid)
    {
        this.tbid = tbid;
    }

    public Integer getApp_id()
    {
        return app_id;
    }

    public void setApp_id(Integer app_id)
    {
        this.app_id = app_id;
    }

    public Integer getMember_id()
    {
        return member_id;
    }

    public void setMember_id(Integer member_id)
    {
        this.member_id = member_id;
    }

    public Integer getStarnum()
    {
        return starnum;
    }

    public void setStarnum(Integer starnum)
    {
        this.starnum = starnum;
    }

    public String getDt()
    {
        return dt;
    }

    public void setDt(String dt)
    {
        this.dt = dt;
    }


}
