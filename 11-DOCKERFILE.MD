# Dockerfile

## ¿Qué es un Dockerfile?

Un **Dockerfile** es un archivo de texto que contiene una serie de instrucciones que Docker utiliza para construir una imagen. En él se definen las características, los componentes y la configuración necesarios para preparar el entorno en el que se ejecutará una aplicación.

Por ejemplo, un Dockerfile puede especificar la imagen base que se utilizará, los archivos que se copiarán, las dependencias que se instalarán, el puerto que utilizará la aplicación y el comando que se ejecutará al iniciar un contenedor.

## ¿Para qué sirve?

El Dockerfile permite automatizar la creación de imágenes de Docker mediante instrucciones definidas en un archivo. Esto facilita la reproducción del mismo entorno en diferentes equipos y servidores, evitando tener que configurar manualmente cada entorno.

## ¿Cómo funciona?

El proceso básico es el siguiente:

1. **Dockerfile:** contiene las instrucciones para construir la imagen.
2. **Imagen de Docker:** se genera a partir de las instrucciones del Dockerfile y contiene los elementos necesarios para ejecutar la aplicación.
3. **Contenedor:** se crea a partir de una imagen y permite ejecutar la aplicación de forma aislada.

Por ejemplo, en una aplicación desarrollada con Java y Spring Boot, un Dockerfile puede definir la imagen base de Java, copiar el archivo `.jar` generado por el proyecto y establecer el comando para ejecutar la aplicación.

## Diferencia entre Dockerfile, imagen y contenedor

- **Dockerfile:** es el archivo que contiene las instrucciones para construir una imagen.
- **Imagen:** es la plantilla que contiene los componentes necesarios para ejecutar una aplicación.
- **Contenedor:** es una instancia ejecutable de una imagen.

## Para conectar IntelliJ con Docker se hace lo siguiente:

1. Instalar el plugin en IntelliJ.
2. Ir a servicios y conectar Docker, mantener la configuración por defecto.
3. En Docker, ir a configuración > Docker Engine y agregar `"min-api-version": "1.24"`.

```json
{
  "builder": {
    "gc": {
      "defaultKeepStorage": "20GB",
      "enabled": true
    }
  },
  "experimental": false,
  "min-api-version": "1.24"
}

**## Instrucciones del Dockerfile**

- `FROM eclipse-temurin:25-jdk-alpine`: Define la imagen base con Java 25 (JDK) y Alpine Linux○
- `COPY target/*.jar app.jar`: Copia el archivo `.jar` generado por Maven desde la carpeta `target` y lo guarda como `app.jar` en la imagen.
- `ENTRYPOINT ["java","-jar","/app.jar"]`: Define el comando que se ejecuta al iniciar el contenedor para arrancar la aplicación Spring Boot`

```

## Comando para crear la imagen
```text
  Antes de esto se debe compilar el proyecto para que gnere el .jar necesario, se compila llendo a a ventana maven
  y escogiendo las opciones clean, compile, package. y ejecutar
```
`docker build -t securityapp:v1 .`

- `docker`: Ejecuta el cliente de Docker.
- `build`: Construye una imagen a partir de un `Dockerfile`.
- `-t`: Permite asignar un nombre (*tag*) a la imagen.
- `securityapp`: Nombre que tendrá la imagen; en este caso, identifica tu aplicación Spring Security.
- `:v1`: Etiqueta (*tag*) que identifica la versión de la imagen; en este caso, la versión 1.
- `.`: Indica la ruta en la que está el archivo `Dockerfile`, tomando como punto de referencia el directorio desde el que se ejecuta el comando.

## Crear un contenedor a partir de la imagen de nuestra aplicación

`docker run -d --name securityapp-container -p 8585:4001 securityapp:v1`

- `-d`: Ejecuta el contenedor en segundo plano.
- `--name securityapp-container`: Asigna un nombre al contenedor.
- `-p 8585:4001`: Mapea el puerto 8585 de la máquina anfitriona al puerto 4001 del contenedor.
- `securityapp:v1`: Indica la imagen que se utilizará para crear el contenedor.

**Nota:** El puerto interno (`4001`) debe coincidir con el puerto en el que escucha la aplicación Spring Boot, configurado mediante `server.port` en `application.properties`. El puerto externo (`8585`) puede ser diferente y es el que se utiliza para acceder a la aplicación desde Postman o el navegador.

## Comunicación del contenedor Java con el contenedor PostgreSQL en una misma red

```text
Creamos un nuevo ambiente de configuración de propiedades para ejecutar
la aplicación Java dentro del contenedor Docker.
```

```properties
spring.datasource.url=jdbc:postgresql://my-postgres:5432/securitydb
```

```text
Utilizamos my-postgres como nombre de host porque el contenedor PostgreSQL
tiene ese nombre y ambos contenedores deben estar conectados a la misma
red Docker para comunicarse mediante ese nombre.
```

### Generar el archivo JAR

```text
Creamos una nueva configuración de ejecución de Maven en IntelliJ IDEA
para generar el archivo JAR del proyecto.
```

```bash
clean package -Dmaven.test.skip=true
```

```text
- clean: elimina los archivos generados en compilaciones anteriores.
- package: compila el proyecto y genera el archivo JAR en la carpeta target.
- -Dmaven.test.skip=true: omite la compilación y ejecución de los tests.
```

**Nota:** podemos omitir temporalmente los tests si necesitan conectarse a un host, como my-postgres, que no está disponible desde el entorno donde ejecutamos Maven.


### Crear la imagen Docker v1.1

```powershell
docker build -t securityapp:v1.1 .
```

```text
- docker build: construye una imagen Docker a partir del Dockerfile.
- -t securityapp:v1.1: asigna el nombre securityapp y la etiqueta v1.1 a la imagen.
- .: indica que el contexto de construcción es el directorio actual.
```

### Crear el contenedor Java

```powershell
docker run -d --name securityapp-container-v1.1 securityapp:v1.1
```

```text
- docker run: crea e inicia un contenedor a partir de una imagen.
- -d: ejecuta el contenedor en segundo plano.
- --name securityapp-container-v1.1: asigna un nombre al contenedor.
- securityapp:v1.1: indica la imagen utilizada para crear el contenedor.
```

### Crear una red Docker

```powershell
docker network create my-red
```

```text
Crea una red Docker llamada my-red que permitirá la comunicación
entre el contenedor Java y el contenedor PostgreSQL.
```

### Conectar los contenedores a la red

```powershell
docker network connect my-red securityapp-container-v1.1
docker network connect my-red my-postgres
```

```text
- docker network connect: conecta un contenedor a una red Docker existente.
- my-red: nombre de la red compartida.
- securityapp-container-v1.1: nombre del contenedor Java.
- my-postgres: nombre del contenedor PostgreSQL.
```

### ¿Cómo se comunican los contenedores?

1. Creamos una red Docker llamada `my-red` para permitir la comunicación entre los contenedores.
2. Conectamos el contenedor Java y el contenedor PostgreSQL a esa misma red.
3. Docker permite que los contenedores conectados se localicen mediante sus nombres de host.
4. Java utiliza el nombre `my-postgres` y el puerto interno `5432` para conectarse a PostgreSQL.
5. No es necesario publicar el puerto `5432` en Windows para que los contenedores se comuniquen entre sí.

### Configuración de las propiedades de conexión

Creamos un nuevo perfil de configuración de propiedades para ejecutar la aplicación Java dentro del contenedor Docker.

```properties
spring.datasource.url=jdbc:postgresql://my-postgres:5432/securitydb
```

```text
Utilizamos my-postgres como nombre de host porque el contenedor PostgreSQL
tiene ese nombre y ambos contenedores deben estar conectados a la misma
red Docker para comunicarse mediante ese nombre.
```

### Comprobar la red Docker

```powershell
docker network inspect my-red
```

```text
Muestra los detalles de la red y los contenedores conectados a ella.
En la sección Containers deben aparecer tanto el contenedor Java
como el contenedor PostgreSQL.
```