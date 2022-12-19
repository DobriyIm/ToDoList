<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String host = (String) request.getContextPath();
%>


<section>

    <form method="post">

        <div>
            <label for="user-email">Email</label>
            <input type="email" id="user-email" name="userEmail"/>
        </div>

        <div>
            <label for="user-password">Password</label>
            <input type="password" id="user-password" name="userPassword"/>
        </div>

        <input type="submit" value="Sign In"/>
    </form>

    <label>Don't have account?  <a href="<%=host%>/signUp">Sign Up</a></label>

</section>
