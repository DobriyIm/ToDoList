<%@ page import="java.util.ArrayList" %>
<%@ page import="tdl.code.entities.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = (String) request.getContextPath();

    List<Task> tasks = (List<Task>) request.getAttribute("tasks");

    String tasksListMessage = (String) request.getAttribute("tasksListMessage");
    String taskListError = (String)  request.getAttribute("taskListError");
%>

<%if(taskListError == null){%>
    <section>

        <form method="post">

            <label for="compTime">Completion time:</label>
            <input type="time" id="compTime" name="compTime"/><br>

            <label for="compDate">Completion date:</label>
            <input type="date" id="compDate" name="compDate" min="<%=new Date(System.currentTimeMillis())%>"/><br>

            <label for="text">Text: </label><br>
            <textarea id="text" name="text"></textarea><br>

            <input type="submit" value="Add new task"/><br>

        </form>


        <% if(tasks != null){ %>
        <%  for(Task task : tasks){ %>
        <hr>
        <div>
            <label>Creating date:<%=task.getCreationDate()%></label><br>
            <label><%=task.getText()%></label><br>
            <%  if(!task.getCompletionTime().isEmpty()){   %>
            <label>Completion time: <%=task.getCompletionTime()%></label><br>
            <%  }   %>
            <%  if(!task.getCompletionDate().isEmpty()){ %>
            <label>Completion date: <%=task.getCompletionDate()%></label><br>
            <%  }   %>
            <a href="<%=path%>/task?taskId=<%=task.getId()%>">More</a>
            <button onclick="deleteTask( '<%=task.getId()%>')">Delete</button>


        </div>
        <%  }   %>
        <%  }else{  %>
        <label><%=tasksListMessage%></label>
        <%  }   %>
    </section>

<%}else{%>
    <b><%=taskListError%></b>
<%}%>

<script>
    let deleteTask = (id) => {
        fetch("<%=path%>/task?"
            + "taskId=" + id, {
            method:"DELETE",
            headers:{},
            body:""
        }).then(r => r.text())
            .then(t => console.log(t))
    }
</script>
