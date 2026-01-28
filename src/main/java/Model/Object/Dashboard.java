package Model.Object;

import java.util.List;

public class Dashboard {
    private double revenue;          // doanh thu
    private int totalOrders;         // tổng đơn
    private int newCustomers;        // khách hàng mới (30 ngày)

    private List<AdminOrderRow> recentOrders;
    private List<AdminProductRow> recentProducts;

    // Chart 1: doanh thu 7 ngày
    private List<String> revenueLabels;  // yyyy-mm-dd
    private List<Double> revenueValues;  // doanh thu theo ngày

    // Chart 2: tỉ lệ trạng thái đơn
    private List<String> orderStatusLabels; // ["Chờ xử lý", "Đang vận chuyển", "Hoàn tất", "Đã hủy"]
    private List<Integer> orderStatusValues; // [..counts..]

    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

    public int getNewCustomers() { return newCustomers; }
    public void setNewCustomers(int newCustomers) { this.newCustomers = newCustomers; }

    public List<AdminOrderRow> getRecentOrders() { return recentOrders; }
    public void setRecentOrders(List<AdminOrderRow> recentOrders) { this.recentOrders = recentOrders; }

    public List<AdminProductRow> getRecentProducts() { return recentProducts; }
    public void setRecentProducts(List<AdminProductRow> recentProducts) { this.recentProducts = recentProducts; }

    public List<String> getRevenueLabels() { return revenueLabels; }
    public void setRevenueLabels(List<String> revenueLabels) { this.revenueLabels = revenueLabels; }

    public List<Double> getRevenueValues() { return revenueValues; }
    public void setRevenueValues(List<Double> revenueValues) { this.revenueValues = revenueValues; }

    public List<String> getOrderStatusLabels() { return orderStatusLabels; }
    public void setOrderStatusLabels(List<String> orderStatusLabels) { this.orderStatusLabels = orderStatusLabels; }

    public List<Integer> getOrderStatusValues() { return orderStatusValues; }
    public void setOrderStatusValues(List<Integer> orderStatusValues) { this.orderStatusValues = orderStatusValues; }
}
