package pl.krzpob.projects.githubintegration.port;

import org.joda.time.DateTime;

/**
 * Created by krzypo on 19.10.17.
 */
public final class GitHubRepoDTOBuilder {
    private String fullName;
    private String description;
    private String cloneUrl;
    private DateTime createdAt;
    private int stargazersCount;

    private GitHubRepoDTOBuilder() {
    }

    public static GitHubRepoDTOBuilder aGitHubRepoDTO() {
        return new GitHubRepoDTOBuilder();
    }

    public GitHubRepoDTOBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public GitHubRepoDTOBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public GitHubRepoDTOBuilder withCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
        return this;
    }

    public GitHubRepoDTOBuilder withCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public GitHubRepoDTOBuilder withStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
        return this;
    }

    public GitHubRepoDTO build() {
        GitHubRepoDTO gitHubRepoDTO = new GitHubRepoDTO();
        gitHubRepoDTO.setFullName(fullName);
        gitHubRepoDTO.setDescription(description);
        gitHubRepoDTO.setCloneUrl(cloneUrl);
        gitHubRepoDTO.setCreatedAt(createdAt.toString());
        gitHubRepoDTO.setStargazersCount(stargazersCount);
        return gitHubRepoDTO;
    }
}
