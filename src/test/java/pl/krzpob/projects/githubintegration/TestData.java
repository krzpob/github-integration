package pl.krzpob.projects.githubintegration;

public interface TestData {
    String OWNER = "krzpob";
    String REPOSITORY = "jug-factor";

    String AUTHORIZATION = "Basic a3J6cG9iQGdtYWlsLmNvbToyVG0yLDMtcXJ0";

    String REPOSITORY_FULL_NAME = new StringBuilder(OWNER).append("/").append(REPOSITORY).toString();
}
