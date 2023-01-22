package com.application.utilities;

import com.cucumber.listener.Reporter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Slf4j
public class LogsTool {

    private static Logger logger = LoggerFactory.getLogger(LogsTool.class);
    private static final String EXTENT_CONFIG_PATH = "src/test/resources/extent-config.xml";

    public static void setExtentReportsInfo() {

        try {
            Reporter.loadXMLConfig(new File(EXTENT_CONFIG_PATH));
            logger.info("The ExtentReports configuration file has been read from: {}", EXTENT_CONFIG_PATH);
        } catch (Exception e) {
            logger.error("Failure to read extent-config.xml: {}", e);
        }

        Reporter.setSystemInfo("User", System.getProperty("user.name"));
        Reporter.setSystemInfo("Operating System", String.format("%s (%s)", System.getProperty("os.name"), System.getProperty("os.arch")));
        Reporter.setSystemInfo("Environment", "QA");
        Reporter.setSystemInfo("Java version", System.getProperty("java.version"));
        Reporter.setTestRunnerOutput("Test Execution Cucumber Report");
    }
}
