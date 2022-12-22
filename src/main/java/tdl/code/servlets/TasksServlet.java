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
public class TasksServlet extends HttpServlet {
    private final TaskDAO taskDAO;

    @Inject
    public TasksServlet(TaskDAO taskDAO){
        this.taskDAO = taskDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(session.getAttribute("authUserId") == null){
            resp.sendRedirect( req.getContextPath() + "/signIn");
            return;
        }


        List<Task> tasks = taskDAO.getAllTasks((String)session.getAttribute("authUserId"));

        if(tasks == null)
            req.setAttribute("tasksError", "You don't have tasks.");
        else{
            req.setAttribute("tasks", tasks);
        }

        req.setAttribute("pageBody", "tasks.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String text = req.getParameter("textT");
        String CompTime = req.getParameter("compTimeT");
        String CompDate = req.getParameter("compDateT");

        try{
            if(text == "" || text == null)
                throw new Exception("Can't add task without text.");

            Task task = new Task();

            task.setUserId((String)session.getAttribute("authUserId"));
            task.setText(text);
            task.setCompletionTime(CompTime);
            task.setCompletionDate(CompDate);
            task.setCreationDate(new Date(System.currentTimeMillis()));


            if(taskDAO.add(task) == null){
                throw new Exception("Server error: try later.");
            }

        }catch (Exception ex){
            System.out.println("Adding task error: " + ex.getMessage());
        }

        resp.sendRedirect(req.getRequestURI());
    }
}
