# Arquitectura a gran escala

>`https://docs.spring.io/spring-security/reference/servlet/architecture.html`

Spring security se debe incorporar a lo siclos de vida de Jakarta y lo hace a traves del metodo:

## DelegationFilterProxy (integrar siclos de vida de jakarta servlet y spring boot)
> Es un filtro del paquete `Jakarta.servlet`. Se encarga de hacer un puente entre el siclo de vida de Spring Boot y el siclo de vida del contendeor de servlets de Jakarta para que los filtros de seguridad accedan a los recursos de Spring y de Jakarta.
> Una vez se injecta en la cadena de filtros de Jakarta le delega el trabajo al `FilterChainProxy`.

---

## FilterChainProxy (coordinar la cadena de filtros de seguridad)

> Cordina la o las cadenas de filtros para la seguridad en Spring Security basada en peticiones `GET`, `PUT` o `DELETE`.
> Indica que filtros se deben ejecutar y en que orden.
> Puede administrar una cadena de filtros o más cadenas de filtro.

---

## SecurityFilterChain (Contiene los filtros de seguridad representada como una lista)

> Es una cadena de filtros, basicamente es una estructura de datos que contiene filtros de seguridad.
>estos filtros provienen de jakarta.servlet y que fueron vinculados al siclo de vida de spring boot mediante DelegationFilterProxy
> Esta cadena es cordinada por el objeto de arriba.

### ⚠️ Importante

> De estos tres componentes el unico que creamos es el `SecurityFilterChain`.

---

# SecurityContextHolder

> Es una Clase fundamental en Spring Security quye proporciona una acceso centralizado al contexto de seguridad de una aplicacion.
> Permite acceder y gestionar la información de autenticacion y aurorizacion asociada con uns solicitud HTTP en el contexto de Seguridad actual.

---

# SecurityContext

> Es una interfaz en Spring Security que representa el contexto de seguridad actual de una solicitud.
> Contiene toda la informacion relacionada con la autenticacion y autorizacion del usuario que ha realizado la solicitud.

---

# Principal

> Es una representacion del usuario autenticado en el sistema, representa la identidad del usuario.
> Este objeto encapsula los detalles del usuario como su nombre roles y otra informacion relacionada con la autenticación.

---

# GrantedAuthority

### Permiso Concedido

> Es un permiso que es otorgado al principal (Usuario Logueado) lectura, escritura, eliminacion, actualizacion sobre una entidad en especifico.

---

# AuthenticationConfiguration y AuthenticationManager

## AuthenticationConfiguration

> **Configuración de autenticación:** proporciona el `AuthenticationManager` que Spring Security ha configurado para la aplicación.  
> Permite obtener o personalizar el `AuthenticationManager` cuando necesitamos una configuración más específica.

---

## AuthenticationManager

> **Administrador de autenticaciones:** es la interfaz central que administra el proceso de autenticación y expone el método `authenticate()`.  
> Recibe un objeto `Authentication` y delega la autenticación al componente encargado de realizarla.

---

## authenticate

> Es el método de `AuthenticationManager` que recibe un objeto `Authentication` con las credenciales y demás información necesaria para autenticar al usuario.  
> Si la autenticación es correcta, devuelve un `Authentication` autenticado; si falla, lanza una excepción.

---

## ProviderManager

> **Administrador de proveedores:** es la implementación más común de `AuthenticationManager` y también implementa su método `authenticate()`.  
> Coordina uno o varios `AuthenticationProvider` y delega la autenticación al proveedor adecuado según el tipo de autenticación.

---

## AuthenticationProvider

### Estrategias de autenticación

> **Proveedor de autenticación:** es una interfaz que define el contrato que debe cumplir un componente capaz de realizar una estrategia concreta de autenticación.  
> `ProviderManager` utiliza uno o varios `AuthenticationProvider`, por ejemplo para usuario/contraseña, google, facebok, etc.

---

# Diagrama mental

```text
AuthenticationConfiguration
        │
        │ proporciona
        ▼
AuthenticationManager
"Administrador de autenticaciones"
        │
        │ authenticate()
        ▼
ProviderManager
"Administrador de proveedores"
        │
        │ delega en
        ▼
AuthenticationProvider
"Proveedor de autenticación"
        │
        ├── Usuario / contraseña
        ├── LDAP
        ├── OAuth
        └── etc.

# UserDetailsService

> Es una interfaz que se usa para cargar los detalles de un usuario especifico como nombre contraseña roles, desde una fuente de datos.

---

# PasswordEncoder

> Es una interfaz que se usa para codificar y verficar las contraseas, su proposito principal es proteger las contraseñas de los usuarios.

```text
                    PETICIÓN HTTP
                         │
                         ▼
              ┌──────────────────────┐
              │   Jakarta Servlet    │
              │   Filter Chain       │
              └──────────┬───────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │ DelegationFilterProxy│
              │                      │
              │ Puente entre         │
              │ Servlet y Spring     │
              └──────────┬───────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │   FilterChainProxy   │
              │                      │
              │ Coordina una o varias│
              │ SecurityFilterChain  │
              └──────────┬───────────┘
                         │
                selecciona la cadena
                correspondiente
                         │
                         ▼
              ┌──────────────────────┐
              │ SecurityFilterChain  │
              │                      │
              │ [Filter 1]           │
              │ [Filter 2]           │
              │ [Filter 3]           │
              │ [Filter ...]         │
              └──────────┬───────────┘
                         │
                         │
              ┌──────────▼───────────┐
              │ AuthenticationManager│
              │                      │
              │ authenticate(...)    │
              └──────────┬───────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │   ProviderManager    │
              │                      │
              │ Administrador de     │
              │ AuthenticationProvider│
              └──────────┬───────────┘
                         │
              ┌──────────┼───────────┐
              │          │           │
              ▼          ▼           ▼
        ┌──────────┐ ┌──────────┐ ┌──────────┐
        │Provider 1│ │Provider 2│ │Provider 3│
        │          │ │          │ │          │
        │Database  │ │  Google  │ │ Facebook │
        └────┬─────┘ └──────────┘ └──────────┘
             │
             │ si usa usuario/contraseña
             ▼
      ┌───────────────────┐
      │ UserDetailsService│
      │                   │
      │ Carga el usuario  │
      │ desde una fuente  │
      │ de datos          │
      └─────────┬─────────┘
                │
                ▼
      ┌───────────────────┐
      │    Base de datos  │
      │                   │
      │ usuario           │
      │ password          │
      │ roles/permisos    │
      └───────────────────┘
                │
                ▼
      ┌───────────────────┐
      │   PasswordEncoder │
      │                   │
      │ Verifica password │
      └─────────┬─────────┘
                │
                │ autenticación correcta
                ▼
      ┌────────────────────────────┐
      │       Authentication       │
      │                            │
      │ principal                  │
      │ credentials                │
      │ authorities                │
      └─────────────┬──────────────┘
                    │
                    ▼
      ┌────────────────────────────┐
      │      SecurityContext       │
      │                            │
      │ Authentication             │
      │ ├── Principal              │
      │ ├── Credentials            │
      │ └── GrantedAuthority       │
      └─────────────┬──────────────┘
                    │
                    ▼
      ┌────────────────────────────┐
      │    SecurityContextHolder   │
      │                            │
      │ Mantiene/accede al         │
      │ SecurityContext actual     │
      └─────────────┬──────────────┘
                    │
                    ▼
             PETICIÓN AUTORIZADA
                    │
                    ▼
              Controller / API