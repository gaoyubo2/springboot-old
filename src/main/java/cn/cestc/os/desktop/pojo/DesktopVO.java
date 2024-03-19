package cn.cestc.os.desktop.pojo;


import cn.cestc.os.desktop.model.MemberAppModel;

import java.io.Serializable;
import java.util.List;


/**
 * Description:桌面初始化vo
 *
 * @author bo.xu
 * 2015年7月29日 下午12:47:58
 */

public class DesktopVO implements Serializable
{

    private static final long serialVersionUID = 1L;

    private List<MemberAppModel> dock;

    private List<MemberAppModel> desk1;

    private List<MemberAppModel> desk2;

    private List<MemberAppModel> desk3;

    private List<MemberAppModel> desk4;

    private List<MemberAppModel> desk5;

    private List<DeskTopFolerAppVO> folder;

    public List<MemberAppModel> getDock()
    {
        return dock;
    }

    public void setDock(List<MemberAppModel> dock)
    {
        this.dock = dock;
    }

    public List<MemberAppModel> getDesk1()
    {
        return desk1;
    }

    public void setDesk1(List<MemberAppModel> desk1)
    {
        this.desk1 = desk1;
    }

    public List<MemberAppModel> getDesk2()
    {
        return desk2;
    }

    public void setDesk2(List<MemberAppModel> desk2)
    {
        this.desk2 = desk2;
    }

    public List<MemberAppModel> getDesk3()
    {
        return desk3;
    }

    public void setDesk3(List<MemberAppModel> desk3)
    {
        this.desk3 = desk3;
    }

    public List<MemberAppModel> getDesk4()
    {
        return desk4;
    }

    public void setDesk4(List<MemberAppModel> desk4)
    {
        this.desk4 = desk4;
    }

    public List<MemberAppModel> getDesk5()
    {
        return desk5;
    }

    public void setDesk5(List<MemberAppModel> desk5)
    {
        this.desk5 = desk5;
    }

    public List<DeskTopFolerAppVO> getFolder()
    {
        return folder;
    }

    public void setFolder(List<DeskTopFolerAppVO> folder)
    {
        this.folder = folder;
    }

}
