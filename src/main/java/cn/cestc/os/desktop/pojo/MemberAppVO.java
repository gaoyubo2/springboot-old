package cn.cestc.os.desktop.pojo;

import java.io.Serializable;

/**
 * Description:桌面应用vo
 *
 * @author bo.xu
 * 2015年7月29日 上午10:25:12
 */
public class MemberAppVO implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer id;//主键

    private String val_type;//类型

    private String val_url;//地址

    private Integer desk;

    private String val_name;//图标名称

    private String val_icon;//图标图片

    private Integer val_width;//窗口宽度

    private Integer val_height;//窗口高度

    private Integer val_isresize;//是否能对窗口进行拉伸

    private Integer val_isopenmax;//是否打开直接最大化

    private Integer val_isflash;//是否为flash应用

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getVal_name()
    {
        return val_name;
    }

    public void setVal_name(String val_name)
    {
        this.val_name = val_name;
    }

    public String getVal_icon()
    {
        return val_icon;
    }

    public void setVal_icon(String val_icon)
    {
        this.val_icon = val_icon;
    }

    public Integer getVal_width()
    {
        return val_width;
    }

    public void setVal_width(Integer val_width)
    {
        this.val_width = val_width;
    }

    public Integer getVal_height()
    {
        return val_height;
    }

    public void setVal_height(Integer val_height)
    {
        this.val_height = val_height;
    }

    public Integer getVal_isresize()
    {
        return val_isresize;
    }

    public void setVal_isresize(Integer val_isresize)
    {
        this.val_isresize = val_isresize;
    }

    public Integer getVal_isopenmax()
    {
        return val_isopenmax;
    }

    public void setVal_isopenmax(Integer val_isopenmax)
    {
        this.val_isopenmax = val_isopenmax;
    }

    public Integer getVal_isflash()
    {
        return val_isflash;
    }

    public void setVal_isflash(Integer val_isflash)
    {
        this.val_isflash = val_isflash;
    }

    public String getVal_type()
    {
        return val_type;
    }

    public void setVal_type(String val_type)
    {
        this.val_type = val_type;
    }

    public String getVal_url()
    {
        return val_url;
    }

    public void setVal_url(String val_url)
    {
        this.val_url = val_url;
    }

    public Integer getDesk()
    {
        return desk;
    }

    public void setDesk(Integer desk)
    {
        this.desk = desk;
    }

}
