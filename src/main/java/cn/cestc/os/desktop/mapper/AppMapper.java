package cn.cestc.os.desktop.mapper;


import cn.cestc.os.desktop.model.AppModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:appMapper类
 * <p>
 * 2015年7月17日 下午1:16:22
 */

@Repository
public interface AppMapper
{

    /**
     * Description: 新增
     */
    Integer insert(AppModel appModel);

    /**
     * Description: 删除
     */
    Integer delete(AppModel appModel);

    /**
     * Description: 通过tbid修改
     */
    Integer updateById(AppModel appModel);

    /**
     * Description: 通过条件查询
     */
    List<AppModel> selectByCondition(AppModel appModel);

    List<AppModel> selectByCondition_(@Param("member_id") int member_id, @Param("orderBy") String orderBy,
                                      @Param("keyword") String keyword, @Param("app_category_id") int app_category_id,
                                      @Param("verifytype") int verifytype, @Param("appByUsername") String appByUsername);

    /**
     * Description: 根据条件查询记录数
     */
    int getCount(AppModel appModel);

    /**
     * Description: 修改今日推荐
     */
    Integer update(@Param("sql") String sql);

    /**
     * Description: 根据条件查询
     */
    List<AppModel> selectByConditions(@Param("sql") String sql);

    /**
     * Description: 根据条件查询数量
     */
    int getCounts(@Param("sql") String sql);

    /**
     * 插入应用
     *
     * @param sql
     */
    void insertApp(@Param("sql") String sql);

    /**
     * 修改应用
     *
     * @param sql
     */
    void updateApp(@Param("sql") String sql);


    List<String> selectByUserName(@Param("appId")Integer appId);

    //获取所有的app相关数据
    @Select("select * from tb_app;")
    List<AppModel> getApps();
}
