package cn.cestc.os.desktop.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CasController
 * @Description TODO
 * @Author CYY
 * Date 2022/2/18 16:27
 * @Version 1.0
 */
@Controller
@Slf4j
public class CasController
{

    @Autowired
    RestTemplate restTemplate;
    @Value("${cas.serverUrl}")
    private String casServerUrl;
    @Value("${cas.client.clientId}")
    private String clientId;
    @Value("${cas.client.clientSecret}")
    private String clientSecret;
    @Value("${cas.appUrl}")
    private String appUrl;

    @GetMapping(value = "/code")
    public String name(@RequestParam(value = "code") String code, HttpServletResponse response)
    {
        log.info("code : {}", code);
        String url = casServerUrl + "/oauth2.0/accessToken?" +
                "grant_type=authorization_code&client_id=" +
                "{clientId}&client_secret={clientSecret}&code={code}&redirect_uri={redirectURI}";
        Map<String, Object> param = new HashMap<>();
        param.put("clientId", clientId);
        param.put("clientSecret", clientSecret);
        param.put("code", code);
        param.put("redirectURI", appUrl);
        log.info(code);
        String result = restTemplate.getForObject(url, String.class, param);
        log.info(result);
        Map hash = JSONObject.parseObject(result, Map.class);
        String token = (String) hash.get("access_token");
        String refreshToken = (String) hash.get("refresh_token");
        log.info(refreshToken);
        String profileUrl = casServerUrl + "/oauth2.0/profile?access_token=" + token;
        log.info(profileUrl);
        String userInfo = restTemplate.getForObject(profileUrl, String.class);
        response.addCookie(new Cookie("access_token", token));

        return "redirect:" + appUrl;
    }

}
