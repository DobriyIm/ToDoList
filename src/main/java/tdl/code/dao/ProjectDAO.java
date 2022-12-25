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

public class ProjectDAO {
    private final Connection connection;
    private final HashService hashService;
    private final DataService dataService;

    @Inject
    public ProjectDAO(DataService dataService, HashService hashService){
        this.dataService = dataService;
        this.hashService = hashService;
        this.connection = dataService.getConnection();
    }

    /**
     * Insert project in to Db
     * @param project data to insert
     * @return project ID in table
     */
    public String add(Project project){

        project.setId(UUID.randomUUID().toString());
        project.setIsCompleted(false);

        String sql = "INSERT INTO projects (id, userId, name, creationDate, completionDate, isCompleted) VALUES(?,?,?,?,?,?) ";

        System.out.println(project.getUserId() + "P" + project.getId());

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, project.getId());
            prep.setString(2, project.getUserId());
            prep.setString(3, project.getName());
            prep.setDate(4, project.getCreationDate());
            prep.setString(5, project.getCompletionDate());
            prep.setBoolean(6, project.getIsCompleted());

            prep.executeUpdate();

        }catch (SQLException ex){
            System.out.println("MySqlService::add project error : " + ex.getMessage());
            return null;
        }
        return project.getId();
    }

    /**
     * Update project in Db
     * @param project data to update
     * @return project ID in table
     */
    public String update(Project project){

        String sql = "UPDATE projects SET name = ?, completionDate = ?, isCompleted = ? WHERE id = ?" ;

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, project.getName());
            prep.setString(2, project.getCompletionDate());
            prep.setBoolean(3, project.getIsCompleted());
            prep.setString(4, project.getId());

            prep.executeUpdate();

        }catch (SQLException ex){
            System.out.println("MySqlService::update project error : " + ex.getMessage());
            return null;
        }
        return project.getId();
    }

    /**
     * Get all project's of specific user
     * @param userId
     * @return array of tasks
     */
    public List<Project> getAllProjectsByUserId(String userId){
        String sql = "SELECT * FROM projects p WHERE p.userId = ?";
        List<Project> projects = new ArrayList<>();

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, userId);
            ResultSet res = prep.executeQuery();
            while(res.next()){
                Project project = new Project(res);
                projects.add(project);
            }
        }
        catch (SQLException ex){
            System.out.println("ProjectDAO error::getAllProjects : " + ex.getMessage());
        }

        if(projects.size() > 0)
            return projects;
        else
            return null;

    }

    /**
     * Get project by ID
     * @param projectId
     * @return project
     */
    public Project getProjectById(String projectId){
        String sql = "SELECT * FROM projects p WHERE p.id = ? ";

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, projectId);
            ResultSet res = prep.executeQuery();
            if(res.next()){
                Project project = new Project(res);
                return  project;
            }
        }catch (SQLException ex){
            System.out.println("ProjectDAO error::getProjectById: " + ex.getMessage());
        }
        return null;
    }
}
