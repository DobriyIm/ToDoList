<%@ page import="java.util.ArrayList" %>
<%@ page import="tdl.code.entities.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
    String tasksErr = (String) request.getAttribute("tasksError");
%>
<section>

    <form method="post">
        <div>
            <label for="compDateT">Completion date</label>
            <input type="date" id="compDateT" name="compDateT" min="<%=new Date(System.currentTimeMillis())%>"/>
        </div>
        <div>
            <label for="compTimeT">Completion Time</label>
            <input type="time" id="compTimeT" name="compTimeT"/>
        </div>
        <div>
            <label for="textT">Text</label><br>
            <textarea id="textT" name="textT"></textarea>
        </div>
        <input type="submit" value="Add new task"/>
    </form>


    <% if(tasks != null){ %>
        <%  for(Task task : tasks){ %>
            <hr>
            <div>
                <label>Creating date:<%=task.getCreationDate()%></label><br>
                <textarea><%=task.getText()%></textarea><br>
                <%  if(!task.getCompletionTime().isEmpty()){   %>
                    <label>Completion time: <%=task.getCompletionTime()%></label><br>
                <%  }   %>
                <%  if(!task.getCompletionDate().isEmpty()){ %>
                    <label>Completion date: <%=task.getCompletionDate()%></label>
                <%  }   %>
                <button>Delete</button>
+
            </div>
        <%  }   %>
    <%  }else{  %>
        <label><%=tasksErr%></label>
    <%  }   %>

</section>
