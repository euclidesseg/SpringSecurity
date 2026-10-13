### Authorizacion y autenticacion customizados
```
Esta rama consiste en hacer el proceso de authenticacion y authorización directamente desde una base de datos.
en esta db tendremos las tablas user, role, permission, module, y operacion
donde la relacion seria que un usuario puede tener un role, ese rol puede realizar muhas operaciones y operaciones puede ser realizada pur muchos roles
por tanto aqui se establece una relacion llamada permisso para relacionar una operacion y un rol
y por ultimo la tabla modulo.

┌──────────┐      ┌──────────┐      ┌────────────┐      ┌────────────┐      ┌──────────┐
│   USER   │ N:1  │   ROLE   │ 1:N  │ PERMISSION │ N:1  │ OPERATION  │ N:1  │  MODULE  │
└──────────┘ ───► └──────────┘ ───► └────────────┘ ───► └────────────┘ ───► └──────────┘

```

## Que hacemos?
`1:`
```text 
Modificamos los nombres de los enums para no tener problemas
```
`2:`
```text 
    Creamos en entidades el package security donde vamos a agregar nuestra entidad usuario
    y luego vamos a definir primero la tabla Role a a traves de una clase
    de esta manera modelamos las tablas Role RolePermission, User, Operation y Modulo a traves de entidades
```
`2:`
```text 
   en nuestro HttpSecurityConfig ponermos .authorizeHttpRequests(authReqConfig -> authReqConfig.anyRequest().access(myAuthorizationManager))
   esde debido que vamos a crear un authorization manager que vamos a crear personalizado desde base de datos.
   
```
