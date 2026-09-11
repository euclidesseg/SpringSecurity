package SpringSecurity.SpringSecurity.config.security;

import SpringSecurity.SpringSecurity.interfacesimpl.CustomizeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// EnableWebSecurity activa y congigura componentes como el AuthenticationConfiguration
// que tenemos en SecurityBeansInjector
// también activa el autenticatorentripoint, Habilita la seguridad basada en coincidencias de url
@EnableWebSecurity
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider daoAuthProvider;
    @Bean
    // HttpSecurity permite gestionar y proteger las solicitudes http
    public SecurityFilterChain filterChain (HttpSecurity http) {
        CustomizeImpl customize = new CustomizeImpl();
        SecurityFilterChain filterChain = http
                .csrf(customize)
                .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthProvider)//Le indica a Spring Security que use daoAuthProvider como proveedor encargado de autenticar las credenciales de los usuarios.
                //==en la línea anterior no necesitamos llamar explícitamente ningún método de SecurityBeansInjector porque Spring Security
                //== se encarga de inyectar los beans necesarios automáticamente. Al referenciar authenticationProvider(this.authenticationProvider),
                //== Spring Security buscará un bean de tipo AuthenticationProvider en el contexto de la aplicación y lo utilizará para la autenticación
                .addFilterBefore()
                .authorizeHttpRequests(authReqConfig -> {
                    authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

                    authReqConfig.anyRequest().authenticated();
                }).build();


        return filterChain;
    }
}
