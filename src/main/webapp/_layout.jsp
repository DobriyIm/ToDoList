<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String pageBody = "/WEB-INF/Pages/" + request.getAttribute("pageBody");
%>

<jsp:include page="WEB-INF/Components/header.jsp"/>

<jsp:include page="<%=pageBody%>"/>

<jsp:include page="WEB-INF/Components/footer.jsp"/>