package selenium.helpers;

import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.app.ApplicationManager;

import java.util.Properties;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

public class NavigationHelper extends HelperBase {

    private final Properties properties;

    public NavigationHelper(ApplicationManager app) {
        super(app);
        this.properties = app.getProperties();
    }

    public void homepage() {
        wd.get(properties.getProperty("web.baseUrl"));
        wait.until(urlToBe(properties.getProperty("web.baseUrl")));
    }
}
