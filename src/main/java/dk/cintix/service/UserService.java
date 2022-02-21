/*
 */
package dk.cintix.service;

import dk.cintix.graph.User;
import dk.cintix.db.jdbc.PooledDataSource;
import dk.cintix.db.managers.DataSourceManager;
import dk.cintix.db.managers.EntityManager;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author migo
 */
@ApplicationScoped
public class UserService {
    
    public UserService() {
        System.out.println("Adding database");
        PooledDataSource dataSource = new PooledDataSource("jdbc:postgresql://localhost:5432/quarkus", "quarkus_user", "quarkus_pass", 5);
        DataSourceManager.addDataSource("jdbc/demo", dataSource);
    }
    
    
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        List<dk.cintix.db.entities.User> all = EntityManager.create(dk.cintix.db.entities.User.class).loadAll();
        
        if (all != null && !all.isEmpty())
        for(dk.cintix.db.entities.User eUser : all) {
            System.out.println(eUser);
            User user = new User();
            user.id = (eUser.getId());
            user.username = (eUser.getUsername());
            user.email = (eUser.getEmail());
            user.password =  (eUser.getPassword());
            users.add(user);
        }
        
        return users;        
    }
    
}
