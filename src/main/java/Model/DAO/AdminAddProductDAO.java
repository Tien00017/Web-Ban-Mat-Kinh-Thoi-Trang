package Model.DAO;

import Model.Object.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminAddProductDAO extends BaseDAO {

    // =========================
    // THÊM SẢN PHẨM
    // =========================
    public int insertProduct(Product p) {
        String sql = """
            INSERT INTO products (
                category_id,
                product_name,
                brand,
                price,
                stock,
                origin,
                general_description,
                shipping_info,
                guarantee_details,
                sold_quantity,
                deleted,
                created_at
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getCategoryId());
            ps.setString(2, p.getProductName());
            ps.setString(3, p.getBrand());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getOrigin());
            ps.setString(7, p.getGeneralDescription());
            ps.setString(8, p.getShippingInfo());
            ps.setString(9, p.getGuaranteeDetails());
            ps.setInt(10, p.getSoldQuantity());     // thường = 0 khi thêm
            ps.setBoolean(11, p.isDeleted());        // thường = false

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // id sản phẩm mới
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
