package tdl.code.dao;

import com.google.inject.Inject;
import tdl.code.entities.User;
import tdl.code.services.DataServices.DataService;
import tdl.code.services.HashServices.HashService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDAO {
    private final Connection connection;
    private final HashService hashService;
    private final DataService dataService;

    @Inject
    public UserDAO(DataService dataService, HashService hashService){
        this.dataService = dataService;
        this.hashService = hashService;
        this.connection = dataService.getConnection();
    }

    /**
     * Insert User in Db
     * @param user data to insert
     * @return user ID in table
     */
    public String add(User user){
        user.setId(UUID.randomUUID().toString());
        user.setSalt(hashService.hash(UUID.randomUUID().toString()));

        String sql = "INSERT INTO users (id,login,pass,salt,email) VALUES(?,?,?,?,?)";

        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, user.getId());
            prep.setString(2, user.getLogin());
            prep.setString(3, this.makePasswordHash(user.getPass(), user.getSalt()));
            prep.setString(4, user.getSalt());
            prep.setString(5, user.getEmail());
            prep.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println("MySqlService::add error: " + ex.getMessage());
            return null;
        }
        return user.getId();
    }

    /**
     * Checks if email is out from Db table
     * @param email string to test
     * @return true if email NOT in Db
     */
    public boolean isEmailFree(String email){
        String sql = "SELECT COUNT(u.email) FROM users u WHERE u.email = ? ";
        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, email);
            ResultSet res = prep.executeQuery();
            if(res.next()){
                return res.getInt(1) == 0;
            }
        }
        catch (SQLException ex){
            System.out.println("UserDAO::isEmailFree() error: " + ex.getMessage() + "\n" + sql + " --- " + email);
        }
        return false;
    }

    /**
     * Look for user in Db
     * @param email Credentials: email
     * @param password Credentials: password
     * @return entities.User or null if not found
     */
    public User getUserByCredentials(String email, String password){
        String sql = "SELECT * FROM users u WHERE u.email = ?";
        try(PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, email);
            ResultSet res = prep.executeQuery();
            if(res.next()){
                User user = new User(res);
                System.out.println(user.getEmail());
                String expectedHash = this.makePasswordHash(password, user.getSalt());
                if(expectedHash.equals(user.getPass())){
                    return user;
                }
            }
        }
        catch (SQLException ex){
            System.out.println("UserDAO::getUserByCredentials() error: " + ex.getMessage() + "\n" + sql + " --- " +
                    email + " || " + password);
        }
        return null;
    }

    /**
     * Search user by ID
     * @param userId user's id in Db
     * @return User entity or null
     */
    public User getUserById(String userId){
        String sql = "SELECT * FROM users u WHERE u.id = ?";
        try(PreparedStatement prep = dataService.getConnection().prepareStatement(sql)){
            prep.setString(1, userId);
            ResultSet res = prep.executeQuery();
            if(res.next()) return new User(res);
        }
        catch (SQLException ex){
            System.out.println("UserDAO::getUserById() error: " + ex.getMessage() + "\n" + sql + " --- " + userId);
        }
        return null;
    }

    private String makePasswordHash( String password, String salt ) {
        return hashService.hash( salt + password + salt ) ;
    }
}
