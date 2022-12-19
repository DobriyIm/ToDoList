package tdl.code.dao;

import com.google.inject.Inject;
import tdl.code.entities.Task;
import tdl.code.services.DataServices.DataService;
import tdl.code.services.HashServices.HashService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class TaskDAO {
    private final Connection connection;
    private final HashService hashService;
    private final DataService dataService;

    @Inject
    public TaskDAO(DataService dataService, HashService hashService){
        this.dataService = dataService;
        this.hashService = hashService;
        this.connection = dataService.getConnection();
    }

    /**
     * Insert task in to Db
     * @param task data to insert
     * @return task ID in table
     */
    public String add(Task task){
        task.setId(UUID.randomUUID().toString());
        String sql = "INSERT INTO tasks (id, userId,text, creationDate, completionTime, completionDate) VALUES(?,?,?,?,?,?) " ;

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, task.getId());
            prep.setString(2, task.getUserId());
            prep.setString(3,task.getText());
            prep.setDate(4,task.getCreationDate());
            prep.setString(5, task.getCompletionTime());
            prep.setString(6,task.getCompletionDate());
        }catch (SQLException ex){
            System.out.println("MySqlService::add task error : " + ex.getMessage());
            return null;
        }
        return task.getId();
    }
}
