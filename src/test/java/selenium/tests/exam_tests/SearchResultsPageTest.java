package selenium.tests.exam_tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.models.Product;
import selenium.tests.TestBase;

import java.math.BigDecimal;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SearchResultsPageTest extends TestBase {

    List<Product> products;

    @BeforeClass
    public void executePreconditions() {
        app.goTo().homepage();
        app.homepage().selectCurrency("$");
        app.homepage().searchInCatalog("dress");
        app.searchResultsPage().sortResultsBy("price: from high to low");
        products = app.searchResultsPage().getAllProducts();
    }

    @Test (alwaysRun = true)
    public void testSearchResultsCounter() {
        int searchResultsCounterValue = app.searchResultsPage().getSearchResultsCounterValue();
        assertEquals(searchResultsCounterValue, products.size(),
                "Search results counter is not equal to the actual amount of found products!");
    }

    @Test (alwaysRun = true)
    public void testAllPricesAreInUSD() {
        for (Product product : products) {
            assertEquals(product.getPriceCurrency(), "$",
                    "Currency of prices does not match selected currency - UDS!");
        }
    }

    @Test (alwaysRun = true)
    public void testProductsAreSortedByPriceFromHighToLow() {
        for (int i = 0; i < products.size() - 1; i++) {
            assertTrue(products.get(i).getRegularPrice() >= products.get(i + 1).getRegularPrice(),
                    "Products are not sorted from high to low:\n" +
                            "current product price = " + products.get(i).getRegularPrice() +
                            "next product price = " + products.get(i + 1).getRegularPrice());
        }
    }

    @Test (alwaysRun = true)
    public void testPriceWithDiscountCalculatedCorrectly() {
        for (Product product : products) {
            if (product.getDiscount() > 0) {
                double priceWithDiscount =
                        product.getRegularPrice() - (product.getRegularPrice() / 100 * product.getDiscount());
                double expectedPrice =
                        new BigDecimal(priceWithDiscount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                assertEquals(product.getCurrentPrice(), expectedPrice,
                        "The product price with discount incorrectly calculated!");
            }
        }
    }
}
