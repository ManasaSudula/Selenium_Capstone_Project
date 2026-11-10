package com.capstone.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Creates ONE ExtentReports object for the whole run and sets up the HTML file in reports/. */
public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            new File("reports").mkdirs();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/ExtentReport_" + timestamp + ".html");
            sparkReporter.config().setDocumentTitle("SauceDemo Test Results");
            sparkReporter.config().setReportName("SauceDemo Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Browser", ConfigReader.get("browser"));
            extent.setSystemInfo("Application URL", ConfigReader.get("base.url"));
        }
        return extent;
    }
}
