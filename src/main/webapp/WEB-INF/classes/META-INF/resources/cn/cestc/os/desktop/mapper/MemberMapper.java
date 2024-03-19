package cn.cestc.os.desktop.mapper;


import cn.cestc.os.desktop.model.MemberModel;

import java.util.List;

/**
 * Description:MemberMapperl类
 * <p>
 * 2015年7月20日 下午7:32:23
 */


public interface MemberMapper
{
    /**
     * Description: 新增
     *
     * @param memberModel
     */
    Integer insert(MemberModel memberModel);

    /**
     * Description: 删除
     *
     * @param memberModel
     */
    Integer delete(MemberModel memberModel);

    /**
     * Description: 通过tbid修改
     *
     * @param memberModel
     */
    Integer updateById(MemberModel memberModel);

    /**
     * Description: 通过用户名修改
     *
     * @param memberModel
     */
    Integer updateByUsername(MemberModel memberModel);

    /**
     * Description: 通过条件查询
     *
     * @param memberModel
     */
    List<MemberModel> selectByCondition(MemberModel memberModel);
}