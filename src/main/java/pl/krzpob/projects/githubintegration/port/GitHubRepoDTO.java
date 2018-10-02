package pl.krzpob.projects.githubintegration.port;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

/**
 * Created by krzypo on 16.10.17.
 */
public class GitHubRepoDTO {
    private String fullName;
    private String description;
    private String cloneUrl;
    private DateTime createdAt;
    private int stargazersCount;

    public String getDescription() {
        return description;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    @JsonProperty(value = "full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty(value = "description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty(value = "clone_url")
    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    @JsonProperty(value = "created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt=DateTime.parse(createdAt);
    }

    @JsonProperty(value = "stargazers_count")
    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fullName", fullName)
                .append("description", description)
                .append("cloneUrl", cloneUrl)
                .append("createdAt", createdAt)
                .append("stargazersCount", stargazersCount)
                .toString();
    }



}
