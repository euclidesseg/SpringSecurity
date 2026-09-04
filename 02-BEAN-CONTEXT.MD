# Spring Beans y ApplicationContext

## ¿Qué es un Bean?

En Java, si quiero crear un objeto normalmente hago algo como esto:

```java
AuthenticationManager authenticationManager =
        new AuthenticationManagerImpl();
```

En este caso, **yo soy el responsable de crear el objeto**, saber qué implementación utilizar, decidir dónde pasarlo, etc.

Por ejemplo:

```java
@Bean
public AuthenticationManager authenticationManager() {
    return authenticationConfiguration.getAuthenticationManager();
}
```

En cambio, al marcar un método con `@Bean` le digo a Spring que **el objeto que devuelve este método será registrado como Bean** y, por tanto, Spring será responsable de administrarlo.

Cuando Spring administra un objeto, se encarga de su **ciclo de vida** y permite que esté disponible para ser **inyectado como dependencia** en otras partes de la aplicación.

---

# ¿Cómo se registra un objeto como Bean?

Existen dos formas principales de registrar Beans en Spring.

## 1. Anotaciones sobre clases

Algunas de las anotaciones más utilizadas son:

```java
@Component
@Service
@Repository
@Controller
@RestController
```

Estas anotaciones indican a Spring que debe detectar esa **clase**, crear una instancia y registrarla como Bean dentro del `ApplicationContext`.

Por ejemplo:

```java
@Service
public class UsuarioService {
}
```

Spring detecta `UsuarioService`, crea una instancia y la registra dentro del contenedor.

---

## 2. Anotación sobre métodos: `@Bean`

`@Bean` se utiliza normalmente dentro de una clase marcada con `@Configuration`.

```java
@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

Aquí Spring ejecuta el método:

```java
passwordEncoder()
```

y registra como Bean **el objeto que devuelve**:

```java
new BCryptPasswordEncoder();
```

Es decir:

```text
@Bean
   ↓
método
   ↓
objeto retornado
   ↓
Bean registrado en Spring
```

---

# Anotaciones más importantes

## `@Component`

Es un Bean genérico.

Se coloca sobre una **clase**:

```java
@Component
public class MiComponente {
}
```

Es ideal para marcar clases que no encajan específicamente en `@Service`, `@Repository` o `@Controller`.

Puede utilizarse, por ejemplo, para:

- Componentes auxiliares.
- Validadores.
- Administradores de acceso.
- Procesadores.
- Otras clases propias de la aplicación que queremos que Spring administre.

---

## `@Service`

Se utiliza normalmente para clases que contienen **lógica de negocio**.

Se coloca sobre una **clase**:

```java
@Service
public class UsuarioService {

    public void registrarUsuario() {
        // Lógica de negocio
    }
}
```

Spring detectará la clase y la registrará como Bean.

---

## `@Repository`

Se utiliza normalmente para clases encargadas del **acceso a datos**.

Se coloca sobre una **clase**:

```java
@Repository
public class UsuarioRepository {
}
```

Por ejemplo, puede representar una clase encargada de consultar o guardar información en una base de datos.

---

## `@Controller` / `@RestController`

Se utilizan normalmente para clases que manejan **peticiones HTTP**.

Ejemplo con `@RestController`:

```java
@RestController
public class UsuarioController {

    @GetMapping("/usuarios")
    public String obtenerUsuarios() {
        return "Lista de usuarios";
    }
}
```

Estas clases también son Beans administrados por Spring.

---

## `@Bean`

Registra explícitamente como Bean el **objeto retornado por un método**.

Normalmente se utiliza dentro de una clase `@Configuration`.

```java
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

En este caso:

```text
Método passwordEncoder()
        ↓
return new BCryptPasswordEncoder()
        ↓
Spring registra ese objeto como Bean
```

---

# ¿Por qué `@Service`, `@Repository` y `@Controller` son anotaciones sobre clases, mientras `@Bean` se utiliza sobre métodos?

La diferencia principal está en **qué estamos indicando a Spring**.

## Anotaciones como `@Service`

Cuando escribimos:

```java
@Service
public class UsuarioService {
}
```

estamos diciendo:

> "Esta clase forma parte de mi aplicación y quiero que Spring cree y administre una instancia de ella como Bean."

Spring puede descubrir esta clase mediante el **component scanning**.

---

## `@Bean`

En cambio:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

estamos diciendo:

> "Ejecuta este método y registra como Bean el objeto que devuelve."

Aquí tenemos un **control más explícito sobre la creación del objeto**.

Esto resulta especialmente útil cuando:

- La clase no es nuestra.
- No podemos modificar su código para agregarle `@Component`.
- Necesitamos configurar cómo se crea el objeto.
- Necesitamos proporcionar argumentos o configuración específica.
- Queremos registrar una implementación concreta de una interfaz.

Por ejemplo:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

`BCryptPasswordEncoder` es una implementación concreta de `PasswordEncoder`.

Estamos indicando explícitamente a Spring qué implementación queremos utilizar.

---

# `@Configuration`

`@Configuration` se utiliza para indicar que una clase contiene **configuración de Spring**, especialmente métodos `@Bean`.

Ejemplo:

```java
@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) {

        return authenticationConfiguration.getAuthenticationManager();
    }
}
```

Podemos imaginar esta clase como un lugar donde declaramos:

> "Spring, estos son algunos de los objetos que quiero que formen parte de mi aplicación y así es como deben crearse."

---

# ¿Qué es el `ApplicationContext`?

El `ApplicationContext` es el **contenedor principal de Spring**.

Podemos imaginarlo como un almacén o contenedor que mantiene y administra los objetos que forman parte de nuestra aplicación.

De forma simplificada:

```text
                    APPLICATION CONTEXT
        ┌─────────────────────────────────────┐
        │                                     │
        │  AuthenticationManager              │
        │  PasswordEncoder                    │
        │  UsuarioService                     │
        │  UsuarioRepository                  │
        │  PaisService                        │
        │  UsuarioController                  │
        │  ...                                │
        │                                     │
        └─────────────────────────────────────┘
```

Cuando una aplicación Spring inicia, Spring crea el `ApplicationContext`.

Dentro de este contenedor registra los Beans que necesita la aplicación.

---

# ¿Qué hace Spring con los Beans?

Una vez que un objeto está registrado como Bean, Spring puede administrarlo y utilizarlo para realizar **inyección de dependencias**.

Por ejemplo:

```java
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
```

Podemos imaginar lo que ocurre de esta manera:

```text
ApplicationContext
       │
       ├── UsuarioRepository
       │
       └── UsuarioService
                │
                │ necesita
                ↓
       UsuarioRepository
```

Spring detecta que `UsuarioService` necesita un `UsuarioRepository`.

Si `UsuarioRepository` también está registrado como Bean, Spring puede proporcionárselo automáticamente mediante **inyección de dependencias**.

---

# Resumen visual

## Registrar una clase como Bean

```text
@Component
@Service
@Repository
@Controller
@RestController
        │
        ↓
      CLASE
        │
        ↓
      BEAN
        │
        ↓
ApplicationContext
```

## Registrar un objeto mediante `@Bean`

```text
@Configuration
        │
        ↓
      @Bean
        │
        ↓
     MÉTODO
        │
        ↓
OBJETO RETORNADO
        │
        ↓
      BEAN
        │
        ↓
ApplicationContext
```

---

# Resumen

| Anotación | Se coloca sobre | Uso habitual |
|---|---|---|
| `@Component` | Clase | Bean genérico |
| `@Service` | Clase | Lógica de negocio |
| `@Repository` | Clase | Acceso a datos |
| `@Controller` | Clase | Controladores MVC |
| `@RestController` | Clase | Controladores REST |
| `@Bean` | Método | Registrar explícitamente un objeto |
| `@Configuration` | Clase | Clase de configuración que puede declarar Beans |

La idea fundamental es:

> **Un Bean es un objeto cuya creación y administración está bajo el control de Spring.**

Y:

> **El `ApplicationContext` es el contenedor donde Spring mantiene y administra esos Beans.**

Por eso, cuando una dependencia está registrada como Bean, podemos pedirla mediante inyección de dependencias en lugar de crearla manualmente con `new`.