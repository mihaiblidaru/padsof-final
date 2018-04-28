SET FOREIGN_KEY_CHECKS = 0;DROP TABLE IF EXISTS `caracteristica`;DROP TABLE IF EXISTS `opinion`;DROP TABLE IF EXISTS `reserva`;DROP TABLE IF EXISTS `oferta`;DROP TABLE IF EXISTS `inmueble`;DROP TABLE IF EXISTS `cliente`;SET FOREIGN_KEY_CHECKS = 1;CREATE TABLE `cliente` (`id` int UNSIGNED NOT NULL AUTO_INCREMENT,`identificador` varchar(30) NOT NULL,`password` varchar(50) NOT NULL,`full_name` varchar(80) NOT NULL,`rol` varchar(2) NOT NULL,`importe_pendiente` float NOT NULL DEFAULT 0.00,`ccard` varchar(16) NOT NULL,`problema_pagos` smallint NOT NULL DEFAULT 0,PRIMARY KEY (`id`) );CREATE TABLE `inmueble` (`id` int UNSIGNED NOT NULL AUTO_INCREMENT,`cliente` int UNSIGNED NOT NULL,`localidad` varchar(100) NOT NULL,`cp` int UNSIGNED NOT NULL,`direccion` varchar(500) NOT NULL,PRIMARY KEY (`id`) );CREATE TABLE `caracteristica` (`id` int UNSIGNED NOT NULL AUTO_INCREMENT,`clave` varchar(80) NOT NULL,`valor` varchar(80) NOT NULL,`inmueble` int UNSIGNED NOT NULL,PRIMARY KEY (`id`) );CREATE TABLE `oferta` (`id` int UNSIGNED NOT NULL AUTO_INCREMENT,`fechaInicio` date NOT NULL,`fechaFin` date NULL DEFAULT NULL,`precio` float NOT NULL,`fianza` float NOT NULL,`estado` varchar(30) NOT NULL,`tipo` varchar(15) NOT NULL,`inmueble` int UNSIGNED NOT NULL,`num_meses` smallint NULL,`descripcion` varchar(500) NOT NULL,`reserva` int UNSIGNED NULL,`demandante` int UNSIGNED NULL,PRIMARY KEY (`id`) );CREATE TABLE `reserva` (`id` int UNSIGNED NOT NULL AUTO_INCREMENT,`cliente` int UNSIGNED NOT NULL,`fechaInicio` date NOT NULL,`fechaCaducar` date NULL,`oferta` int UNSIGNED NOT NULL,PRIMARY KEY (`id`) );CREATE TABLE `opinion` (`id` int UNSIGNED NOT NULL AUTO_INCREMENT,`valor` int UNSIGNED NULL,`comentario` varchar(200) NULL,`padre` int UNSIGNED NULL,`oferta` int UNSIGNED NOT NULL,`usuario` int UNSIGNED NOT NULL,`fecha` date NOT NULL,PRIMARY KEY (`id`) );ALTER TABLE `inmueble` ADD CONSTRAINT `fk_Inmueble_Usuarios_1` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`id`);ALTER TABLE `caracteristica` ADD CONSTRAINT `fk_Caracteristicas_Inmueble_1` FOREIGN KEY (`inmueble`) REFERENCES `inmueble` (`id`);ALTER TABLE `oferta` ADD CONSTRAINT `fk_Ofertas_Inmueble_1` FOREIGN KEY (`inmueble`) REFERENCES `inmueble` (`id`);ALTER TABLE `reserva` ADD CONSTRAINT `fk_reserva_cliente_1` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`id`);ALTER TABLE `oferta` ADD CONSTRAINT `fk_oferta_cliente_1` FOREIGN KEY (`demandante`) REFERENCES `cliente` (`id`);ALTER TABLE `opinion` ADD CONSTRAINT `fk_Opinion_oferta_1` FOREIGN KEY (`oferta`) REFERENCES `oferta` (`id`);ALTER TABLE `opinion` ADD CONSTRAINT `fk_Opinion_cliente_1` FOREIGN KEY (`usuario`) REFERENCES `cliente` (`id`);ALTER TABLE `reserva` ADD CONSTRAINT `fk_reserva_oferta_1` FOREIGN KEY (`oferta`) REFERENCES `oferta` (`id`);ALTER TABLE `oferta` ADD CONSTRAINT `fk_oferta_reserva_1` FOREIGN KEY (`reserva`) REFERENCES `reserva` (`id`);