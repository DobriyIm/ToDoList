package tdl.code.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String id;
    private String login;
    private String pass;
    private String email;
    private String salt;

    public User(){}

    public User(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setLogin(res.getString("login"));
        this.setPass(res.getString("pass"));
        this.setEmail(res.getString("email"));
        this.setSalt(res.getString("salt"));
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public String getPass(){
        return pass;
    }
    public void setPass(String pass){
        this.pass = pass;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getSalt(){
        return salt;
    }
    public void setSalt(String salt){
        this.salt = salt;
    }

}

