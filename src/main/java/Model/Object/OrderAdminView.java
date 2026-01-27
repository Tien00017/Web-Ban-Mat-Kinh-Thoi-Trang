package Model.Object;

import java.sql.Timestamp;

public class OrderAdminView {
    private int id;
    private String customerName;
    private String phone;
    private String productsSummary;
    private int totalQuantity;
    private Timestamp createdAt;
    private String status;
    private double totalAmount;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getProductsSummary() { return productsSummary; }
    public void setProductsSummary(String productsSummary) { this.productsSummary = productsSummary; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
