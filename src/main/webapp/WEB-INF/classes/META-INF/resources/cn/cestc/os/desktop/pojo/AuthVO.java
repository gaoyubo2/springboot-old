package cn.cestc.os.desktop.pojo;

import java.io.Serializable;


/**
 * Description:功能权限VO
 *
 * @author bo.xu
 * 2015年7月29日 上午10:25:12
 */
public class AuthVO implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer functionId;//编码

    private String name;//显示名称

    private String comment;//描述

    private Integer parentId;//父节点code

    private Integer seq;//排序

    private Integer status;//状态 -1：删除，0：正常

    private String createTime;//创建时间

    private String updateTime;//更新时间


    private Boolean open = true;//节点默认开启

    private Boolean checked = false;//节点默认不选中

    public Boolean getChecked()
    {
        return checked;
    }

    public void setChecked(Boolean checked)
    {
        this.checked = checked;
    }

    public Boolean getOpen()
    {
        return open;
    }

    public void setOpen(Boolean open)
    {
        this.open = open;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getFunctionId()
    {
        return functionId;
    }

    public void setFunctionId(Integer functionId)
    {
        this.functionId = functionId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public Integer getSeq()
    {
        return seq;
    }

    public void setSeq(Integer seq)
    {
        this.seq = seq;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

}
