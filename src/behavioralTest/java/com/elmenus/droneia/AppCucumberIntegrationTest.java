package com.elmenus.droneia;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/behavioralTest/resources/features",
        glue = {
                "com.elmenus.droneia.drones",
                "com.elmenus.droneia.orders",
                "com.elmenus.droneia.configs"
        },
        plugin = {"pretty", "html:target/cucumber-drones-reports.html"}
)
public class AppCucumberIntegrationTest {
}
