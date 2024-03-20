package cn.cestc.os.desktop.service;


import cn.cestc.os.desktop.model.AppModel;
import cn.cestc.os.desktop.model.MemberModel;

import java.util.List;

public interface DateManageService {
    List<AppModel> getApps();

    List<MemberModel> getRoles();
}
