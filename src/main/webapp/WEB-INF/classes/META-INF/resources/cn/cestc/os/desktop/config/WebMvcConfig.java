package cn.cestc.os.desktop.config;

import cn.cestc.os.desktop.handler.CustomHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    /**
     * -设置url后缀模式匹配规则
     * -该设置匹配所有的后缀，使用.do或.action都可以
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer)
    {
        configurer.setUseSuffixPatternMatch(true)    //设置是否是后缀模式匹配,即:/test.*
                .setUseTrailingSlashMatch(true);    //设置是否自动后缀路径模式匹配,即：/test/
    }

//    @Bean
//    public CustomHandlerInterceptor getHandlerInterceptorD()
//    {
//        return new CustomHandlerInterceptor();
//    }

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry)
//    {
//        registry.addInterceptor(getHandlerInterceptorD()).addPathPatterns("/**")
//                .excludePathPatterns("/**/index.html", "/**/token/**", "/**/code/**",
//                        "/**/login/**", "/**/static/**", "/**/api/**", "/**/metadata/**",
//                        "/**/error/**", "/**/cas/**", "/**/tenant/**");
//    }
}
