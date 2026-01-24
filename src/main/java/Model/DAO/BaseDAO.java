package Model.DAO;

import Model.Object.Product;
import Model.Object.ProductImage;
import Model.Object.User;

import java.sql.ResultSet;

public abstract class BaseDAO {
    protected User mapUser(ResultSet rs) throws Exception {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setFullName(rs.getString("full_name"));
        u.setDisplayName(rs.getString("display_name"));
        u.setAvatar(rs.getString("avatar"));
        u.setEmail(rs.getString("email"));
        u.setPhone(rs.getString("phone"));
        u.setBirthDate(rs.getDate("birth_date"));
        u.setGender(rs.getInt("gender"));
        u.setAddress(rs.getString("address"));
        u.setPassword(rs.getString("password"));
        u.setStatus(rs.getBoolean("status"));
        u.setRole(rs.getInt("role"));
        u.setCreatedAt(rs.getTimestamp("created_at"));
        return u;
    }

    protected ProductImage mapProductImage(ResultSet rs) throws Exception {
        ProductImage img = new ProductImage();
        img.setId(rs.getInt("id"));
        img.setProductId(rs.getInt("product_id"));
        img.setImageUrl(rs.getString("image_url"));
        img.setMain(rs.getBoolean("is_main"));
        img.setType(rs.getString("type"));
        img.setCreatedAt(rs.getTimestamp("created_at"));
        return img;
    }

    protected Product mapProduct(ResultSet rs) throws Exception {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setProductName(rs.getString("product_name"));
        p.setBrand(rs.getString("brand"));
        p.setPrice(rs.getDouble("price"));
        p.setStock(rs.getInt("stock"));
        p.setOrigin(rs.getString("origin"));
        p.setGeneralDescription(rs.getString("general_description"));
        p.setShippingInfo(rs.getString("shipping_info"));
        p.setGuaranteeDetails(rs.getString("guarantee_details"));
        p.setSoldQuantity(rs.getInt("sold_quantity"));
        p.setDeleted(rs.getBoolean("deleted"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        return p;
    }
}
