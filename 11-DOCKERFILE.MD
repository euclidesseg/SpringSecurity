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

- `FROM eclipse-temurin:25-jdk-alpine`: Define la imagen base con Java 25 (JDK) y Alpine Linux.
- `COPY target/*.jar app.jar`: Copia el archivo `.jar` generado por Maven desde la carpeta `target` y lo guarda como `app.jar` en la imagen.
- `ENTRYPOINT ["java","-jar","/app.jar"]`: Define el comando que se ejecuta al iniciar el contenedor para arrancar la aplicación Spring Boot.