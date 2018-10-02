package pl.krzpob.projects.githubintegration.cucumber;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.krzpob.projects.githubintegration.Repository;
import pl.krzpob.projects.githubintegration.TestData;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@AutoConfigureWebTestClient
@AutoConfigureStubRunner(ids = {
        "pl.krzpob.projects:github-integration:+:stubs:6565" }, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class FetchInfoAboutRepositoryStepDefs {

    private static final String CLONE_URL = "https://github/test";

    @Autowired
    private WebTestClient client;

    @Autowired
    private ApplicationContext applicationContext;

    private String author;
    private String repository;
    private Repository response;

    @Before
    public void setUp() throws Exception {
        log.info("TEST stup");

        log.info("client: {}", client);
    }

    @Given("^a author (.+)$")
    public void givenAuthor(String author) {
        this.author = author;
    }

    @Given("^he has a repo (.+)$")
    public void he_has_a_repo_jug_factor(String repository) throws Throwable {
        this.repository = repository;
    }

    @When("^call service$")
    public void call_service() throws Throwable {
        log.info("client1: {}", client);
        WebTestClient.ResponseSpec responseSpec = client.get().uri("/repositories/" + author + "/" + repository)
                .exchange();
        WebTestClient.BodySpec<Repository, ?> repositoryBodySpec = responseSpec.expectBody(Repository.class);
        EntityExchangeResult<Repository> repositoryEntityExchangeResult = repositoryBodySpec.returnResult();
        response = repositoryEntityExchangeResult.getResponseBody();
        log.info("responce: {}", response);
    }

    @Then("^get fullName of repo$")
    public void get_fullName_of_repo() throws Throwable {
        then(response.getFullName()).isEqualTo(TestData.REPOSITORY_FULL_NAME);
    }

    @Then("^amount of stars$")
    public void amount_of_stars() throws Throwable {
        then(response.getStars()).isEqualTo(10);
    }

    @Then("^url for clonning repo$")
    public void url_for_clonning_repo() throws Throwable {
        then(response.getCloneUrl()).isEqualTo(CLONE_URL);
    }

    @Then("^empty description$")
    public void empty_description() throws Throwable {
        then(response.getDescription()).contains("Lorem ipsum");
    }

}
