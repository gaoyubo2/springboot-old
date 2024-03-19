package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.MemberAppModel;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.pojo.DesktopVO;
import cn.cestc.os.desktop.pojo.MoveAppVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * Description:MemberAppService类
 *
 * @author bo.xu
 * 2015年7月20日 下午7:35:25
 */

public interface MemberAppService
{
    /**
     * Description: 新增
     *
     * @param memberAppModel
     * @return
     */
    Integer insert(MemberAppModel memberAppModel);

    /**
     * Description: 删除
     *
     * @param memberAppModel
     * @return
     */
    Integer delete(MemberAppModel memberAppModel);

    /**
     * Description: 通过tbid修改
     *
     * @param memberAppModel
     * @return
     */
    Integer updateById(MemberAppModel memberAppModel);

    /**
     * Description: 通过条件查询
     *
     * @param memberAppModel
     * @return
     */
    List<MemberAppModel> selectByCondition(MemberAppModel memberAppModel);

    /**
     * 根据会员ID查询会员所有应用
     *
     * @param member_id
     * @return
     */
    List<MemberAppModel> selectByMemberId(int member_id);

    /**
     * Description: 通过用户名查询应用
     *
     * @param userModel
     * @return
     */
    DesktopVO selectByUsername(String username, HttpServletRequest req);

    /**
     * Description: 通过用户名查询用户无权限的应用名称符串（按,隔开）
     *
     * @param userModel
     * @return
     */
    // String selectNoAuthByUsername(String username, HttpServletRequest req);


    /*
     * @Author 关玉珍
     * @Description  获取有权限的app应用列表
     * @Date 12:16 2022/1/17
     * @Param [username, req]
     * @return java.lang.String
     **/
    List<String> selectAppByUsername(String username, HttpServletRequest req);

    /**
     * Description: 根据用户应用表id和类型以及用户名称查询应用详细信息
     *
     * @param Integer Memberappid,String type,String username
     * @return
     */
    MemberAppModel getAppByMemberappidAndTypeAndUsername(Integer Memberappid, String type, String username);

    /**
     * Description: 根据用户应用表id下载文件类型应用
     *
     * @param Integer appid,HttpServletRequest request,HttpServletResponse response
     * @return
     */
    void downloadfileappByid(Integer appid, HttpServletRequest request, HttpServletResponse response);

    /**
     * 上传文件并生成文件应用
     *
     * @param request
     * @return
     */
    Integer uploadfileAndBuildApp(HttpServletRequest request, Integer desk);

    /**
     * 生成文件夹应用
     *
     * @param request
     * @return
     */
    Integer builFolderApp(String userName, String name, String icon, Integer desk);

    /**
     * 生成Pwindow或者Pwidget类型应用
     *
     * @param request
     * @return
     */
    Integer builPwindowOrPwidgetApp(MemberAppModel memberAppModel, Integer desk, String userName);


    /**
     * 删除应用
     *
     * @param request
     * @return
     */
    Integer deleteMyApp(String username, Integer appid) throws Exception;

    /**
     * 删除应用
     *
     * @param 上传桌面应用图片
     * @return
     */

    Map<String, Object> uploadImg(HttpServletRequest request);

    /**
     * Description:桌面移动app
     *
     * @return
     * @paramString MemberModel memberModel,MoveAppVO moveAppVO
     */
    Integer moveMyApp(MemberModel memberModel, MoveAppVO moveAppVO) throws Exception;

    /**
     * Description:查看用户应用是否存在
     *
     * @return
     * @paramString MemberModel memberModel,MoveAppVO moveAppVO
     */
    String memAppIsExist(Integer memAppId, String username);

    /**
     * Description: 通过无权限的应用名称删除对应用户应用
     *
     * @param appIds noAuthByUsernames
     * @return
     */
    Integer deletBynoAuthByUsernames(Integer memberId, String appByUsername);

}