package cn.cestc.os.desktop.service;

import cn.cestc.os.desktop.model.Result;
import cn.dev33.satoken.session.SaSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public interface SsoService {

    Result<Integer> login(String username, String password);

    Result<SaSession> getSession( String username, String password);

    Result<List<String>> getPermissionList(Integer uid);

    Result<Boolean> logout(HttpServletRequest request);

    Result<Boolean> ifLogin(HttpServletRequest request);
}
