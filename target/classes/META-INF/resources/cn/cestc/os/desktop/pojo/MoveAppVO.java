package cn.cestc.os.desktop.pojo;

import java.io.Serializable;

/**
 * Description:移动app  vo
 *
 * @author bo.xu
 * 2015年8月10日 上午10:36:21
 */
public class MoveAppVO implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String movetype;

    private Integer from;

    private Integer to;

    private String boa;

    private Integer desk;

    private Integer fromdesk;

    private Integer todesk;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getMovetype()
    {
        return movetype;
    }

    public void setMovetype(String movetype)
    {
        this.movetype = movetype;
    }

    public Integer getFrom()
    {
        return from;
    }

    public void setFrom(Integer from)
    {
        this.from = from;
    }

    public Integer getTo()
    {
        return to;
    }

    public void setTo(Integer to)
    {
        this.to = to;
    }

    public String getBoa()
    {
        return boa;
    }

    public void setBoa(String boa)
    {
        this.boa = boa;
    }

    public Integer getDesk()
    {
        return desk;
    }

    public void setDesk(Integer desk)
    {
        this.desk = desk;
    }

    public Integer getFromdesk()
    {
        return fromdesk;
    }

    public void setFromdesk(Integer fromdesk)
    {
        this.fromdesk = fromdesk;
    }

    public Integer getTodesk()
    {
        return todesk;
    }

    public void setTodesk(Integer todesk)
    {
        this.todesk = todesk;
    }


}
