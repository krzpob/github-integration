package pl.krzpob.projects.githubintegration.port;


import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GitHubClient {
    public static final String URL = "https://api.github.com/repos/{owner}/{repo}";
    final private RestTemplate restTemplate;

    public GitHubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "repositories", key = "#username+#repositoryName")
    public GitHubRepoDTO fetchReposInfo(String username, String repositoryName, String authentication){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", authentication);
        HttpEntity httpEntity = new HttpEntity(headers);
        Map<String, Object> params = new HashMap<>();
        params.put("owner",username);
        params.put("repo", repositoryName);
        ResponseEntity<GitHubRepoDTO> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, httpEntity, GitHubRepoDTO.class, params);
        return responseEntity.hasBody()?responseEntity.getBody():null;
    }


}
