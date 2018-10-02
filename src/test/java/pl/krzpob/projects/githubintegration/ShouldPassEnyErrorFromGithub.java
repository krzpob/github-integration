package pl.krzpob.projects.githubintegration;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureStubRunner(ids = {
        "pl.krzpob.projects:github-integration:+:stubs:6565" }, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ShouldPassEnyErrorFromGithub {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void checkFor401Error() throws Exception {
        webTestClient.get().uri("/repositories/krzpob/jug-factor")
                .header(HttpHeaders.AUTHORIZATION, "Basic a3J6cG9iQGdtYWlsLmNvbToyVG0yLDMtcXJ0").exchange()
                .expectStatus().isUnauthorized();

    }

    @Test
    public void checkFor404ErrorForNoExistingRepo() {
        webTestClient.get().uri("/repositories/krzpob/jshop").exchange().expectStatus().isNotFound();
    }
}
