DROP DATABASE web;
CREATE DATABASE web CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE web;

CREATE TABLE `products` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `category_id` int,
  `product_name` varchar(100),
  `brand` varchar(20),
  `price` BIGINT UNSIGNED,
  `stock` integer,
  `origin` varchar(100),
  `general_description` LONGTEXT,
  `shipping_info` TEXT,
  `guarantee_details` TEXT,
  `sold_quantity` integer,
  `deleted` boolean,
  `created_at` timestamp
);

CREATE TABLE `reviews` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `product_id` int,
  `rating` integer,
  `comment` text,
  `created_at` timestamp
);

CREATE TABLE `product_images` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `product_id` int,
  `image_url` VARCHAR(255),
  `is_main` boolean,
  `type` varchar(50),
  `created_at` timestamp
);

CREATE TABLE `categories` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `category_name` varchar(100)
);

CREATE TABLE `attributes` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `attribute_name` varchar(50)
);

CREATE TABLE `category_attribute_map` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `category_id` int,
  `attribute_id` int
);

CREATE TABLE `attribute_values` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `attribute_id` int,
  `name_value` varchar(50)
);

CREATE TABLE `products_attribute_values_map` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `product_id` int,
  `attribute_values_id` int
);

CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `full_name` varchar(100),
  `display_name` varchar(100),
  `avatar` varchar(255),
  `email` varchar(250) UNIQUE,
  `phone` varchar(20),
  `birth_date` date,
  `gender` integer,
  `address` varchar(250),
  `password` varchar(500),
  `status` boolean,
  `role` integer,
  `created_at` timestamp
);

CREATE TABLE `otp` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `email` VARCHAR(255),
  `otp` varchar(6),
  `type` VARCHAR(30),
  `status` boolean,
  `expired_at` timestamp
);

CREATE TABLE `messages` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `sender_id` int,
  `receiver_id` int,
  `content` text,
  `sent_at` timestamp,
  `status` varchar(20)
);

CREATE TABLE `order_item_attribute` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `order_item_id` int,
  `attribute_values_id` int
);

CREATE TABLE `orders` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `total_amount` double,
  `status` varchar(30),
  `name` varchar(20),
  `phone` varchar(20),
  `payment_method` varchar(30),
  `shipping_address` varchar(255),
  `created_at` timestamp
);

CREATE TABLE `order_items` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `product_id` int,
  `order_id` int,
  `quantity` integer,
  `price` double
);

CREATE TABLE `promotions` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(100),
  `content` varchar(1000),
  `start_date` date,
  `end_date` date,
  `discount_type` varchar(20),
  `discount_value` double,
  `status` varchar(20)
);

CREATE TABLE `promotion_product` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `promotion_id` int,
  `product_id` int
);

CREATE TABLE `banners` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `promotions_id` int,
  `image_url` VARCHAR(255),
  `is_main` boolean,
  `created_at` timestamp
);

ALTER TABLE `products` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `product_images` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `category_attribute_map` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `category_attribute_map` ADD FOREIGN KEY (`attribute_id`) REFERENCES `attributes` (`id`);

ALTER TABLE `attribute_values` ADD FOREIGN KEY (`attribute_id`) REFERENCES `attributes` (`id`);

ALTER TABLE `products_attribute_values_map` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `products_attribute_values_map` ADD FOREIGN KEY (`attribute_values_id`) REFERENCES `attribute_values` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`);

ALTER TABLE `otp` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `order_item_attribute` ADD FOREIGN KEY (`order_item_id`) REFERENCES `order_items` (`id`);

ALTER TABLE `order_item_attribute` ADD FOREIGN KEY (`attribute_values_id`) REFERENCES `attribute_values` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `promotion_product` ADD FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`id`);

ALTER TABLE `promotion_product` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `banners` ADD FOREIGN KEY (`promotions_id`) REFERENCES `promotions` (`id`);
