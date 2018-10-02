package pl.krzpob.projects.githubintegration;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

/**
 * Created by krzypo on 17.10.17.
 */
@Slf4j
@RestController
class GitController {

    final private GitHubService gitHubService;

    public GitController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/repositories/{owner}/{repository-name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Repository repository(@PathVariable("owner") String owner,@PathVariable("repository-name") String repositoryName, HttpServletRequest request){

        return gitHubService.fetchRepositoryInfo(owner,repositoryName,request.getHeader("Authorization"));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity clientErrorHandler(HttpClientErrorException ex){
        log.error("Github return error code {} with message ", ex.getStatusCode().value(),ex.getResponseBodyAsString() );
            return new ResponseEntity(ex.getStatusCode());

    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<VndErrors> serverErrorHandler(HttpServerErrorException ex){
        log.error("Github return error code {} with message ", ex.getStatusCode().value(),ex.getResponseBodyAsString() );
        VndErrors vndErrors = new VndErrors("Error when try connect to github", ex.getMessage());
        switch (ex.getStatusCode()){
            case GATEWAY_TIMEOUT:
            case BAD_GATEWAY:
            case SERVICE_UNAVAILABLE:
                return new ResponseEntity<>(vndErrors, HttpStatus.NOT_FOUND);
            default: return new ResponseEntity<>(vndErrors, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ResourceAccessException.class)
    public VndErrors githubIsUnreachable(ResourceAccessException ex){
        return new VndErrors("GitHub is unreachable",ex.getMessage());
    }
}
