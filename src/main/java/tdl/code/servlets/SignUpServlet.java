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
public class SignUpServlet extends HttpServlet
{
    private final UserDAO userDAO;

    @Inject
    public SignUpServlet(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String regError = (String) session.getAttribute("regError");
        String userLogin = (String) session.getAttribute("userLogin");
        String userEmail = (String) session.getAttribute("userEmail");

        if(regError != null){
            if(userLogin != null)
                req.setAttribute("userLogin", userLogin);
            if(userEmail != null)
                req.setAttribute("userEmail", userEmail);
            req.setAttribute("regError", regError);
            session.removeAttribute("regError");
        }



        req.setAttribute("pageBody", "signUp.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userLogin = req.getParameter("userLogin");
        String userPass = req.getParameter("userPassword");
        String confirmPass = req.getParameter("confirmPassword");
        String userEmail = req.getParameter("userEmail");

        HttpSession session = req.getSession();

        try {
            //region validation
            if(userLogin == null || userLogin.isEmpty()){
                throw new Exception("User Login could not be empty!");
            }
            if(userPass == null || userPass.isEmpty()){
                throw new Exception("User Password could not be empty!");
            }
            if(userPass.length() < 3){
                throw new Exception("Password length must be more then 3!");
            }
            if(!confirmPass.equals(userPass)){
                throw new Exception("Passwords do not match!");
            }
            if(userEmail == null || userEmail.isEmpty()){
                throw new Exception("User Name could not be empty!");
            }
            if(!userDAO.isEmailFree(userEmail)){
                throw new Exception("This Email already in use!");
            }
            //endregion

            User user = new User();

            user.setLogin(userLogin);
            user.setPass(userPass);
            user.setEmail(userEmail);

            if(userDAO.add(user) == null){
                throw new Exception("Server error: try later.");
            }


        }
        catch (Exception ex){
            session.setAttribute("regError", ex.getMessage());
            session.setAttribute("userLogin", userLogin);
            session.setAttribute("userEmail", userEmail);
            resp.sendRedirect(req.getRequestURI());
            return;
        }


        resp.sendRedirect(req.getContextPath() + "/signIn");
    }
}
