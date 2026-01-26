package Model.DAO;

import Model.Object.ProductImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductImgDAO extends BaseDAO {

    public Map<Integer, ProductImage> getMainImagesByProductIds(List<Integer> productIds) {
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
                ProductImage img = mapProductImage(rs);
                map.put(img.getProductId(), img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<ProductImage> getAllProductImageById(int productId) {
        List<ProductImage> list = new ArrayList<>();

        String sql = """
                    SELECT *
                    FROM product_images
                    WHERE product_id = ?
                    ORDER BY is_main DESC, created_at ASC
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapProductImage(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(ProductImage img) {
        String sql = """
                    INSERT INTO product_images (
                        product_id,
                        image_url,
                        is_main,
                        type,
                        created_at
                    )
                    VALUES (?, ?, ?, ?, NOW())
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, img.getProductId());
            ps.setString(2, img.getImageUrl());
            ps.setBoolean(3, img.isMain());
            ps.setString(4, img.getType());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

