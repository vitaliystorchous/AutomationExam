package selenium.helpers;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.app.ApplicationManager;
import selenium.models.Product;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class HomepageHelper extends HelperBase {

    @FindBy(css = ".currency-selector span.expand-more")
    public WebElement currencyDropdown;

    @FindBy(className = "product-miniature")
    public List<WebElement> products;
    public By productCurrentPriceLocator = By.className("price");

    @FindBy(xpath = "//a[@class=\"dropdown-item\" and contains(., \"$\")]")
    public WebElement USDDropdownOption;

    @FindBy(xpath = "//a[@class=\"dropdown-item\" and contains(., \"€\")]")
    public WebElement EURDropdownOption;

    @FindBy(xpath = "//a[@class=\"dropdown-item\" and contains(., \"₴\")]")
    public WebElement UAHDropdownOption;

    @FindBy(name = "s")
    public WebElement searchField;

    @FindBy(css = "button[type=\"submit\"] .search")
    public WebElement searchButton;

    public HomepageHelper(ApplicationManager app) {
        super(app);
        PageFactory.initElements(wd, this);
    }

    public String getSelectedCurrency() {
        String selectedCurrency = currencyDropdown.getText();
        String currencySign = selectedCurrency.substring(selectedCurrency.length() - 1);
        return currencySign;
    }

    public List<Product> getAllProducts() {
        List<Product> productsJava = new ArrayList<>();
        for (WebElement product : products) {
            String priceAsString = product.findElement(productCurrentPriceLocator).getText();
            String priceCurrency = priceAsString.substring(priceAsString.length() - 1);
            productsJava.add(new Product().withPriceCurrency(priceCurrency));
        }
        return productsJava;
    }

    public void selectCurrency(String currency) {
        currencyDropdown.click();
        switch (currency) {
            case "$": {
                USDDropdownOption.click();
                break;
            }
            case "€": {
                EURDropdownOption.click();
                break;
            }
            case "₴": {
                UAHDropdownOption.click();
            }
            default:
                System.out.println("Invalid currency sign!");
        }
        wait.until(textToBePresentInElement(currencyDropdown, currency));
    }

    public void searchInCatalog(String searchQuery) {
        WebElement homepageFirstProduct = products.get(0);
        searchField.sendKeys(searchQuery);
        searchButton.click();
        wait.until(stalenessOf(homepageFirstProduct));
    }
}
