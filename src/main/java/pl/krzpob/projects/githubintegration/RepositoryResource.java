package pl.krzpob.projects.githubintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class RepositoryResource {

    @Bean
    public RouterFunction<ServerResponse> repositoryRouterFunction(RepostioryHandler repostioryHandler) {
        RouterFunction<ServerResponse> repoRoute = route(GET("/repositories/{owner}/{repositoryName}"),
                repostioryHandler::fetchRepository);
        return repoRoute;

    }
}
