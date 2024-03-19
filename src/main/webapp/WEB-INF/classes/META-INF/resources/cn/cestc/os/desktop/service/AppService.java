package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.AppModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description:UserService类
 *
 * @author xubo
 * 2015年6月3日 下午2:34:43
 */

public interface AppService
{

    /**
     * Description: 新增
     *
     * @param
     * @return
     */
    Integer insert(AppModel appModel);

    /**
     * Description: 删除
     *
     * @param
     * @return
     */
    Integer delete(AppModel appModel);

    /**
     * Description: 通过id修改
     *
     * @param
     * @return
     */
    Integer updateById(AppModel appModel);

    /**
     * Description: 通过条件查询
     *
     * @param
     * @return
     */
    List<AppModel> selectByCondition(AppModel appModel);

    /**
     * 根据应用ID查询
     *
     * @param
     * @return
     */
    List<AppModel> selectByCondition(int member_id, String orderBy, String like, int app_category_id, int verifytype, String appByUsername);

    /**
     * Description: 根据条件查询记录数
     *
     * @param
     * @return
     */
    int getCount(AppModel appModel);

    /**
     * 通过执行sql，修改数据
     *
     * @param sql
     * @return
     */
    Integer update(String sql);

    List<AppModel> selectByConditions(String string);

    int getCounts(String sql);

    /**
     * 通过执行上传文件并生成文件夹应用
     *
     * @param request
     * @return
     */
    Integer uploadfileAndBuildApp(HttpServletRequest request);

    /**
     * 插入app
     *
     * @param sql
     */
    void insertApp(String sql);

    /**
     * 修改
     *
     * @param sql
     */
    void updateApp(String sql);


    List<String> selectByUserName(Integer appId);

}
