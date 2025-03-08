package com.ezepsosa.marcusbike.models;

public class ProductPartConditions {

    private ProductPart partId;
    private ProductPart dependantPartId;
    private Double priceAdjustment;
    private Boolean isRestriction;

    public ProductPart getPartId() {
        return partId;
    }

    public ProductPart getDependantPartId() {
        return dependantPartId;
    }

    public Double getPriceAdjustment() {
        return priceAdjustment;
    }

    public Boolean getIsRestriction() {
        return isRestriction;
    }

    public void setPartId(ProductPart partId) {
        this.partId = partId;
    }

    public void setDependantPartId(ProductPart dependantPartId) {
        this.dependantPartId = dependantPartId;
    }

    public void setPriceAdjustment(Double priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public void setIsRestriction(Boolean isRestriction) {
        this.isRestriction = isRestriction;
    }

    public ProductPartConditions() {
    }

    public ProductPartConditions(ProductPart partId, ProductPart dependantPartId, Double priceAdjustment,
            Boolean isRestriction) {
        this.partId = partId;
        this.dependantPartId = dependantPartId;
        this.priceAdjustment = priceAdjustment;
        this.isRestriction = isRestriction;
    }

    // Method for debugging and logs
    @Override
    public String toString() {
        return "ProductPartConditions [partId=" + partId + ", dependantPartId=" + dependantPartId + ", priceAdjustment="
                + priceAdjustment + ", isRestriction=" + isRestriction + "]";
    }

}
