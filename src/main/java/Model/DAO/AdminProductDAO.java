package Model.DAO;

import Model.Object.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminProductDAO extends BaseDAO {

    /* =========================
       LẤY DANH SÁCH SẢN PHẨM (ADMIN)
       ========================= */
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM products
            WHERE deleted = false
            ORDER BY id DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* =========================
       LẤY SẢN PHẨM THEO ID (EDIT)
       ========================= */
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapProduct(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // =========================
    // CẬP NHẬT SẢN PHẨM
    // =========================
    public void updateProduct(Product p) {
        String sql = """
            UPDATE products
            SET category_id = ?,
                product_name = ?,
                brand = ?,
                price = ?,
                stock = ?,
                origin = ?,
                general_description = ?,
                shipping_info = ?,
                guarantee_details = ?,
                sold_quantity = ?,
                deleted = ?
            WHERE id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getCategoryId());
            ps.setString(2, p.getProductName());
            ps.setString(3, p.getBrand());
            ps.setLong(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getOrigin());
            ps.setString(7, p.getGeneralDescription());
            ps.setString(8, p.getShippingInfo());
            ps.setString(9, p.getGuaranteeDetails());
            ps.setInt(10, p.getSoldQuantity());
            ps.setBoolean(11, p.isDeleted());
            ps.setInt(12, p.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    /* =========================
       XÓA SẢN PHẨM (XÓA MỀM)
       ========================= */
    public void deleteProduct(int id) {
        String sql = "UPDATE products SET deleted = true WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
