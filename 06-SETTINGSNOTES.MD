# Paginación con `Pageable`

Ejemplo:

```text
http://localhost:4001/api/v1/products?page=0&size=5
```

Por defecto, `Pageable` utiliza:

* `page` → número de página (comienza en `0`).
* `size` → cantidad de elementos por página.

Estos valores pueden personalizarse en `application.properties`:

```properties
# Cantidad por defecto
spring.data.web.pageable.default-page-size=5

# Nombre del parámetro para la página
spring.data.web.pageable.page-parameter=p

# Nombre del parámetro para el tamaño
spring.data.web.pageable.size-parameter=limit
```

Con esta configuración, la URL sería:

```text
http://localhost:4001/api/v1/products?p=0&limit=5
```

> `default-page-size` se utiliza cuando no se especifica el tamaño (`size`/`limit`) en la petición.
### `@RestController` vs `@RestControllerAdvice`

- `@RestController` → Maneja peticiones HTTP y devuelve datos, normalmente en JSON.
- `@RestControllerAdvice` → Maneja excepciones y respuestas de error de forma global.
-  En resumen: **`@RestController` maneja peticiones; `@RestControllerAdvice` maneja errores en las peticiones.**
### `ObjectNotFoundException` vs `GlobalExceptionHandler`

`ObjectNotFoundException` → Representa y **lanza el error** cuando no se encuentra un objeto.

`GlobalExceptionHandler` → **Captura la excepción** y la transforma en una respuesta HTTP para el cliente.
