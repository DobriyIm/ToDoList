package tdl.code.services.DataServices;

import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class MySqlService implements DataService{
    private final String connectionString =  "jdbc:mysql://localhost:3306/todolistdb" +
            "?useUnicode=true&characterEncoding=UTF-8" +
            "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private final String dbUser = "testUser";
    private final String dbPass = "testPass";

    private Connection connection;

    public Connection getConnection() {
        if(connection == null){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(connectionString,dbUser,dbPass);
            }
            catch (Exception ex){
                System.out.println("MySqlService error: " + ex.getMessage());
            }
        }
        return connection;
    }
}
