package cn.cestc.os.desktop.model;

import java.io.Serializable;

/**
 * Description:tb_wallpaper表
 *
 * @author bo.xu
 * 2015年7月20日 下午2:43:45
 */
public class WallpaperModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer tbid;

    private String title;//壁纸名称

    private String url;//壁纸地址

    private Integer width;//壁纸宽度

    private Integer height;//壁纸高度

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
        this.title = title == null ? null : title.trim();
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url == null ? null : url.trim();
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }
}