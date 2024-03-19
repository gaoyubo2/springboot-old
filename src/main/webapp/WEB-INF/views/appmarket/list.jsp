<%@ page language="java" import="cn.cestc.os.desktop.model.AppModel" pageEncoding="UTF-8" %>
<%@page import="cn.cestc.os.desktop.model.MemberAppModel" %>
<%@page import="java.util.List" %>
<%
    List<MemberAppModel> memberAppList = (List<MemberAppModel>) request.getAttribute("memberAppList");
    List<AppModel> appList = (List<AppModel>) request.getAttribute("appList");
    int app_category_id = (Integer) request.getAttribute("app_category_id");

    int from = (Integer) request.getAttribute("from");
    int to = (Integer) request.getAttribute("to");
    int end = (from + to);
    end = end > appList.size() ? appList.size() : end;
    out.print(appList.size());
    out.print("<{|*|}>");

    for (int i = from; i < end; i++)
    {
        AppModel app = appList.get(i);
%>
<li>
    <a href="javascript:beforeopenDetailIframe2('${pageContext.request.contextPath}/appMarketController/detail.do?id=<%=app.getTbid()%>',<%=app.getTbid()%>);"><img
            src="${pageContext.request.contextPath}/<%=app.getIcon()%>"></a>
    <a href="javascript:beforeopenDetailIframe2('${pageContext.request.contextPath}/appMarketController/detail.do?id=<%=app.getTbid()%>',<%=app.getTbid()%>);"
       class="app-name"><%=app.getName()%>
    </a>
    <span class="app-desc" title="<%=app.getRemark()%>"><%=app.getRemark()%></span>
    <span class="star-box"><i style="width:<%=app.getStarnum() * 20%>%;"></i></span>
    <span class="star-num"><%=app.getStarnum()%></span>
    <span class="app-stat"><%=app.getUsecount()%>人正在使用</span>
    <%
        boolean flag = false;
        for (MemberAppModel mApp : memberAppList)
        {
            if (mApp.getRealid() != null && app.getTbid() != null && mApp.getRealid().equals(app.getTbid()))
            {
                flag = true;
                if (app_category_id == -2)
                {%>
    <a href="javascript:;" app_id="<%=mApp.getTbid()%>" real_app_id="<%=app.getTbid()%>" app_type="window"
       class="btn-run-s" style="right:35px" title="打开应用">打开应用</a>
    <a href="javascript:;" app_id="<%=mApp.getTbid()%>" real_app_id="<%=app.getTbid()%>" app_type="window"
       class="btn-remove-s" style="right:10px" title="删除应用">删除应用</a>
    <%
    } else
    {
    %>
    <a href="javascript:;" app_id="<%=mApp.getTbid()%>" real_app_id="<%=app.getTbid()%>" app_type="window"
       class="btn-run-s" title="打开应用">打开应用</a>
    <%
                }
                break;
            }
        }
        if (!flag)
        {
    %><a href="javascript:;" real_app_id="<%=app.getTbid()%>" class="btn-add-s" title="添加应用">添加应用</a><%
    }%>
</li>
<%}%>
