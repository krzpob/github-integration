package pl.krzpob.projects.githubintegration.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by krzypo on 19.10.17.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", plugin = { "pretty" })
public class RunCukeTest {

}