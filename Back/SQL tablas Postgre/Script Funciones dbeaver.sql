----------------------------------------------------------------------
--fn_pre_format_cadena function ** FUNCION PARA REEMPLAZAR LAS TILDES
----------------------------------------------------------------------
create or replace function dbo.fn_pre_format_cadena(
in ref_msg text
)returns text
as 
$$
declare 
 r_a text;
 r_e text;
 r_i text;
 r_o text; 
 r_u text;
begin
 r_a:=replace(lower(ref_msg),chr(225),chr(97));
 r_e:=replace(lower(r_a),chr(233),chr(101));
 r_i:=replace(lower(r_e),chr(237),chr(105));
 r_o:=replace(lower(r_i),chr(243),chr(111));
 r_u:=replace(lower(r_o),chr(250),chr(117));
 return r_u;
end;
$$
language 'plpgsql';



----------------------------------------------------------------------
--fn_buscar_autor function **FUNCION PARA BUSCAR POR NOMBRE DEL AUTOR
----------------------------------------------------------------------
 

-- crear funcion para buscar por nombre
   
create or replace function dbo.fn_buscar_autor(
    in in_key_word varchar
) returns table (
    id_autor int,
    nombre varchar,
    apellidos varchar
)
as 
$$
begin
    return query (
        SELECT
            ta.id_autor,
            ta."nombre",
            ta.apellidos 
        FROM
            dbo.autores ta
        WHERE
            replace(dbo.fn_pre_format_cadena(lower(concat(ta.nombre, ta.apellidos))), chr(32), '') LIKE '%' || replace(dbo.fn_pre_format_cadena(lower(in_key_word)), chr(32), '') || '%'
    );
end;
$$
language 'plpgsql';



select * from dbo.libros l 

----------------------------------------------------------------------
--fn_guardar_autor function -- FUNCION PARA GUARDAR UN AUTOR
----------------------------------------------------------------------
create or replace function dbo.fn_guardar_autor(
in in_nombre varchar,
in in_apellidos varchar
)
returns setof dbo.autores
as 
$$
begin 
	insert into dbo.autores(nombre,apellidos)
	values(in_nombre,in_apellidos);
	return query
    select a.* from dbo.autores a where a.id_autor = lastval();
end;
$$
language 'plpgsql';


----------------------------------------------------------------------
--fn_buscar_libro function **FUNCION PARA BUSCAR POR NOMBRE DEL LIBRO
----------------------------------------------------------------------
 

-- crear funcion para buscar por nombre
      
create or replace function dbo.fn_buscar_nombre_libro (
    in in_key_word varchar
) 
returns table (
    id_libro int,
    titulo varchar,
    id_autor int,
    nombre varchar,
    apellidos varchar,
    genero varchar,
    paginas int,
    editorial varchar,
    descripcion varchar,
    precio double precision
)
as 
$$
begin
    return query (
        SELECT
		    ta.id_libro,
		    ta.titulo ,
		    ta.id_autor,
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
		    dbo.autores ON ta.id_autor = dbo.autores.id_autor
		WHERE
		    replace(dbo.fn_pre_format_cadena(lower(ta.titulo)), chr(32), '') LIKE '%' || replace(dbo.fn_pre_format_cadena(lower(in_key_word)), chr(32), '') || '%'
    );
end;
$$
language 'plpgsql';


----------------------------------------------------------------------
--fn_guardar_libro function -- FUNCION PARA GUARDAR UN LIBRO , comprueba si el autor existe y se añade el id al libro y si no existe lo crea con un nuevo id
-- tambien comprueba si el libro existe para no añadirlo
----------------------------------------------------------------------

create or replace function dbo.fn_guardar_libro(
    in_titulo varchar,
    in_nombre_autor varchar,
    in_apellidos_autor varchar,
    in_genero varchar,
    in_paginas int,
    in_editorial varchar,
    in_descripcion varchar,
    in_precio double precision
)
returns setof dbo.libros
as 
$$
declare
    v_id_autor int;
    v_id_libro int;
begin 
    -- Verificar si el autor existe
	select id_autor into v_id_autor from dbo.autores where nombre = in_nombre_autor and apellidos = in_apellidos_autor;
	
	-- Si el autor no existe, insertarlo
	if v_id_autor is null then
	    insert into dbo.autores(nombre, apellidos) values(in_nombre_autor, in_apellidos_autor);
	    v_id_autor := lastval(); -- Obtener el ID del nuevo autor
	end if;
	
	-- Verificar si el libro ya existe
	select id_libro into v_id_libro from dbo.libros where titulo = in_titulo and id_autor = v_id_autor;
	
	-- Si el libro no existe, insertarlo
	if v_id_libro is null then
	    insert into dbo.libros(titulo, id_autor, genero, paginas, editorial, descripcion, precio)
	    values(in_titulo, v_id_autor, in_genero, in_paginas, in_editorial, in_descripcion, in_precio);
	else
	    raise exception 'El libro ya existe en la base de datos.';
	end if;

    -- Devolver el libro recién insertado
    return query select * from dbo.libros where id_libro = lastval();
end;
$$
language 'plpgsql';


--------------------------------------------------------------------------------------
