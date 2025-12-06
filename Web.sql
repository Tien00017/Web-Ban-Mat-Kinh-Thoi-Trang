CREATE TABLE `products` (
  `id` varchar(10) PRIMARY KEY,
  `category_id` varchar(10),
  `product_name` varchar(50),
  `brand` varchar(20),
  `price` double,
  `stock` integer,
  `short_infor_decription` varchar(100),
  `short_guarantee_decription` varchar(100),
  `general_description` varchar(100),
  `shipping_info` varchar(100),
  `product_details` varchar(100),
  `glass_details` varchar(100),
  `sold_quantity` integer,
  `created_at` timestamp
);

CREATE TABLE `reviews` (
  `id` varchar(10) PRIMARY KEY,
  `user_id` varchar(10),
  `product_id` varchar(10),
  `rating` integer,
  `comment` text,
  `created_at` timestamp
);

CREATE TABLE `product_images` (
  `id` varchar(10) PRIMARY KEY,
  `product_id` varchar(10),
  `image_url` VARCHAR(255),
  `is_main` boolean,
  `type` varchar(50),
  `created_at` timestamp
);

CREATE TABLE `categories` (
  `id` varchar(10) PRIMARY KEY,
  `category_name` varchar(100)
);

CREATE TABLE `attributes` (
  `id` varchar(10) PRIMARY KEY,
  `category_id` varchar(10),
  `name` varchar(50)
);

CREATE TABLE `category_attribute_map` (
  `id` varchar(10) PRIMARY KEY,
  `category_id` varchar(10),
  `attribute_id` varchar(10)
);

CREATE TABLE `attribute_values` (
  `id` varchar(10) PRIMARY KEY,
  `attribute_id` varchar(10),
  `name_value` varchar(50)
);

CREATE TABLE `products_attribute_values_map` (
  `id` varchar(10) PRIMARY KEY,
  `product_id` varchar(10),
  `attribute_values_id` varchar(10)
);

CREATE TABLE `users` (
  `id` varchar(10) PRIMARY KEY,
  `full_name` varchar(100),
  `display_name` varchar(100),
  `avatar` varchar(255),
  `email` varchar(250),
  `phone` varchar(20),
  `birth_date` date,
  `gender` integer,
  `address` varchar(250),
  `password` varchar(500),
  `status` boolean,
  `role` integer,
  `created_at` timestamp
);

CREATE TABLE `password_reset` (
  `id` varchar(10) PRIMARY KEY,
  `user_id` varchar(10),
  `otp` varchar(6),
  `expired_at` timestamp
);

CREATE TABLE `messages` (
  `id` varchar(10) PRIMARY KEY,
  `sender_id` varchar(10),
  `receiver_id` varchar(10),
  `content` text,
  `sent_at` timestamp,
  `status` varchar(20)
);

CREATE TABLE `carts` (
  `id` varchar(10) PRIMARY KEY,
  `user_id` varchar(10),
  `created_at` timestamp
);

CREATE TABLE `cart_item` (
  `id` varchar(10) PRIMARY KEY,
  `cart_id` varchar(10),
  `product_id` varchar(10),
  `quantity` integer
);

CREATE TABLE `cart_item_attribute` (
  `id` varchar(10) PRIMARY KEY,
  `cart_item_id` varchar(10),
  `attribute_values_id` varchar(10)
);

CREATE TABLE `order_item_attribute` (
  `id` varchar(10) PRIMARY KEY,
  `order_item_id` varchar(10),
  `attribute_values_id` varchar(10)
);

CREATE TABLE `orders` (
  `id` varchar(10) PRIMARY KEY,
  `user_id` varchar(10),
  `total_amount` double,
  `status` varchar(30),
  `name` varchar(20),
  `phone` varchar(20),
  `payment_method` varchar(30),
  `shipping_address` varchar(255),
  `created_at` timestamp
);

CREATE TABLE `order_items` (
  `id` varchar(10) PRIMARY KEY,
  `product_id` varchar(10),
  `order_id` varchar(10),
  `quantity` integer,
  `price` double
);

CREATE TABLE `promotions` (
  `id` varchar(10) PRIMARY KEY,
  `title` varchar(100),
  `content` varchar(1000),
  `start_date` date,
  `end_date` date,
  `discount_type` varchar(20),
  `discount_value` double,
  `status` varchar(20)
);

CREATE TABLE `promotion_product` (
  `id` varchar(10) PRIMARY KEY,
  `promotion_id` varchar(10),
  `product_id` varchar(10)
);

CREATE TABLE `banners` (
  `id` varchar(10) PRIMARY KEY,
  `promotions_id` varchar(10),
  `image_url` VARCHAR(255),
  `is_main` boolean,
  `created_at` timestamp
);

ALTER TABLE `products` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `product_images` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `attributes` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `category_attribute_map` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `category_attribute_map` ADD FOREIGN KEY (`attribute_id`) REFERENCES `attributes` (`id`);

ALTER TABLE `attribute_values` ADD FOREIGN KEY (`attribute_id`) REFERENCES `attributes` (`id`);

ALTER TABLE `products_attribute_values_map` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `products_attribute_values_map` ADD FOREIGN KEY (`attribute_values_id`) REFERENCES `attribute_values` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `password_reset` (`user_id`);

ALTER TABLE `carts` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `cart_item` ADD FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`);

ALTER TABLE `cart_item` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `cart_item_attribute` ADD FOREIGN KEY (`cart_item_id`) REFERENCES `cart_item` (`id`);

ALTER TABLE `cart_item_attribute` ADD FOREIGN KEY (`attribute_values_id`) REFERENCES `attribute_values` (`id`);

ALTER TABLE `order_item_attribute` ADD FOREIGN KEY (`order_item_id`) REFERENCES `order_items` (`id`);

ALTER TABLE `order_item_attribute` ADD FOREIGN KEY (`attribute_values_id`) REFERENCES `attribute_values` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `promotion_product` ADD FOREIGN KEY (`promotion_id`) REFERENCES `promotions` (`id`);

ALTER TABLE `promotion_product` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `banners` ADD FOREIGN KEY (`promotions_id`) REFERENCES `promotions` (`id`);
