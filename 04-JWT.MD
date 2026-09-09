
>`https://www.jwt.io/`

>`https://www.base64decode.org/`

# JWT

> **JWT (JSON Web Token)** es un estándar abierto (RFC 7519) que define un formato compacto y autenticado
> para la transmisión segura de información entre partes como un objeto JSON.

---

## Esta definido por 3 partes

### 1. Header

> Definido por nosotros podemos elegir el tipo de algoritmo usado para codificar en base 64

```json
{
   "alg": "HS256",  
   "typ": "JWT"
}
```

---

### 2. Pyload

> Datos o Claims enviados en la peticion

```json
{
    "sub": "1234567890", // indica propietario de tocken puede ser el usuario eje lmarquez
    "name": "John Doe",
    "iat": 1516239022    // indica el milisegundo en el que se entrego el tocken
}
```

---

### 3. Verify Signature

**Firma de verificación**

> Contiene el header y el pyload convertidos a base 64 y concatenados

> tambien contiene la clave secreta que es definida por el desarrollador

```text
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload), my-clave-en-256-bits
) secret base64 encoded
```

---

## Como se Genera la firma?

> Primero se convierte el header y pyload en base 64 y se concatenean separados por `.`

> Luego, la clave my-clave-en-256-bits es convertida en una matriz de bits con UTF-8

> Luego, se inicializa el objeto que generará la firma, este objeto se crea con el algoritmo y se instancia con la clave secreta

### Ejemplo

```java
Mac mac = Mac.getInstance("HmacSHA256");
mac.init(secretKey);
```

> **Nota:** HmacSHA256 se refiere al tipo de algoritmo

> Luego, se genera la firma usando el objeto que se creo anteriormente con nuestra clave secreta

### Ejemplo

```java
base64String = objetoConfirma(header-y-pyload.getBytes("UTF-8"))
```

> Po ultimo esta firma generada se codifica en base 64

---

## Nota

> En el estándar JWT, cuando se especifica `"HS256"` en el campo `"alg"` (algoritmo) del encabezado del token,
> se está indicando que se debe utilizar HMAC con SHA-256 (HmacSHA256) para generar la firma.
