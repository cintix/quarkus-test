package dk.cintix.db.managers;

import dk.cintix.db.entities.User;
import dk.cintix.db.annotations.InjectConnection;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.util.logging.*;


/**
 *
 * @author MockDB
 */
public class UserManager extends User {

    private static final Logger LOGGER = Logger.getLogger(UserManager.class.getName());
    private final DataSource dataSource = DataSourceManager.getInstance("jdbc/demo");

    private final static String TABLE_SQL = "CREATE TABLE users(id serial not null primary key,username varchar,password varchar,email varchar);".intern();
    private final static String CREATE_SQL = "INSERT INTO users ( username, password, email) VALUES (?,?,?)".intern();
    private final static String UPDATE_SQL = "UPDATE User users username= ?,password= ?,email= ? WHERE id = ?".intern();
    private final static String REMOVE_SQL = "UPDATE User users username= ?,password= ?,email= ? WHERE id = ?".intern();
    private final static String SELECT_ALL_SQL = "SELECT * FROM users;".intern();

    @InjectConnection
    private final Connection cachedConnection = null;


    @Override
    public List<User> loadAll() {
        List<User> entities = new ArrayList<>();
        try {
            Connection connection = (cachedConnection != null && !cachedConnection.isClosed()) ? cachedConnection : dataSource.getConnection();
            try ( PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL)) {
                try ( ResultSet executeQuery = preparedStatement.executeQuery()) {
                    while (executeQuery.next()) {
                        entities.add(readEntity(executeQuery));
                    }
                }
            }
            if (cachedConnection == null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "User.loadAll() threw an exception", e);
        }
        return entities;
    }

    @Override
    public boolean create() {
        try {
            Connection connection = (cachedConnection != null && !cachedConnection.isClosed()) ? cachedConnection : dataSource.getConnection();
            try ( PreparedStatement ps = connection.prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, getUsername());
                ps.setString(2, getPassword());
                ps.setString(3, getEmail());
                
                if (ps.executeUpdate() > 0) {
                    if (cachedConnection == null) {
                        connection.close();
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "User.create() threw an exception", e);
        }
        return false;
    }

    @Override
    public boolean update() {
        try {
            Connection connection = (cachedConnection != null && !cachedConnection.isClosed()) ? cachedConnection : dataSource.getConnection();
            try ( PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {
                ps.setString(1, getUsername());
                ps.setString(2, getPassword());
                ps.setString(3, getEmail());
                ps.setInt(4, (int) getId());

                int affectedCount = ps.executeUpdate();
                if (cachedConnection == null) {
                    connection.close();
                }
                return affectedCount > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "User.update() threw an exception", e);
        }
        return false;
    }

    @Override
    public boolean delete(){
        try {
            Connection connection = (cachedConnection != null && !cachedConnection.isClosed()) ? cachedConnection : dataSource.getConnection();
            try ( PreparedStatement ps = connection.prepareStatement(REMOVE_SQL)) {
                ps.setInt(1, (int) getId());
                int affectedCount = ps.executeUpdate();
                if (cachedConnection == null) {
                    connection.close();
                }
                return (affectedCount > 0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "User.delete() threw an exception", e);
        }
        return false;
    }

    private User readEntity(ResultSet resultset) throws SQLException {
        User entity = EntityManager.create(User.class, cachedConnection);
        entity.setUsername(resultset.getString("username"));
        entity.setPassword(resultset.getString("password"));
        entity.setEmail(resultset.getString("email"));
        entity.setId(resultset.getInt("id"));
        return entity;
    }

}