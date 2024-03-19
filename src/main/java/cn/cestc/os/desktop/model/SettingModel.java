package cn.cestc.os.desktop.model;

import java.io.Serializable;

/**
 * Description:tb_setting表
 *
 * @author bo.xu
 * 2015年7月20日 下午2:30:23
 */

public class SettingModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer tbid;//主键

    private String title;//网站标题

    private String description;//网站描述

    private String keywords;//网站关键字

    private Integer isforcedlogin;//是否开启强制登录，1开启0不开启


    public Integer getTbid()
    {
        return tbid;
    }

    public void setTbid(Integer tbid)
    {
        this.tbid = tbid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public Integer getIsforcedlogin()
    {
        return isforcedlogin;
    }

    public void setIsforcedlogin(Integer isforcedlogin)
    {
        this.isforcedlogin = isforcedlogin;
    }


}