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


DROP FUNCTION IF EXISTS dbo.fn_guardar_autor(character varying, character varying) CASCADE;
DROP TABLE IF EXISTS dbo.autores CASCADE;

-- 

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



-- Buscar por nombre de libro
select * from dbo.libros l 

SELECT
    ta.id_libro,
    ta.titulo ,
    ta.autor_id,
    ta.genero,
    ta.paginas,
    ta.editorial,
    ta.descripcion,
    ta.precio 
FROM
    dbo.libros ta
WHERE
    replace(dbo.fn_pre_format_cadena(lower(ta.titulo)), chr(32), '') LIKE '%' || replace(dbo.fn_pre_format_cadena(lower('cien')), chr(32), '') || '%';
   
-- buscar por nombre del libro y que con un join nos del el nombre y el apellido, no el id
   
SELECT
    ta.id_libro,
    ta.titulo ,
    ta.autor_id ,
    dbo.autores.nombre AS nombre_autor,
    dbo.autores.apellidos as apellidos_autor,
    ta.genero,
    ta.paginas,
    ta.editorial,
    ta.descripcion,
    ta.precio 
FROM
    dbo.libros ta
JOIN
    dbo.autores ON ta.autor_id = dbo.autores.id_autor
WHERE
    replace(dbo.fn_pre_format_cadena(lower(ta.titulo)), chr(32), '') LIKE '%' || replace(dbo.fn_pre_format_cadena(lower('cien')), chr(32), '') || '%';

-- funcion para buscar titulo libro
   
select * from dbo.fn_buscar_nombre_libro('a');


