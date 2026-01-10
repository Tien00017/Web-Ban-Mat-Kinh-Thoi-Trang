package Model.DAO;

import Model.Object.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

        sql.append(" WHERE p.category_id = ? AND p.deleted = false ");

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
                    WHERE p.id = ? AND p.deleted = false
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

}

