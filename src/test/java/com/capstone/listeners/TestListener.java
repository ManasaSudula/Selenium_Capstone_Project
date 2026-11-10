package com.capstone.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.capstone.base.BaseTest;
import com.capstone.utils.ExtentManager;
import com.capstone.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG calls these methods automatically when a test starts, passes, fails or is skipped.
 * We use it to fill the Extent report and take a screenshot on failure.
 */
public class TestListener implements ITestListener {

    private final ExtentReports extent = ExtentManager.getInstance();
    private ExtentTest currentTest;

    @Override
    public void onTestStart(ITestResult result) {
        currentTest = createReportEntry(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        currentTest.pass("Test passed");
        currentTest = null;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (currentTest == null) {
            currentTest = createReportEntry(result);
        }
        currentTest.fail(result.getThrowable());
        attachScreenshot(result);
        currentTest = null;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (currentTest == null) {
            currentTest = createReportEntry(result);
        }
        if (result.getThrowable() != null) {
            currentTest.skip(result.getThrowable());
        } else {
            currentTest.skip("Test skipped");
        }
        currentTest = null;
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush(); // writes the HTML report to disk
    }

    private ExtentTest createReportEntry(ITestResult result) {
        String description = result.getMethod().getDescription();
        ExtentTest entry = extent.createTest(result.getMethod().getMethodName(), description == null ? "" : description);
        entry.assignCategory(result.getMethod().getGroups());
        return entry;
    }

    private void attachScreenshot(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testInstance).getDriver();
            if (driver != null) {
                String path = ScreenshotUtil.capture(driver, result.getMethod().getMethodName());
                currentTest.addScreenCaptureFromPath(path, "Failure screenshot");
            }
        }
    }
}
