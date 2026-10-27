>`https://docs.spring.io/spring-security/reference/servlet/oauth2/authorization-server/index.html?utm_source=chatgpt.com`

# OAuth 2.0

OAuth 2.0 (*Open Authorization*) es un protocolo de autorización que permite que aplicaciones y servicios accedan a recursos protegidos en nombre del propietario de esos recursos.

Además, existe una capa de identidad llamada **OpenID Connect (OIDC)**, construida sobre OAuth 2.0, que permite autenticar a los usuarios.

## Componentes de OAuth 2.0

### 1. Cliente (*Client*)
Es la aplicación o servicio que solicita acceso a los recursos protegidos en nombre del usuario.

### 2. Servidor de autorización (*Authorization Server*)
Es el encargado de autenticar al usuario, gestionar su consentimiento cuando corresponda y emitir los tokens de acceso (*Access Tokens*).
También verifica la identidad del cliente y determina qué permisos puede conceder.

### 3. Servidor de recursos (*Resource Server*)
Es el servidor que aloja los recursos protegidos que el cliente desea consultar o utilizar.
Verifica los tokens de acceso y decide si concede o deniega el acceso a los recursos solicitados.

### 4. Propietario del recurso o usuario (*Resource Owner*)
Es el usuario que posee los recursos protegidos y que puede autorizar al cliente a acceder a ellos en su nombre.
Este rol de usuario es fundamental ya que representa a la persona que otorga permiso para el acceso a sus datos o recursos protegidos.

## Topos de tockens
OAuth 2 utiliza varios tipos de tokens para gestionar el acceso a los recursos protegidos.

### Token de acceso (Access Token)
El token de acceso es un código que el servidor de autorizacion emite al cliente después de que se haya autenticado correctamente y se haya obtenido el consentimiento del usuario. Este token es utilizado por el cliente para acceder a los recursos protegidos en nombre del usuario. Los tokens de acceso son temporales y tienen un tiempo de vida limitado.

### Token de actualización (Refresh Token)
El refresh token es un token especial que se utiliza para obtener un nuevo token de acceso después de que el anterior haya expirado. Permite que el cliente mantenga el acceso a los recursos protegidos sin requerir que el usuario vuelva a autenticarse

### ID Token (Token de identificacion)
Es un token que proporciona información sobre la identidad del usuario. Contiene datos como el nombre del usuario correo
u otra inforamacin del perfil

### Authoization code (Codigo de autorización)
Aunque no es un token, el Authorization code es una parte fundamental en el flujo de autorizacion de OAuth 2. Es un código de un solo uso que el cliente obtiene del servidor de autorización y luego intercambia por un token de acceso y un refresh token. Se utiliza para proteger la transmisión del token de acceso durante el proceso de autorización.

>`Estos token son esenciales para la seguridad y la gestión de accesos en Oauth2, y cada uno cumple un proposito especifico en el proceso de autorizacion y autenticacion`

## Flujos en Oauth2
Oauth 2 define varios flujos que especifican como un cliente puede obtener un token

### Authorization Code Flow
Es utilizado por aplicaciones web y permite que un cliente y permite que un cliente obtenga un token de acceso despues de que el usuario 
haya sido redirigido a una página de inicio de sesión y haya otorgado su consentimiento

### Authorization Code Flow with PKCE (el más utilizado)
Es un flujo diseñado para proporcionar una mayor seguridad cuando una aplicación cliente web se autentica y ofrece protección adicional contra ataques como la suplantación de código y el robo de tokens.

### Implicit Flow
Este flujo es utilizado por aplicaciones de Venezuela Pay sin Callspace Authentication y permite que el cliente tenga un token de acceso directamente en el navegador de usuario sin necesidad de un token de autorización. (Es muy poco recomendado).

### Client Credentials Flow:
Este flujo es utilizado por aplicaciones confidenciales, como servicios web o aplicaciones de servidor a servidor, para obtener un token de acceso mediante credenciales directas del cliente.

## Resource Owner Password Credentials Flow:
Este flujo permite que un cliente obtenga un token de acceso utilizando las credenciales del usuario (nombre de usuario y contraseña) en lugar de un token de autorización.

## Device Authorization Flow:
Este flujo es útil en dispositivos con recursos limitados y permite que un dispositivo obtenga un token de acceso a través de un proceso de autenticación en otro dispositivo.

Estos son algunos de los conceptos y flujos clave en OAuth 2.0. Puedes profundizar en cada uno de ellos según las necesidades de tu proyecto y tu audiencia. También puedes considerar ejemplos prácticos y demostraciones para ilustrar estos conceptos.