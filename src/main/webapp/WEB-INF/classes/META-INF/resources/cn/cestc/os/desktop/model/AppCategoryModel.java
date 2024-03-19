package cn.cestc.os.desktop.model;

import java.io.Serializable;


/**
 * Description:tb_app_category表
 *
 * @author bo.xu
 * 2015年7月20日 上午10:11:46
 */

public class AppCategoryModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer tbid;//主键

    private String name;//名称

    private Integer issystem;//是否为系统类目，1（是）0（否）

    public Integer getTbid()
    {
        return tbid;
    }

    public void setTbid(Integer tbid)
    {
        this.tbid = tbid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getIssystem()
    {
        return issystem;
    }

    public void setIssystem(Integer issystem)
    {
        this.issystem = issystem;
    }


}
