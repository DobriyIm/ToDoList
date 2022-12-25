package tdl.code.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tdl.code.dao.TaskDAO;
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
public class TasksListServlet extends HttpServlet {
    private final TaskDAO taskDAO;

    @Inject
    public TasksListServlet(TaskDAO taskDAO){
        this.taskDAO = taskDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(session.getAttribute("authUserId") == null){
            resp.sendRedirect( req.getContextPath() + "/signIn");
            return;
        }

        try{

            List<Task> tasks = taskDAO.getAllTasksByUserId((String)session.getAttribute("authUserId"));

            if(tasks == null)
                req.setAttribute("tasksListMessage", "You don't have task.");
            else
                req.setAttribute("tasks", tasks);

        }catch (Exception ex){
            req.setAttribute("taskListError", ex.getMessage());
        }

        req.setAttribute("pageBody", "tasks.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            HttpSession session = req.getSession();

            String taskText = req.getParameter("text");
            String taskCompletionTime = req.getParameter("compTime");
            String taskCompletionDate = req.getParameter("compDate");
            String taskProjectId = req.getParameter("projectId");

            if (taskText == null)
                throw new Exception("Task must have some text.");

            Task task = new Task();

            task.setUserId((String)session.getAttribute("authUserId"));
            task.setText(taskText);
            task.setCompletionTime(taskCompletionTime);
            task.setCompletionDate(taskCompletionDate);
            task.setProjectId(taskProjectId);
            task.setCreationDate(new Date(System.currentTimeMillis()));

            if(taskDAO.add(task) == null)
                throw new Exception("Can't add task.");


        }catch (Exception ex){
            req.setAttribute("taskError", ex.getMessage());
        }

        resp.sendRedirect(req.getRequestURI());
    }
}
