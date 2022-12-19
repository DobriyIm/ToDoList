package tdl.code.entities;

import com.sun.org.apache.bcel.internal.generic.PUSH;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Task {
    private String id;
    private String userId;
    private String text;
    private Date creationDate;
    private String completionTime;
    private String completionDate;

    public Task(){}

    public Task(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setUserId(res.getString("userId"));
        this.setText(res.getString("text"));
        this.setCreationDate(res.getDate("creationDate"));
        this.setCompletionTime(res.getString("completionTime"));
        this.setCompletionDate(res.getString("completionDate"));
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getUserId() { return userId;}
    public void setUserId(String userId){this.userId = userId;}

    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }

    public Date getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public String getCompletionTime(){
        return completionTime;
    }
    public void setCompletionTime(String completionTime){
        this.completionTime = completionTime;
    }

    public String getCompletionDate(){
        return completionDate;
    }
    public void setCompletionDate(String completionDate){
        this.completionDate = completionDate;
    }
}
