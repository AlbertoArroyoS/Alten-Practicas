select * from dbo.librerias l
select * from dbo.autores a 
select * from dbo.libros l 
select * from dbo.libreria_libro ll 

delete from dbo.libreria_libro 
delete from dbo.libros

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
   
   
 -- Buscar por nombre usando la funcion de quitar las tildes y borrando los espacios en blanco
   
SELECT
    ta.id,
    ta."nombre",
    ta.apellidos 
FROM
    dbo.autores ta
WHERE
    replace(dbo.fn_pre_format_cadena(lower(concat(ta.nombre, ta.apellidos))), chr(32), '') LIKE '%' || replace(dbo.fn_pre_format_cadena(lower('Gabriel')), chr(32), '') || '%';

   

   
   

