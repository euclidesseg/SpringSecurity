# Arquitectura a gran escala

>`https://docs.spring.io/spring-security/reference/servlet/architecture.html`

>`https://www.jwt.io/`

>`https://www.base64decode.org/`

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

# AuthenticationManager y AuthenticationConfiguration

## AuthenticationManager

> Es una interfaz clave en el marco de Spring Security que se utiliza para autenticar a los usuarios en una aplicación.
> Es el componente central responsable de coordinar y administrar el proceso de autenticación.
> Solo contiene un unico metodo `authenticate`.

---

## AuthenticationConfiguration

> Nos ayuda a tunear o hacer una creacion mas especifica de `AuthenticationManager`, rara vez se utiliza.

---

# ProviderManager

> Es la implementacion mas comun de la interfaz `AuthenticationManager`, es el componente sentral responsable de la autenticacion y delegar la responsabilidad de autenticacion a los proveedores adecuados.

> Actua como administrador de proveedores de autenticacion delega la responsavilidad porque pueden a ver muchas estrategias de login que puede ser con usuario y contraseña o con Facebook Google entre otros.

---

# AuthenticationProvider

### Estrategias de autenticacion

> Es una interfaz que define el contrato para un proveedor de autenticación.

> Es una pirza clave del mecanismo de autenticacion en Spring Security ya que permite personalizar como se autentica a los usuarios en el sistema.

> Son las estrategias que el `ProviderManager` va a utilizar segun el tipo de autenticacion que haré.

---

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