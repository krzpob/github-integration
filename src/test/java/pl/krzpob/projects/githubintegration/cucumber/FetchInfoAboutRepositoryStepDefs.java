package pl.krzpob.projects.githubintegration.cucumber;

import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.krzpob.projects.githubintegration.TestData;
import pl.krzpob.projects.githubintegration.port.GitHubClient;
import pl.krzpob.projects.githubintegration.port.GitHubRepoDTO;
import pl.krzpob.projects.githubintegration.port.GitHubRepoDTOBuilder;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by krzypo on 19.10.17.
 */
@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc()
public class FetchInfoAboutRepositoryStepDefs {

    private static final String CLONE_URL = "https://github/test";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Autowired
    private GitHubClient gitHubClient;

    private String author;
    private String repository;
    private String contentAsString;
    private DateTime now =new DateTime();

    @Before
    public void setUp() throws Exception {
        log.info("gitHubClient test {}", gitHubClient);

        GitHubRepoDTO dto =GitHubRepoDTOBuilder.aGitHubRepoDTO()
                .withCloneUrl(CLONE_URL)
                .withCreatedAt(now)
                .withFullName(TestData.REPOSITORY_FULL_NAME)
                .withStargazersCount(10)
                .withDescription("Lorem ipsum blabla")
                .build();

        given(gitHubClient.fetchReposInfo(TestData.OWNER,TestData.REPOSITORY, "Basic a3J6cG9iQGdtYWlsLmNvbToyVG0yLDMtcXJ0"))
                .willReturn(dto);
        log.info("Client: {}", dto);

    }

    @Given("^a author (.+)$")
    public void givenAuthor(String author){
        this.author = author;
    }

    @Given("^he has a repo (.+)$")
    public void he_has_a_repo_jug_factor(String repository) throws Throwable {
        this.repository=repository;
    }

    @When("^call service$")
    public void call_service() throws Throwable {
        log.info("mockmvctest: {}", mockMvc);
        contentAsString = mockMvc.perform(get("/repositories/" + author + "/" + repository).header("Authorization",TestData.AUTHORIZATION))
                .andReturn()
                .getResponse()
                .getContentAsString();
        log.info("response content: {}", contentAsString);
    }

    @Then("^get fullName of repo$")
    public void get_fullName_of_repo() throws Throwable {
        String fullName = JsonPath.parse(contentAsString).read("$['fullName']");
        then(fullName).contains(TestData.REPOSITORY_FULL_NAME);
    }

    @Then("^amount of stars$")
    public void amount_of_stars() throws Throwable {
        Integer stars = JsonPath.parse(contentAsString).read("$['stars']");
        then(stars).isEqualTo(10);
    }

    @Then("^url for clonning repo$")
    public void url_for_clonning_repo() throws Throwable {
        String cloneUrl = JsonPath.parse(contentAsString).read("$['cloneUrl']");
        then(cloneUrl).contains(CLONE_URL);
    }

    @Then("^some description$")
    public void empty_description() throws Throwable {
        String description = JsonPath.parse(contentAsString).read("$['description']");
        then(description).contains("Lorem ipsum");
    }

}
