select * from dbo.librerias l
select * from dbo.autores a 
select * from dbo.libros l 
select * from dbo.libreria_libro ll 




-- QUITAR LAS TILDES A UNA COLUMNA DE UNA TABLA USANDO LA FUNCION CREADA fn_pre_format_cadena
select dbo.fn_pre_format_cadena(nombre_dueno) from dbo.librerias l 
