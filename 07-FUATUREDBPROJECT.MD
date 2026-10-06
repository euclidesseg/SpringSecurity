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
