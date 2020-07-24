package selenium.tests.exam_tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.models.Product;
import selenium.tests.TestBase;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class CurrencyTest extends TestBase {

    @BeforeClass
    public void executePreconditions() {
        app.goTo().homepage();
    }

    @Test (alwaysRun = true)
    public void testDefaultSelectedCurrency() {
        String selectedCurrency = app.homepage().getSelectedCurrency();
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), selectedCurrency,
                    "Currency of prices does not match selected currency by default - " + selectedCurrency + "!");
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency")
    public void testUSDCurrencySelection() {
        app.homepage().selectCurrency("$");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "$",
                    "Currency of prices does not match selected currency - USD!");
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency")
    public void testEURCurrencySelection() {
        app.homepage().selectCurrency("€");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "€",
                    "Currency of prices does not match selected currency - EUR!");
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency")
    public void testUAHCurrencySelection() {
        app.homepage().selectCurrency("₴");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "₴",
                    "Currency of prices does not match selected currency - UAH!");
        }
    }
}
