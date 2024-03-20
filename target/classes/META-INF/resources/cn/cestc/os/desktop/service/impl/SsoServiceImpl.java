package cn.cestc.os.desktop.service.impl;

import cn.cestc.os.desktop.model.Result;
import cn.cestc.os.desktop.model.manage.User;
import cn.cestc.os.desktop.service.SsoService;
import cn.dev33.satoken.session.SaSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Service
public class SsoServiceImpl implements SsoService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${sso.ifLoginUrl}")
    private String ifLoginUrl;

    @Value("${sso.loginUrl}")
    private String loginUrl;

    @Value("${sso.logoutUrl}")
    private String logoutUrl;

    @Value("${sso.getSessionUrl}")
    private String getSessionUrl;

    @Value("${sso.getPermissionsUrl}")
    private String getPermissionsUrl;

    @Value("${sso.getUserUrl}")
    private String getUserUrl;


    @Override
    public Result<Integer> login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);
        return restTemplate.postForObject(loginUrl,requestEntity,Result.class,username,password);
    }

    @Override
    public Result<SaSession> getSession(String username, String password) {
        return restTemplate.getForObject(getSessionUrl, Result.class, username, password);
    }

    @Override
    public Result<List<String>> getPermissionList(Integer uid) {
        return restTemplate.getForObject(getPermissionsUrl, Result.class,uid);
    }

    @Override
    public Result<Boolean> logout(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token",request.getHeader("token"));
        HttpEntity requestEntity = new HttpEntity(headers);
        return restTemplate.postForObject(logoutUrl, requestEntity, Result.class);
    }

    @Override
    public Result<Boolean> ifLogin(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("token",request.getHeader("token"));
        HttpEntity requestEntity = new HttpEntity(headers);
        return restTemplate.exchange(ifLoginUrl, HttpMethod.GET,requestEntity,Result.class).getBody();
    }

    @Override
    public Result<User> getUser(Integer uid) {
        return restTemplate.getForObject(getUserUrl, Result.class,uid);
    }
}
