package selenium.helpers;

import selenium.app.ApplicationManager;

import java.util.Properties;

public class NavigationHelper extends HelperBase {

    private final Properties properties;

    public NavigationHelper(ApplicationManager app) {
        super(app);
        this.properties = app.getProperties();
    }

    public void homepage() {
        wd.get(properties.getProperty("web.baseUrl"));
    }
}
