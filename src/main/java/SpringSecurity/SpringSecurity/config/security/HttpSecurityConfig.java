package SpringSecurity.SpringSecurity.config.security;

import SpringSecurity.SpringSecurity.config.security.filter.JwtAuthenticationFilter;
import SpringSecurity.SpringSecurity.interfacesimpl.CustomizerImpl;
import SpringSecurity.SpringSecurity.persistance.util.Role;
import SpringSecurity.SpringSecurity.persistance.util.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
// EnableWebSecurity activa y congigura componentes como el AuthenticationConfiguration
// que tenemos en SecurityBeansInjector
// también activa el autenticatorentripoint, Habilita la seguridad basada en coincidencias de url
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita aseguración por metodos, prePostEnabled habilita las anotaciones preAuthorize y postAuthorize
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider daoAuthProvider;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    // HttpSecurity permite gestionar y proteger las solicitudes http
    public SecurityFilterChain filterChain (HttpSecurity http) {
        CustomizerImpl customize = new CustomizerImpl();
        SecurityFilterChain filterChain = http
                .csrf(customize)
                .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.daoAuthProvider)//Le indica a Spring Security que use daoAuthProvider como proveedor encargado de autenticar las credenciales de los usuarios.
                //==en la línea anterior no necesitamos llamar explícitamente ningún método de SecurityBeansInjector porque Spring Security
                //== se encarga de inyectar los beans necesarios automáticamente. Al referenciar authenticationProvider(this.authenticationProvider),
                //== Spring Security buscará un bean de tipo AuthenticationProvider en el contexto de la aplicación y lo utilizará para la autenticación

                // agrega jwtAuthenticationFilter antes de UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();

                // aplica filtros a las peticiones

                //.authorizeHttpRequests(HttpSecurityConfig::buildRequestMatchersV2).build();
                // Ya no se llama a ningun metodo para agregar agregar los endpoints publicos si no que estos se
                // especifican dentro de cada componente con permitAll
                /*.authorizeHttpRequests(httpReqConfig ->{
                }).build();*/


        return filterChain;
    }

    // Metodo buildRequestMatchers basado en aseguramientos a traves de coincidencias de URL
    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {

        /* Estrategia basado en permisos especificos o autrhoritys*/
        /* Cuando trabajamos con autoridades el AuthorizationManager se llama AuthorityAuthenticationManager*/

        /*Autorizacion de enpoints de productos*/
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                //.hasAuthority(RolePermission.READ_ALL_PRODUCTS.name());
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());


        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
                //.hasAuthority(RolePermission.READ_ONE_PRODUCT.name());
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                //.hasAuthority(RolePermission.CREATE_ONE_PRODUCT.name());
                .hasRole(Role.ADMINISTRATOR.name());

        //authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
        authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher("/products/[0-9]*")) // validación con regex, que reciba cualquier simbolo que valla de 0 a nueve y que se repita n veces
                //.hasAuthority(RolePermission.UPDATE_ONE_PRODUCT.name());
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                //.hasAuthority(RolePermission.DISABLE_ONE_PRODUCT.name());
                .hasRole(Role.ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name(), Role.CUSTOMER.name());



        /*Autorizacion de enpoints de categorias*/
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                //.hasAuthority(RolePermission.READ_ALL_CATEGORIES.name());
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/category")
                //.hasAuthority(RolePermission.READ_ONE_CATEGORY.name());
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/category/{categoryId}")
                //.hasAuthority(RolePermission.CREATE_ONE_CATEGORY.name());
                .hasRole(Role.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/category/{categoryId}")
                //.hasAuthority(RolePermission.UPDATE_ONE_CATEGORY.name());
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/category/{categoryId}/disabled")
                //.hasAuthority(RolePermission.DISABLE_ONE_CATEGORY.name());
                .hasRole(Role.ADMINISTRATOR.name());


        /*Autorizacion de enpoints públicos*/
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        authReqConfig.anyRequest().authenticated();
    }

    // Método buildRequestMatchersV2 para aseguración a traves de metodos aquí solo se mantienen las url publicas ya que
    // En los diferentes metodos vamos a agregar nuestras anotaciones
    private static void buildRequestMatchersV2(	AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {


        /*Autorizacion de enpoints públicos*/
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll(); // se especifica que el POST dentro de customers será público
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        authReqConfig.anyRequest().authenticated();
    }

}

// UsernamePasswordAuthenticationFilter no es necesario para autenticar
// las peticiones posteriores al login, ya que utilizamos el JWT.

//! STATELESS
//Indica que el tipo de politica de sesion de mi app es una sesion sin estado es decir, no va a manterner un
// estado de la sesion en el servidor ya que es pr jwt

// El orden
// los filtros en Spring Security son  cruciales para determinar cómo se procesan las solicitudes
// y se aplica la seguridad. En este caso, la inserción de JwtAuthenticationFilter antes de UsernamePasswordAuthenticationFilter
// tiene un propósito específico:

//==  1. Priorizar la autenticación JWT:
//  JwtAuthenticationFilter se encarga de la autenticación basada en tokens JWT.
//  Al colocarlo antes de UsernamePasswordAuthenticationFilter, se le da prioridad a la aurorizacion JWT.
//  Si la solicitud contiene un token JWT válido, el usuario será autorizado utilizando el token

//==  2. Optimizar el rendimiento:
//  Si la solicitud no contiene un token JWT válido o la autorizacion JWT falla,
//  la solicitud sera bloqueada pro los mecanismos de seguridad yy se lanzará una excepcion con AuthenticationEntryPoint

//== 3. Seguridad
// El filtro JwtAuthenticationFilter no solo valida el token JWT, sino que también establece
// el contexto de seguridad con la información del usuario autenticado. Esto es crucial para que las partes
// posteriores del flujo de la solicitud, como los controladores o servicios, puedan acceder a la identidad del usuario
// y aplicar las reglas de autorización correspondientes.

//# ¿Qué es UsernamePasswordAuthenticationFilter?
// UsernamePasswordAuthenticationFilter es un filtro de Spring Security predefinido que maneja la autenticación
// tradicional basada en nombre de usuario y contraseña.


//# Porque se pone jwtAuthenticationFilter antes  de UsernamePasswordAuthenticationFilter
// Primero que todo, todos estos son filtros de spring boot, que se aplican a todas las peticiones
// y la razon del orden es que jwtAuthenticationFilter restrinje o permite el acceso a enpoints establecidos,
// y UsernamePasswordAuthenticationFilter se encarga de procesar la autenticación
// mediante usuario y contraseña, principalmente durante el login.
// ahora, no se nota, pero el jwtAuthenticationFilter siempre se ejecuta primero que UsernamePasswordAuthenticationFilter incluso en una peticion de logueo o registro
// solo que va a permmitir el acceso ya que estos epoints son publicos, por tanto el jwtAuthenticationFilter comienza a trabajar más durante las
// peticiones subsecuentes


/* Todos los filtros de authenticación se ejecutan antes de entrar a los controladores */
/* Entre mas grande sea el peso del filtro mas tarde se ejecuta el filtro asociado al peso*/