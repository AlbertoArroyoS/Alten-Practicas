select * from dbo.librerias l


-- QUITAR LAS TILDES A UNA COLUMNA DE UNA TABLA USANDO LA FUNCION CREADA fn_pre_format_cadena
select dbo.fn_pre_format_cadena(nombre_dueno) from dbo.librerias l 
