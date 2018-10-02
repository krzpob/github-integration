package pl.krzpob.projects.githubintegration.port;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pl.krzpob.projects.githubintegration.GitHubProperties;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class GitHubClient {

    private GitHubProperties gitHubProperties;
    private WebClient webClient;

    public GitHubClient(GitHubProperties gitHubProperties) {
        this.gitHubProperties = gitHubProperties;
        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(options -> options
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 600).compression(true).afterNettyContextInit(ctx -> {
                    ctx.onClose(() -> {
                        log.info("Close");
                    });
                    ctx.addHandlerLast(new ReadTimeoutHandler(300, TimeUnit.MILLISECONDS));
                }));
        this.webClient = WebClient.builder().clientConnector(connector).build();
    }

    public Mono<GitHubRepoDTO> fetchRepoInfo(String username, String repositoryName, String authentication) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(gitHubProperties.getUrl() + "repos/")
                .pathSegment(username, repositoryName).build();
        return webClient.get().uri(uriComponents.toString()).header("Authorization", authentication)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(GitHubRepoDTO.class)
                .doOnError(WebClientResponseException.class, e -> {
                    throw new ResponseStatusException(e.getStatusCode());
                }).doOnError(SSLException.class, e -> {
                    throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT);
                }).doOnError(ReadTimeoutException.class, e -> {
                    throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT);
                }).doOnError(e -> log.error("Error! {}, {}", e.getCause(), e.getClass()))
                .doOnSuccess(gitHubRepoDTO -> log.info("DTO: {}", gitHubRepoDTO)).cache(Duration.ofMillis(200));

    }
}
