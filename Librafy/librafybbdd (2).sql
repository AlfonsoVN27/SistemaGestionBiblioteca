-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.28-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para librafybbdd
CREATE DATABASE IF NOT EXISTS `librafybbdd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `librafybbdd`;

-- Volcando estructura para tabla librafybbdd.historial
CREATE TABLE IF NOT EXISTS `historial` (
  `id_historial` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_id` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `fecha_prestamo` varchar(10) NOT NULL,
  `fecha_devolucion` varchar(10) NOT NULL,
  `estado` varchar(50) NOT NULL,
  PRIMARY KEY (`id_historial`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `historial_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla librafybbdd.libros
CREATE TABLE IF NOT EXISTS `libros` (
  `id_libro` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `fecha_prestamo` varchar(50) DEFAULT NULL,
  `fecha_devolucion` varchar(50) DEFAULT NULL,
  `categoria` enum('Ficción','No Ficción','Infantil','Inglés','Misterio y suspense') DEFAULT NULL,
  `estado` enum('EN PRÉSTAMO','DEVUELTO','CON RETRASO') DEFAULT NULL,
  `rutaImagen` varchar(255) DEFAULT NULL,
  `resumen` text DEFAULT NULL,
  PRIMARY KEY (`id_libro`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla librafybbdd.solicitudes
CREATE TABLE IF NOT EXISTS `solicitudes` (
  `id_solicitud` int(11) NOT NULL AUTO_INCREMENT,
  `usuario_id` int(11) DEFAULT NULL,
  `libro_id` int(11) DEFAULT NULL,
  `modo_entrega` enum('RECOGIDA','A DOMICILIO') NOT NULL,
  `disponibilidad` enum('EN PRÉSTAMO','DISPONIBLE') NOT NULL,
  `estado` enum('pendiente','rechazado','aprobado') DEFAULT 'pendiente',
  PRIMARY KEY (`id_solicitud`),
  KEY `usuario_id` (`usuario_id`),
  KEY `libro_id` (`libro_id`),
  CONSTRAINT `solicitudes_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE,
  CONSTRAINT `solicitudes_ibfk_2` FOREIGN KEY (`libro_id`) REFERENCES `libros` (`id_libro`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla librafybbdd.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `num_telefono` varchar(20) DEFAULT NULL,
  `dni` varchar(20) DEFAULT NULL,
  `contrasena` varchar(255) NOT NULL,
  `es_admin` tinyint(1) NOT NULL DEFAULT 0,
  `nomUsuario` varchar(50) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `dni` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- La exportación de datos fue deseleccionada.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
