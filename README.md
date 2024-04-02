# Alten-Practicas
## Proyecto API REST Practicas Alten



Realizada por:

###  Alberto Arroyo Santofimia


1- Preparar una idea de proyecto del tipo API REST en el que se consuman los servicios proporcionados por la API.



La idea del proyecto es gestionar una cadena de librerias.
Se va a utilizar un servicio REST para gestionar los libros.

A través de la web podran acceder 3 tipos de usuarios:


- Cliente: Podrá comprar y consultar los libros que están disponibles.
- Libreria: Podrá dar de alta, baja, modificar libros disponibles.
- Administrador: Podrá dar de baja usuarios cliente y libreria.



El modelo de datos consistira en que:

- El cliente puede comprar de 1 a muchos libros y el libro puede ser comprado por más de un cliente
- La libreria puede tener muchos libros y los libros a su vez pueden estar en más de una libreria



Inicialmente tendremos el siguiente diagrama entidad relacion:

![Imagen](imgReadme/Relaciones.png)

Diagrama de clases

![Imagen](imgReadme/DiagramaClases.png)

Diagrama de casos de uso

![Imagen](imgReadme/DiagramaUso.png)


