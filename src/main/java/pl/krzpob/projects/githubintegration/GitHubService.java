package pl.krzpob.projects.githubintegration;

import org.springframework.stereotype.Service;
import pl.krzpob.projects.githubintegration.port.GitHubClient;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Mapuje dane zwracane przez githuba, na oczekiwane
 */
@Service
public class GitHubService {

    private GitHubClient gitHubClient;

    public GitHubService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    public Mono<Repository> fetchRepoInfo(String owner, String repository, String auth) {
        return gitHubClient.fetchRepoInfo(owner, repository, auth)
                .map(gitHubRepoDTO -> Repository.builder().fullName(gitHubRepoDTO.getFullName())
                        .cloneUrl(gitHubRepoDTO.getCloneUrl().toString()).description(gitHubRepoDTO.getDescription())
                        .stars(gitHubRepoDTO.getStargazersCount()).build());
    }
}
