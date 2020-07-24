package selenium.app;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.helpers.pages.HomepageHelper;
import selenium.helpers.NavigationHelper;
import selenium.helpers.pages.SearchResultsPageHelper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ApplicationManager {

    private final Properties properties;
    private WebDriver wd;
    private WebDriverWait wait;
    public int implicitWaitTimeAmount = 10;
    private String browser;
    private NavigationHelper navigationHelper;
    private HomepageHelper homepageHelper;
    private SearchResultsPageHelper searchResultsPageHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if (wd != null) {
            wd.quit();
        }
    }

    public WebDriver getDriver() {
        if (wd == null) {
            if ("".equals(properties.getProperty("selenium.server"))) {
                switch (browser) {
                    case BrowserType.CHROME: {
                        File file = new File("src/test/resources/drivers/chromedriver.exe");
                        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
                        wd = new ChromeDriver();
                        break;
                    }
                    case BrowserType.FIREFOX: {
                        File file = new File("src/test/resources/drivers/geckodriver.exe");
                        System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
                        wd = new FirefoxDriver();
                        break;
                    }
                    case BrowserType.IE: {
                        File file = new File("src/test/resources/drivers/IEDriverServer.exe");
                        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
                        wd = new InternetExplorerDriver();
                        break;
                    }
                    case BrowserType.EDGE: {
                        File file = new File("src/test/resources/drivers/msedgedriver.exe");
                        System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
                        wd = new EdgeDriver();
                        break;
                    }
                    case BrowserType.OPERA_BLINK: {
                        File file = new File("src/test/resources/drivers/operadriver.exe");
                        System.setProperty("webdriver.opera.driver", file.getAbsolutePath());
                        wd = new OperaDriver();
                        break;
                    }
                    case "mobile":
                        Map<String, String> mobileEmulation = new HashMap<>();
                        mobileEmulation.put("deviceName", System.getProperty("deviceName", "iPhone 6"));

                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                        wd = new ChromeDriver(chromeOptions);
                        break;
                }
            } else {
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("os", "OS X");
                caps.setCapability("os_version", "Mojave");
                caps.setCapability("browser", "Safari");
                caps.setCapability("browser_version", "12.0");
                caps.setCapability("browserstack.local", "false");
                caps.setCapability("browserstack.selenium_version", "3.141.59");
                caps.setCapability("name", "qwerasdf17's First Test");
                try {
                    wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), caps);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            wd.manage().window().maximize();
            wd.manage().timeouts().implicitlyWait(implicitWaitTimeAmount, SECONDS);
            wait = new WebDriverWait(wd, 10);
        }
        return wd;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
    }

    public Dimension getWindowSize() {
        return wd.manage().window().getSize();
    }

    public Dimension getViewportSize() {
        Long width = (Long) ((JavascriptExecutor) wd).executeScript(" return document.documentElement.clientWidth;");
        Long height = (Long) ((JavascriptExecutor) wd).executeScript(" return document.documentElement.clientHeight;");
        return new Dimension(width.intValue(), height.intValue());
    }

    public void refresh() {
        wd.navigate().refresh();
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Properties getProperties() {
        return properties;
    }

    public int getNumberOfWindows() {
        return wd.getWindowHandles().size();
    }


    public NavigationHelper goTo() {
        if (navigationHelper == null) {
            navigationHelper = new NavigationHelper(this);
        }
        return navigationHelper;
    }

    public HomepageHelper homepage() {
        if (homepageHelper == null) {
            homepageHelper = new HomepageHelper(this);
        }
        return homepageHelper;
    }

    public SearchResultsPageHelper searchResultsPage() {
        if (searchResultsPageHelper == null) {
            searchResultsPageHelper = new SearchResultsPageHelper(this);
        }
        return searchResultsPageHelper;
    }
}
