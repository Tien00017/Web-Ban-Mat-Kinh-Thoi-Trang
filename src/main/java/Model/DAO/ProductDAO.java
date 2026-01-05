package Model.DAO;

import Model.Object.Product;
import Model.Object.ProductImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDAO {

    public static List<Product> getFilteredProducts(int categoryId, String material, String shape, String color, String priceRange, String sortPrice) {
        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT p.* FROM products p ");

        // Join nếu có material
        if (material != null && !material.isEmpty()) {
            sql.append("""
            JOIN products_attribute_values_map pav_m
                ON p.id = pav_m.product_id
            JOIN attribute_values av_m
                ON pav_m.attribute_values_id = av_m.id
        """);
        }

        // Join nếu có shape
        if (shape != null && !shape.isEmpty()) {
            sql.append("""
            JOIN products_attribute_values_map pav_s
                ON p.id = pav_s.product_id
            JOIN attribute_values av_s
                ON pav_s.attribute_values_id = av_s.id
        """);
        }

        // Join nếu có color
        if (color != null && !color.isEmpty()) {
            sql.append("""
            JOIN products_attribute_values_map pav_c
                ON p.id = pav_c.product_id
            JOIN attribute_values av_c
                ON pav_c.attribute_values_id = av_c.id
        """);
        }

        // Where cơ bản
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

        // lọc giá
        if ("low".equals(priceRange)) {
            sql.append(" AND p.price < 500000 ");
        } else if ("mid".equals(priceRange)) {
            sql.append(" AND p.price BETWEEN 500000 AND 1000000 ");
        } else if ("high".equals(priceRange)) {
            sql.append(" AND p.price > 1000000 ");
        }

        // sắp xếp
        if ("asc".equals(sortPrice)) {
            sql.append(" ORDER BY p.price ASC");
        } else if ("desc".equals(sortPrice)) {
            sql.append(" ORDER BY p.price DESC");
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                p.setProductDetails(rs.getString("product_details"));
                p.setSoldQuantity(rs.getInt("sold_quantity"));
                p.setDeleted(rs.getBoolean("deleted"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Product> getProductsByPage(List<Product> filteredList, int offset, int limit) {
        int start = Math.min(offset, filteredList.size()); // tránh offset vượt quá size
        int end = Math.min(start + limit, filteredList.size()); // lấy đúng số sản phẩm còn lại
        return filteredList.subList(start, end);
    }

    public static Map<Integer, ProductImage> getMainImagesByProductIds(List<Integer> productIds) {
        Map<Integer, ProductImage> map = new HashMap<>();
        if (productIds.isEmpty()) return map;

        String sql = "SELECT * FROM product_images WHERE is_main = true AND product_id IN ("
                + productIds.stream().map(id -> "?").collect(Collectors.joining(","))
                + ")";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < productIds.size(); i++) {
                ps.setInt(i + 1, productIds.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductImage img = new ProductImage();
                img.setId(rs.getInt("id"));
                img.setProductId(rs.getInt("product_id"));
                img.setImageUrl(rs.getString("image_url"));
                img.setMain(rs.getBoolean("is_main"));
                img.setType(rs.getString("type"));
                img.setCreatedAt(rs.getTimestamp("created_at"));

                map.put(img.getProductId(), img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}

