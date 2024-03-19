package cn.cestc.os.desktop.pojo;

import java.io.Serializable;


/**
 * Description:皮肤vo
 *
 * @author BFD_506
 * 2015年8月4日 下午3:49:50
 */
public class SkinVO implements Serializable
{

    private static final long serialVersionUID = 1L;

    private String name;
    private String img;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }


}
