package tdl.code.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tdl.code.dao.ProjectDAO;
import tdl.code.dao.TaskDAO;
import tdl.code.entities.Project;
import tdl.code.entities.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Singleton
public class ProjectServlet extends HttpServlet {
    private final TaskDAO taskDAO;
    private final ProjectDAO projectDAO;
    @Inject
    public ProjectServlet(TaskDAO taskDAO, ProjectDAO projectDAO){
        this.taskDAO = taskDAO;
        this.projectDAO = projectDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(session.getAttribute("authUserId") == null){
            resp.sendRedirect( req.getContextPath() + "/signIn");
            return;
        }

        try{

            String projectId = req.getParameter("projectId");

            if (projectId == null)
                throw new Exception("ProjectIdIsNull");

            Project project = projectDAO.getProjectById(projectId);

            if(project == null)
                throw new Exception("Can'tGetProject");

            req.setAttribute("currentProject", project);

            List<Task> tasks = taskDAO.getAllTasksByProjectId(projectId);

            if(tasks == null)
                req.setAttribute("projectMessage", "There are no tasks in this project yet");
            else
                req.setAttribute("tasks", tasks);

        }catch (Exception ex){
            req.setAttribute("projectError", ex.getMessage());
        }

        req.setAttribute("pageBody", "projectInfo.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        try{
            String name = req.getParameter("nameP");
            String CompDate = req.getParameter("compDateP");

            if(name.isEmpty() || name == null)
                req.setAttribute("validationMessage","Project must have a name.");

            Project project = new Project();

            project.setUserId((String)session.getAttribute("authUserId"));
            project.setName(name);
            project.setCompletionDate(CompDate);
            project.setCreationDate(new Date(System.currentTimeMillis()));

            if(projectDAO.add(project) == null){
                throw new Exception("Can't add new project.");
            }

        }catch (Exception ex){
            req.setAttribute("projectListError", ex.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/projects");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{

            String name = req.getParameter("nameP");
            String compDate = req.getParameter("compDateP");
            String isComp = req.getParameter("isCompP");
            String projectId = req.getParameter("projectId");

            if (name == null || name.isEmpty())
                throw new Exception("Project must have name");

            Project project = new Project();

            project.setId(projectId);
            project.setName(name);
            project.setCompletionDate(compDate);
            project.setIsCompleted(Boolean.parseBoolean(isComp));

            if(projectDAO.update(project) == null)
                throw new Exception("Can't update project.");

        }catch (Exception ex){
            req.setAttribute("projectError", ex.getMessage());
        }
    }
}

