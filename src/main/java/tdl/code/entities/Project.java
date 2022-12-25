package tdl.code.entities;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Project {
    private String id;
    private String userId;
    private String name;
    private Date creationDate;
    private String completionDate;
    private Boolean isCompleted;

    public Project(){}

    public Project(ResultSet res) throws SQLException {
        this.setId(res.getString("id"));
        this.setUserId(res.getString("userId"));
        this.setName(res.getString("name"));
        this.setCreationDate(res.getDate("creationDate"));
        this.setCompletionDate(res.getString("completionDate"));
        this.setIsCompleted(res.getBoolean("isCompleted"));
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getUserId() { return userId;}
    public void setUserId(String userId){this.userId = userId;}

    public String getName() { return name;}
    public void setName(String name){this.name = name;}

    public Date getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }

    public String getCompletionDate(){
        return completionDate;
    }
    public void setCompletionDate(String completionDate){
        this.completionDate = completionDate;
    }

    public Boolean getIsCompleted(){ return isCompleted; }
    public void setIsCompleted(Boolean isCompleted){ this.isCompleted = isCompleted; }

}
