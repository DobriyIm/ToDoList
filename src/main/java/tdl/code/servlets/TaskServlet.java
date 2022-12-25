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
import java.sql.PreparedStatement;

@Singleton
public class TaskServlet extends HttpServlet {
    private final TaskDAO taskDAO;

    @Inject
    public  TaskServlet(TaskDAO taskDAO){this.taskDAO = taskDAO;}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(session.getAttribute("authUserId") == null){
            resp.sendRedirect( req.getContextPath() + "/signIn");
            return;
        }

        try {

            String taskId = req.getParameter("taskId");

            if (taskId == null)
                taskId = (String) req.getAttribute("taskId");

            if (taskId == null)
                throw new Exception("TaskIdIsNull");

            Task task = taskDAO.getTaskById(taskId);

            if (task == null)
                throw new Exception("TaskNotFound");

            req.setAttribute("task", task);

        } catch (Exception ex){
            req.setAttribute("taskError", ex.getMessage());
        }

        req.setAttribute("pageBody", "taskInfo.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            HttpSession session = req.getSession();

            String taskId = req.getParameter("taskId");
            String taskText = req.getParameter("text");
            String taskCompletionTime = req.getParameter("compTime");
            String taskCompletionDate = req.getParameter("compDate");

            if (taskText == null)
                throw new Exception("Task must have some text.");

            Task task = new Task();

            task.setId(taskId);
            task.setText(taskText);
            task.setCompletionTime(taskCompletionTime);
            task.setCompletionDate(taskCompletionDate);

            String resultId = taskDAO.update(task);

            if(resultId == null)
                throw new Exception("Can't update task.");

            req.setAttribute("taskId", resultId);

        }catch (Exception ex){
            req.setAttribute("taskError", ex.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/tasks");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("PUT works");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String taskId = req.getParameter("taskId");

        if(taskDAO.delete(taskId) == null)
            System.out.println("ERROORR");


        resp.sendRedirect(req.getContextPath() + "/tasks");
    }
}










