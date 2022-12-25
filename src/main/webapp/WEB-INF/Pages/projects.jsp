<%@ page import="java.sql.Date" %>
<%@ page import="tdl.code.entities.Project" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Project> projects = (List<Project>) request.getAttribute("projects");
  String projectMessage = (String) request.getAttribute("projectMessage");
  String host = (String) request.getContextPath();
%>

<section>

  <form ACTION="<%=host%>/project" method="post">
    <div>
      <label for="compDateP">Completion date</label>
      <input type="date" id="compDateP" name="compDateP" min="<%=new Date(System.currentTimeMillis())%>"/>
    </div>
    <div>
      <label for="nameP">Name</label><br>
      <input type="text" id="nameP" name="nameP"/>
    </div>
    <input type="submit" value="Add new project   "/>
  </form>

  <% if(projects != null){ %>
  <%  for(Project project : projects){ %>
  <hr>
  <div>
    <label>Name: <%=project.getName()%></label><br>
    <%  if(!project.getCompletionDate().isEmpty()){ %>
    <label>Completion date: <%=project.getCompletionDate()%></label>
    <%  }   %>
    <label>
      <%if(project.getIsCompleted() == true){%>
      Completed.
      <%}else{%>
      Not competed.
      <%}%>
    </label>
    <button id="editB" value="<%=project.getId()%>">Save</button>
    <button id="deleteB" value="<%=project.getId()%>">Delete</button>
    <label><a href="<%=host%>/project?projectId=<%=project.getId()%>">More</a></label>


  </div>
  <%  }   %>
  <%  }else{  %>
  <label><%=projectMessage%></label>
  <%  }   %>

</section>
