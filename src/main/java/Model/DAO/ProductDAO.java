package Model.DAO;

import Model.Object.Category;
import Model.Object.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDAO extends BaseDAO {

    public List<Product> getFilteredProducts(
            int categoryId,
            String material,
            String shape,
            String color,
            String priceRange,
            String sortPrice
    ) {
        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT p.* FROM products p ");

        if (material != null && !material.isEmpty()) {
            sql.append("""
                        JOIN products_attribute_values_map pav_m ON p.id = pav_m.product_id
                        JOIN attribute_values av_m ON pav_m.attribute_values_id = av_m.id
                    """);
        }

        if (shape != null && !shape.isEmpty()) {
            sql.append("""
                        JOIN products_attribute_values_map pav_s ON p.id = pav_s.product_id
                        JOIN attribute_values av_s ON pav_s.attribute_values_id = av_s.id
                    """);
        }

        if (color != null && !color.isEmpty()) {
            sql.append("""
                        JOIN products_attribute_values_map pav_c ON p.id = pav_c.product_id
                        JOIN attribute_values av_c ON pav_c.attribute_values_id = av_c.id
                    """);
        }

        sql.append(" WHERE p.category_id = ? AND p.deleted = false AND p.stock > 0");

        List<Object> params = new ArrayList<>();
        params.add(categoryId);

        if (material != null && !material.isEmpty()) {
            sql.append(" AND av_m.name_value = ? ");
            params.add(material);
        }

        if (shape != null && !shape.isEmpty()) {
            sql.append(" AND av_s.name_value = ? ");
            params.add(shape);
        }

        if (color != null && !color.isEmpty()) {
            sql.append(" AND av_c.name_value = ? ");
            params.add(color);
        }

        if ("low".equals(priceRange)) {
            sql.append(" AND p.price < 500000 ");
        } else if ("mid".equals(priceRange)) {
            sql.append(" AND p.price BETWEEN 500000 AND 1000000 ");
        } else if ("high".equals(priceRange)) {
            sql.append(" AND p.price > 1000000 ");
        }

        if ("asc".equals(sortPrice)) {
            sql.append(" ORDER BY p.price ASC ");
        } else if ("desc".equals(sortPrice)) {
            sql.append(" ORDER BY p.price DESC ");
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductsByPage(List<Product> filteredList, int offset, int limit) {
        int start = Math.min(offset, filteredList.size()); // tránh offset vượt quá size
        int end = Math.min(start + limit, filteredList.size()); // lấy đúng số sản phẩm còn lại
        return filteredList.subList(start, end);
    }

    public Product getProductById(int id) {
        String sql = """
                    SELECT p.*
                    FROM products p
                    WHERE p.id = ? AND p.deleted = false AND p.stock > 0
                """;

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

    public List<Product> getSimilarProducts(Product base, int limit) {
        List<Product> list = new ArrayList<>();

        String sql = """
                    SELECT *
                    FROM products
                    WHERE id <> ?
                      AND deleted = false
                      AND category_id = ?
                      AND stock > 0
                    LIMIT ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, base.getId());
            ps.setInt(2, base.getCategoryId());
            ps.setInt(3, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean decreaseStockAndIncreaseSold(int productId, int quantity) {
        String sql = """
                    UPDATE products
                    SET stock = stock - ?, sold_quantity = sold_quantity + ?
                    WHERE id = ? AND stock >= ?
                """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, quantity);
            ps.setInt(3, productId);
            ps.setInt(4, quantity);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getAllProductsAdmin() {
        List<Product> list = new ArrayList<>();

        String sql = """
                    SELECT p.*, c.name AS categoryName
                    FROM products p
                    JOIN categories c ON p.category_id = c.id
                    WHERE p.deleted = false 
                    ORDER BY p.id DESC
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            CategoryDAO categoryDAO = new CategoryDAO();
            Map<Integer, Category> categoryMap = categoryDAO.getCategoryMap();

            while (rs.next()) {
                Product p = mapProduct(rs);

                Category c = categoryMap.get(p.getCategoryId());
                // Cái này tui lấy cate cho nhưng tui đang ko hiểu ôg lấy làm gì có gì tự sửa sài sao nha
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Product p) {
        String sql = """
                    UPDATE products
                    SET product_name = ?,
                        price = ?,
                        stock = ?,
                        brand = ?,
                        category_id = ?,
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

            ps.setString(1, p.getProductName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getBrand());
            ps.setInt(5, p.getCategoryId());
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

    public void delete(int id) {
        String sql = "UPDATE products SET deleted = true WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertAndReturnId(Product p) {
        String sql = """
                    INSERT INTO products (
                        category_id, product_name, brand, price, stock,
                        origin, general_description, shipping_info,
                        guarantee_details, sold_quantity, deleted, created_at
                    )
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, false, NOW())
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getCategoryId());
            ps.setString(2, p.getProductName());
            ps.setString(3, p.getBrand());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getOrigin());
            ps.setString(7, p.getGeneralDescription());
            ps.setString(8, p.getShippingInfo());
            ps.setString(9, p.getGuaranteeDetails());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<>();

        String sql = """
        SELECT *
        FROM products
        WHERE deleted = false
          AND (product_name LIKE ? OR brand LIKE ?)
    """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs)); // BaseDAO mapping
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    }
