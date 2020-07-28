package selenium.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class WebDriverLogger extends AbstractWebDriverEventListener {

    File fileWithLogs;

    public WebDriverLogger() {
        String newLogFilePath = System.getProperty("user.dir") + "/src/test/resources/driver_logs" +
                "/WebDriver_logs" +
                LocalDateTime.now().toString().replaceAll(":", "-") +
                ".txt";
        fileWithLogs = new File(newLogFilePath);
    }

    private void writeToLogFile(String log) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileWithLogs, true));
            out.write(log);
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occurred" + e);
        }
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        writeToLogFile("\nprocessing: navigate to " + url);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        writeToLogFile("\nsuccess: navigated to " + url);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        writeToLogFile("\nprocessing: navigate back");
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        writeToLogFile("\nsuccess: navigate back");
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        writeToLogFile("\nprocessing: navigate forward");
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        writeToLogFile("\nsuccess: navigate forward");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        writeToLogFile("\nprocessing: navigate refresh (page refresh)");
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        writeToLogFile("\nsuccess: navigate refresh (page refresh)");
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        if (element != null) {
            writeToLogFile("\nprocessing: find " + by + " in element " + element);
        } else {
            writeToLogFile("\nprocessing: find " + by);
        }
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        writeToLogFile("\nsuccess: find " + by);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        writeToLogFile("\nprocessing: click on " + element);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        writeToLogFile("\nsuccess: click on " + element);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        writeToLogFile("\nprocessing: change value of " + element + " to " + Arrays.toString(keysToSend));
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        writeToLogFile("\nsuccess: change value of " + element + " to " + Arrays.toString(keysToSend));
    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {
        writeToLogFile("\nprocessing: switch to window " + windowName);
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        writeToLogFile("\nsuccess: switch to window " + windowName);
    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        writeToLogFile("\nprocessing: get text of " + element);
    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {
        writeToLogFile("\nsuccess: get text of " + element + "; gotten text: " + text);
    }
}
