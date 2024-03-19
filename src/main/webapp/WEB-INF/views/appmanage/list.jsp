<%@page import="cn.cestc.os.desktop.model.AppCategoryModel" %>
<%@ page language="java" import="cn.cestc.os.desktop.model.AppModel" pageEncoding="UTF-8" %>
<%@page import="java.util.List" %>
<%
    List<AppModel> appList = (List<AppModel>) request.getAttribute("appList");
    List<AppCategoryModel> appCategoryList = (List<AppCategoryModel>) request.getAttribute("appCategoryList");
    int from = (Integer) request.getAttribute("from");
    int to = (Integer) request.getAttribute("to");
    int end = (from + to);
    end = Math.min(end, appList.size());
    out.print(appList.size());
    out.print("<{|*|}>");

    for (int i = from; i < end; i++)
    {
        AppModel app = appList.get(i);
%>
<tr class="list-bd">
    <td><%=(i + 1)%>
    </td>
    <td style="text-align:left;padding-left:15px">
        <img src="${pageContext.request.contextPath}/<%=app.getIcon()%>" alt="<%=app.getName()%>" class="appicon"/>
        <span class="appname"><%=app.getName()%></span>
    </td>
    <%
        for (AppCategoryModel vo : appCategoryList)
        {
            if (app.getApp_category_id().equals(vo.getTbid()))
            {%>
    <td><%=vo.getName() %>
    </td>
    <% break;
    }
    }
        if (app.getApp_category_id() == 0 | app.getApp_category_id() > 9)
        {%>
    <td>未分类</td>
    <%}%>
    <td><%=app.getUsecount()%>
    </td>
    <td width="100"><a href="javascript:;" class="btn btn-link do-recommend" appid="<%=app.getTbid()%>">
        <%
            if (app.getVerifytype() == 1)
            {
                if (app.getIsrecommend() == 0)
                {
        %>设为今日推荐<%
    } else
    {
    %>今日推荐<%
            }
        }
    %>
    </a></td>
    <td width="50">
        <%
            if (app.getTbid() > 3)
            {
        %>
        <a href="javascript:openDetailIframe('${pageContext.request.contextPath}/appManageController/detail.do?appid=<%=app.getTbid()%>');"
           class="btn btn-link">编辑</a></td>
    <%}%>
    <td width="50">
        <%
            if (app.getTbid() > 3)
            {
        %>
        <a href="javascript:;" class="btn btn-link do-del" appid="<%=app.getTbid()%>">删除</a>
        <%}%>
    </td>
</tr>
<%}%>