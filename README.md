# Sistema de gestión bancaria BancAr

&nbsp;
BancAr es un sistema de gestión bancaria diseñado para administrar las operaciones de una entidad bancaria, permitiendo la gestión de clientes, cuentas, préstamos y transferencias.
Cuenta con una plataforma para que los clientes puedan realizar operaciones y los administradores gestionarlas.

## Tabla de contenidos

- [Sistema de gestión bancaria BancAr](#sistema-de-gestión-bancaria-bancar)
	- [Tabla de contenidos](#tabla-de-contenidos)
	- [Funcionalidades](#funcionalidades)
		- [Usuario Administrador](#usuario-administrador)
		- [Usuario Cliente](#usuario-cliente)
		- [Tipos de movimientos disponibles](#tipos-de-movimientos-disponibles)
	- [UX/UI](#uxui)
	- [Configuración y compilación](#configuración-y-compilación)
		- [Base de datos MySQL](#base-de-datos-mysql)
		- [Java Development Kit](#java-development-kit)
		- [Apache Tomcat](#apache-tomcat)
		- [MySQL Connector y .classpath](#mysql-connector-y-classpath)
		- [Propiedades de conexión](#propiedades-de-conexión)
	- [Licencia y contribuciones](#licencia-y-contribuciones)

## Funcionalidades

### Usuario Administrador

&nbsp;
Los usuarios de tipo Administrador tienen acceso a las siguientes funciones para gestionar el banco:

- **ABML de clientes**: Permite al administrador agregar, buscar, modificar y eliminar clientes. Al crear un cliente, se asignan automáticamente un usuario y contraseña para el acceso a la web del banco. Al eliminarlo, su usuario también se da de baja.
  
- **Gestión de usuarios**: Permite crear y visualizar usuarios asociados a los clientes. El usuario y contraseña son generados automáticamente al crear un cliente. Al dar de baja un cliente, se elimina su usuario para que no pueda acceder a la aplicación.

- **Gestión de cuentas**: Incluye la creación, consulta, modificación y eliminación de cuentas. Una cuenta se asigna a un cliente y se crea con un monto inicial de $10,000. Un cliente puede tener hasta tres cuentas activas; al eliminar una cuenta, el cliente podrá crear otra nueva si tiene menos de tres.

- **Autorización de préstamos**: Los clientes pueden solicitar préstamos, y el administrador tiene la capacidad de aprobar o rechazar estas solicitudes. Al aprobar un préstamo, el sistema generará automáticamente las cuotas para que el cliente pueda realizar los pagos desde la aplicación.

- **Generación de informes**: Permite al administrador generar reportes estadísticos sobre diversas métricas del sistema. Los informes son procesados en base a los datos del sistema y se pueden filtrar por parámetros de fecha.

### Usuario Cliente

&nbsp;
Los clientes tienen acceso a las siguientes funciones dentro del sistema:

- **Historial de movimientos**: Los clientes pueden seleccionar una de sus cuentas y ver el historial completo de los movimientos realizados en esa cuenta.

- **Transferencias**: Permite transferencias entre cuentas del mismo cliente o a cuentas de otros clientes a través del CBU. Las transferencias están condicionadas a la disponibilidad de saldo.

- **Solicitud de préstamos**: Los clientes pueden solicitar préstamos, eligiendo el monto y el número de cuotas para su pago. La solicitud se envía al administrador del banco para su aprobación.

- **Pago de cuotas**: Los clientes pueden ver las cuotas pendientes de sus préstamos, seleccionar una cuota y pagarla, eligiendo la cuenta de la que se deducirá el pago. Cada pago de cuota se registra con la fecha de pago.

- **Visualización de información personal**: Los clientes pueden ver sus datos personales, aunque no pueden modificarlos.

### Tipos de movimientos disponibles

- **Alta de cuenta**: El alta de una cuenta genera un movimiento positivo en la cuenta origen.
- **Alta de préstamo**: La aprobación de un préstamo genera un movimiento positivo en la cuenta origen.
- **Pago de préstamo**: Cada pago de cuota de préstamo genera un movimiento negativo en la cuenta origen.
- **Transferencia**: Cada transferencia implica un movimiento negativo en la cuenta de origen y un movimiento positivo en la cuenta de destino.

## UX/UI

&nbsp;
La interfaz de usuario fue diseñana para permitir un acceso intuitivo a las distintas funciones del sistema.
Los prototipos de diseño se pueden [visualizar en Figma](https://www.figma.com/design/0T0ur1TkGJ3DIxlOodS5Hy/TP-Integrador-Grupo-4-Lab-4).

## Configuración y compilación

> [!WARNING]
> &nbsp;
> Es necesario que sigas los pasos indicados en [MySQL Connector y .classpath](#jdbc) y en [Propiedades de conexión](#config) para poder compilar el proyecto.

### Base de datos MySQL

&nbsp;
El proyecto utiliza una base de datos MySQL. Para generarla en un servidor local, ejecutar [**run_scripts.bat**](./sql/run_scripts.bat) en Windows o [**run_scripts.sh**](./sql/run_scripts.sh) en MacOs.

### Java Development Kit

&nbsp;
El Java Development Kit (JDK) incluye el compilador de Java, el Java Runtime Environment (JRE) y otras herramientas necesarias.
Se recomienda instalar la última versión disponible (o la versión más reciente soportada) desde el [sitio oficial de Oracle](https://www.oracle.com/cis/java/technologies/downloads/).

### Apache Tomcat

&nbsp;
Se requiere Apache Tomcat para ejecutar aplicaciones web basadas en Java.
Se recomienda instalar la versión 8.5, que puede ser descargada desde el [repositorio de la universidad Unicamp](https://ftp.unicamp.br/pub/apache/tomcat/tomcat-8/v8.5.73/bin/).

&nbsp;
La versión 9 del [sitio oficial de Apache Tomcat](https://tomcat.apache.org/download-90.cgi) no fue probada pero podría ser una alternativa viable.
Versiones posteriores pueden tener problemas de compatibilidad con versiones antiguas del JDK, debido a cambios en los nombres de algunas bibliotecas que necesitan ser importadas en el código fuente.

### <a name="jdbc"></a>MySQL Connector y .classpath

&nbsp;
Es necesario descargar MySQL Connector (JDBC) desde el [sitio oficial de MySQL](https://dev.mysql.com/downloads/).

> [!IMPORTANT]
> &nbsp;
> El archivo `.classpath` que indica la ruta de MySQL Connector no está siendo versionado para que no haya que configurar la ubicación del mismo cada vez que otro contribuidor la cambia.
> Es necesario crear en el [**directorio raíz**](./) un archivo llamado `.classpath` (sin extensión) que contenga el siguiente código reemplazando `/tu/ruta/al/mysqlconnector.jar` por la ubicación correspondiente.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="src" path="src"/>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER">
		<attributes>
			<attribute name="module" value="true"/>
			<attribute name="owner.project.facets" value="java"/>
		</attributes>
	</classpathentry>
	<classpathentry kind="con" path="org.eclipse.jst.server.core.container/org.eclipse.jst.server.tomcat.runtimeTarget/Apache Tomcat v8.5">
		<attributes>
			<attribute name="owner.project.facets" value="jst.web"/>
		</attributes>
	</classpathentry>
	<classpathentry kind="con" path="org.eclipse.jst.j2ee.internal.web.container"/>
	<classpathentry kind="con" path="org.eclipse.jst.j2ee.internal.module.container"/>
	<classpathentry kind="lib" path="/tu/ruta/al/mysqlconnector.jar">
		<attributes>
			<attribute name="org.eclipse.jst.component.dependency" value="/WEB-INF/lib"/>
		</attributes>
	</classpathentry>
	<classpathentry kind="output" path="build/classes"/>
</classpath>
```

### <a name="config"></a>Propiedades de conexión

&nbsp;
Se deben configurar las credenciales de acceso a la base de datos.
Para ello, hay que crear un archivo llamado `config.properties` en el [**directorio src**](./src) con el siguiente código, reemplazando `tupassword` por la contraseña de la base de datos.

```properties
# Dirección del host de la base de datos
db.host=jdbc:mysql://localhost:3306/

# Usuario de la base de datos
db.user=root

# Contraseña del usuario
db.pass=tupassword

# Nombre de la base de datos
db.name=bancar_db
```

## Licencia y contribuciones

&nbsp;
Este es un proyecto de código abierto desarrollado bajo la [Licencia Pública General GNU](./LICENSE).

&nbsp;
Nos encantaría recibir tus aportes de cualquier tipo, ya sea en forma de código, documentación, reportes de errores, o nuevas ideas para mejorar el proyecto.
Asegurate de revisar nuestra [Guía de contribución](./doc/contribute.md) antes de empezar a contribuir.