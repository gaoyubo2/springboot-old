package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.model.manage.RoleWithMembersAndAppsVO;

import java.util.List;


/**
 * Description:MemberService类
 *
 * @author bo.xu
 * 2015年7月20日 下午7:35:43
 */

public interface MemberService
{
    /**
     * Description: 新增
     *
     * @param memberModel
     * @return
     */
    Integer insert(MemberModel memberModel);

    /**
     * Description: 删除
     *
     * @param memberModel
     * @return
     */
    Integer delete(MemberModel memberModel);

    /**
     * Description: 通过tbid修改
     *
     * @param memberModel
     * @return
     */
    Integer updateById(MemberModel memberModel);

    /**
     * Description: 通过用户名修改
     *
     * @param memberModel
     * @return
     */
    Integer updateByUsername(MemberModel memberModel);

    /**
     * Description: 通过条件查询
     *
     * @param memberModel
     * @return
     */
    List<MemberModel> selectByCondition(MemberModel memberModel);

    public MemberModel selectByUserName(String userName);

    /**
     * Description: 通过用户名去新建或者更新用户表
     */
    int saveMemberOnMemberIsNotExist(String username);

    /**
     * Description:通过用户名设置壁纸
     */
    int setWallpaperByUsername(Integer wpstate, String wptype, String wp, String username);

    /**
     * Description:通过用户名获取壁纸
     *
     * @return
     * @paramString username
     */
    String getWallpaperByUsername(String username);

    /**
     * Description:通过用户,desk,用户应用id去更新desk的值
     *
     * @return
     * @paramString MemberModel memberModel,Integer memberappid,Integer desk
     */
    Integer updateSomedeskByMemberidAndMemapid(MemberModel memberModel, Integer memberappid, Integer desk);


}