En nuestro proyecto hemos usados:
Java 8
MySQL 5.6.39
MySQL Connector/J is the official JDBC driver for MySQL 5.1.46

El driver que hemos usado se encuentra en la carpeta lib de proyecto.

Con respecto a la entega anterior hemos eliminado la libreria de pooling de conexiones y manteniendo 
una conexion abierta manualmente. La velocidad no ha mejorado demasiado, el problema reside en el propio diseño que 
realiza muchas consultas pequeñas. Con la base de datos local la interfaz se nota fluida y no hay tiempos de espera.

Para configurar los parametros de conexion a la base de datos
hay que editar las constantes de la clase DBManager.

La configuracion actual de la base de datos el localhost:3306 con usuario root y sin contraseña.
El nombre de la base de datos es mivacapiso


El programa buscara los archivos "create_table.sql" y "datos_usuarios.txt" en la raiz del proyecto del 
eclipse, aunque si da algun problema se pueden editar las fuentes para que apunten a la ruta 
absoluta de esos archivos

En este proyecto además de componentes de swing hemos usado algunos de la libreria javafx
ya que existe una clase JFX panel que permite incorporarlos. En general escalan mejor con la resolucion
de la pantalla, DPI,etc. Visualmente son mas agradables(aunque los de swing tambien se pueden configurar) y tienen
funcionalidad que los componentes de swing no tiene(o que directamente no existen: DatePicker).

Si embargo, en algunas versiones de java, combinar elementos de las dos librerias graficas causa algunos problemas de 
threading(ej: https://bugs.openjdk.java.net/browse/JDK-8195739  ).

Hemos probado varias versiones de java. En las versiones "1.8.0_161" , y "1.8.0_162" la interfaz
tiene problemas. 
La version estable con la que conseguimos trabjajar es la "1.8.0_172". También funciona en java 9 
aunque alli encontramos otros bugs de swing donde algunos componentes estan desplazados algunos pixeles.

El sistema operativo sobre cual hemos probado es Windows 10.


Hemos añadido varios usuarios para hacer que la prueba sea mas rapida:
Para los tres, la contraseña es 1234 y los nombres de usuario son:
o          - tiene rol de ofertante
d          - rol de demandante
od         - rol de ofertante demandante

Mihai Blidaru
Sergio Dominguez
