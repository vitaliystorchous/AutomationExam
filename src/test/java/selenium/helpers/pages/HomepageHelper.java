package selenium.helpers.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.app.ApplicationManager;
import selenium.helpers.HelperBase;
import selenium.helpers.blocks.ProductsBlockHelper;
import selenium.models.Product;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class HomepageHelper extends HelperBase {

    ProductsBlockHelper productsMiniaturesBlock;

    @FindBy(css = ".currency-selector span.expand-more")
    public WebElement currencyDropdown;

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
        productsMiniaturesBlock = new ProductsBlockHelper(app);
        PageFactory.initElements(wd, this);
    }

    public String getSelectedCurrency() {
        String selectedCurrency = currencyDropdown.getText();
        String currencySign = selectedCurrency.substring(selectedCurrency.length() - 1);
        return currencySign;
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
        WebElement homepageFirstProduct = productsMiniaturesBlock.getProduct(0);
        searchField.sendKeys(searchQuery);
        searchButton.click();
        wait.until(stalenessOf(homepageFirstProduct));
    }

    public List<Product> getAllProducts() {
        return productsMiniaturesBlock.getAllProducts();
    }
}
