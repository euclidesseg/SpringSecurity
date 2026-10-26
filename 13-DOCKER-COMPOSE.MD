# Docker compose

## ¿Qué es Docker compose?

Un **docker compose** es una herramienta de docker que permite definir y gestionar multiples contenedores
como un conjunto utilizando normalmente un archivo llamado componse.yaml es muy utili a la hora por ejemplo de trabajar con contenedores.
docker-compose puede crear un contenedor a partir de una imagen que se tenga descargada en docker y si no va a ir a descargarla antes 
de crear el contenedor

1. ## Orquestacion simplificada
```text
En lugar de ejecutar y vincular manualmente varios contenendores individualmente
```
2. ## Configuracion en un archivo 
```text
Docker Compose permite definir la configuración de todos los servicios y su relación en un solo archivo docker-compose.yml.
```
3. ## Ambientes consistentes
```text
Asegura que los contenedores se ejecuten con la misma configuración en todos los entornos: desarrollo, prueba, producción, etc.

```
4. ## Facil escalabilidad
```text
Se pueden escalar servicios individuales en función de la demanda sin tener que preocuparte por la complejidad de la orquestación manual
```
5. ## Aislamiento de servicios
```text
Aunque los servicios se ejecutan en contenedores separados, Docker Compose permite que estos servicios se comuniquen entre sí de manera sencilla a través de redes internas.
```
6. ## Gestion de volumenes y redes
```text
Permite definir volúmenes y redes personalizadas en el mismo archivo.
```

7. ## Adecuacion del archivo docker-compose.yml
```yml

# Define los servicios de la aplicación
services:
  # Define el servicio de PostgreSQL
  db:
    # Indica la imagen de PostgreSQL que se utilizará
    image: postgres
    # Reinicia el contenedor si se detiene
    restart: always
    # Establece el tamaño de la memoria compartida
    shm_size: 128mb
    # Configura el almacenamiento persistente de la base de datos
    volumes:
      # Asocia el volumen con el directorio de datos
      - postgres-data:/var/lib/postgresql
    # Define las variables de entorno de PostgreSQL
    environment:
      # Establece la contraseña del usuario administrador
      POSTGRES_PASSWORD: my_password
      # Define la base de datos inicial
      POSTGRES_DB: securitydb
    # Asigna un nombre al contenedor de PostgreSQL
    container_name: my-postgres-docker-compose

    # Comprueba si PostgreSQL está listo para aceptar conexiones
    healthcheck:
      # Ejecuta la comprobación de disponibilidad de PostgreSQL
      test: ["CMD-SHELL", "pg_isready -U postgres -d securitydb"]
      # Ejecuta la comprobación cada 10 segundos
      interval: 10s
      # Establece el tiempo máximo de cada comprobación
      timeout: 5s
      # Define los intentos fallidos permitidos
      retries: 5

  # Define el servicio de Spring Security
  securityapp:
    # Construye la imagen usando el Dockerfile local
    build: .
    # Asigna un nombre y una etiqueta a la imagen
    image: securityapp:v1.2
    # Publica el puerto 4001 como puerto 8585 del host
    ports:
      - "8585:4001"
    # Asigna un nombre al contenedor de la aplicación
    container_name: securityapp-docker-compose
    # Espera a que PostgreSQL esté saludable antes de iniciar
    depends_on:
      # Define la condición de inicio del servicio de base de datos
      db:
        condition: service_healthy

# Define los volúmenes administrados por Docker Compose
volumes:
  # Declara el volumen persistente de PostgreSQL
  postgres-data:

```
```text
Conexión a PostgreSQL:
Docker Compose proporciona DNS interno para que los contenedores se comuniquen mediante el nombre del servicio.
spring.datasource.url=jdbc:postgresql://db:5432/securitydb

Fallo común: JAR desactualizado. Ejecuta `mvn clean package` y luego `docker compose up -d --build` para aplicar los últimos cambios.

```
`docker compose up --build -d`: Construye las imágenes necesarias y ejecuta los servicios definidos en `compose.yaml`.

`--build`: Fuerza la construcción de las imágenes antes de iniciar los contenedores, como la de `securityapp`.

`-d`: Ejecuta los contenedores en segundo plano.

## 8. Configurar un cliente

1. **Configurar CORS:** en `HttpSecurityConfig` de la aplicación principal Spring Security, crear un método para habilitar CORS y permitir el origen del servicio `client` en el perfil `Docker`.

2. **Modificar Docker Compose:** agregar un nuevo servicio llamado `client` al archivo `compose.yaml` de la aplicación principal, utilizando la imagen de la aplicación cliente.

3. **Configurar el DNS:** establecer en el cliente la URL de la aplicación principal mediante el nombre DNS de su servicio en Docker Compose.

4. **Crear la imagen del cliente:** construir la imagen Docker de la aplicación cliente para poder ejecutar su contenedor.

5. **Compilar nuevamente la app principal :** Compilar con nuestra configuracion without test la aplicacion principal.

6. **Levantar los servicios:** ejecutar Docker Compose para iniciar los contenedores y permitir la comunicación entre el cliente y la aplicación principal.