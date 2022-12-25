package tdl.code.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tdl.code.dao.ProjectDAO;
import tdl.code.entities.Project;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Singleton
public class ProjectsListServlet extends HttpServlet {
    private final ProjectDAO projectDAO;

    @Inject
    ProjectsListServlet(ProjectDAO projectDAO){
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

            List<Project> projects = projectDAO.getAllProjectsByUserId((String)session.getAttribute("authUserId"));

            if(projects == null)
                req.setAttribute("projectListMessage", "You don't have any project's yet.");
            else
                req.setAttribute("projects", projects);

        }catch (Exception ex){
            req.setAttribute("projectListError", ex.getMessage());
        }

        req.setAttribute("pageBody", "projects.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }
}
