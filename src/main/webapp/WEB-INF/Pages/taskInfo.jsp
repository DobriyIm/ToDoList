<%@ page import="tdl.code.entities.Task" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Task task = (Task) request.getAttribute("task");
    String taskError = (String)  request.getAttribute("taskError");
%>

<%if(taskError != null){%>
    <b><%=taskError%></b>
<%}else{%>
    <section>

        <form method="post">

            <label for="compTime">Completion time:</label>
            <input type="time" id="compTime" name="compTime" value="<%=task.getCompletionTime()%>">

            <label for="compDate">Completion date:</label>
            <input type="date" id="compDate" name="compDate" value="<%=task.getCompletionDate()%>" min="<%=new Date(System.currentTimeMillis())%>">

            <label for="text">Text: </label>
            <textarea id="text" name="text"><%=task.getText()%></textarea>

            <label>Creating date: <%=task.getCreationDate()%></label>

            <input type="submit" value="Save"/>

        </form>

    </section>
<%}%>
