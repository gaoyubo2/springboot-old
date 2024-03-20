package cn.cestc.os.desktop.service.impl;

import cn.cestc.os.desktop.mapper.AppMapper;
import cn.cestc.os.desktop.mapper.MemberMapper;
import cn.cestc.os.desktop.model.AppModel;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.service.DateManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateManageServiceImpl implements DateManageService {
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private MemberMapper memberMapper;


    @Override
    public List<AppModel> getApps() {
        return appMapper.getApps();
    }

    @Override
    public List<MemberModel> getRoles() {
        return memberMapper.getRoles();
    }
}
