-- Database: db_libreria_Alten

-- DROP DATABASE IF EXISTS "db_libreria_Alten";

CREATE DATABASE "db_libreria_Alten"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
	
	
-- SCHEMA: dbo

-- DROP SCHEMA IF EXISTS dbo ;

CREATE SCHEMA IF NOT EXISTS dbo
    AUTHORIZATION postgres;



-- Table: dbo.administradores

-- DROP TABLE IF EXISTS dbo.administradores;

CREATE TABLE IF NOT EXISTS dbo.administradores
(
    id_administrador integer NOT NULL DEFAULT nextval('dbo.administradores_id_administrador_seq'::regclass),
    nivel_permiso integer,
    apellidos character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    nombre character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    rol character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT administradores_pkey PRIMARY KEY (id_administrador)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dbo.administradores
    OWNER to postgres;

	
	-- Table: dbo.authorities

-- DROP TABLE IF EXISTS dbo.authorities;

CREATE TABLE IF NOT EXISTS dbo.authorities
(
    id integer NOT NULL DEFAULT nextval('dbo.authorities_id_seq'::regclass),
    authority character varying(255) COLLATE pg_catalog."default",
    usermane character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT authorities_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dbo.authorities
    OWNER to postgres;
	
	
-- Table: dbo.clientes

-- DROP TABLE IF EXISTS dbo.clientes;

CREATE TABLE IF NOT EXISTS dbo.clientes
(
    id_cliente integer NOT NULL DEFAULT nextval('dbo.clientes_id_cliente_seq'::regclass),
    nivel_permiso integer,
    apellidos character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    nombre character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT clientes_pkey PRIMARY KEY (id_cliente)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dbo.clientes
    OWNER to postgres;


-- Table: dbo.librerias

-- DROP TABLE IF EXISTS dbo.librerias;

CREATE TABLE IF NOT EXISTS dbo.librerias
(
    id_libreria integer NOT NULL DEFAULT nextval('dbo.librerias_id_libreria_seq'::regclass),
    nivel_permiso integer,
    ciudad character varying(255) COLLATE pg_catalog."default",
    direccion character varying(255) COLLATE pg_catalog."default",
    nombre_dueno character varying(255) COLLATE pg_catalog."default",
    nombre_libreria character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT librerias_pkey PRIMARY KEY (id_libreria)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dbo.librerias
    OWNER to postgres;


-- Table: dbo.libros

-- DROP TABLE IF EXISTS dbo.libros;

CREATE TABLE IF NOT EXISTS dbo.libros
(
    id_libro integer NOT NULL DEFAULT nextval('dbo.libros_id_libro_seq'::regclass),
    paginas integer,
    precio double precision,
    autor character varying(255) COLLATE pg_catalog."default",
    descripcion character varying(255) COLLATE pg_catalog."default",
    editorial character varying(255) COLLATE pg_catalog."default",
    genero character varying(255) COLLATE pg_catalog."default",
    titulo character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT libros_pkey PRIMARY KEY (id_libro)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dbo.libros
    OWNER to postgres;


-- Table: dbo.users

-- DROP TABLE IF EXISTS dbo.users;

CREATE TABLE IF NOT EXISTS dbo.users
(
    enabled smallint,
    id integer NOT NULL DEFAULT nextval('dbo.users_id_seq'::regclass),
    password character varying(255) COLLATE pg_catalog."default",
    usermane character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS dbo.users
    OWNER to postgres;
	