package cn.cestc.os.desktop.config;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.util.Set;


/**
 * @author Guan
 * @date 2022/1/12 16:41
 * @Description :在tomcat容器中注册Nacos 必须使用此类获取tomcat外部端口
 */

@Slf4j
@Component
public class NacosConfig implements ApplicationRunner
{

    @Value("${server.port}")
    Integer port;
    @Autowired
    private NacosAutoServiceRegistration registration;

    @Override
    public void run(ApplicationArguments args)
    {
        if (registration != null && port != null)
        {
            Integer tomcatPort = port;
            try
            {
                tomcatPort = getTomcatPort();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            registration.setPort(tomcatPort);
            registration.start();
        }
    }

    /*
     * @Author 关玉珍
     * @Description  获取外部tomcat端口
     * @Date 16:53 2022/1/12
     * @Param []
     * @return java.lang.String
     **/
    public Integer getTomcatPort() throws Exception
    {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();

        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*")
                , Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

        if (objectNames.size() != 0)
        {
            String port = objectNames.iterator().next().getKeyProperty("port");
            this.port = Integer.parseInt(port);
        }

        log.info("tomcat -----------------------> port : {}", port);

        return port;

    }
}
