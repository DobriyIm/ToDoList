package tdl.code.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tdl.code.dao.UserDAO;
import tdl.code.entities.User;
import tdl.code.services.DataServices.DataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class HomeServlet extends HttpServlet {

    DataService dataService;
    private final UserDAO userDAO;
    @Inject
    public HomeServlet(DataService dataService, UserDAO userDAO){
        this.dataService = dataService;
        this.userDAO = userDAO;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String userId = (String) session.getAttribute("authUserId");
        if(userId != null){
            req.setAttribute("authUser", userDAO.getUserById(userId));
        }

        req.setAttribute("pageBody", "home.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }
}
