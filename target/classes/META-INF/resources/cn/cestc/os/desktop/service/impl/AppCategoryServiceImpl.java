package cn.cestc.os.desktop.service.impl;


import cn.cestc.os.desktop.mapper.AppCategoryMapper;
import cn.cestc.os.desktop.model.AppCategoryModel;
import cn.cestc.os.desktop.service.AppCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Throwable.class)
public class AppCategoryServiceImpl implements AppCategoryService
{

    @Autowired
    private AppCategoryMapper appCategoryMapper;

    public Integer insert(AppCategoryModel appCategoryModel)
    {
        return appCategoryMapper.insert(appCategoryModel);
    }

    public Integer delete(AppCategoryModel appCategoryModel)
    {
        return appCategoryMapper.delete(appCategoryModel);
    }

    public Integer updateById(AppCategoryModel appCategoryModel)
    {
        return appCategoryMapper.updateById(appCategoryModel);
    }

    public List<AppCategoryModel> selectByCondition(
            AppCategoryModel appCategoryModel)
    {
        return appCategoryMapper.selectByCondition(appCategoryModel);
    }

    public Integer selectPageCount(String keyword)
    {
        return appCategoryMapper.selectPageCount(keyword);
    }


    public List<AppCategoryModel> selectPageByName(String keyword, Integer currentPage, Integer pageSize)
    {
        return appCategoryMapper.selectPageByName(keyword, currentPage, pageSize);
    }


    public Integer updateAppCategoryId(Integer appCategoryId, Integer tbid)
    {
        return appCategoryMapper.updateAppCategoryId(appCategoryId, tbid);
    }
}