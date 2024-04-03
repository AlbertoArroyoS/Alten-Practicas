-- Datos para la BBDD db_libreria_Alten


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



