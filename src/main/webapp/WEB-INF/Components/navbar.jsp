<%@ page import="tdl.code.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String host = request.getContextPath();
    String authId = (String) session.getAttribute("authUserId");
%>

<nav>
    <ul>
        <% if(authId != null) { %>
            <li><a href="<%=host%>/">Home</a></li>
            <li><a href="<%=host%>/tasks">Tasks</a> </li>
            <li><a href="<%=host%>/signIn?logout=true">Log out</a></li>
        <% } else{ %>
            <li><a href="<%=host%>/signIn">Sign In</a></li>
            <li><a href="<%=host%>/signUp">Sign Up</a></li>
        <% } %>
    </ul>
</nav>
