package pl.krzpob.projects.githubintegration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
public class GithubIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubIntegrationApplication.class, args);


	}

	@Bean
	RestTemplate restTemplate(){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
				= new HttpComponentsClientHttpRequestFactory(httpClient);

		return new RestTemplate(clientHttpRequestFactory);
	}
}
