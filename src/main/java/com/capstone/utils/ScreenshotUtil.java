package com.capstone.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Saves a screenshot as screenshots/<testName>_<timestamp>.png */
public final class ScreenshotUtil {

    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtil() {
    }

    /** @return the absolute path of the saved screenshot */
    public static String capture(WebDriver driver, String testName) {
        String fileName = testName + "_" + LocalDateTime.now().format(TIMESTAMP) + ".png";
        Path target = Paths.get("screenshots", fileName);
        try {
            Files.createDirectories(target.getParent());
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save screenshot " + fileName, e);
        }
        return target.toAbsolutePath().toString();
    }
}
