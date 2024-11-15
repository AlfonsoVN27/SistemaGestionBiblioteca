-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-11-2024 a las 09:34:29
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `librafybbdd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial`
--

CREATE TABLE `historial` (
  `id_historial` int(11) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `libro_id` int(11) DEFAULT NULL,
  `fecha_prestamo` date DEFAULT NULL,
  `fecha_devolucion` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE `libros` (
  `id_libro` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `fecha_prestamo` varchar(50) DEFAULT NULL,
  `fecha_devolucion` varchar(50) DEFAULT NULL,
  `categoria` enum('Ficción','No Ficción','Infantil','Inglés','Misterio y suspense') DEFAULT NULL,
  `estado` enum('EN PRÉSTAMO','DEVUELTO','CON RETRASO') DEFAULT NULL,
  `rutaImagen` varchar(255) DEFAULT NULL,
  `resumen` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`id_libro`, `titulo`, `autor`, `fecha_prestamo`, `fecha_devolucion`, `categoria`, `estado`, `rutaImagen`, `resumen`) VALUES
(6, 'Cien años de soledad', 'Gabriel García Márquez', '2024-04-08', '2024-05-07', 'Ficción', 'DEVUELTO', 'cien_años.jpg', 'La novela cuenta la historia de la familia Buendía en el pueblo ficticio de Macondo. A través de generaciones, los Buendía viven episodios de amor, tragedia, y realismo mágico, capturando la esencia de la soledad y los ciclos de repetición en la historia humana.\n\n'),
(7, '1984', 'George Well', '2024-12-07', '2024-01-01', 'Ficción', 'EN PRÉSTAMO', '1984.jpg', 'En una sociedad distópica dominada por un gobierno totalitario y omnipresente, Winston Smith lucha contra la opresión del Gran Hermano y la manipulación de la verdad. La novela explora temas de vigilancia, censura y la pérdida de la libertad individual.'),
(8, 'Sapiens: De animales a dioses', 'Yuval Noah Harari', '2024-04-18', '2024-04-19', 'No Ficción', 'EN PRÉSTAMO', 'sapiens.jpg', 'Este ensayo traza la historia de la humanidad, desde el surgimiento del Homo sapiens hasta la sociedad moderna. Harari examina cómo la revolución cognitiva, agrícola y científica moldearon nuestras vidas y plantea preguntas sobre el futuro de la humanidad.\n\n'),
(9, 'Franco para jovenes', 'José A. Martínez Soler', '2024-03-10', '2024-04-06', 'No Ficción', 'EN PRÉSTAMO', 'fran_jovenes.jpg', 'Un libro destinado a educar a las nuevas generaciones sobre la figura de Francisco Franco y su papel en la historia de España. Explica los aspectos más relevantes de su dictadura y cómo marcó el curso del país.'),
(10, 'Harry Potter y la piedra filosofal', 'J.K. Rowling', '2024-12-05', '2024-01-03', 'Infantil', 'EN PRÉSTAMO', 'harry.jpg', 'La primera entrega de la serie de Harry Potter sigue la historia de un joven mago que descubre sus poderes y asiste a la escuela de magia Hogwarts, donde enfrenta al malvado Voldemort mientras hace amigos y vive aventuras mágicas.'),
(11, 'El niño con el pijama de rayas', 'John Boyne', '2024-12-06', '2024-12-31', 'Infantil', 'EN PRÉSTAMO', 'niño_pijama.jpg', 'La novela narra la amistad entre Bruno, un niño alemán, y Shmuel, un niño judío en un campo de concentración. Desde la perspectiva inocente de Bruno, se muestran los horrores del Holocausto de una manera conmovedora y desgarradora.'),
(12, 'Pride and Prejudice', 'Jane Austen', '2024-04-25', '2024-04-29', 'Inglés', 'EN PRÉSTAMO', 'pride.jpg', 'Ambientada en la Inglaterra rural del siglo XIX, esta novela clásica explora las complejidades de las relaciones y la sociedad a través de la historia de amor entre Elizabeth Bennet y el reservado señor Darcy.'),
(13, 'The Great Gatsby', 'F. Scott Fitzgerald', '2024-08-02', '2024-08-21', 'Inglés', 'EN PRÉSTAMO', 'gatsby.jpg', 'Ambientada en los años 20, la historia sigue a Jay Gatsby, un millonario con un amor obsesivo por Daisy Buchanan. La novela examina temas de amor, ambición, y la corrupción del \"sueño americano\".'),
(14, 'El Enigma de Darwin', 'M.A. Rothman', '2024-04-26', '2024-05-19', 'Misterio y suspense', 'EN PRÉSTAMO', 'enigma.jpg', 'Un libro que explora la teoría de la evolución de Charles Darwin y sus implicaciones para la ciencia y la religión. Profundiza en los misterios de la selección natural y cómo ha influido en nuestra comprensión del mundo.\n\n'),
(15, 'Gone Girl', 'Gillian Flynn', '2024-10-02', '2024-10-19', 'Misterio y suspense', 'CON RETRASO', 'gone.jpg', 'Un thriller psicológico sobre la desaparición de Amy Dunne y las sospechas que recaen sobre su esposo, Nick. La novela explora la complejidad de las relaciones y la manipulación en un juego oscuro y retorcido de secretos y mentiras.'),
(28, 'El principito', 'Antoine de Saint-Exupéry', '2024-11-01', '2024-11-15', 'Infantil', 'DEVUELTO', 'principito.jpg', 'Una historia poética y filosófica sobre un joven príncipe que viaja por diferentes planetas, conociendo personajes que le enseñan sobre la naturaleza humana. La obra es una reflexión sobre la amistad, el amor y la inocencia.'),
(29, 'Los cuentos de la abuela', 'Fernando Pérez', '2024-11-05', '2024-11-20', 'Infantil', 'DEVUELTO', 'cuentosabuela.jpg', 'Una colección de historias tradicionales transmitidas de generación en generación, que enseñan valores, moralejas y la cultura de antaño. Ideal para toda la familia y para compartir historias entrañables.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitudes`
--

CREATE TABLE `solicitudes` (
  `id_solicitud` int(11) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  `libro_id` int(11) DEFAULT NULL,
  `modo_entrega` enum('RECOGIDA','A DOMICILIO') NOT NULL,
  `disponibilidad` enum('EN PRÉSTAMO','DISPONIBLE') NOT NULL,
  `estado` enum('pendiente','rechazado','aprobado') DEFAULT 'pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `solicitudes`
--

INSERT INTO `solicitudes` (`id_solicitud`, `usuario_id`, `libro_id`, `modo_entrega`, `disponibilidad`, `estado`) VALUES
(16, 11, 10, 'A DOMICILIO', 'DISPONIBLE', 'pendiente'),
(17, 6, 11, 'A DOMICILIO', 'EN PRÉSTAMO', 'aprobado'),
(18, 4, 12, 'RECOGIDA', 'DISPONIBLE', 'rechazado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `num_telefono` varchar(20) DEFAULT NULL,
  `dni` varchar(20) DEFAULT NULL,
  `contrasena` varchar(255) NOT NULL,
  `es_admin` tinyint(1) NOT NULL DEFAULT 0,
  `nomUsuario` varchar(50) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `fecha_nacimiento`, `email`, `num_telefono`, `dni`, `contrasena`, `es_admin`, `nomUsuario`, `direccion`) VALUES
(1, 'Juan Pérez', '1985-05-15', 'juanperez@email.com', '123456789', '12345678A', 'contraseña1', 0, 'juanperez', 'Calle Ficticia, 123'),
(2, 'Ana Gómez', '1990-08-23', 'anagomez@email.com', '987654321', '23456789B', 'contraseña2', 1, 'anagomez', 'Avenida Siempre Viva, 456'),
(4, 'María López', '1995-11-30', 'maria@email.com', '223344556', '45678901D', 'contraseña4', 0, 'marialopez', 'Calle Luna, 101'),
(5, 'Pedro Sánchez', '1982-07-14', 'pedro@email.com', '334455667', '56789012E', 'contraseña5', 1, 'pedrosanchez', 'Calle Sol, 202'),
(6, 'Carla Espigares', '2006-05-19', 'carlaliun@gmail.com', '658675422', '37445221J', 'Carlaa11', 0, 'Carla11', 'sucasa 6ºA'),
(7, 'Juan Vicaria', '1961-03-12', 'vicarelo@gmail.com', '658772134', '78654422R', 'Vicarelo11', 0, 'Vicarelo', 'eufrates 35'),
(8, 'Alfonso1', '2004-06-01', 'alfonso@gmail.com', '658681941', '30986123R', 'alfonso11', 0, 'Alfonso1', 'eufrates 35'),
(9, 'Pedro Picapiedras', '2000-10-02', 'pica@gmail.com', '657652333', '18493598Y', 'Picapie11', 0, 'Picapie', 'cueva 3'),
(11, 'ana nieves', '1966-07-26', 'ana@gmail.com', '658681940', '12345678B', '12345678a', 0, 'anita', 'eufrates 35');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `historial`
--
ALTER TABLE `historial`
  ADD PRIMARY KEY (`id_historial`),
  ADD KEY `usuario_id` (`usuario_id`),
  ADD KEY `libro_id` (`libro_id`);

--
-- Indices de la tabla `libros`
--
ALTER TABLE `libros`
  ADD PRIMARY KEY (`id_libro`);

--
-- Indices de la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  ADD PRIMARY KEY (`id_solicitud`),
  ADD KEY `usuario_id` (`usuario_id`),
  ADD KEY `libro_id` (`libro_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `historial`
--
ALTER TABLE `historial`
  MODIFY `id_historial` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `libros`
--
ALTER TABLE `libros`
  MODIFY `id_libro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  MODIFY `id_solicitud` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `historial`
--
ALTER TABLE `historial`
  ADD CONSTRAINT `historial_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE,
  ADD CONSTRAINT `historial_ibfk_2` FOREIGN KEY (`libro_id`) REFERENCES `libros` (`id_libro`) ON DELETE CASCADE;

--
-- Filtros para la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  ADD CONSTRAINT `solicitudes_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE,
  ADD CONSTRAINT `solicitudes_ibfk_2` FOREIGN KEY (`libro_id`) REFERENCES `libros` (`id_libro`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
