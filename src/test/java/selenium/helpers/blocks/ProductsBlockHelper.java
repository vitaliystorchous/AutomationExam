package selenium.helpers.blocks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.app.ApplicationManager;
import selenium.helpers.HelperBase;
import selenium.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsBlockHelper extends HelperBase {

    @FindBy(css = ".product-miniature")
    public List<WebElement> products;
    public By productCurrentPriceLocator = By.cssSelector(".price");
    public By productRegularPriceLocator = By.cssSelector(".regular-price");
    public By productDiscountLocator = By.cssSelector(".discount-percentage");

    public ProductsBlockHelper(ApplicationManager app) {
        super(app);
        PageFactory.initElements(wd, this);
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

    public WebElement getProduct(int index) {
        return products.get(index);
    }
}
