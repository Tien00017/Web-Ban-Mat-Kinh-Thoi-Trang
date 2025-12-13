DROP DATABASE IF EXISTS web;
CREATE DATABASE web;
USE web;

CREATE TABLE categories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category_name VARCHAR(100)
) ENGINE=InnoDB;

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT,
  product_name VARCHAR(50),
  brand VARCHAR(20),
  price DOUBLE,
  stock INT,
  short_infor_decription VARCHAR(100),
  short_guarantee_decription VARCHAR(100),
  general_description VARCHAR(100),
  shipping_info VARCHAR(100),
  product_details VARCHAR(100),
  glass_details VARCHAR(100),
  sold_quantity INT,
  created_at TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES categories(id)
) ENGINE=InnoDB;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(100),
  display_name VARCHAR(100),
  avatar VARCHAR(255),
  email VARCHAR(250),
  phone VARCHAR(20),
  birth_date DATE,
  gender INT,
  address VARCHAR(250),
  password VARCHAR(500),
  status BOOLEAN,
  role INT,
  created_at TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE reviews (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  product_id INT,
  rating INT,
  comment TEXT,
  created_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB;

CREATE TABLE product_images (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT,
  image_url VARCHAR(255),
  is_main BOOLEAN,
  type VARCHAR(50),
  created_at TIMESTAMP,
  FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB;

CREATE TABLE attributes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT,
  name VARCHAR(50),
  FOREIGN KEY (category_id) REFERENCES categories(id)
) ENGINE=InnoDB;

CREATE TABLE category_attribute_map (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT,
  attribute_id INT,
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (attribute_id) REFERENCES attributes(id)
) ENGINE=InnoDB;

CREATE TABLE attribute_values (
  id INT AUTO_INCREMENT PRIMARY KEY,
  attribute_id INT,
  name_value VARCHAR(50),
  FOREIGN KEY (attribute_id) REFERENCES attributes(id)
) ENGINE=InnoDB;

CREATE TABLE products_attribute_values_map (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT,
  attribute_values_id INT,
  FOREIGN KEY (product_id) REFERENCES products(id),
  FOREIGN KEY (attribute_values_id) REFERENCES attribute_values(id)
) ENGINE=InnoDB;

CREATE TABLE password_reset (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  otp VARCHAR(6),
  expired_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE messages (
  id INT AUTO_INCREMENT PRIMARY KEY,
  sender_id INT,
  receiver_id INT,
  content TEXT,
  sent_at TIMESTAMP,
  status VARCHAR(20),
  FOREIGN KEY (sender_id) REFERENCES users(id),
  FOREIGN KEY (receiver_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE carts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  created_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE cart_item (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cart_id INT,
  product_id INT,
  quantity INT,
  FOREIGN KEY (cart_id) REFERENCES carts(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB;

CREATE TABLE cart_item_attribute (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cart_item_id INT,
  attribute_values_id INT,
  FOREIGN KEY (cart_item_id) REFERENCES cart_item(id),
  FOREIGN KEY (attribute_values_id) REFERENCES attribute_values(id)
) ENGINE=InnoDB;

CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  total_amount DOUBLE,
  status VARCHAR(30),
  name VARCHAR(20),
  phone VARCHAR(20),
  payment_method VARCHAR(30),
  shipping_address VARCHAR(255),
  created_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE order_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT,
  order_id INT,
  quantity INT,
  price DOUBLE,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB;

CREATE TABLE order_item_attribute (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_item_id INT,
  attribute_values_id INT,
  FOREIGN KEY (order_item_id) REFERENCES order_items(id),
  FOREIGN KEY (attribute_values_id) REFERENCES attribute_values(id)
) ENGINE=InnoDB;

CREATE TABLE promotions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100),
  content VARCHAR(1000),
  start_date DATE,
  end_date DATE,
  discount_type VARCHAR(20),
  discount_value DOUBLE,
  status VARCHAR(20)
) ENGINE=InnoDB;

CREATE TABLE promotion_product (
  id INT AUTO_INCREMENT PRIMARY KEY,
  promotion_id INT,
  product_id INT,
  FOREIGN KEY (promotion_id) REFERENCES promotions(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB;

CREATE TABLE banners (
  id INT AUTO_INCREMENT PRIMARY KEY,
  promotions_id INT,
  image_url VARCHAR(255),
  is_main BOOLEAN,
  created_at TIMESTAMP,
  FOREIGN KEY (promotions_id) REFERENCES promotions(id)
) ENGINE=InnoDB;
