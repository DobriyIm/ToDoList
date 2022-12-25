<%@ page import="tdl.code.entities.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="tdl.code.entities.Project" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();

    Project project = (Project) request.getAttribute("currentProject");
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");

    String projectMessage = (String) request.getAttribute("projectMessage");
    String projectError = (String) request.getAttribute("projectError");
%>

<%if(projectError == null){%>
    <section>


        <form>
            <label>Creation date: <%=project.getCreationDate()%></label><br>

            <label for="nameP">Name: </label>
            <input id="nameP" type="text" value="<%=project.getName()%>"/><br>

            <label for="compDateP">Completion date: </label>
            <input id="compDateP" type="date" value="<%=project.getCompletionDate()%>"><br>

            <label for="isCompP">Completed</label><br>
            <input id="isCompP" type="checkbox" value="<%=project.getIsCompleted()%>"/><br>

            <button onclick="updateProject()">Save</button>
        </form>




        <fieldset>
            <figcaption>Add new task</figcaption>
            <form action="<%=path%>/tasks" method="post">

                <label for="compTime">Completion time:</label>
                <input type="time" id="compTime" name="compTime"/><br>

                <label for="compDate">Completion date:</label>
                <input type="date" id="compDate" name="compDate" min="<%=new Date(System.currentTimeMillis())%>"/><br>

                <label for="text">Text: </label><br>
                <textarea id="text" name="text"></textarea><br>

                <input name="projectId" type="hidden" value="<%=project.getId()%>">

                <input type="submit" value="Add new task"/><br>

            </form>
        </fieldset>

        <fieldset>
            <figcaption>Tasks:</figcaption>

            <% if(tasks != null){ %>
            <%  for(Task task : tasks){ %>
            <hr>
            <div>
                <label>Creating date:<%=task.getCreationDate()%></label><br>
                <label name="taskText"><%=task.getText()%></label><br>
                <%  if(!task.getCompletionTime().isEmpty()){   %>
                <label>Completion time: <%=task.getCompletionTime()%></label><br>
                <%  }   %>
                <%  if(!task.getCompletionDate().isEmpty()){ %>
                <label>Completion date: <%=task.getCompletionDate()%></label>
                <%  }   %>
                <a href="<%=path%>/task?taskId=<%=task.getId()%>">More</a>
                <button onclick="deleteTask( '<%=task.getId()%>')">Delete</button>
            </div>
            <%  }   %>
            <%  }else{  %>
            <label><%=projectMessage%></label>
            <%  }   %>
        </fieldset>

    </section>
<%}else{%>
    <b><%=projectError%></b>
<%}%>

<script>
    let name = document.getElementById("name");
    let compDate = document.getElementById("compDate");
    let isComp = document.getElementById("isComp");
    function updateProject(){
        fetch("<%=path%>/project?"
            + "projectId=<%=project.getId()%>"
            + "&name=" + name.value
            + "&compDate=" + compDate.value
            + "&isComp=" + isComp.value, {
            method:"PUT",
            headers:{},
            body:""
        }).then(r => r.text())
            .then(t => console.log(t))
    }

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