package org.dimensinfin.eveonline.neocom.infinity.backend.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "src/test/resources/features" },
		glue = { "org.dimensinfin.eveonline.neocom.infinity.backend.steps" },
		plugin = { "pretty", "json:target/cucumber_report.json" },
		tags = { "not @skip_scenario", "not @front", "not @duplication", "@NIB04.02" })
public class RunAcceptanceTests {}
