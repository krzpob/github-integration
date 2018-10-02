package pl.krzpob.projects.githubintegration.port;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import org.joda.time.DateTime;

import java.net.URI;

@Value
@Builder
@JsonDeserialize(builder = GitHubRepoDTO.GitHubRepoDTOBuilder.class)
public class GitHubRepoDTO {
    String fullName;
    String description;
    URI cloneUrl;
    Integer stargazersCount;
    DateTime createdAt;

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonPOJOBuilder(withPrefix = "")
    public static class GitHubRepoDTOBuilder {

    }
}
