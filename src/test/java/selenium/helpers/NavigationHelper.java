package selenium.helpers;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.app.ApplicationManager;

import java.util.Properties;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

public class NavigationHelper extends HelperBase {

    private final String baseUrl;

    public NavigationHelper(ApplicationManager app) {
        super(app);
        baseUrl = app.getProperties().getProperty("web.baseUrl");
    }

    @Step("Open the site homepage")
    public void homepage() {
        wd.get(baseUrl);
        wait.until(urlToBe(baseUrl));
    }
}
