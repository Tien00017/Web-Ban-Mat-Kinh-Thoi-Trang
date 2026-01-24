package Model.Object;

import java.sql.Timestamp;
import java.util.List;

    public class Order {

        private int id;
        private int userId;
        private double totalAmount;
        private String status;
        private String name;
        private String phone;
        private String paymentMethod;
        private String shippingAddress;
        private Timestamp createdAt;

        public Order() {
        }

        public Order(int id, int userId, double totalAmount, String status,
                     String name, String phone, String paymentMethod,
                     String shippingAddress, Timestamp createdAt) {
            this.id = id;
            this.userId = userId;
            this.totalAmount = totalAmount;
            this.status = status;
            this.name = name;
            this.phone = phone;
            this.paymentMethod = paymentMethod;
            this.shippingAddress = shippingAddress;
            this.createdAt = createdAt;
        }

        // ===== Getter & Setter =====

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public Timestamp getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
        }

    }

