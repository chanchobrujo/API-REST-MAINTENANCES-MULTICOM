-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-12-2022 a las 00:06:33
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectointegrador_21`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `appointment`
--

CREATE TABLE `appointment` (
  `id` varchar(12) NOT NULL,
  `created_date` datetime NOT NULL,
  `customer_id` varchar(6) DEFAULT NULL,
  `state` bit(1) NOT NULL,
  `total` decimal(5,2) NOT NULL,
  `user_register` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(22) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `category`
--

INSERT INTO `category` (`id`, `created_date`, `description`, `name`) VALUES
(1, '2022-12-27 17:47:28', 'fff', 'Prueba categoria'),
(2, '2022-12-27 17:50:05', 'fff', 'Prueba categoria 2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orderdetail`
--

CREATE TABLE `orderdetail` (
  `id` varchar(12) NOT NULL,
  `actual_price` decimal(4,2) NOT NULL,
  `detail` longtext NOT NULL,
  `quantity` int(11) NOT NULL,
  `subtotal` decimal(4,2) NOT NULL,
  `appointment_id` varchar(12) NOT NULL,
  `product_id` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `payment`
--

CREATE TABLE `payment` (
  `id` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `total` decimal(5,2) NOT NULL,
  `appointment_id` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product`
--

CREATE TABLE `product` (
  `id` varchar(4) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(25) NOT NULL,
  `price` decimal(4,2) NOT NULL,
  `created_date` datetime NOT NULL,
  `state` bit(1) NOT NULL,
  `category` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `display_name` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `role`
--

INSERT INTO `role` (`id`, `display_name`, `name`) VALUES
(1, 'Cliente', 'ROLE_CUSTOMER'),
(2, 'Moderador', 'ROLE_MOD'),
(3, 'Administrador', 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` varchar(6) NOT NULL,
  `created_date` datetime NOT NULL,
  `document_number` varchar(8) NOT NULL,
  `document_type` varchar(4) NOT NULL,
  `email` varchar(50) NOT NULL,
  `firts_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `number_phone` varchar(15) NOT NULL,
  `password` longtext NOT NULL,
  `state` bit(1) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `created_date`, `document_number`, `document_type`, `email`, `firts_name`, `last_name`, `number_phone`, `password`, `state`, `role`) VALUES
('486E96', '2022-12-27 11:55:35', '72657666', 'DNI', 'umb.kevsidorov@gmail.com', 'kevin', 'palma', '947275237', '$2a$10$UF1c3cXuPEcRjBTRH.AO9eCmJxKmDSHeGh1/n0z6vuksvhYukmv5C', b'1', 3),
('6C42DA', '2022-12-27 12:09:19', '72657668', 'DNI', 'primoppp666@gmail.com', 'lupe', 'perez', '942475238', '$2a$10$pVmjHfDO.0Sn0WQtN64icOdkhT7m5b/o/ac5q37oumvW3F1PCuD8G', b'1', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbufwiwkx727mgbc5t8gmtb1xv` (`appointment_id`),
  ADD KEY `FKdubukg3j0fymgci0idnd72k0` (`product_id`);

--
-- Indices de la tabla `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9vafbyi48ic7wo7n417cun7tf` (`appointment_id`);

--
-- Indices de la tabla `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqx9wikktsev17ctu0kcpkrafc` (`category`);

--
-- Indices de la tabla `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ac56dul55smn5it0ce76jswg3` (`display_name`),
  ADD UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6njoh3pti5jnlkowken3r8ttn` (`role`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `orderdetail`
--
ALTER TABLE `orderdetail`
  ADD CONSTRAINT `FKbufwiwkx727mgbc5t8gmtb1xv` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
  ADD CONSTRAINT `FKdubukg3j0fymgci0idnd72k0` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Filtros para la tabla `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `FK9vafbyi48ic7wo7n417cun7tf` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`);

--
-- Filtros para la tabla `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FKqx9wikktsev17ctu0kcpkrafc` FOREIGN KEY (`category`) REFERENCES `category` (`id`);

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK6njoh3pti5jnlkowken3r8ttn` FOREIGN KEY (`role`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
