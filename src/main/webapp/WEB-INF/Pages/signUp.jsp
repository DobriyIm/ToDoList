<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String regError = (String) request.getAttribute("regError");
%>

<section>

    <form method="post">
        <% if(regError != null) { %>
            <span><%=regError%></span>
        <% } %>

        <div>
            <label for="user-login">Login</label>
            <input type="text" id="user-login" name="userLogin"/>
        </div>
        <div>
            <label for="user-password">Password</label>
            <input type="password" id="user-password" name="userPassword"/>
        </div>
        <div>
            <label for="confirmPassword">Confirm password</label>
            <input type="password" id="confirmPassword" name="confirmPassword"/>
        </div>
        <div>
            <label for="user-email">Email</label>
            <input type="email" id="user-email" name="userEmail"/>
        </div>
        <input type="submit" value="Submit"/>
    </form>


</section>
