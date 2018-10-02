package pl.krzpob.projects.githubintegration;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import pl.krzpob.projects.githubintegration.port.GitHubClient;
import pl.krzpob.projects.githubintegration.port.GitHubRepoDTO;

import static pl.krzpob.projects.githubintegration.Repository.*;

/**
 * Created by krzypo on 17.10.17.
 */
@Slf4j
@Service
public class GitHubService {

    private GitHubClient gitHubClient;

    @Autowired
    public GitHubService(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }



    public Repository fetchRepositoryInfo(String owner, String repository, String authentication){
        GitHubRepoDTO gitHubRepoDTO = gitHubClient.fetchReposInfo(owner, repository, authentication);
        RepositoryBuilder builder = RepositoryBuilder.aRepository()
                .withFullName(gitHubRepoDTO.getFullName())
                .withCloneUrl(gitHubRepoDTO.getCloneUrl())
                .withCreatedAt(gitHubRepoDTO.getCreatedAt())
                .withDescription(gitHubRepoDTO.getDescription())
                .withStars(gitHubRepoDTO.getStargazersCount());
        return builder.build();
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<Cache>();
        caches.add(new ConcurrentMapCache("repositories"));
        cacheManager.setCaches(caches);
        return cacheManager;
    }

}
