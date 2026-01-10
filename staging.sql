USE web;

CREATE TABLE product_csv_staging (
    category VARCHAR(100),
    product_name TEXT,
    brand VARCHAR(100),
    price_raw VARCHAR(50),
    stock INT,
    origin VARCHAR(100),
    general_description TEXT,
    shipping_infor TEXT,
    guarantee_details TEXT,
    image_url TEXT,
    material VARCHAR(100),
    style VARCHAR(100)
);