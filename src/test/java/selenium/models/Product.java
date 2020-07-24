package selenium.models;

public class Product {

    public String priceCurrency;
    public double currentPrice;
    public double regularPrice;
    public double discount;


    public String getPriceCurrency() {
        return priceCurrency;
    }

    public Product withPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
        return this;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public Product withCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
        return this;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public Product withRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
        return this;
    }

    public double getDiscount() {
        return discount;
    }

    public Product withDiscount(double discount) {
        this.discount = discount;
        return this;
    }
}
