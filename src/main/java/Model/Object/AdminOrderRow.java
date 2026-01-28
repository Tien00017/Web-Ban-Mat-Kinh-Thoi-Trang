package Model.Object;

import java.sql.Timestamp;

public class AdminOrderRow {
    private int id;
    private String name;
    private String phone;
    private String productsSummary;
    private int totalQty;
    private String status;
    private double totalAmount;
    private Timestamp createdAt;

    // getters/setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getProductsSummary() { return productsSummary; }
    public void setProductsSummary(String productsSummary) { this.productsSummary = productsSummary; }
    public int getTotalQty() { return totalQty; }
    public void setTotalQty(int totalQty) { this.totalQty = totalQty; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
