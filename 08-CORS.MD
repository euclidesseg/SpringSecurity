# CORS (Cross-Origin Resource Sharing)

## ¿Qué es CORS?

CORS es un mecanismo de seguridad aplicado por los navegadores que controla qué orígenes pueden acceder a las respuestas del servidor mediante solicitudes entre distintos orígenes.

Un origen está compuesto por el protocolo, el dominio o IP y el puerto. Si cambia cualquiera de estos elementos, se considera un origen diferente.

**Ejemplo:** un frontend en `http://localhost:5500` y una API en `http://localhost:4001` tienen orígenes diferentes.

## ¿Cómo funciona y por qué se configura en el backend?

El navegador comprueba las cabeceras CORS de la respuesta para determinar si permite que JavaScript acceda a ella. Algunas solicitudes requieren previamente una petición `OPTIONS`, conocida como *preflight*.

El backend indica qué orígenes, métodos y cabeceras permite. CORS no elimina la diferencia entre los orígenes; permite la comunicación cuando se cumplen las condiciones establecidas.

CORS no sustituye la autenticación ni la autorización.

## ¿Cómo lo hacemos?

1. Añadimos `@CrossOrigin` al método o controlador que queremos habilitar para solicitudes CORS.
2. Habilitamos CORS en la configuración de Spring Security:

   ```java
   .cors(Customizer.withDefaults())
   ```

3. Configuramos los orígenes y las opciones permitidas mediante `@CrossOrigin`.

### Argumentos de `@CrossOrigin`

1. `origins`: especifica los orígenes permitidos.

   ```java
   @CrossOrigin(origins = "http://localhost:5500")
   ```

2. `originPatterns`: permite especificar patrones de orígenes.

   ```java
   @CrossOrigin(originPatterns = "https://*.google.com")
   ```

3. `methods`: indica los métodos HTTP permitidos.

   ```java
   @CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST})
   ```

4. `allowedHeaders`: indica las cabeceras que el cliente puede enviar.

   ```java
   @CrossOrigin(allowedHeaders = {"Authorization", "Content-Type"})
   ```

5. `exposedHeaders`: indica qué cabeceras de la respuesta pueden leer los scripts del navegador.

   ```java
   @CrossOrigin(exposedHeaders = "Authorization")
   ```

6. `allowCredentials`: permite solicitudes que incluyen credenciales del navegador, como cookies.

   ```java
   @CrossOrigin(allowCredentials = "true")
   ```

7. `maxAge`: indica cuántos segundos puede almacenarse en caché el resultado de la petición *preflight*.

   ```java
   @CrossOrigin(maxAge = 3600)
   ```

## Consideraciones importantes

- `@CrossOrigin` sin argumentos permite, por defecto, cualquier origen, cualquier cabecera y los métodos HTTP asociados al controlador.
- Las credenciales no se permiten por defecto.
- No se debe combinar `allowCredentials = "true"` con `origins = "*"`. Si se necesitan credenciales, se debe especificar el origen permitido o utilizar un patrón adecuado.
- `@CrossOrigin` puede colocarse en un método o en una clase. Si se coloca en la clase, se aplica a sus métodos del controlador.


### Crear una configuracion de cors a nivel global
``` text
En esta ocación vamos a configurar un cors que afecte a todos los controladores, tanto de categorias como de productos y autenticación

```
``` text
Para realizar esta configuración debemos agregar un beean en HttpSecurityConfig  

```
`UrlBasedCorsConfigurationSource`
```text
este bena establece las configuraciones de orignes y de cabceras que se pueden enviar en la petición
```
