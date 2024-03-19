package cn.cestc.os.desktop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

import java.time.LocalDateTime;

/**
 * @author Guan
 * @date 2022/1/6 17:11
 * @Description :
 */
@Slf4j
@MapperScan("cn.cestc.os.desktop.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class DesktopApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        log.info("开始启动 Desktop桌面服务...");
        SpringApplication.run(DesktopApplication.class, args);
        log.info("Desktop桌面服务启动成功...");
        log.info("当前时间: {}", LocalDateTime.now());
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        // 注意这里要指向原先用main方法执行的Application启动类
        log.info("开始启动 Desktop 桌面服务");
        return builder.sources(DesktopApplication.class);
    }
}
