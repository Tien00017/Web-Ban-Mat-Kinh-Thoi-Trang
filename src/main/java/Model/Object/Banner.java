package Model.Object;

import java.sql.Timestamp;

public class Banner {

    private int id;
    private int promotionId;
    private String imageUrl;
    private boolean isMain;
    private Timestamp createdAt;

    public Banner() {
    }

    public Banner(int id, int promotionId, String imageUrl, boolean isMain, Timestamp createdAt) {
        this.id = id;
        this.promotionId = promotionId;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banner banner = (Banner) o;
        return id == banner.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
