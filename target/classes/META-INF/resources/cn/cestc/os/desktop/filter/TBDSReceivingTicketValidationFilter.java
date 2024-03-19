package cn.cestc.os.desktop.filter;

import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class TBDSReceivingTicketValidationFilter extends Cas30ProxyReceivingTicketValidationFilter
{

    @Override
    public void onSuccessfulValidation(HttpServletRequest request,
                                       HttpServletResponse response, Assertion assertion)
    {

        AttributePrincipal principal = assertion.getPrincipal();
        Map<String, Object> map = principal.getAttributes();

        // log.info("验证ticket 获取的用户ID 为 : {}", map.get("id"));
        log.info("验证ticket 获取的租户ID 为 : {}", map.get("tenantId"));
        log.info("验证ticket 获取的租户名 为 : {}", map.get("name"));
        log.info("验证ticket 获取的用户名 为 : {}", principal.getName());
        AssertionHolder.setAssertion(assertion);//获取用户信息

    }

    @Override
    public void onFailedValidation(HttpServletRequest request, HttpServletResponse response)
    {
        log.info("Failed to validate cas ticket");
    }
}
