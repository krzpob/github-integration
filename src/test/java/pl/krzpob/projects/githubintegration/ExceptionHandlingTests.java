package pl.krzpob.projects.githubintegration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import org.apache.commons.collections.MapUtils;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.krzpob.projects.githubintegration.port.GitHubClient;
import pl.krzpob.projects.githubintegration.port.GitHubRepoDTO;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;

/**
 * Created by krzypo on 31.10.17.
 */

@RunWith(DataProviderRunner.class)
@TestConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExceptionHandlingTests {


    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public SpringMethodRule springMethodRule = new SpringMethodRule();

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DataProvider({"NOT_FOUND","UNAUTHORIZED","BAD_REQUEST","FORBIDDEN"})
    public void shouldPassThruHttpClientException(HttpStatus status){
        //given
        Map<String, Object> params = new HashMap<>();
        params.put("owner",TestData.OWNER);
        params.put("repo", TestData.REPOSITORY);

        given(
                restTemplate.exchange(
                        eq(GitHubClient.URL),
                        eq(HttpMethod.GET)
                        , any(HttpEntity.class),eq(GitHubRepoDTO.class),anyMapOf(String.class, Object.class)))
                .willThrow(new HttpClientErrorException(status, "some error"));
        //then
        ResponseEntity<Repository> entity = testRestTemplate.getForEntity("/repositories/" + TestData.OWNER + "/" + TestData.REPOSITORY, Repository.class);



        then(entity.getStatusCode()).isEqualTo(status);
    }

    private ResponseEntity<GitHubRepoDTO> responseEntity(HttpStatus status){
        return new ResponseEntity<GitHubRepoDTO>(status);
    }



}
