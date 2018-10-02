package pl.krzpob.projects.githubintegration;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Repository {
    String fullName;
    String cloneUrl;
    int stars;
    String description;
}
