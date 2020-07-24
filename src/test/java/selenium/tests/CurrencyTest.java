package selenium.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.models.Product;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class CurrencyTest extends TestBase {

    @BeforeTest
    public void executePreconditions() {
        app.goTo().homepage();
    }

    @Test (alwaysRun = true)
    public void testDefaultSelectedCurrency() {
        String selectedCurrency = app.homepage().getSelectedCurrency();
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), selectedCurrency);
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency")
    public void testUSDCurrencySelection() {
        app.homepage().selectCurrency("$");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "$");
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency")
    public void testEURCurrencySelection() {
        app.homepage().selectCurrency("€");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "€");
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency")
    public void testUAHCurrencySelection() {
        app.homepage().selectCurrency("₴");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "₴");
        }
    }
}
