package com.ezepsosa.models;

public class ProductPart {

    private Long id;
    private String partOption;
    private Boolean isAvailable;
    private Double basePrice;
    private ProductPartCategory category;

    public Long getId() {
        return id;
    }

    public String getPartOption() {
        return partOption;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public ProductPartCategory getCategory() {
        return category;
    }

    public void setPartOption(String partOption) {
        this.partOption = partOption;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public void setCategory(ProductPartCategory category) {
        this.category = category;
    }

    public ProductPart() {
    }

    public ProductPart(Long id, String partOption, Boolean isAvailable, Double basePrice,
            ProductPartCategory category) {
        this.id = id;
        this.partOption = partOption;
        this.isAvailable = isAvailable;
        this.basePrice = basePrice;
        this.category = category;
    }

    public ProductPart(String partOption, Boolean isAvailable, Double basePrice, ProductPartCategory category) {
        this.partOption = partOption;
        this.isAvailable = isAvailable;
        this.basePrice = basePrice;
        this.category = category;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "ProductPart [id=" + id + ", partOption=" + partOption + ", isAvailable=" + isAvailable + ", basePrice="
                + basePrice + ", category=" + category + "]";
    }

}
