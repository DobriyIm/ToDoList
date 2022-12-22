package tdl.code.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tdl.code.dao.UserDAO;
import tdl.code.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class SignInServlet extends HttpServlet {

    private final UserDAO userDAO;

    @Inject
    public SignInServlet(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if(req.getParameter("logout") != null){
            session.removeAttribute("authUserId");
            resp.sendRedirect(req.getContextPath());
            return;
        }

        req.setAttribute("pageBody", "signIn.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userEmail = req.getParameter("userEmail");
        String  userPass = req.getParameter("userPassword");

        HttpSession session = req.getSession();

        try{
            User user = userDAO.getUserByCredentials(userEmail,userPass);
            if(user == null)
                throw new Exception("Incorrect credentials.");
            else {
                session.setAttribute("authUserId", user.getId());
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
        }catch (Exception ex){
            System.out.println("SignIn Db error: " + ex.getMessage());
        }

        resp.sendRedirect(req.getRequestURI());
    }
}
