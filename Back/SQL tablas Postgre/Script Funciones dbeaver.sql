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
--fn_save_author function -- FUNCION PARA GUARDAR UN AUTOR
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

