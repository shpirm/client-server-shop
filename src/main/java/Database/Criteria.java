package Database;

public class Criteria {
    private String name;
    private Double priceFrom;
    private Double priceTill;
    private Double amountFrom;
    private Double amountTill;


    public Criteria(){}

    public String getName() {
        return name;
    }

    public Criteria setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPriceFrom() {
        return priceFrom;
    }

    public Criteria setPriceFrom(Double priceFrom) {
        this.priceFrom = priceFrom;
        return this;
    }

    public Double getPriceTill() {
        return priceTill;
    }

    public Criteria setPriceTill(Double priceTill) {
        this.priceTill = priceTill;
        return this;
    }

    public Double getAmountFrom() {
        return amountFrom;
    }

    public Criteria setAmountFrom(Double amountFrom) {
        this.amountFrom = amountFrom;
        return this;
    }

    public Double getAmountTill() {
        return amountTill;
    }

    public Criteria setAmountTill(Double amountTill) {
        this.amountTill = amountTill;
        return this;
    }

}
