﻿-- Datos para la BBDD db_libreria_Alten

--1. librerias ficticias

INSERT INTO dbo.librerias 
    (nivel_permiso, ciudad, direccion, nombre_dueno, nombre_libreria, email) 
VALUES 
    (1, 'Ciudad A', 'Calle 123', 'Juan Pérez', 'Librería ABC', 'emailabc@ejemplo.com'),
    (2, 'Ciudad B', 'Avenida 456', 'María Gómez', 'Librería XYZ', 'emailxyz@ejemplo.com'),
    (3, 'Ciudad C', 'Plaza Principal', 'Carlos Martínez', 'Librería 123', 'email123@ejemplo.com'),
    (1, 'Ciudad D', 'Calle Principal', 'Laura Rodríguez', 'Librería Test', 'emailtest@ejemplo.com');



--2. Insertar datos de autores 
INSERT INTO dbo.autores (nombre, apellidos) VALUES 
('Gabriel', 'García Márquez'),
('J.K.', 'Rowling'),
('Harper', 'Lee'),
('Stephen', 'King');


--3. Insertar libros obteniendo los ids de autores que ya existen
-- Obtener los IDs de los autores
DO $$
DECLARE
    gabrielId INT;
    jkId INT;
    harperId INT;
    stephenId INT;
BEGIN
    -- Obtener los IDs de los autores
    SELECT id_autor INTO gabrielId FROM dbo.autores WHERE nombre = 'Gabriel' AND apellidos = 'García Márquez';
    SELECT id_autor INTO jkId FROM dbo.autores WHERE nombre = 'J.K.' AND apellidos = 'Rowling';
    SELECT id_autor INTO harperId FROM dbo.autores WHERE nombre = 'Harper' AND apellidos = 'Lee';
    SELECT id_autor INTO stephenId FROM dbo.autores WHERE nombre = 'Stephen' AND apellidos = 'King';
    
    -- Insertar datos de libros con los IDs de autores
    INSERT INTO dbo.libros (paginas, id_autor, descripcion, editorial, genero, titulo)
    VALUES 
    (300, gabrielId, 'Una saga familiar que abarca varias generaciones de la familia Buendía en el ficticio pueblo de Macondo, describiendo la historia de su fundación y la decadencia de la familia.', 'Editorial Sudamericana', 'Ficción', 'Cien años de soledad'),
    (400, jkId, 'La historia sigue a Harry Potter, un joven mago, mientras descubre su verdadera identidad y lucha contra Lord Voldemort, el mago tenebroso que intentó asesinarlo cuando era un bebé.', 'Salani Editore', 'Fantasía', 'Harry Potter y la piedra filosofal'),
    (250, harperId, 'La novela explora los temas del racismo y la injusticia a través de la historia de Atticus Finch, un abogado que defiende a un hombre negro acusado de violar a una mujer blanca.', 'J.B. Lippincott & Co.', 'Novela', 'Matar a un ruiseñor'),
    (350, stephenId, 'Jack Torrance, un aspirante a escritor y alcohólico en recuperación, acepta un trabajo como cuidador de invierno del Hotel Overlook en las montañas de Colorado, lo que lleva a una serie de eventos terroríficos.', 'Doubleday', 'Terror', 'El resplandor');
END$$;





--4. Asignar libro a libreria
-- Ahora Supongamos que queremos asignar el libro "Cien años de soledad" a la Librería ABC con una cantidad de 20 y un precio de 30.99,
-- y el libro "Harry Potter y la piedra filosofal" a la Librería XYZ con una cantidad de 15 y un precio de 25.99.


-- Obtener los IDs de las librerías y los libros
DO $$DECLARE
    libreriaABCId INT;
    libreriaXYZId INT;
    cienAniosDeSoledadId INT;
    harryPotterId INT;
BEGIN
    SELECT id_libreria INTO libreriaABCId FROM dbo.librerias WHERE nombre_libreria = 'Librería ABC';
    SELECT id_libreria INTO libreriaXYZId FROM dbo.librerias WHERE nombre_libreria = 'Librería XYZ';
    SELECT id_libro INTO cienAniosDeSoledadId FROM dbo.libros WHERE titulo = 'Cien años de soledad';
    SELECT id_libro INTO harryPotterId FROM dbo.libros WHERE titulo = 'Harry Potter y la piedra filosofal';

    -- Insertar registros en la tabla intermedia
    INSERT INTO dbo.libreria_libro (id_libreria, id_libro, cantidad, edicion, precio, fecha_publicacion)
    VALUES 
        (libreriaABCId, cienAniosDeSoledadId, 20, 1, 30.99, '2023-01-01'), -- Para la Librería ABC y "Cien años de soledad"
        (libreriaXYZId, harryPotterId, 15, 2 , 25.99, '2023-01-02'); -- Para la Librería XYZ y "Harry Potter y la piedra filosofal"
END$$;

-- Insertar clientes

INSERT INTO dbo.clientes (nombre, apellidos, email, password, nivel_permiso)
VALUES 
    ('Juan', 'Pérez', 'juan.perez@example.com', 'p4ssw0rd', 1),
    ('María', 'Gómez', 'maria.gomez@example.com', 'm4ri4p4ss', 2),
    ('Carlos', 'Martínez', 'carlos.martinez@example.com', 'c4rl05', 3),
    ('Laura', 'Rodríguez', 'laura.rodriguez@example.com', 'laulau123', 1);

 
   
-- Insertar los libros que compran los clientes   
   
INSERT INTO dbo.cliente_compra_libro (id_cliente, id_libro, fecha_compra, precio)
VALUES 
    (1, 1, '2023-04-01', 29.99),
    (2, 2, '2023-04-02', 39.99),
    (3, 3, '2023-04-03', 19.99);
   







-- **********************************************************************************
-- SENTENCIAS VIEJAS -- DE CUANTO HABRIA SOLO 2 TABLAS

-- Insertar datos de autores ficticios


INSERT INTO dbo.librerias (nivel_permiso, ciudad, direccion, nombre_dueno, nombre_libreria) 
VALUES 
(1, 'Ciudad A', 'Calle 123', 'Juan Pérez', 'Librería ABC'),
(2, 'Ciudad B', 'Avenida 456', 'María Gómez', 'Librería XYZ'),
(3, 'Ciudad C', 'Plaza Principal', 'Carlos Martínez', 'Librería 123'),
(1, 'Ciudad D', 'Calle Principal', 'Laura Rodríguez', 'Librería Test');


INSERT INTO dbo.libros (paginas, precio, autor, descripcion, editorial, genero, titulo)
VALUES 
(300, 25.99, 'Gabriel García Márquez', 'Una saga familiar que abarca varias generaciones de la familia Buendía en el ficticio pueblo de Macondo, describiendo la historia de su fundación y la decadencia de la familia.', 'Editorial Sudamericana', 'Ficción', 'Cien años de soledad'),
(400, 19.99, 'J.K. Rowling', 'La historia sigue a Harry Potter, un joven mago, mientras descubre su verdadera identidad y lucha contra Lord Voldemort, el mago tenebroso que intentó asesinarlo cuando era un bebé.', 'Salani Editore', 'Fantasía', 'Harry Potter y la piedra filosofal'),
(250, 15.50, 'Harper Lee', 'La novela explora los temas del racismo y la injusticia a través de la historia de Atticus Finch, un abogado que defiende a un hombre negro acusado de violar a una mujer blanca.', 'J.B. Lippincott & Co.', 'Novela', 'Matar a un ruiseñor'),
(350, 30.75, 'Stephen King', 'Jack Torrance, un aspirante a escritor y alcohólico en recuperación, acepta un trabajo como cuidador de invierno del Hotel Overlook en las montañas de Colorado, lo que lleva a una serie de eventos terroríficos.', 'Doubleday', 'Terror', 'El resplandor');




-- Supongamos que queremos asignar el libro "Cien años de soledad" a la Librería ABC con una cantidad de 20 y un precio de 30.99,
-- y el libro "Harry Potter y la piedra filosofal" a la Librería XYZ con una cantidad de 15 y un precio de 25.99.


-- Obtener los IDs de las librerías y los libros
DO $$DECLARE
    libreriaABCId INT;
    libreriaXYZId INT;
    cienAniosDeSoledadId INT;
    harryPotterId INT;
BEGIN
    SELECT id_libreria INTO libreriaABCId FROM dbo.librerias WHERE nombre_libreria = 'Librería ABC';
    SELECT id_libreria INTO libreriaXYZId FROM dbo.librerias WHERE nombre_libreria = 'Librería XYZ';
    SELECT id_libro INTO cienAniosDeSoledadId FROM dbo.libros WHERE titulo = 'Cien años de soledad';
    SELECT id_libro INTO harryPotterId FROM dbo.libros WHERE titulo = 'Harry Potter y la piedra filosofal';

    -- Insertar registros en la tabla intermedia
    INSERT INTO dbo.libreria_libro (libreria_id, libro_id, cantidad, precio)
    VALUES 
    (libreriaABCId, cienAniosDeSoledadId, 20, 30.99), -- Para la Librería ABC y "Cien años de soledad"
    (libreriaXYZId, harryPotterId, 15, 25.99); -- Para la Librería XYZ y "Harry Potter y la piedra filosofal"
END$$;