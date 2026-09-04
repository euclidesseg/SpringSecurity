# Autenticación y Autorización

> **La Authenticacion = Quien soy**
> **La Authorizacion = A que tengo permisos**

---

# Metodo autoregistro de usuario y generar el token

> Cuando un usuario realiza un registro nuevo usualmente se redirige a pantalla principal despues de completar el registro.

> De esta manera cuando se registra por primera vez se realiza la autenticación y login automaticamente.

> Se puede ver este metodo desde el controlador `CustomerController`.

---

# Metodo para login

> Cuando un usuario envía una peticion se debe validar el tocken que si coinsida.

> Tambien cuando un usuario cierra sesión su tocken es borrado por lo que debemos crear metodos para validar y para loguear en la aplicación.

> Esta implementacion estará a partir del `AuthenticationController`.

---

# Validar un token

> Este metodo nos va a servir para saber cuando un token es autentico y no ha sido manipulado por un usuario malintensionado.

> Este metodo tambien lo agregaremos en nuestro controlador de authenticacion.

---

# Implementar JwtAuthenticationFilter

> El `JwtAuthenticationFilter` se encargara de incorporar a cada peticon el JWT que ya ha sido generado.

---

# Autorización

> La authenbticasion trata sobre como todas las implementacionde autenticacion almacenan una lista de objetos.

### Nota

---

# Invocation Handling

> Setear los authorities es parte de la authenticacíon usarlos y validarlos es parte de la authorización.

> Spring Security proporciona interceptores que controlan el acceso a objetos seguros (Enpoints de los controladores) o mediante metodos es decir, agregar anotaciones a los metodos protegidos.

---

# AuthorizationManager

> Son llamados por los componentres de autrorizacion basados en solicitudes, metodos y mensajes de Spring Security y son responsables de tomar decisiones finales de control de acceso.

> Contiene dos metodos `verify` y `check` siendo este ultimo el que debemos implementar, el cual recibe toda la informacion de la solicitud, junto con el objeto `authentication` y en vase a esto se valida si se tiene aceso a la solicitud o al metodo devuelve un `authorizationDecisin` que es un booleano.

---

# Tipos de autorizaciones

1. Coincidencia de solicitudes HTTP
2. Asegurar metodos de controladores, servicios, etc con anotaciones

---

# Coincidencia de solicitudes HTTP

> Se pueden authorizar de varias formas entre ellas estan:

* `permitAll`
* `denyAll`
* `hasAuthority`
* `hasRole`

```java
.hasRole(Role.ROLE_ADMIN.name());
```

* `hasAnyAuthority`
* `hasAnyRole`
* `access`

---

# Asegurar metodos de controladores con anotaciones

> Las anotcaciones que podemos utilizar son:

* `@PreAuthorize`

```java
@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
```

* `@PostAuthorize`
* `@PreFilter`
* `@PostFilter`
* `@Secured`

---

# Como usar la aseguración de metodos?

### 1. Habilitamos esta caracteristicas en nuestro `SecurityConfig`

```java
@EnableMethodSecurity(prePostEnabled = true)
```

### 2. Vamos a cada cotrolador y agregamos en los metodos las anotaciones

```java
```

---
