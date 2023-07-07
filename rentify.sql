-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 04, 2023 at 06:04 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rentify`
--

-- --------------------------------------------------------

--
-- Table structure for table `addresses`
--

CREATE TABLE `addresses` (
  `id` int(11) NOT NULL,
  `neighborhood_id` int(11) NOT NULL,
  `street` varchar(200) NOT NULL,
  `x` bigint(20) NOT NULL,
  `y` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `addresses`
--

INSERT INTO `addresses` (`id`, `neighborhood_id`, `street`, `x`, `y`) VALUES
(34, 1, 'Main St', 123, 12345),
(35, 2, 'Maple Ave', 456, 23456),
(36, 1, 'Broadway', 789, 34567),
(37, 3, 'Yonge St', 10, 123123),
(38, 4, 'Oxford St', 15, 123123),
(39, 5, 'Campbell Parade', 20, 123123123),
(40, 6, 'Hollywood Blvd', 123, 90028),
(41, 8, 'Bulevar Ivana Crnojevića', 95, 81000),
(42, 8, 'Njegoševa ulica', 28, 81000),
(43, 8, 'Slobode ulica', 77, 81000),
(44, 9, 'Vaka Đurovića ulica', 10, 81000),
(45, 8, 'Hercegovačka ulica', 15, 81000),
(46, 7, 'Stanka Dragojevića ulica', 17, 81000),
(47, 10, 'Bratstva i jedinstva ulica', 22, 81000),
(48, 10, 'Jadranski put', 31, 85310),
(49, 7, 'Mediteranska ulica', 5, 85310),
(50, 13, 'Mainski put', 2823423, 85310),
(51, 8, 'Velji Vinogradi', 12, 85310),
(52, 11, 'Njegoševa ulica', 2234, 85310),
(53, 8, 'Slovenska obala', 14, 85310),
(54, 13, 'Novaka Miloševa ulica', 7, 85310),
(55, 7, 'Vuka Karadžića ulica', 8, 85310),
(56, 14, 'Mosorska', 234234, 81000),
(76, 1, 'Broadway', 123, 10001),
(77, 1, 'Park Avenue', 456, 10022),
(78, 2, 'Wilshire Boulevard', 789, 90210),
(79, 3, 'Baker Street', 221123, 123123),
(80, 3, 'Yonge Street', 10, 123123),
(81, 5, 'George Street', 23, 2000),
(82, 16, 'Friedrichstraße', 123, 10117),
(95, 1, 'Broadway 789', 123123123, 123123123),
(96, 1, 'Broadway 789', 123123123, 123123123),
(97, 1, 'Broadway 789', 123123123, 123123123),
(98, 1, 'Broadway 789', 123123123, 123123123),
(99, 1, 'Broadway 789', 123123123, 123123123),
(100, 1, 'Broadway 789', 123123123, 123123123),
(101, 1, 'Broadway 789', 123123123, 123123123),
(102, 1, 'Broadway 789', 123123123, 123123123),
(103, 1, 'Broadway 789', 123123123, 123123123),
(104, 1, 'Broadway 789', 123123123, 123123123),
(105, 1, 'Broadway 789', 123123123, 123123123),
(106, 1, 'Broadway 789', 123123123, 123123123),
(107, 1, 'Broadway 789', 123123123, 123123123),
(108, 1, 'Broadway 789', 123123123, 123123123),
(109, 1, 'Broadway 789', 123123123, 123123123),
(110, 1, 'Broadway 789', 123123123, 123123123),
(111, 1, 'Broadway 789', 123123123, 123123123),
(112, 1, 'Broadway 789', 123123123, 123123123),
(113, 1, 'Broadway 789', 123123123, 123123123),
(114, 1, 'Broadway 789', 123123123, 123123123),
(115, 1, 'Broadway 789', 123123123, 123123123),
(116, 1, 'Broadway 789', 123123123, 123123123),
(117, 1, 'Broadway 789', 123123123, 123123123),
(118, 1, 'Broadway 789', 123123123, 123123123),
(119, 1, 'Broadway 789', 123123123, 123123123),
(120, 1, 'Broadway 789', 123123123, 123123123),
(121, 1, 'Broadway 789', 123123123, 123123123),
(122, 1, 'Broadway 789', 123123123, 123123123),
(123, 1, 'Broadway 789', 123123123, 123123123),
(124, 1, 'Broadway 789', 123123123, 123123123),
(125, 1, 'Broadway 789', 123123123, 123123123),
(126, 1, 'Broadway 789', 123123123, 123123123),
(127, 1, 'Broadway 789', 123123123, 123123123),
(128, 1, 'Broadway 789', 123123123, 123123123),
(129, 1, 'Broadway 789', 123123123, 123123123),
(130, 1, 'Broadway 789', 123123123, 123123123),
(131, 1, 'Broadway 789', 123123123, 123123123),
(132, 1, 'Broadway 789', 123123123, 123123123),
(133, 1, 'Broadway 789', 123123123, 123123123),
(134, 1, 'Broadway 789', 123123123, 123123123),
(135, 1, 'Broadway 789', 123123123, 123123123),
(136, 1, 'Broadway 789', 123123123, 123123123),
(137, 1, 'Broadway 789', 123123123, 123123123),
(138, 1, 'Broadway 789', 123123123, 123123123),
(139, 1, 'Broadway 789', 123123123, 123123123),
(140, 1, 'Broadway 789', 123123123, 123123123),
(141, 1, 'Broadway 789', 123123123, 123123123),
(142, 1, 'Broadway 789', 123123123, 123123123),
(143, 1, 'Broadway 789', 123123123, 123123123),
(144, 1, 'Broadway 789', 123123123, 123123123),
(145, 1, 'Broadway 789', 123123123, 123123123),
(146, 1, 'Broadway 789', 123123123, 123123123),
(147, 1, 'Broadway 789', 123123123, 123123123),
(148, 1, 'Broadway 789', 123123123, 123123123),
(149, 1, 'Broadway 789', 123123123, 123123123),
(150, 1, 'Broadway 789', 123123123, 123123123),
(151, 1, 'Broadway 789', 123123123, 123123123),
(152, 1, 'Broadway 789', 123123123, 123123123),
(153, 1, 'Broadway 789', 123123123, 123123123),
(154, 1, 'Broadway 789', 123123123, 123123123),
(155, 1, 'Broadway 789', 123123123, 123123123),
(156, 1, 'Broadway 789', 123123123, 123123123),
(157, 1, 'Broadway 789', 123123123, 123123123),
(158, 1, 'Broadway 789', 123123123, 123123123),
(159, 1, 'Broadway 789', 123123123, 123123123),
(160, 1, 'Broadway 789', 123123123, 123123123),
(161, 1, 'Broadway 789', 123123123, 123123123),
(162, 1, 'Broadway 789', 123123123, 123123123),
(163, 1, 'Broadway 789', 123123123, 123123123),
(164, 1, 'Broadway 789', 123123123, 123123123);

-- --------------------------------------------------------

--
-- Table structure for table `apartments`
--

CREATE TABLE `apartments` (
  `id` int(11) NOT NULL,
  `title` varchar(500) NOT NULL,
  `description` text NOT NULL,
  `square_meters` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `number_of_bedrooms` int(11) NOT NULL,
  `number_of_bathrooms` int(11) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `contact_number` varchar(70) NOT NULL,
  `user_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  `is_active` bit(1) DEFAULT b'1',
  `period_name` varchar(10) NOT NULL,
  `property_type` varchar(20) NOT NULL,
  `grade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `apartments`
--

INSERT INTO `apartments` (`id`, `title`, `description`, `square_meters`, `price`, `number_of_bedrooms`, `number_of_bathrooms`, `created_at`, `contact_number`, `user_id`, `address_id`, `is_active`, `period_name`, `property_type`, `grade`) VALUES
(1, 'Luxury apartment in downtown New York', 'This is a luxurious apartment located in the heart of New York City. It features modern furnishings and top-of-the-line appliances.', 100, '2000.00', 2, 2, '2023-03-14 22:29:28', '+38267173485', 8, 34, b'1', 'day', 'Apartment', 5),
(2, 'Cozy Apartment', 'A Luxurious and Contemporary Apartment in the Vibrant Heart of the City\n\nStep into a world of comfort and sophistication in this magnificent apartment, nestled right in the bustling heart of the city. Offering an exquisite blend of cozy warmth and sleek modernity, this dwelling is the epitome of urban living at its finest.\n\nAs you enter this remarkable space, you\'ll be immediately captivated by its inviting ambiance. Every corner exudes a sense of tranquility and elegance, creating a haven of relaxation amidst the energetic cityscape. The meticulously designed interior combines plush furnishings with tasteful decor, resulting in an atmosphere that is both stylish and comfortable.\n\nThe living area is a sanctuary of serenity, adorned with sumptuous sofas and adorned with elegant lighting fixtures. Whether you\'re curling up with a good book, enjoying a movie night with loved ones, or simply unwinding after a long day, this space is perfectly tailored for all your relaxation needs.\n\nThe kitchen is a culinary haven, boasting state-of-the-art appliances and a sleek, minimalist design. It offers ample space for culinary creativity, allowing you to whip up delicious meals with ease. The contemporary finishes and top-of-the-line equipment make cooking an absolute pleasure, whether you\'re a seasoned chef or a passionate amateur.\n\nThe bedroom is a sanctuary of tranquility, designed to provide the utmost comfort and a peaceful night\'s sleep. With a plush, king-sized bed adorned with soft, high-quality linens, you\'ll find yourself drifting off into dreams effortlessly. The room is tastefully decorated, creating an atmosphere that is both soothing and rejuvenating.\n\nThe apartment\'s location is unparalleled, placing you right in the heart of the city\'s vibrant energy. Step outside, and you\'ll find yourself immersed in a thriving metropolis, with an array of entertainment, dining, and shopping options at your doorstep. From trendy cafes to renowned restaurants, boutique shops to cultural landmarks, everything you desire is just a stone\'s throw away.', 50, '1000.00', 1, 2, '2023-03-14 22:29:28', '+38267555748', 8, 34, b'1', 'month', 'Apartment', 0),
(3, 'Luxury Condo', 'A Luxurious and Spacious Condo with Breathtaking Views\n\nWelcome to an exquisite world of opulence and ample space in this remarkable condo that boasts truly mesmerizing views. Prepare to be enchanted as you step into a haven of luxury where every detail has been meticulously crafted to provide an unparalleled living experience.\n\nThis stunning condo offers a perfect blend of lavishness and expansiveness, ensuring that you have all the room you need to unwind and indulge in the beauty that surrounds you. From the moment you enter, you\'ll be captivated by the sheer grandeur and elegance that emanates from every corner.\n\nThe living area is a true sanctuary of comfort and style, where plush furnishings invite you to relax and unwind in absolute luxury. Immerse yourself in the tranquility of this space as you bask in the natural light that pours in through the expansive windows, treating you to panoramic vistas that will take your breath away.\n\nThe kitchen is a culinary masterpiece, equipped with state-of-the-art appliances and designed with both functionality and aesthetics in mind. It offers ample counter space and top-of-the-line amenities, allowing you to unleash your inner chef and create culinary delights that will leave a lasting impression on your guests.', 150, '5342.50', 3, 3, '2023-03-14 22:29:28', '+38269255255', 10, 36, b'1', 'month', 'Condo', 2),
(4, 'Studio Apartment', 'Nestled in a tranquil corner, you\'ll find a truly enchanting and incredibly affordable studio apartment. This charming space offers a haven of comfort, inviting you to indulge in its cozy embrace. From the moment you step inside, you\'ll be captivated by the ambiance of warmth and tranquility that permeates every inch of this thoughtfully designed sanctuary.\n\nThe studio apartment boasts an intimate atmosphere, where every nook and cranny has been carefully crafted to maximize both functionality and aesthetic appeal. The tastefully curated decor creates an inviting environment, seamlessly blending modern sophistication with a touch of rustic charm. Soft hues and delicate textures adorn the walls, while carefully placed furnishings add an elegant flair', 30, '500.00', 0, 1, '2023-03-14 22:29:28', '999-999-9999', 11, 34, b'1', 'month', 'Studio', 4),
(91, 'Charming Brownstone House in Historic District with Private Backyard Oasis', 'Immerse yourself in the allure of this charming brownstone house, nestled within a coveted historic district of New York. The house boasts original architectural details, hardwood floors, and an inviting atmosphere. Step outside to discover a private backyard oasis, perfect for relaxing or entertaining. Experience the rich history of the neighborhood while enjoying the comforts of a modern home, complete with updated kitchen appliances and spacious living areas.', 123, '122.00', 22, 2, '2023-05-06 15:35:37', '+38386766878', 8, 122, b'1', 'month', 'House', 0),
(93, 'Spacious Family Home with Tranquil Backyard Oasis in the Heart of the City', 'Discover an oasis of tranquility in the heart of the bustling city with this spacious family home. Featuring generous living areas, multiple bedrooms, and a beautifully landscaped backyard, this residence offers ample space for relaxation and entertaining. The updated kitchen, elegant finishes, and abundant natural light add to the allure of this urban retreat, providing a perfect sanctuary for families seeking both comfort and convenience.\r\n', 123, '122.00', 22, 2, '2023-05-07 15:57:49', '+38386766878', 8, 121, b'1', 'month', 'House', 0),
(115, 'Elegant Townhouse with Timeless Architecture and Private Garden', 'Step into this elegant townhouse that exudes timeless architecture and sophisticated charm. The meticulously designed interior showcases exquisite details, including crown moldings, marble fireplaces, and grand staircases. Retreat to the private garden oasis, a serene escape from the bustling city. With its spacious layout, luxurious amenities, and proximity to renowned schools and parks, this townhouse offers a prestigious and refined urban lifestyle.\r\n', 123, '122.00', 3, 2, '2023-05-13 23:30:31', '+3838676878', 8, 144, b'1', 'month', 'Apartment', 0),
(122, 'Cozy and Characterful Studio in a Historic Brownstone Building', 'Welcome to this cozy and characterful studio situated within a beautifully preserved historic brownstone building. Adorned with exposed brick walls, hardwood floors, and large windows that flood the space with natural light, this studio exudes warmth and charm. The well-designed layout maximizes functionality, while the proximity to local amenities and attractions ensures a vibrant and connected city lifestyle. Experience the perfect blend of historic charm and modern comfort in this delightful studio.\r\n', 123, '122.00', 3, 2, '2023-05-15 16:46:15', '+3838676878', 8, 151, b'1', 'month', 'Studio', 0),
(123, 'Luxurious Condo in an Iconic Skyscraper with World-Class Amenities', 'Experience the epitome of luxury living in this extraordinary condo located within an iconic skyscraper in New York. Immerse yourself in breathtaking views of the cityscape from every room. The condo features sleek, modern design, high-end finishes, and access to world-class amenities, including a private fitness center, spa, and exclusive residents\' lounge. Enjoy the convenience of being steps away from renowned restaurants, shops, and cultural landmarks, making this condo an unrivaled urban sanctuary.\r\n', 123, '122.00', 3, 2, '2023-05-15 18:22:53', '+3838676878', 8, 152, b'1', 'month', 'Condo', 0),
(124, 'Modern Condo with Breathtaking City Views and Luxury Amenities', 'Step into this modern condo situated in downtown New York, offering breathtaking city views from its floor-to-ceiling windows. The sleek and contemporary design, high-end finishes, and state-of-the-art amenities create an exceptional urban living experience. Enjoy access to a fully equipped fitness center, a rooftop pool, and a 24-hour concierge service, making this condo the epitome of luxury and convenience.\r\n\r\n', 123, '122.00', 3, 2, '2023-05-15 23:13:25', '+3838676878', 8, 153, b'1', 'day', 'Condo', 0);

-- --------------------------------------------------------

--
-- Table structure for table `apartments_attributes`
--

CREATE TABLE `apartments_attributes` (
  `id` int(11) NOT NULL,
  `apartment_id` int(11) NOT NULL,
  `attribute_name` varchar(200) NOT NULL,
  `attribute_value` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `apartments_attributes`
--

INSERT INTO `apartments_attributes` (`id`, `apartment_id`, `attribute_name`, `attribute_value`) VALUES
(314, 1, 'Air Conditioning', 'Yes'),
(315, 1, 'Balcony', 'Yes'),
(316, 1, 'Parking', 'No'),
(317, 1, 'WiFi', 'Yes');

-- --------------------------------------------------------

--
-- Table structure for table `attributes`
--

CREATE TABLE `attributes` (
  `name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `attributes`
--

INSERT INTO `attributes` (`name`) VALUES
('Air Conditioning'),
('Appliances'),
('Balcony'),
('Elevator'),
('Furnished'),
('Parking'),
('Pets Allowed'),
('WiFi');

-- --------------------------------------------------------

--
-- Table structure for table `cities`
--

CREATE TABLE `cities` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `country_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cities`
--

INSERT INTO `cities` (`id`, `name`, `country_id`) VALUES
(1, 'Podgorica', 1),
(2, 'Nikšić', 1),
(3, 'Herceg Novi', 1),
(4, 'Pljevlja', 1),
(5, 'Bar', 1),
(6, 'Bijelo Polje', 1),
(7, 'Cetinje', 1),
(8, 'Budva', 1),
(9, 'Kotor', 1),
(10, 'Berane', 1),
(11, 'Ulcinj', 1),
(12, 'Tivat', 1),
(16, 'New York', 11),
(17, 'Los Angeles', 11),
(18, 'London', 12),
(19, 'Toronto', 13),
(20, 'Sydney', 14),
(21, 'Berlin', 15),
(22, 'Konik city', 1),
(23, 'Konik city', 1);

-- --------------------------------------------------------

--
-- Table structure for table `countries`
--

CREATE TABLE `countries` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `short_code` char(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `countries`
--

INSERT INTO `countries` (`id`, `name`, `short_code`) VALUES
(1, 'Montenegro', 'ME'),
(11, 'United States', 'US'),
(12, 'United Kingdom', 'UK'),
(13, 'Canada', 'CA'),
(14, 'Australia', 'AU'),
(15, 'Germany', 'DE');

-- --------------------------------------------------------

--
-- Table structure for table `favorites`
--

CREATE TABLE `favorites` (
  `user_id` int(11) NOT NULL,
  `apartment_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `favorites`
--

INSERT INTO `favorites` (`user_id`, `apartment_id`) VALUES
(8, 1),
(8, 4),
(8, 123);

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `id` int(11) NOT NULL,
  `path` varchar(300) NOT NULL,
  `apartment_id` int(11) NOT NULL,
  `size_mb` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`id`, `path`, `apartment_id`, `size_mb`) VALUES
(10, '/home/ja/Downloads/Images/b3.jpeg', 3, '10.00'),
(30, '/home/ja/Downloads/Images/a1.jpg', 1, '10.00'),
(34, '/home/ja/Downloads/Images/b1.jpg', 1, '10.00'),
(35, '/home/ja/Downloads/Images/c1.jpg', 1, '10.00'),
(71, '/home/ja/Downloads/Images/d1.jpg', 1, '3.22'),
(107, '/home/ja/Downloads/Images/a2.jpeg', 2, '8.08'),
(108, '/home/ja/Downloads/Images/b2.jpeg', 2, '3.22'),
(109, '/home/ja/Downloads/Images/c2.jpeg', 2, '8.08'),
(110, '/home/ja/Downloads/Images/d2.jpeg', 2, '3.22'),
(111, '/home/ja/Downloads/Images/e2.jpeg', 2, '8.08'),
(112, '/home/ja/Downloads/Images/f2.jpeg', 2, '3.22'),
(145, '/home/ja/Downloads/Images/a3.png', 3, '10.00'),
(146, '/home/ja/Downloads/Images/c3.jpeg', 3, '10.00'),
(147, '/home/ja/Downloads/Images/d3.jpeg', 3, '10.00'),
(148, '/home/ja/Downloads/Images/e3.jpeg', 3, '10.00'),
(149, '/home/ja/Downloads/Images/f3.jpeg', 3, '10.00'),
(150, '/home/ja/Downloads/Images/g3.png', 3, '10.00'),
(151, '/home/ja/Downloads/Images/h3.jpeg', 3, '10.00'),
(152, '/home/ja/Downloads/Images/i3.jpeg', 3, '10.00'),
(153, '/home/ja/Downloads/Images/j3.jpeg', 3, '10.00'),
(158, '/home/ja/Downloads/Images/b4.png', 4, '10.00'),
(159, '/home/ja/Downloads/Images/c4.png', 4, '10.00'),
(160, '/home/ja/Downloads/Images/d4.png', 4, '10.00'),
(161, '/home/ja/Downloads/Images/a5.jpg', 91, '5.00'),
(162, '/home/ja/Downloads/Images/b5.jpg', 91, '5.00'),
(163, '/home/ja/Downloads/Images/c5.jpg', 91, '2.00'),
(164, '/home/ja/Downloads/Images/d5.jpg', 91, '2.00'),
(165, '/home/ja/Downloads/Images/e5.jpg', 91, '5.00'),
(166, '/home/ja/Downloads/Images/f5.jpg', 91, '7.00'),
(167, '/home/ja/Downloads/Images/g5.jpg', 91, '10.00'),
(168, '/home/ja/Downloads/Images/h5.jpg', 91, '5.00'),
(169, '/home/ja/Downloads/Images/a6.jpeg', 93, '7.00'),
(170, '/home/ja/Downloads/Images/b6.jpeg', 93, '7.00'),
(171, '/home/ja/Downloads/Images/c6.jpeg', 93, '2.50'),
(172, '/home/ja/Downloads/Images/d6.jpeg', 93, '5.00'),
(173, '/home/ja/Downloads/Images/e6.jpeg', 93, '4.00'),
(174, '/home/ja/Downloads/Images/f6.jpeg', 93, '7.00'),
(175, '/home/ja/Downloads/Images/g6.jpeg', 93, '9.00'),
(176, '/home/ja/Downloads/Images/a7.jpg', 115, '4.00'),
(177, '/home/ja/Downloads/Images/b7.jpg', 115, '6.00'),
(178, '/home/ja/Downloads/Images/c7.jpg', 115, '5.00'),
(179, '/home/ja/Downloads/Images/d7.jpg', 115, '5.40'),
(180, '/home/ja/Downloads/Images/e7.jpg', 115, '12.00'),
(181, '/home/ja/Downloads/Images/f7.jpg', 115, '3.00'),
(182, '/home/ja/Downloads/Images/g7.jpg', 115, '10.00'),
(183, '/home/ja/Downloads/Images/a8.jpeg', 122, '4.22'),
(184, '/home/ja/Downloads/Images/b8.jpeg', 122, '5.00'),
(185, '/home/ja/Downloads/Images/c8.jpeg', 122, '6.70'),
(186, '/home/ja/Downloads/Images/d8.jpeg', 122, '8.00'),
(187, '/home/ja/Downloads/Images/a9.jpeg', 123, '4.00'),
(188, '/home/ja/Downloads/Images/b9.jpeg', 123, '7.54'),
(189, '/home/ja/Downloads/Images/c9.jpeg', 123, '4.00'),
(190, '/home/ja/Downloads/Images/d9.jpeg', 123, '6.70'),
(191, '/home/ja/Downloads/Images/a10.jpeg', 124, '4.50'),
(192, '/home/ja/Downloads/Images/b10.jpeg', 124, '10.20'),
(193, '/home/ja/Downloads/Images/c10.jpeg', 124, '5.60'),
(194, '/home/ja/Downloads/Images/d10.jpeg', 124, '6.00'),
(195, '/home/ja/Downloads/Images/f10.jpeg', 124, '4.50'),
(196, '/home/ja/Downloads/Images/g10.jpeg', 124, '5.60'),
(197, '/home/ja/Downloads/Images/h10.jpeg', 124, '5.70');

-- --------------------------------------------------------

--
-- Table structure for table `neighborhoods`
--

CREATE TABLE `neighborhoods` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `city_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `neighborhoods`
--

INSERT INTO `neighborhoods` (`id`, `name`, `city_id`) VALUES
(1, 'Manhattan', 16),
(2, 'Brooklyn', 16),
(3, 'Downtown', 19),
(4, 'West End', 18),
(5, 'Bondi Beach', 20),
(6, 'Hollywood', 17),
(7, 'Preko morače', 1),
(8, 'Centar', 1),
(9, 'Blok 5', 1),
(10, 'Stari aerodrom', 1),
(11, 'Zabjelo', 1),
(12, 'Adok', 8),
(13, 'Dubovica', 8),
(14, 'Murtovina i zlatica', 1),
(15, 'Brixton', 18),
(16, 'Mitte', 21);

-- --------------------------------------------------------

--
-- Table structure for table `periods`
--

CREATE TABLE `periods` (
  `name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `periods`
--

INSERT INTO `periods` (`name`) VALUES
('day'),
('month');

-- --------------------------------------------------------

--
-- Table structure for table `property_types`
--

CREATE TABLE `property_types` (
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `property_types`
--

INSERT INTO `property_types` (`name`) VALUES
('Apartment'),
('Condo'),
('House'),
('Studio');

-- --------------------------------------------------------

--
-- Table structure for table `rentals`
--

CREATE TABLE `rentals` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `apartment_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `rental_price` decimal(10,2) NOT NULL,
  `status` varchar(50) DEFAULT 'rented'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rentals`
--

INSERT INTO `rentals` (`id`, `user_id`, `apartment_id`, `start_date`, `end_date`, `rental_price`, `status`) VALUES
(35, 8, 3, '2024-02-01', '2025-05-05', '80137.50', 'rented'),
(36, 8, 2, '2024-02-01', '2025-05-05', '15000.00', 'rented'),
(37, 8, 1, '2024-02-01', '2024-03-01', '58000.00', 'rented'),
(38, 8, 4, '2024-02-01', '2024-03-20', '500.00', 'rented'),
(39, 8, 91, '2024-02-01', '2025-05-05', '1830.00', 'rented');

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL,
  `apartment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  `comment` text NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `is_active` bit(1) DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`id`, `apartment_id`, `user_id`, `grade`, `comment`, `created_at`, `is_active`) VALUES
(1, 4, 8, 5, ' Stan je savrsen! :D.Preporucio bih ga svima.Ugodno drustvo i sve Stan je savrsen! :D.Preporucio bih ga svima.Ugodno drustvo i sve Stan je savrsen! :D.Preporucio bih ga svima.Ugodno drustvo i sve Stan je savrsen! :D.Preporucio bih ga svima.Ugodno drustvo i sves', '2023-03-14 22:44:57', b'1'),
(3, 1, 10, 5, 'Absolutely loved this place! Will definitely come back!', '2023-03-14 22:44:57', b'1'),
(4, 3, 11, 2, 'Disappointing experience. Not as advertised.', '2023-03-14 22:44:57', b'1'),
(24, 4, 8, 5, 'Great apartment with beautiful views!', '2023-04-18 16:30:41', b'1'),
(25, 4, 8, 1, 'ssss', '2023-04-18 19:55:17', b'1'),
(26, 4, 8, 1, 'KKKKKKKKK', '2023-04-18 19:56:12', b'1'),
(27, 4, 8, 1, 'aaaa', '2023-04-18 19:56:34', b'0'),
(28, 4, 8, 1, 'aaaa', '2023-04-18 19:56:58', b'0'),
(29, 4, 8, 5, 'kkkkk', '2023-04-18 19:57:00', b'0'),
(30, 4, 8, 1, 'aaaa', '2023-04-18 19:57:08', b'0'),
(31, 4, 8, 1, 'Great apartment with beautiful views :D', '2023-04-18 20:32:23', b'1'),
(32, 4, 8, 1, 'KKKKKKKKK', '2023-04-18 20:32:48', b'1'),
(33, 4, 8, 5, 'akoakoakoako', '2023-04-18 23:56:40', b'0'),
(34, 4, 8, 5, 'komentar', '2023-04-19 20:58:21', b'1'),
(35, 4, 8, 5, 'komentar', '2023-04-19 21:01:34', b'1'),
(36, 4, 8, 5, 'komentar', '2023-04-19 21:01:43', b'1'),
(37, 4, 8, 5, 'OTAAAC!!!!', '2023-04-20 00:17:07', b'0'),
(38, 4, 8, 5, 'OTAAAC!!!!', '2023-04-20 00:17:13', b'0'),
(39, 4, 8, 5, 'OTAAAC!!!!', '2023-04-20 00:26:53', b'1'),
(40, 4, 8, 5, 'komentar', '2023-05-04 15:13:14', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(3, 'ROLE_ADMIN'),
(9, 'ROLE_REGISTERED');

-- --------------------------------------------------------

--
-- Table structure for table `statuses`
--

CREATE TABLE `statuses` (
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `statuses`
--

INSERT INTO `statuses` (`name`) VALUES
('cancelled'),
('ended'),
('rented');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `is_active` bit(1) DEFAULT b'0',
  `code` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`, `username`, `password`, `created_at`, `is_active`, `code`) VALUES
(8, 'John', 'Doe', 'johndoe@gmail.com', 'johndoe', '$2y$10$z2xPsg9sCceeR.aQyi.a6eZax/WVEONv6ZgRyUhaEWGVRSFl4g50.', '2023-03-14 22:18:51', b'1', ''),
(10, 'Bob', 'Smith', 'bobsmith@gmail.com', 'bobsmith', 'password789', '2023-03-14 22:18:51', b'1', ''),
(11, 'Alice', 'Johnson', 'alicejohnson@gmail.com', 'alicejohnson', 'tralala1243', '2023-03-14 22:18:51', b'1', ''),
(13, 'Marko', 'Vukovic', 'bula20@gmail.com', 'bula12345!', '$2y$10$cZjDNzJHTvZgUG3AYG5msO/TBJHvz0Ah10KQSiE2e8RlfNaiK2KbC', '2023-03-15 15:21:01', b'1', ''),
(54, 'Jovan', 'Vukovic', 'jovanvukovic09@gmail.com', 'lala', '$2a$10$iBG/F7RnWVeW6pxdD7c5c.6P7iTcUPSOGdYQzE5P0CzpYpU4tAytm', '2023-06-22 21:51:23', b'1', '');

-- --------------------------------------------------------

--
-- Table structure for table `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(8, 3),
(10, 9),
(54, 9);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `neighborhood_id` (`neighborhood_id`);

--
-- Indexes for table `apartments`
--
ALTER TABLE `apartments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `apartments_ibfk_2` (`address_id`),
  ADD KEY `period` (`period_name`),
  ADD KEY `property_type` (`property_type`),
  ADD KEY `apartments_ibfk_1` (`user_id`);

--
-- Indexes for table `apartments_attributes`
--
ALTER TABLE `apartments_attributes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `apartment_id` (`apartment_id`),
  ADD KEY `attribute_name` (`attribute_name`);

--
-- Indexes for table `attributes`
--
ALTER TABLE `attributes`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`id`),
  ADD KEY `country_id` (`country_id`);

--
-- Indexes for table `countries`
--
ALTER TABLE `countries`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `favorites`
--
ALTER TABLE `favorites`
  ADD PRIMARY KEY (`user_id`,`apartment_id`),
  ADD KEY `apartment_id` (`apartment_id`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `images_ibfk_1` (`apartment_id`);

--
-- Indexes for table `neighborhoods`
--
ALTER TABLE `neighborhoods`
  ADD PRIMARY KEY (`id`),
  ADD KEY `city_id` (`city_id`);

--
-- Indexes for table `periods`
--
ALTER TABLE `periods`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `property_types`
--
ALTER TABLE `property_types`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `rentals`
--
ALTER TABLE `rentals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `apartment_id` (`apartment_id`),
  ADD KEY `status` (`status`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reviews_ibfk_1` (`apartment_id`),
  ADD KEY `reviews_ibfk_2` (`user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `statuses`
--
ALTER TABLE `statuses`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `users_roles_ibfk_2` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=166;

--
-- AUTO_INCREMENT for table `apartments`
--
ALTER TABLE `apartments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=136;

--
-- AUTO_INCREMENT for table `apartments_attributes`
--
ALTER TABLE `apartments_attributes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=318;

--
-- AUTO_INCREMENT for table `cities`
--
ALTER TABLE `cities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `countries`
--
ALTER TABLE `countries`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `images`
--
ALTER TABLE `images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=198;

--
-- AUTO_INCREMENT for table `neighborhoods`
--
ALTER TABLE `neighborhoods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `rentals`
--
ALTER TABLE `rentals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `addresses`
--
ALTER TABLE `addresses`
  ADD CONSTRAINT `addresses_ibfk_1` FOREIGN KEY (`neighborhood_id`) REFERENCES `neighborhoods` (`id`);

--
-- Constraints for table `apartments`
--
ALTER TABLE `apartments`
  ADD CONSTRAINT `apartments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `apartments_ibfk_2` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`),
  ADD CONSTRAINT `apartments_ibfk_3` FOREIGN KEY (`period_name`) REFERENCES `periods` (`name`),
  ADD CONSTRAINT `apartments_ibfk_4` FOREIGN KEY (`property_type`) REFERENCES `property_types` (`name`);

--
-- Constraints for table `apartments_attributes`
--
ALTER TABLE `apartments_attributes`
  ADD CONSTRAINT `apartments_attributes_ibfk_1` FOREIGN KEY (`apartment_id`) REFERENCES `apartments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `apartments_attributes_ibfk_2` FOREIGN KEY (`attribute_name`) REFERENCES `attributes` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cities`
--
ALTER TABLE `cities`
  ADD CONSTRAINT `cities_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`);

--
-- Constraints for table `favorites`
--
ALTER TABLE `favorites`
  ADD CONSTRAINT `favorites_ibfk_1` FOREIGN KEY (`apartment_id`) REFERENCES `apartments` (`id`),
  ADD CONSTRAINT `favorites_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `images_ibfk_1` FOREIGN KEY (`apartment_id`) REFERENCES `apartments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `neighborhoods`
--
ALTER TABLE `neighborhoods`
  ADD CONSTRAINT `neighborhoods_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`);

--
-- Constraints for table `rentals`
--
ALTER TABLE `rentals`
  ADD CONSTRAINT `rentals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `rentals_ibfk_2` FOREIGN KEY (`apartment_id`) REFERENCES `apartments` (`id`),
  ADD CONSTRAINT `rentals_ibfk_3` FOREIGN KEY (`status`) REFERENCES `statuses` (`name`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`apartment_id`) REFERENCES `apartments` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `users_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `users_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
