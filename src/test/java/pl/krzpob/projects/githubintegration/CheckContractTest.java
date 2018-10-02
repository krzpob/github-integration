package pl.krzpob.projects.githubintegration;

import io.github.robwin.swagger.test.SwaggerAssertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by krzypo on 17.10.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckContractTest {

        @LocalServerPort
        private Long port;

        @Test
        @Ignore
        public void checkContract(){
            String designFirstSwagger = CheckContractTest.class.getResource("/schema.yml").getPath();
            SwaggerAssertions.assertThat(new StringBuilder().append("http://localhost:").append(port).append("/v2/api-docs").toString()).satisfiesContract(designFirstSwagger);
        }
}
