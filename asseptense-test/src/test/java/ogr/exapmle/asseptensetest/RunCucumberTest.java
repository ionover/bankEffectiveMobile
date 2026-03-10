package ogr.exapmle.asseptensetest;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("ogr/exapmle/asseptensetest")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "ogr.exapmle.asseptensetest")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/cucumber.html")
public class RunCucumberTest {

}
