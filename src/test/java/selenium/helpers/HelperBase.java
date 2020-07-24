package selenium.helpers;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.app.ApplicationManager;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class HelperBase {

    ApplicationManager app;
    protected WebDriver wd;
    protected WebDriverWait wait;
    protected long implicitWaitTimeAmount;

    public HelperBase(ApplicationManager app) {
        this.app = app;
        this.wd = app.getDriver();
        this.wait = app.getWait();
        this.implicitWaitTimeAmount = app.implicitWaitTimeAmount;
    }

    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = wd.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                wait.until(elementToBeClickable(locator));
                wd.findElement(locator).clear();
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void attach(By locator, File file) {
        if (file != null) {
            wait.until(presenceOfElementLocated(locator));
            wd.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    public boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wd.findElement(locator);
            wd.manage().timeouts().implicitlyWait(implicitWaitTimeAmount, TimeUnit.SECONDS);
            return true;
        } catch (NoSuchElementException ex) {
            wd.manage().timeouts().implicitlyWait(implicitWaitTimeAmount, TimeUnit.SECONDS);
            return false;
        }
    }

    public boolean areElementsPresent(By locator) {
        return wd.findElements(locator).size() > 0;
    }

    public boolean areElementsPresent(List<WebElement> elements) {
        return elements.size() > 0;
    }

    public boolean isElementPresent(String byText) {
        try {
            wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wd.findElement(By.xpath("//*[.='" + byText + "']"));
            wd.manage().timeouts().implicitlyWait(implicitWaitTimeAmount, TimeUnit.SECONDS);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    protected void switchFrame(WebElement frameElement) {
        wd.switchTo().frame(frameElement);
    }

    protected void switchToBaseFrame() {
        wd.switchTo().defaultContent();
    }

    protected WebElement getElement(By locator) {
        return wd.findElement(locator);
    }

    protected void editElementsContent(WebElement element, String text) {
        ((JavascriptExecutor)wd).executeScript("var ele=arguments[0]; ele.innerHTML = '" + text + "';", element);
    }

    public void pause(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void waitElementContainSomeText(By locator, int seconds) {
        for(int i = 0; i < seconds; i++) {
            pause(1);
            try {
                if(! wd.findElement(locator).getText().equals("")) return;
            } catch (StaleElementReferenceException ex) {
                continue;
            }
        }
    }

    public static String XpathExpression(String value) {
        if (!value.contains("'"))
            return '\'' + value + '\'';

        else if (!value.contains("\""))
            return '"' + value + '"';

        else
            return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }

    protected void scrollTo(By locator) {
        WebElement element = wd.findElement(locator);
        ((JavascriptExecutor) wd).executeScript("arguments[0].scrollIntoView({" +
                "behavior: 'auto'," +
                "block: 'center'," +
                "inline: 'center'" +
                "});", element);
    }

    public Dimension getViewportSize() {
        Long width = (Long) ((JavascriptExecutor) wd).executeScript(" return document.documentElement.clientWidth;");
        Long height = (Long) ((JavascriptExecutor) wd).executeScript(" return document.documentElement.clientHeight;");
        return new Dimension(width.intValue(), height.intValue());
    }

    protected boolean isElementVisible(By selector) {
        try {
            wait.withTimeout(Duration.ofSeconds(0));
            wait.until(visibilityOfElementLocated(selector));
            wait.withTimeout(Duration.ofSeconds(implicitWaitTimeAmount));
            return true;
        } catch (TimeoutException ex) {
            wait.withTimeout(Duration.ofSeconds(implicitWaitTimeAmount));
            return false;
        }
    }

    protected boolean isElementVisible(By selector, long timeout_millis) {
        try {
            wait.withTimeout(Duration.ofMillis(timeout_millis));
            wait.until(visibilityOfElementLocated(selector));
            wait.withTimeout(Duration.ofSeconds(implicitWaitTimeAmount));
            return true;
        } catch (TimeoutException ex) {
            wait.withTimeout(Duration.ofSeconds(implicitWaitTimeAmount));
            return false;
        }
    }

    public String getPageTitle() {
        return wd.findElement(By.cssSelector("h1")).getAttribute("innerText");
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            @NullableDecl
            @Override
            public String apply(@NullableDecl WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    public boolean isElementPresent(WebElement element) {
        try {
            app.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            element.getTagName();
            app.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return true;
        } catch (NoSuchElementException ignored) {
            app.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return false;
        }
    }

    public boolean isInnerElementPresent(WebElement element, By innerElementLocator) {
        try {
            wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            element.findElement(innerElementLocator);
            wd.manage().timeouts().implicitlyWait(implicitWaitTimeAmount, TimeUnit.SECONDS);
            return true;
        } catch (NoSuchElementException ex) {
            wd.manage().timeouts().implicitlyWait(implicitWaitTimeAmount, TimeUnit.SECONDS);
            return false;
        }
    }
}
