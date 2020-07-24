package selenium.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.app.ApplicationManager;
import selenium.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class SearchResultsPageHelper extends HelperBase {

    @FindBy(css = ".products-sort-order .select-title")
    public WebElement sortDropdown;

    @FindBy(css = "a[href*=\"order=product.position.asc\"]")
    public WebElement relevanceSortDropdownOption;

    @FindBy(css = "a[href*=\"order=product.name.asc\"]")
    public WebElement nameAtoZSortDropdownOption;

    @FindBy(css = "a[href*=\"order=product.name.desc\"]")
    public WebElement nameZtoASortDropdownOption;

    @FindBy(css = "a[href*=\"order=product.price.asc\"]")
    public WebElement priceFromLowToHighSortDropdownOption;

    @FindBy(css = "a[href*=\"order=product.price.desc\"]")
    public WebElement priceFromHighToLowSortDropdownOption;

    @FindBy(css = ".product-miniature")
    public List<WebElement> products;
    public By productCurrentPriceLocator = By.cssSelector(".price");
    public By productRegularPriceLocator = By.cssSelector(".regular-price");
    public By productDiscountLocator = By.cssSelector(".discount-percentage");

    @FindBy(css = ".total-products p")
    public WebElement productsCounter;

    public SearchResultsPageHelper(ApplicationManager app) {
        super(app);
        PageFactory.initElements(wd, this);
    }

    public void sortResultsBy(String sortOption) {
        WebElement firstProduct = products.get(0);
        sortDropdown.click();
        switch (sortOption) {
            case "relevance": {
                relevanceSortDropdownOption.click();
                break;
            }
            case "name: from A to Z": {
                nameAtoZSortDropdownOption.click();
                break;
            }
            case "name: from Z to A": {
                nameZtoASortDropdownOption.click();
                break;
            }
            case "price: from low to high": {
                priceFromLowToHighSortDropdownOption.click();
                break;
            }
            case "price: from high to low": {
                priceFromHighToLowSortDropdownOption.click();
                break;
            }
            default:
                System.out.println("Invalid sortOption parameter specified!");
        }
        wait.until(stalenessOf(firstProduct));
    }

    public List<Product> getAllProducts() {
        List<Product> productsJava = new ArrayList<>();
        for (WebElement product : products) {
            productsJava.add(new Product()
                    .withCurrentPrice(getCurrentPrice(product))
                    .withRegularPrice(getRegularPrice(product))
                    .withDiscount(getDiscount(product))
                    .withPriceCurrency(getPriceCurrency(product)));
        }
        return productsJava;
    }

    private String getPriceCurrency(WebElement product) {
        String productPrice = product.findElement(productCurrentPriceLocator).getText();
        String currencySign = productPrice.substring(productPrice.length() - 1);
        return currencySign;
    }

    private double getDiscount(WebElement product) {
        String discountPercent;
        if (isInnerElementPresent(product, productDiscountLocator)) {
            discountPercent = product.findElement(productDiscountLocator).getText();
            String discount = discountPercent.substring(1, discountPercent.indexOf("%"));
            return Double.parseDouble(discount);
        } else {
            return 0;
        }
    }

    private double getRegularPrice(WebElement product) {
        String regularPrice;
        if (isInnerElementPresent(product, productRegularPriceLocator)){
            regularPrice = product.findElement(productRegularPriceLocator).getText();
        } else {
            regularPrice = product.findElement(productCurrentPriceLocator).getText();
        }
        String price = regularPrice.substring(0, regularPrice.indexOf(" ")).replaceAll(",", ".");
        return Double.parseDouble(price);
    }

    private double getCurrentPrice(WebElement product) {
        String currentPrice = product.findElement(productCurrentPriceLocator).getText();
        String price = currentPrice.substring(0, currentPrice.indexOf(" ")).replaceAll(",", ".");
        return Double.parseDouble(price);
    }

    public int getSearchResultsCounterValue() {
        String amountOfSearchResults = productsCounter.getText().replaceAll("\\D", "");
        return Integer.parseInt(amountOfSearchResults);
    }
}
