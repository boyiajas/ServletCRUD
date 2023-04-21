-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 21, 2023 at 02:27 PM
-- Server version: 10.6.12-MariaDB-0ubuntu0.22.04.1
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `servletproductmgt_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `category_name`, `created_at`) VALUES
(1, 'Beverages', '2023-04-20 03:46:27'),
(2, 'Drinks', '2023-04-20 03:46:27'),
(3, 'Fruit and Vegetables ', '2023-04-20 03:46:27'),
(4, 'Sea Food', '2023-04-21 10:08:52');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `category_id`, `product_name`, `description`, `price`, `created_at`) VALUES
(12, 3, 'Apple', 'Fresh Apple ', 2.5, '2023-04-20 13:46:28'),
(13, 2, 'Coke', 'Original Taste Coke', 25, '2023-04-20 14:26:22'),
(15, 1, 'Milo', 'Milo 500g', 40, '2023-04-21 09:55:57'),
(16, 1, 'Banana', 'Fresh and Ripe Banana', 15, '2023-04-21 09:58:25'),
(17, 3, 'Sweet Potatoes', 'Sweet Potatoes', 120, '2023-04-21 10:00:14'),
(18, 4, 'Dry Fish', 'Dry Fish ', 15, '2023-04-21 10:15:40');

-- --------------------------------------------------------

--
-- Table structure for table `product_images`
--

CREATE TABLE `product_images` (
  `image_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product_images`
--

INSERT INTO `product_images` (`image_id`, `product_id`, `image_url`, `created_at`) VALUES
(1, 12, 'productImg/Honeycrisp-Apple.jpg', '2023-04-20 14:10:43'),
(2, 12, 'productImg/red apples.jpeg', '2023-04-20 14:10:55'),
(3, 13, 'productImg/SKU10229.jpg', '2023-04-20 14:26:22'),
(7, 13, 'productImg/pepsico-coke-call-out-india-as-a-key-growth-market-in-global-earnings.jpg', '2023-04-21 09:53:25'),
(8, 13, 'productImg/PR177BI850_L000171_Coke_Cans_24_X_200ml_SZ4.jpg', '2023-04-21 09:54:49'),
(9, 15, 'productImg/MILO 500G TIN-500x500.JPG', '2023-04-21 09:55:57'),
(10, 16, 'productImg/Bananas.jpg', '2023-04-21 09:58:25'),
(11, 17, 'productImg/Sweet Potatos.jpg', '2023-04-21 10:00:14'),
(12, 18, 'productImg/ezgif.com-gif-maker.jpg', '2023-04-21 10:04:13'),
(13, 18, 'productImg/dry fish.png', '2023-04-21 10:04:13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`image_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `product_images`
--
ALTER TABLE `product_images`
  MODIFY `image_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
