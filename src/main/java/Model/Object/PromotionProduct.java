package Model.Object;

public class PromotionProduct {

    private int id;
    private int promotionId;
    private int productId;

    public PromotionProduct() {
    }

    public PromotionProduct(int id, int promotionId, int productId) {
        this.id = id;
        this.promotionId = promotionId;
        this.productId = productId;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionProduct that = (PromotionProduct) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
