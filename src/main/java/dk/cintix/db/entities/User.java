package dk.cintix.db.entities;

import java.util.*;
import java.lang.*;
import dk.cintix.db.annotations.Entity;
import dk.cintix.db.managers.UserManager;

/**;
 * Author: MockDB
 **/

@Entity(manager = UserManager.class)
public abstract class User  {

    private int id;
    private String username;
    private String password;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int __value) {
        id = __value;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String __value) {
        username = __value;
    }

    public void setPassword(String __value) {
        password = __value;
    }

    public void setEmail(String __value) {
        email = __value;
    }


    public abstract List<User> loadAll();
    public abstract boolean create();
    public abstract boolean update();
    public abstract boolean delete();

}