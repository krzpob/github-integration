package pl.krzpob.projects.githubintegration.port;


import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import pl.krzpob.projects.githubintegration.TestData;

import static org.assertj.core.api.BDDAssertions.then;
import static pl.krzpob.projects.githubintegration.TestData.REPOSITORY_FULL_NAME;

@Slf4j
public class GitHubClientIT {



    private GitHubClient gitHubClient;

    @Before
    public void setUp() throws Exception {
        gitHubClient = new GitHubClient(new RestTemplate());
    }

    @Test
    public void shouldGetInfoAboutMyReposFromGithub(){
        //given - TestData class

        //when
        GitHubRepoDTO gitHubRepoDTO = gitHubClient.fetchReposInfo(TestData.OWNER, TestData.REPOSITORY, TestData.AUTHORIZATION);
        log.info("Github response: {}", gitHubRepoDTO);
        //then
        then(gitHubRepoDTO).hasFieldOrPropertyWithValue("fullName", REPOSITORY_FULL_NAME);
        then(gitHubRepoDTO).hasFieldOrPropertyWithValue("cloneUrl","https://github.com/krzpob/jug-factor.git");

    }
}