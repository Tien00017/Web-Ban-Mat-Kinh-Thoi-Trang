package Model.Object;

import java.sql.Timestamp;

public class Product {
    private int id;
    private int categoryId;
    private String productName;
    private String brand;
    private double price;
    private int stock;
    private String origin;
    private String generalDescription;
    private String shippingInfo;
    private String guaranteeDetails;
    private int soldQuantity;
    private boolean deleted;
    private Timestamp createdAt;

    public Product() {
    }

    public Product(int id, int categoryId, String productName, String brand, double price, int stock, String origin, String generalDescription, String shippingInfo, String productDetails, int soldQuantity, boolean deleted, Timestamp createdAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.productName = productName;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.origin = origin;
        this.generalDescription = generalDescription;
        this.shippingInfo = shippingInfo;
        this.guaranteeDetails = guaranteeDetails;
        this.soldQuantity = soldQuantity;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(String shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public String getGuaranteeDetails() {
        return guaranteeDetails;
    }

    public void setGuaranteeDetails(String productDetails) {
        this.guaranteeDetails = productDetails;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
