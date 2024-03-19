package cn.cestc.os.desktop.handler;

import cn.cestc.os.desktop.utils.Result;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

/**
* @Author: Zgh
* @Date: 2022/3/25
* @description: cas拦截器
*/
public class CustomHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    CasProperty casProperty;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = "";
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("access_token")) {
                    token = cookie.getValue();
                }
            }
            if (StringUtils.isNotBlank(token)) {
                String url = casProperty.casServer + "/oauth2.0/profile?access_token=" + token;
                try {
                    String user = restTemplate.getForObject(url, String.class);
                    return true;
                } catch (Exception e) {
                    PrintWriter printWriter = null;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("data", casProperty.casServer);
                    result.put("code", 302);
                    result.put("msg", "fail");
                    Result error = Result.ok(result);
                    printWriter = response.getWriter();
                    try {
                        printWriter.write(JSONObject.toJSONString(error));
                        printWriter.flush();
                    } finally {
                        if (ObjectUtils.allNotNull(printWriter)) {
                            printWriter.close();
                        }
                    }
                }
                return false;
            } else {
                PrintWriter printWriter = null;
                HashMap<String, Object> result = new HashMap<>();
                result.put("data", casProperty.casServer);
                result.put("code", 302);
                result.put("msg", "fail");
                Result error = Result.ok(result);
                printWriter = response.getWriter();
                try {
                    printWriter.write(JSONObject.toJSONString(error));
                    printWriter.flush();
                } finally {
                    if (ObjectUtils.allNotNull(printWriter)) {
                        printWriter.close();
                    }
                }
                return false;
            }
        } catch (Exception exception) {
            PrintWriter printWriter = null;
            HashMap<String, Object> result = new HashMap<>();
            result.put("data", casProperty.casServer);
            result.put("code", 302);
            result.put("msg", "fail");
            Result error = Result.ok(result);
            printWriter = response.getWriter();
            try {
                printWriter.write(JSONObject.toJSONString(error));
                printWriter.flush();
            } finally {
                if (ObjectUtils.allNotNull(printWriter)) {
                    printWriter.close();
                }
            }
            return false;
        }


    }
}
