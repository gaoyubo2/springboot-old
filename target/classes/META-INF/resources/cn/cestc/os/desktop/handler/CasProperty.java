package cn.cestc.os.desktop.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* @Author: Zgh
* @Date: 2022/3/25
* @description: cas配置
*/
@Component
public class CasProperty {

    @Value("${cas.serverUrl}")
    public String casServer;

    @Value("${cas.client.clientId}")
    public String ClientName;

    @Value("${cas.client.clientSecret}")
    public String ClientPassword;

}
