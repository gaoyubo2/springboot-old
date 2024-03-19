package cn.cestc.os.desktop.service;

import cn.cestc.os.desktop.model.Result;
import cn.cestc.os.desktop.pojo.MenuDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Guan
 * @date 2022/1/4 10:00
 * @Description :远程调用 租户系统
 * 83:  http://10.32.226.83/api/tenant/V1.0.0
 * 97:  http://10.32.226.97/api/tenant/V1.0.0
 */
@Component
@FeignClient(value = "cestc-os-tenant", url = "http://localhost:8700")
public interface TenantFeignService
{

    @GetMapping("/tenant/V1.0.0/menu/getModuleList/{userId}")
    Result<List<MenuDO>> moduleList(@PathVariable("userId") Integer userId);
}
