/*
 */
package dk.cintix.graph.api;

import dk.cintix.graph.Film;
import dk.cintix.graph.Hero;
import dk.cintix.service.GalaxyService;
import io.vertx.core.cli.annotations.Description;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

/**
 *
 * @author migo
 */
@GraphQLApi
public class FilmResource {

    @Inject
    GalaxyService service;

    @Query("allFilms")
    @Description("Get all Films from a galaxy far far away")
    public List<Film> getAllFilms() {
        return service.getAllFilms();
    }
    
    @Query
    @Description("Get a Films from a galaxy far far away")
    public Film getFilm(@Name("filmId") int id) {
        return service.getFilm(id);
    }
    
    public List<Hero> heroes(@Source Film film) {
        return service.getHeroesByFilm(film);
    }
}
