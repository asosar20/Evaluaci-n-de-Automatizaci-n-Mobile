package com.store.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.store.steps", "com.store.hooks"},
        tags = "@login",
        plugin = {"pretty"}
)
public class AndroidTestRunner {}
