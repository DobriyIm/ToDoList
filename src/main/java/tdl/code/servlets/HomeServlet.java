package tdl.code.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tdl.code.services.DataServices.DataService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class HomeServlet extends HttpServlet {

    DataService dataService;

    @Inject
    public HomeServlet(DataService dataService){
        this.dataService = dataService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> list = new ArrayList<String>();
        try{
            Statement statement = dataService.getConnection().createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM users");

            while(res.next()){
                list.add(res.getLong(1) + " | " + res.getLong(2) + " | " + res.getString(3));
            }

            System.out.println(list.size());
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        req.setAttribute("list", list.toArray(new String[0]));

        req.setAttribute("pageBody", "home.jsp");
        req.getRequestDispatcher("/_layout.jsp")
                .forward(req,resp);
    }
}
