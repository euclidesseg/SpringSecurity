# Dependencias necesarias para trabajar con JWT en Java

## 🔐 DEPENDENCIAS NECESARIAS PRA TRABAJAR CON JWT EN JAVA

### 1. Spring Boot Starter Security

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

> Este starter de Spring Boot proporciona configuraciones y dependencias para agregar seguridad a una aplicación Spring Boot.
> Incluye funcionalidades como autenticación, autorización, cifrado, entre otros.

---

### 2. JJWT API

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.5</version>
</dependency>
```

> Esta es la api para usar jwt en una aplicacion de spring security.
> Esta dependencia agrega la biblioteca Java JWT (JSON Web Token) a la aplicación. Permite trabajar con tokens JWT para autenticación.

---

### 3. JJWT Implementation

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
```

> Es la implementación de la api de JWT.

---

### 4. JJWT Jackson

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.5</version>
    <scope>runtime</scope>
</dependency>
```

> Para serializar y deserializar json a objetos java.

---

### 5. Spring Security Test

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

> Esta dependencia proporciona utilidades de prueba para aplicaciones que utilizan Spring Security.
> Ayuda a escribir pruebas de integración y unidad para la seguridad de Spring.
