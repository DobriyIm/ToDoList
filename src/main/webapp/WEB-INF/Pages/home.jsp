<%@ page import="tdl.code.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  User user = (User) request.getAttribute("authUser");
%>

<section>

  <h2>Home page.</h2>

  <% if(user != null) { %>
    <h5>Hello,<%=user.getLogin()%></h5>
  <% } %>

</section>