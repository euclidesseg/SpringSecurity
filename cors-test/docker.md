1. Instalar Linux para usar Docker desde PowerShell:
   > `wsl --install`

2. Verificar la versión de WSL:
   > `wsl --version`

3. Verificar las distribuciones de Linux instaladas y su versión de WSL:
   > `wsl --list --verbose`

4. Usuario: `eucli`

5. Contraseña: la de Windows.

6. Descargar la imagen de PostgreSQL:
   > `docker pull postgres`

7. Verificar las imágenes descargadas de Docker:
   > `docker images`

   - **Imagen:** es una plantilla que contiene el programa y los componentes necesarios para ejecutarlo incluyendo 
     el codigo las bibliotecas las dependencias y configuraciones.
   - **Contenedor:** es una instancia  creada a partir de una imagen que puede ponerse en funcionamiento (ejecutarse)
     se ejecuta aislado del sistema operativo del host
     
    - `docker pull postgres`: descarga la imagen de PostgreSQL.
    - `docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres`: crea e inicia un contenedor a partir de una imagen.
    - `docker ps`: muestra los contenedores que están ejecutándose.
    - `docker stop <Id de contenedor>`: detiene un contenedor en ejecución.
    - `docker rm <id de contenedor>`: elimina un contenedor detenido.
    - `docker images`: muestra las imágenes descargadas en el sistema.
    - `docker exec -it <id de contenedor> <comando>`: ejecuta un comando dentro de un contenedor en ejecución.
    - `docker logs <id de contenedor>`: muestra los registros de salida de un contenedor.
    - `docker volume create <nombre_del_volumen>`: Crea un volume en docker.
    
8. Crear e iniciar un contenedor de PostgreSQL:
   > `docker run --name mi-postgres -e POSTGRES_PASSWORD=mi_contraseña -p 5433:5432 -d postgres`

   - `--name`: nombre del contenedor.
   - `-e`: configura variables de entorno.
   - `-p 5433:5432`: puerto 5433 de Windows → puerto 5432 del contenedor.
   - `-d`: ejecuta en segundo plano.
   - `postgres` al final indica la imagen a partir de la cual Docker creará el contenedor

9. Acceder a un contenedor PostgreSQL desde la terminal:
   > `docker exec -it mi-postgres psql -U postgres`

   - `docker exec -it`: ejecuta un comando dentro del contenedor.
   - `mi-postgres`: nombre del contenedor.
   - `psql`: cliente de PostgreSQL.
   - `-U postgres`: se conecta con el usuario `postgres`.
   - `\conninfo`: comprobar la información de la conexión actual a PostgreSQL.
   - `\l`: listar las bases de datos.
   - `\q`: salir de `psql`.
   - `CREATE DATABASE mi_base_datos;`: crear una base de datos.
   - `DROP DATABASE mi_base_datos;`: eliminar una base de datos.

9. Volumenes:
```text
   Un volumen es un mecanismo de almacenamiento que permite conservar datos fuera del ciclo de vida de un contenedor. Esto significa que los datos pueden mantenerse aunque el contenedor se detenga, se elimine o se cree uno nuevo.
```
> `Un volumen en docker no esta asociado exclusivamente a un contenedor`

- `docker volume create <nombre_del_volumen>`: Crea un volume en docker.
- `docker run --name mi-postgre-with-volume -d -e POSTGRES_PASSWORD=mi_contraseña -v postgres-data:/var/lib/postgresql postgres`:Crea un contenedor asociado y le asigna un voluman.