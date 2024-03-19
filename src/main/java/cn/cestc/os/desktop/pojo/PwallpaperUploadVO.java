package cn.cestc.os.desktop.pojo;

import java.io.Serializable;


/**
 * Description:自定义桌面vo
 *
 * @author bo.xu
 * 2015年8月4日 下午3:50:03
 */
public class PwallpaperUploadVO implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String tbid;
    private String surl;
    private String url;
    private String fileType;
    private String original;
    private String state;

    public String getTbid()
    {
        return tbid;
    }

    public void setTbid(String tbid)
    {
        this.tbid = tbid;
    }

    public String getSurl()
    {
        return surl;
    }

    public void setSurl(String surl)
    {
        this.surl = surl;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getOriginal()
    {
        return original;
    }

    public void setOriginal(String original)
    {
        this.original = original;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

}
