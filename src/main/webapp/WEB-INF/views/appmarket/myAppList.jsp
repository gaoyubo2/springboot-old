<%@ page language="java" import="cn.cestc.os.desktop.model.AppCategoryModel" pageEncoding="UTF-8" %>
<%@page import="cn.cestc.os.desktop.model.AppModel" %>
<%@page import="java.util.List" %>
<%
    List<AppModel> appList = (List<AppModel>) request.getAttribute("appList");
    List<AppCategoryModel> appMyList = (List<AppCategoryModel>) request.getAttribute("appMyList");

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
<tr class="list-bd">
    <td style="text-align:left;padding-left:15px">
        <img src="${pageContext.request.contextPath}/<%=app.getIcon()%>" alt="test" class="appicon">
        <span class="appname"><%=app.getName()%></span>
    </td>
    <%
        for (AppCategoryModel vo : appMyList)
        {
            if (app.getApp_category_id() == vo.getTbid())
            {
    %>
    <td><%=vo.getName() %>
    </td>
    <td><%=app.getUsecount() %>
    </td>
    <% break;
    }
    }
        if (app.getApp_category_id() == 0)
        {%>
    <td>未分类</td>
    <td><%=app.getUsecount() %>
    </td>
    <%}%>
    <td>
        <a href="javascript:openDetailIframe('${pageContext.request.contextPath}/appMarketController/edit.do?id=<%=app.getTbid()%>');"
           class="btn btn-mini btn-link">详情</a></td>
</tr>
<%}%>
