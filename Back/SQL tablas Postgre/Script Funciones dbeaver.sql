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
--fn_search_author function **FUNCION PARA BUSCAR POR NOMBRE DEL AUTOR
----------------------------------------------------------------------
create or replace function dbo.fn_search_author(
in in_key_word varchar,
in in_state varchar
)returns table(
idauthor int,
name varchar,
lastname varchar,
state varchar
)
as 
$$
begin
	return query(
	select 
	ta.idauthor,
	ta."name",
	ta.lastname,
	ta.state
	from dbo.t_author ta 
	where ta.state = in_state and 
	replace(dbo.fn_pre_format_cadena(lower(concat(ta."name" || ta.lastname))),chr(32),'') like '%'|| replace(dbo.fn_pre_format_cadena(lower(in_key_word)),chr(32),'') ||'%'
	);
end;
$$
language 'plpgsql';


select * from dbo.libros l 

----------------------------------------------------------------------
--fn_save_author function
----------------------------------------------------------------------
create or replace function dbo.fn_save_author(
in in_name varchar,
in in_lastName varchar
)
returns setof dbo.t_author 
as 
$$
begin 
	insert into dbo.t_author(name,lastname,state)
	values(in_name,in_lastname,default);
	return query
    select ta.* from dbo.t_author ta where ta.idauthor = lastval();
end;
$$
language 'plpgsql';