package Model.Object;

import java.sql.Date;

public class Promotion {

    private int id;
    private String title;
    private String content;

    private Date startDate;
    private Date endDate;

    private String discountType;
    private double discountValue;

    private String status;
    private String mainBannerUrl;

    public Promotion() {
    }

    // ===== Getter / Setter =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainBannerUrl() {
        return mainBannerUrl;
    }

    public void setMainBannerUrl(String mainBannerUrl) {
        this.mainBannerUrl = mainBannerUrl;
    }
}