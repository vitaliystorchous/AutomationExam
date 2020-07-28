package selenium.tests.exam_tests;

import org.testng.annotations.BeforeClass;
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

    @Test (alwaysRun = true,
            description = "The product prices are displayed in the currency selected by default")
    public void testDefaultSelectedCurrency() {
        String selectedCurrency = app.homepage().getSelectedCurrency();
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), selectedCurrency,
                    "Currency of prices does not match selected currency by default - " + selectedCurrency + "!");
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency",
            description = "The product prices are displayed in USD after changing currency to USD")
    public void testUSDCurrencySelection() {
        app.homepage().selectCurrency("$");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "$",
                    "Currency of prices does not match selected currency - USD!");
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency",
            description = "The product prices are displayed in EUR after changing currency to EUR")
    public void testEURCurrencySelection() {
        app.homepage().selectCurrency("€");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "€",
                    "Currency of prices does not match selected currency - EUR!");
        }
    }

    @Test (alwaysRun = true,
            dependsOnMethods = "testDefaultSelectedCurrency",
            description = "The product prices are displayed in UAH after changing currency to UAH")
    public void testUAHCurrencySelection() {
        app.homepage().selectCurrency("₴");
        List<Product> products = app.homepage().getAllProducts();
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "₴",
                    "Currency of prices does not match selected currency - UAH!");
        }
    }
}
