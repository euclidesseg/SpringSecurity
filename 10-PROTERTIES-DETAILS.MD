# Configuración de `application.properties`
- `spring.profiles.active` permite indicar qué perfil se utilizará al ejecutar la aplicación.
- `spring.application.name=SpringSecurity`: Define el nombre de la aplicación.
- `server.servlet.context-path=/api/v1`: Define la ruta base de la aplicación.
- `server.port=4001`: Establece el puerto en el que se ejecuta la aplicación.

## Configuración de PostgreSQL

- `spring.datasource.url=jdbc:postgresql://localhost:5432/SecurityDB`: Define la URL de conexión a la base de datos PostgreSQL.
- `spring.datasource.username=postgres`: Establece el usuario de la base de datos.
- `spring.datasource.password=mi_password`: Establece la contraseña de la base de datos.

## Configuración de JPA e Hibernate

- `spring.jpa.show-sql=true`: Muestra las consultas SQL ejecutadas por Hibernate en la consola.
- `spring.sql.init.mode=always`: Ejecuta los scripts SQL de inicialización configurados.
- `spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect`: Especifica el dialecto SQL de PostgreSQL que utilizará Hibernate.
- `spring.jpa.defer-datasource-initialization=true`: Retrasa la inicialización de los scripts SQL de datos hasta después de que Hibernate haya creado el esquema de la base de datos.
- `spring.jpa.hibernate.ddl-auto=create-drop`: Crea las tablas al iniciar el contexto de Hibernate y las elimina al cerrarlo.
- `spring.jpa.hibernate.ddl-auto=update`: Actualiza el esquema de la base de datos sin eliminar las tablas existentes.

> **Nota:** `spring.jpa.hibernate.ddl-auto` debe tener un solo valor efectivo. Utiliza `create-drop` o `update` según el comportamiento que necesites. No debes mantener ambos valores activos simultáneamente.

## Configuración de paginación

- `spring.data.web.pageable.default-page-size=5`: Establece el tamaño predeterminado de las páginas en 5 elementos.
- `spring.data.web.pageable.page-parameter=p`: Define `p` como parámetro para indicar el número de página.
- `spring.data.web.pageable.size-parameter=limit`: Define `limit` como parámetro para indicar el tamaño de página.

## Configuración de seguridad y JWT

- `security.default.role=CUSTOMER`: Define el rol predeterminado de los usuarios.
- `security.jwt.expiration-in-minutes=30`: Establece la duración de los tokens JWT en 30 minutos.
- `security.jwt.secret-key=...`: Define la clave secreta utilizada para firmar los tokens JWT.

**Nota de seguridad:** no es recomendable publicar la clave secreta real en el repositorio. Es preferible configurarla mediante una variable de entorno.

## Configuración de ambientes o perfiles de Spring Boot

Los perfiles de Spring Boot permiten utilizar diferentes configuraciones según el ambiente en el que se ejecute la aplicación. En este proyecto se utilizan los perfiles `local` y `dev` para separar la base de datos H2 de PostgreSQL.

### 1. Archivos de configuración

Los archivos deben ubicarse en `src/main/resources/` y respetar el nombre `application` seguido del perfil correspondiente:

- `application.properties`: contiene la configuración general compartida por los ambientes.
- `application-local.properties`: contiene la configuración específica del ambiente local, que utiliza H2.
- `application-dev.properties`: contiene la configuración específica del ambiente de desarrollo, que utiliza PostgreSQL.

**Importante:** el nombre `application` debe escribirse correctamente para que Spring Boot reconozca los archivos de configuración de cada perfil.

### 2. Activación de los perfiles

La propiedad `spring.profiles.active` permite indicar qué perfil se utilizará al ejecutar la aplicación.


### 3. Ambiente local: H2

El archivo `application-local.properties` contiene la configuración de la base de datos H2 y de los scripts de inicialización.

Ejemplo de configuración:

```properties
spring.sql.init.platform=local
```

### 4. Ambiente dev: PostgreSQL

El archivo `application-dev.properties` contiene la configuración específica de PostgreSQL.

Ejemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/securitydb
spring.datasource.username=postgres
spring.datasource.password=mi_password
spring.sql.init.platform=postgres
```

### 5. Propiedades compartidas y propiedades específicas

Las propiedades definidas en `application.properties` se aplican como configuración general. Los archivos de los perfiles permiten establecer valores específicos para cada ambiente.

## Documentación de referencia

[Configuración de fuentes de datos en Spring Boot](https://howtodoinjava.com/spring-boot2/datasource-configuration/)

[Documentación oficial de Spring Boot: perfiles](https://docs.spring.io/spring-boot/reference/features/profiles.html)

[Documentación oficial de Spring Boot: inicialización de bases de datos SQL](https://docs.spring.io/spring-boot/how-to/data-initialization.html)