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

public class SearchResultsPageHelper extends HelperBase {

    ProductsBlockHelper productsBlock;

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

    @FindBy(css = ".total-products p")
    public WebElement productsCounter;

    public SearchResultsPageHelper(ApplicationManager app) {
        super(app);
        productsBlock = new ProductsBlockHelper(app);
        PageFactory.initElements(wd, this);
    }

    public void sortResultsBy(String sortOption) {
        WebElement firstProduct = productsBlock.getProduct(0);
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

    public int getSearchResultsCounterValue() {
        String amountOfSearchResults = productsCounter.getText().replaceAll("\\D", "");
        return Integer.parseInt(amountOfSearchResults);
    }

    public List<Product> getAllProducts() {
        return productsBlock.getAllProducts();
    }
}
