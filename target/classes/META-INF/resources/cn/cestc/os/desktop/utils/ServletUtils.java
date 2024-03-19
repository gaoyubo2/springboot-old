package cn.cestc.os.desktop.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Component
public class ServletUtils
{
    public static ServletUtils ServletUtils; //声明对象
    @Autowired
    private RestTemplate restTemplate;
    @Value("${cas.serverUrl}")
    private String casServerUrl;

    /*
     * @Author 关玉珍
     * @Description  基于token获取用户全部信息
     * @Date 14:55 2022/2/21
     * @Param [request]
     * @return java.lang.String
     **/
    public static Map getUserInfo(HttpServletRequest request)
    {
        String URL_UAA = ServletUtils.casServerUrl + "/oauth2.0/profile?access_token=";

        // 获取token
        String token = "";
        for (Cookie cookie : request.getCookies())
        {
            if (cookie.getName().equals("access_token"))
            {
                token = cookie.getValue();
            }
        }

        String user = ServletUtils.restTemplate.getForObject(URL_UAA + token, String.class);
        Map map = JSONObject.parseObject(user, Map.class);


        return map;
    }

    /*
     * @Author 关玉珍
     * @Description  获取当前登录的用户名
     * @Date 13:07 2022/2/22
     * @Param [request]
     * @return java.lang.String
     **/
    public static String getUserName(HttpServletRequest request)
    {
//        Map map = getUserInfo(request);
//
//        String username = map.get("username").toString();
//
//
//        if ("".equals(username) || username == null)
//        {
//            String defaultName = "root";
//            log.info("未获取到用户名! 返回默认值!  defaultName = {};", defaultName);
//            return defaultName;
//        }
//
//        log.info("从cas获取用户信息为 ----------------> : {} ", username);
//
//        return username;
        return "";
    }

    /*
     * @Author 关玉珍
     * @Description  获取租户id
     * @Date 13:07 2022/2/22
     * @Param [request]
     * @return java.lang.Integer
     **/
    public static Integer getTenantId(HttpServletRequest request)
    {
        Map map = getUserInfo(request);

        Object tenantId = map.get("tenantId");

        if ("".equals(tenantId) || tenantId == null)
        {
            log.info("未获取到租户id 返回默认id : 0 -- -- -- --");
            return 0;
        }

        Integer anInt = Integer.valueOf(tenantId.toString());

        log.info("从cas获取租户ID为 ----------------> : {} ", anInt);

        return anInt;
    }

    /*
     * @Author 关玉珍
     * @Description  获取用户权限类型
     * @Date 13:07 2022/2/22
     * @Param [request]
     * @return java.lang.String
     **/
    public static String getUserType(HttpServletRequest request)
    {
//        Map map = getUserInfo(request);

//        String roleType = map.get("roleType").toString();
        String roleType = "0";
        String userType = "";

        switch (roleType)
        {
            case "0":
                userType = "系统管理员";
                break;
            case "1":
                userType = "租户管理员";
                break;
        }

        log.info("从cas获取租户权限类型为 ----------------> : {} ", userType);

        return userType;
    }

    /*
     * @Author 关玉珍
     * @Description 获取用户名
     * @Date 13:06 2022/2/22
     * @Param [request]
     * @return java.lang.String
     **/
    public static String getName(HttpServletRequest request)
    {

//        Map map = getUserInfo(request);
//        assert map != null;
//        Object name = map.get("name");
//
//
//        if ("".equals(name) || name == null)
//        {
//            String defaultName = "root";
//            log.info("未获取到用户name! 返回默认值!  defaultName = {};", defaultName);
//            return defaultName;
//        }
//
//        log.info("从cas获取租户权限类型为 ----------------> : {} ", name);
//
//        return name.toString();
//        return "";
          return "jupiter";
    }

    @PostConstruct //初始化
    public void init()
    {
        ServletUtils = this;
        ServletUtils.restTemplate = this.restTemplate;
        ServletUtils.casServerUrl = this.casServerUrl;
    }


}
