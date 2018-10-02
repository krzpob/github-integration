package pl.krzpob.projects.githubintegration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.joda.time.DateTime;
import org.springframework.core.style.ToStringCreator;

/**
 * Created by krzypo on 17.10.17.
 */
@JsonDeserialize(builder = Repository.RepositoryBuilder.class)
public class Repository {
    private String fullName;
    private String cloneUrl;
    private int stars;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private DateTime createdAt;

    public String getFullName() {
        return fullName;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public int getStars() {
        return stars;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("fullName", fullName)
                .append("cloneUrl", cloneUrl)
                .append("stars", stars)
                .append("description", description)
                .append("createdAt", createdAt)
                .toString();
    }

    public static final class RepositoryBuilder {
        private String fullName;
        private String cloneUrl;
        private int stars;
        private DateTime createdAt;
        private String description;

        private RepositoryBuilder() {
        }

        public static RepositoryBuilder aRepository() {
            return new RepositoryBuilder();
        }

        public RepositoryBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public RepositoryBuilder withCloneUrl(String cloneUrl) {
            this.cloneUrl = cloneUrl;
            return this;
        }

        public RepositoryBuilder withStars(int stars) {
            this.stars = stars;
            return this;
        }

        public RepositoryBuilder withCreatedAt(DateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RepositoryBuilder withDescription(String description){
            this.description=description;
            return this;
        }

        public Repository build() {
            Repository repository = new Repository();
            repository.fullName = this.fullName;
            repository.stars = this.stars;
            repository.createdAt = this.createdAt;
            repository.cloneUrl = this.cloneUrl;
            repository.description = this.description;
            return repository;
        }
    }
}
