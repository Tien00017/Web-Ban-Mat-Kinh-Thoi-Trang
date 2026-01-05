package Model.Object;

import java.sql.Timestamp;

public class ProductImage {
    private int id;
    private int productId;
    private String imageUrl;
    private boolean isMain;
    private String type;
    private Timestamp createdAt;

    public ProductImage() {
    }

    public ProductImage(int id, int productId, String imageUrl, boolean isMain, String type, Timestamp createdAt) {
        this.id = id;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
        this.type = type;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
