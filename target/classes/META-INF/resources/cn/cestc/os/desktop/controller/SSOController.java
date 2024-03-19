package cn.cestc.os.desktop.controller;

import cn.cestc.os.desktop.handler.CasProperty;
import cn.cestc.os.desktop.model.SysUserEntity;
import cn.cestc.os.desktop.utils.Result;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SSOController
 * @Description 单点登录端点
 * @Author ZGH
 * Date 2022/2/28 14:19
 * @Version 1.0
 */
@Slf4j
@RestController
public class SSOController {

    @Autowired
    CasProperty casProperty;

    @Autowired
    RestTemplate restTemplate;


    @GetMapping(value = "/sys/login/{code}")
    public Result login(@PathVariable(value = "code") String code) {
        Assert.notNull(code, "code不能为空");
        String url = casProperty.casServer + "/oauth2.0/accessToken?grant_type=authorization_code&client_id={clientId}&client_secret={clientSecret}&code={code}&redirect_uri={redirectURI}";
        Map<String, Object> param = Maps.newHashMapWithExpectedSize(32);
        param.put("clientId", casProperty.ClientName);
        param.put("clientSecret", casProperty.ClientPassword);
        param.put("code", code);
        param.put("redirectURI", casProperty.casServer + "/tenant/");
        log.info("临时 Code : {}", code);
        String result = restTemplate.getForObject(url, String.class, param);
        Map hash = JSONObject.parseObject(result, Map.class);
        String token = (String) hash.get("access_token");
        log.info("token : {} " + token);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("msg", "success");
        resultMap.put("data", token);
        Result ok = Result.ok(resultMap);
        return ok;
    }


    @GetMapping("/sys/info")
    public Result info(HttpServletRequest request, HttpServletResponse response) {
        String token = "";
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("access_token")) {
                token = cookie.getValue();
            }
        }
        String url = casProperty.casServer + "/oauth2.0/profile?access_token=" + token;
        String userInfo = restTemplate.getForObject(url, String.class);
        Map map = JSONObject.parseObject(userInfo, Map.class);
        Integer userId = (Integer) map.get("id");
        String username = (String) map.get("username");
        String name = (String) map.get("name");
        SysUserEntity build = SysUserEntity.builder().userId(userId).username(username).name(name).build();
        return Result.ok().put("code", 200).put("data", build);
    }
}
