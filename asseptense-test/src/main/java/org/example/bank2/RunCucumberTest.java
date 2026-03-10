package org.example.bank2;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("org/example/bank2/bank2")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "org.example.bank2")
public class RunCucumberTest {

}
