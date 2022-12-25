package tdl.code.dao;

import com.google.inject.Inject;
import tdl.code.entities.Project;
import tdl.code.entities.Task;
import tdl.code.services.DataServices.DataService;
import tdl.code.services.HashServices.HashService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

        String sql = "INSERT INTO tasks (id, userId, projectId, text, creationDate, completionTime, completionDate) VALUES(?,?,?,?,?,?,?) " ;

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, task.getId());
            prep.setString(2, task.getUserId());
            prep.setString(3, task.getProjectId());
            prep.setString(4,task.getText());
            prep.setDate(5,task.getCreationDate());
            prep.setString(6, task.getCompletionTime());
            prep.setString(7,task.getCompletionDate());

            prep.executeUpdate();

        }catch (SQLException ex){
            System.out.println("MySqlService::add task error : " + ex.getMessage());
            return null;
        }
        return task.getId();
    }

    /**
     * Update task in Db
     * @param task data to update
     * @return task ID in table
     */
    public String update(Task task){

        String sql = "UPDATE tasks SET text = ?, completionDate = ?, completionTime = ? WHERE id = ?" ;

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, task.getText());
            prep.setString(2, task.getCompletionDate());
            prep.setString(3, task.getCompletionTime());
            prep.setString(4, task.getId());

            prep.executeUpdate();

        }catch (SQLException ex){
            System.out.println("MySqlService::update task error : " + ex.getMessage());
            return null;
        }
        return task.getId();
    }

    public String delete(String taskId){
        String sql = "DELETE FROM tasks  WHERE id = ?" ;

        try{
            PreparedStatement prep = connection.prepareStatement(sql);
            prep.setString(1, taskId);

            prep.executeUpdate();

        }catch (SQLException ex){
            System.out.println("MySqlService::delete task error : " + ex.getMessage());
            return null;
        }
        return taskId;
    }

    /**
     * Get tasks by ID
     * @param taskId
     * @return task
     */
    public Task getTaskById(String taskId){
        String sql = "SELECT * FROM tasks t WHERE t.id = ? ";

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, taskId);
            ResultSet res = prep.executeQuery();
            if(res.next()){
                Task task = new Task(res);
                return  task;
            }
        }catch (SQLException ex){
            System.out.println("TaskDAO error::getTaskById: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Get all task's of specific user
     * @param userId
     * @return array of tasks
     */
    public List<Task> getAllTasksByUserId(String userId){
        String sql = "SELECT * FROM tasks t WHERE (t.userId = ?)";
        List<Task> tasks = new ArrayList<>();

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, userId);
            ResultSet res = prep.executeQuery();
            while(res.next()){
                Task task = new Task(res);
                tasks.add(task);
            }
        }
        catch (SQLException ex){
            System.out.println("TaskDAO error::getAllTasksByUserId : " + ex.getMessage());
        }

        if(tasks.size() > 0)
            return tasks;
        else
            return null;

    }

    /**
     * Get all task's of specific project
     * @param projectId
     * @return array of tasks
     */
    public List<Task> getAllTasksByProjectId(String projectId){
        String sql = "SELECT * FROM tasks t WHERE t.projectId = ?";
        List<Task> tasks = new ArrayList<>();

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, projectId);
            ResultSet res = prep.executeQuery();
            while(res.next()){
                Task task = new Task(res);
                tasks.add(task);
            }
        }
        catch (SQLException ex){
            System.out.println("TaskDAO error::getAllTasksByProjectId : " + ex.getMessage());
        }

        if(tasks.size() > 0)
            return tasks;
        else
            return null;

    }


}
