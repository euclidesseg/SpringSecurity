>`https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html`

 # `Arquitectura a gran escala de la authorization (a que tengo acceso)`
``` txt
La autenticacion trata sobre como las implementaciones de autenticacion almacenan una lista de 
objetos GrantedAuthority estos obetos representan las autoridades o permisos
que se han otorgado al principal/usuario

los objetos GrantedAuthority se insertan en el objeto authentication
```

## `Authorization`
```text
dicho lo anteir y sabiendo que el proceso de autenticacione setear los permisos a los usuarios
el proceso de autorización es utilizar esos permisos
```
## `Invocación`
``` text
spring proporciona los interceptores, que controlan el acceso a objetos asegurados, como metodos o solicitudes web
los cuales estarian anotados con siertos anotaciones, en este punto se toma una decisión antes de invocar al metodo para permitir o denegar
el acceso
```
## `AuthorizationManager`
```text
Son llamados por los componentes de authorization basados en solicitudes metodos y mensajes de sping, son los responsables 
de tomar las decisions de acceso.

Así como AutenticationManager solo tiene el metodo Authenticate, esta interfaz tiene dos metodos:
```
> `check` : recibe toda la información relevante que requeire para tomar una decisión de autorización.

> `verify` : llama al metodo `check` y posteriormente lanza un AccesDniedException si la AuthorizationDecision es negativa

### `Tipos de autorización`

## `Coincidencia de solicitudes http`
```text
si yo envio una peticion a /product la puedo intercetar de acuerdo al path e indicar que solo ciertas usuarios con 
ciertos permisos podran acceder a esta ruta, o caso contrario permitor todo
```
## `Asegurar controladores o servicios etc`
```text
consiste en agregar ciertas anotacioes a los metodos para hacer proceso de authorization
esto es util para:
```
> Aplicar seguridad a la capa de servicio

> Utilizar la logica de autorizacion basada en aseguración de metodos MethodSecurity es mucho más elegante y facil de leer.

> Aplicar seguridad a la capa de servicio

> La implementación de este tipo de seguridad esta basada en `AOP` o programación orientada a aspectos
las anotaciones que podemos utilizar aqui son
>`@PreAuthorize`
>`@PostFilter`
>`@PostAuthorize`
>`@PreAuthorize`

en los diferentes metodos podremos ver las definiciones de estos metodos

### `Autorizacion basada en HtttpServletRequest`
```text
Desde nuestra clase HttpSecurityConfig comenzamos a implementar esta autorización
```

## `AuthorizationFilter`
```text
Al hacer debug se observa que este es el ultimo filtro que se ejecuta en la cadena de filtros
```