En nuestro proyecto hemos usados:
Java 8
MySQL 5.6.39
MySQL Connector/J is the official JDBC driver for MySQL 5.1.46
c3p0 - JDBC3 Connection and Statement Pooling version 0.9.5.2

El driver que hemos usado se encuentra en la carpeta lib de proyecto
al igual que otros dos jar de la libreria c3p0.

Para configurar los parametros de conexion a la base de datos
hay que editar las constantes de la clase DBManager.

Si prefiere evitar la instalacion del motor MySQL puede usar el servidor
remoto que está configurado actualmente(que usamos para tener los dos acceso a la misma base de datos).
Será un poco más lento para hacer pruebas pero no se nota demasiado.


El programa buscara los archivos "create_table.sql" y "datos_usuarios.txt" en la raiz del proyecto del 
eclipse, aunque si da algun problema se pueden editar las fuentes para que apunten a la ruta 
absoluta de esos archivos


Mihai Blidaru
Sergio Dominguez
