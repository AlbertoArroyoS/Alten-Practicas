-- Ver datos tablas
select * from dbo.librerias l
select * from dbo.autores a 
select * from dbo.libros l 
select * from dbo.libreria_libro ll 

delete from dbo.libreria_libro 
delete from dbo.libros

-- borrar tablas

DROP TABLE dbo.librerias
DROP TABLE dbo.autores
DROP TABLE dbo.libros 
DROP TABLE dbo.libreria_libro


SELECT id FROM dbo.autores WHERE nombre = 'Gabriel' AND apellidos = 'García Márquez';

-- QUITAR LAS TILDES A UNA COLUMNA DE UNA TABLA USANDO LA FUNCION CREADA fn_pre_format_cadena
select dbo.fn_pre_format_cadena(nombre_dueno) from dbo.librerias l 


-- Buscar por nombre

SELECT
    ta.id,
    ta."nombre",
    ta.apellidos 
FROM
    dbo.autores ta
WHERE
    lower(concat(ta.nombre, ' ', ta.apellidos)) LIKE '%' || lower('Gabriel') || '%';
   
-- Buscar por nombre usando la funcion de quitar las tildes
   
   SELECT
    ta.id,
    ta."nombre",
    ta.apellidos 
FROM
    dbo.autores ta
WHERE
    dbo.fn_pre_format_cadena(lower(concat(ta.nombre, ' ', ta.apellidos))) LIKE '%' || dbo.fn_pre_format_cadena(lower('Gabriel') || '%');
   
   
 -- Buscar por nombre usando la funcion de quitar las tildes y borrando los espacios en blanco, donde encuentre el caracter del espacio 32 lo ponga vacio
   
SELECT
    ta.id,
    ta."nombre",
    ta.apellidos 
FROM
    dbo.autores ta
WHERE
    replace(dbo.fn_pre_format_cadena(lower(concat(ta.nombre, ta.apellidos))), chr(32), '') LIKE '%' || replace(dbo.fn_pre_format_cadena(lower('St')), chr(32), '') || '%';

   

 -- Usar la funcion para buscar por nombre
   
select * from dbo.autores a ;

select * from dbo.fn_buscar_autor('lee');

select dbo.fn_format_cadena(nombre) from dbo.autores;

select count(*) from dbo.fn_buscar_autor('st')

select * from dbo.fn_buscar_autor('le')


-- (Usar la funcion para guardar un autor)

select * from dbo.fn_guardar_autor('Alberto','Arroyo Santofimia');



--
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
