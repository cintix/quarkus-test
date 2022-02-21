/*
 */
package dk.cintix.graph.api;

import dk.cintix.graph.User;
import dk.cintix.service.UserService;
import io.vertx.core.cli.annotations.Description;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

/**
 *
 * @author migo
 */
@GraphQLApi
public class UsersResource {

    @Inject
    UserService service;

    @Query("allUsers")
    @Description("Get all Users")
    public List<User> getUsers() {
        return service.getAllUsers();
    }

}
