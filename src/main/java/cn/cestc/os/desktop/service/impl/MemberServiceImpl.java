package cn.cestc.os.desktop.service.impl;


import cn.cestc.os.desktop.mapper.MemberMapper;
import cn.cestc.os.desktop.mapper.PwallpaperMapper;
import cn.cestc.os.desktop.mapper.WallpaperMapper;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.model.PwallpaperModel;
import cn.cestc.os.desktop.model.WallpaperModel;
import cn.cestc.os.desktop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Throwable.class)
public class MemberServiceImpl implements MemberService
{

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private WallpaperMapper wallpaperMapper;
    @Autowired
    private PwallpaperMapper pwallpaperMapper;

    public Integer insert(MemberModel memberModel)
    {
        return memberMapper.insert(memberModel);
    }

    public Integer delete(MemberModel memberModel)
    {
        return memberMapper.delete(memberModel);
    }

    public Integer updateById(MemberModel memberModel)
    {
        return memberMapper.updateById(memberModel);
    }

    @Override
    public Integer updateByUsername(MemberModel memberModel)
    {
        return memberMapper.updateByUsername(memberModel);
    }

    public List<MemberModel> selectByCondition(MemberModel memberModel)
    {
        return memberMapper.selectByCondition(memberModel);
    }

    public MemberModel selectByUserName(String userName)
    {
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        return memberMapper.selectByCondition(memberModel).get(0);
    }

    @Override
    public int saveMemberOnMemberIsNotExist(String username)
    {
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(username);
        List<MemberModel> list = memberMapper.selectByCondition(memberModel);
        //默认的用户基本设置
        if (list.size() == 0)
        {
            memberModel.setSkin("default");
            memberModel.setAppxy("x");
            memberModel.setDesk(1);
            memberModel.setDockpos("right");
            memberModel.setAppsize(48);
            memberModel.setApphorizontalspacing(50);
            memberModel.setAppverticalspacing(50);
            memberModel.setWallpaperId(1);
            memberModel.setWallpaperstate(1);
            memberModel.setWallpapertype("lashen");
            return memberMapper.insert(memberModel);
        }
        return 1;
    }

    @Override
    public int setWallpaperByUsername(Integer wpstate, String wptype,
                                      String wp, String username)
    {
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(username);
        if (wpstate != 0)
        {
            memberModel.setWallpaperstate(wpstate);
        }
        memberModel.setWallpapertype(wptype);
        //当为系统壁纸或者自定义壁纸的时候
        if (wpstate == 1 || wpstate == 2)
        {
            if (wp != null && !wp.equals("") && !wp.equals("0"))
            {
                memberModel.setWallpaperId(Integer.valueOf(wp));
            }
            //当为网络壁纸的时候
        } else if (wpstate == 3)
        {
            if (wp != null && !wp.equals(""))
            {
                memberModel.setWallpaperwebsite(wp);
            }
        }
        return memberMapper.updateByUsername(memberModel);
    }

    @Override
    public String getWallpaperByUsername(String username)
    {
        String result = "";
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(username);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);
        if (memberModel.getWallpaperstate() != null)
        {
            //当为系统壁纸的时候
            if (memberModel.getWallpaperstate() == 1)
            {
                WallpaperModel wallpaperModel = new WallpaperModel();
                wallpaperModel.setTbid(memberModel.getWallpaperId());
                wallpaperModel = wallpaperMapper.selectByCondition(wallpaperModel).get(0);
                result = memberModel.getWallpaperstate() + "<{|}>" + wallpaperModel.getUrl() + "<{|}>"
                        + memberModel.getWallpapertype() + "<{|}>" + wallpaperModel.getWidth() + "<{|}>" + wallpaperModel.getHeight();
                //当为自定义壁纸的时候
            } else if (memberModel.getWallpaperstate() == 2)
            {
                PwallpaperModel pwallpaperModel = new PwallpaperModel();
                pwallpaperModel.setTbid(memberModel.getWallpaperId());
                pwallpaperModel = pwallpaperMapper.selectByCondition(pwallpaperModel).get(0);
                result = memberModel.getWallpaperstate() + "<{|}>" + pwallpaperModel.getUrl() + "<{|}>"
                        + memberModel.getWallpapertype() + "<{|}>" + pwallpaperModel.getWidth() + "<{|}>" + pwallpaperModel.getHeight();
                //当为网络壁纸的时候
            } else if (memberModel.getWallpaperstate() == 2)
            {
                WallpaperModel wallpaperModel = new WallpaperModel();
                wallpaperModel.setTbid(memberModel.getWallpaperId());
                wallpaperModel = wallpaperMapper.selectByCondition(wallpaperModel).get(0);
                result = memberModel.getWallpaperstate() + "<{|}>" + memberModel.getWallpaperwebsite();
            }
        }
        return result;
    }

    @Override
    public Integer updateSomedeskByMemberidAndMemapid(MemberModel memberModel,
                                                      Integer memberappid, Integer desk)
    {
        try
        {
            //利用反射将对应的desk的id添加进数据库
            String olddeskmessage = (String) memberModel.getClass().getDeclaredMethod("getDesk" + desk).invoke(memberModel);
            if (olddeskmessage == null || olddeskmessage.equals("") || olddeskmessage.toLowerCase().equals("null"))
            {
                memberModel.getClass().getDeclaredMethod("setDesk" + desk, String.class).invoke(
                        memberModel, memberappid + "");
            } else
            {
                memberModel.getClass().getDeclaredMethod("setDesk" + desk, String.class).invoke(
                        memberModel, olddeskmessage + "," + memberappid);
            }

            memberMapper.updateById(memberModel);
        } catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
        return 1;
    }
}