package pl.krzpob.projects.githubintegration;

import org.assertj.core.api.BDDSoftAssertions;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.krzpob.projects.githubintegration.port.GitHubClient;
import pl.krzpob.projects.githubintegration.port.GitHubRepoDTO;
import pl.krzpob.projects.githubintegration.port.GitHubRepoDTOBuilder;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProperMappingFromGithubTest {

    @InjectMocks
    private GitHubService gitHubService;

    @Mock
    private GitHubClient gitHubClient;

    @Test
    public void checkMapping(){
        //given
        DateTime now = new DateTime();
        GitHubRepoDTO gitHubRepoDTO = GitHubRepoDTOBuilder.aGitHubRepoDTO()
                .withCloneUrl("https://gihub/jug-factor")
                .withDescription("Lorem ipsum")
                .withCreatedAt(now)
                .withFullName(TestData.REPOSITORY_FULL_NAME)
                .withStargazersCount(10)
                .build();
        given(gitHubClient.fetchReposInfo(TestData.OWNER, TestData.REPOSITORY, TestData.AUTHORIZATION)).willReturn(gitHubRepoDTO);
        //when
        Repository repository = gitHubService.fetchRepositoryInfo(TestData.OWNER, TestData.REPOSITORY, TestData.AUTHORIZATION);
        //then
        BDDSoftAssertions softAssertions = new BDDSoftAssertions();
        softAssertions.then(repository).hasFieldOrPropertyWithValue("cloneUrl",gitHubRepoDTO.getCloneUrl());
        softAssertions.then(repository).hasFieldOrPropertyWithValue("description",gitHubRepoDTO.getDescription());
        softAssertions.then(repository).hasFieldOrPropertyWithValue("createdAt",now);
        softAssertions.then(repository).hasFieldOrPropertyWithValue("fullName", TestData.REPOSITORY_FULL_NAME);
        softAssertions.then(repository).hasFieldOrPropertyWithValue("star", 10);

    }

}