package cn.cestc.os.desktop.service.impl;


import cn.cestc.os.desktop.mapper.AppMapper;
import cn.cestc.os.desktop.model.AppModel;
import cn.cestc.os.desktop.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AppServiceImpl implements AppService {

    @Autowired
    private AppMapper appMapper;

    public Integer insert(AppModel appModel) {
        return appMapper.insert(appModel);
    }

    public Integer delete(AppModel appModel) {
        return appMapper.delete(appModel);
    }

    public Integer updateById(AppModel appModel) {
        return appMapper.updateById(appModel);
    }

    public List<AppModel> selectByCondition(AppModel appModel) {
        return appMapper.selectByCondition(appModel);
    }

    public List<AppModel> selectByCondition(int member_id, String orderBy, String like, int app_category_id, int verifytype, String appByUsername) {
        return appMapper.selectByCondition_(member_id, orderBy, like, app_category_id, verifytype, appByUsername);
    }

    @Override
    public int getCount(AppModel appModel) {
        return appMapper.getCount(appModel);
    }

    @Override
    public Integer update(String sql) {
        return appMapper.update(sql);
    }

    @Override
    public List<AppModel> selectByConditions(String sql) {
        return appMapper.selectByConditions(sql);
    }

    @Override
    public int getCounts(String sql) {
        return appMapper.getCounts(sql);
    }

    @Override
    public void insertApp(String sql) {
        appMapper.insertApp(sql);
    }

    @Override
    public void updateApp(String sql) {
        appMapper.updateApp(sql);

    }

    @Override
    public List<String> selectByUserNameOld(Integer appId) {
        return appMapper.selectByUserName(appId);
    }
    @Override
    public List<String> selectByUserName(Integer appId) {
        return appMapper.getAllAppName();
    }

    @Override
    public Integer uploadfileAndBuildApp(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

}
