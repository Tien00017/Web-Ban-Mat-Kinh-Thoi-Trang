USE web;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE products_attribute_values_map;
TRUNCATE TABLE category_attribute_map;
TRUNCATE TABLE product_images;
TRUNCATE TABLE products;
TRUNCATE TABLE attribute_values;
TRUNCATE TABLE attributes;
TRUNCATE TABLE categories;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO categories (category_name) VALUES
('Kính cận'),
('Kính mát'),
('Kính áp tròng'),
('Gọng kính');

INSERT INTO attributes (attribute_name) VALUES
('Chất liệu'),
('Kiểu dáng'),
('Màu sắc');

INSERT INTO attribute_values (attribute_id, name_value) VALUES
-- Chất liệu
(1, N'Kim loại'),
(1, N'Nhựa'),
(1, N'Titan'),

-- Kiểu dáng
(2, N'Vuông'),
(2, N'Tròn'),
(2, N'Oval'),
(2, N'Mắt mèo'),

-- Màu sắc
(3, N'Đen'),
(3, N'Nâu'),
(3, N'Trắng'),
(3, N'Trong suốt'),
(3, N'Xám'),
(3, N'Xanh');

INSERT INTO category_attribute_map (attribute_id, category_id) VALUES
-- Kính cận
(1,1), -- Chất liệu
(2,1), -- Kiểu dáng

-- Kính mát
(1,2),
(2,2),

-- Kính áp tròng
(3,3),

-- Gọng kính
(1,4),
(2,4);

INSERT INTO products (
    category_id,
    product_name,
    brand,
    price,
    stock,
    origin,
    general_description,
    shipping_info,
    product_details,
    sold_quantity,
    deleted,
    created_at
)
SELECT
    c.id,
    s.product_name,
    s.brand,
    CAST(
        REPLACE(
            REPLACE(
                REPLACE(s.price_raw, '.', ''),
            ',', ''),
        'VND', '')
    AS UNSIGNED),
    s.stock,
    s.origin,
    s.general_description,
    s.shipping_infor,
    s.guarantee_details,
    0,
    false,
    NOW()
FROM product_csv_staging s
JOIN categories c ON c.category_name = s.category;
 
INSERT INTO product_images (product_id, image_url, is_main, type, created_at)
SELECT
    p.id,
    s.image_url,
    true,
    'main',
    NOW()
FROM product_csv_staging s
JOIN products p ON p.product_name = s.product_name;

-- Material
INSERT INTO products_attribute_values_map (product_id, attribute_values_id)
SELECT
    p.id,
    av.id
FROM product_csv_staging s
JOIN products p ON p.product_name = s.product_name
JOIN attribute_values av ON av.name_value = s.material;

-- Style
INSERT INTO products_attribute_values_map (product_id, attribute_values_id)
SELECT
    p.id,
    av.id
FROM product_csv_staging s
JOIN products p ON p.product_name = s.product_name
JOIN attribute_values av ON av.name_value = s.style;


