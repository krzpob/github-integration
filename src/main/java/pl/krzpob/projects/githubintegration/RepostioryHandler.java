package pl.krzpob.projects.githubintegration;

import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Slf4j
@Component
class RepostioryHandler {
    private GitHubService gitHubService;

    public RepostioryHandler(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    Mono<ServerResponse> fetchRepository(ServerRequest serverRequest) {
        log.info("Header: {}", serverRequest.headers().asHttpHeaders().getFirst("Authorization"));
        return ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(gitHubService.fetchRepoInfo(serverRequest.pathVariable("owner"),
                        serverRequest.pathVariable("repositoryName"),
                        serverRequest.headers().asHttpHeaders().getFirst("Authorization")), Repository.class)
                .onErrorResume(ReadTimeoutException.class,
                        e -> Mono.error(new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT)));
    }
}
