//package cn.cestc.os.desktop.config;
//
//
//import cn.cestc.os.desktop.model.Result;
//import cn.cestc.os.desktop.model.manage.User;
//import cn.cestc.os.desktop.service.SsoService;
//import cn.dev33.satoken.stp.StpUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class LoginInterceptor implements HandlerInterceptor {
//    //想要注入的类
//    @Autowired
//    SsoService ssoService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!ssoService.ifLogin(request).getData()) {
//            // 用户未登录，重定向到登录页面
//            response.setStatus(401);
//            response.sendRedirect("http://10.56.180.51:5173/"); // 假设登录页面的 URL 是 "/login"
//            return false; // 拦截请求
//        }
//        Integer uid = StpUtil.getLoginIdAsInt();
//        User user = ssoService.getUser(uid);
//        if(user == null || user.getIsDelete() == 1){
//            // 用户没有权限，重定向到登录页面
//            response.setStatus(403);
//            response.sendRedirect("http://10.56.180.51:5173/"); // 假设登录页面的 URL 是 "/login"
//            return false; // 拦截请求
//        }
//
//        //用户已登录，放行请求
//        return true;
//
//    }
//
////    @Override
////    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
////        System.out.println("post 拦截");
////
////    }
////
////    @Override
////    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
////        System.out.println("after 拦截");
////    }
//}
