package SpringSecurity.SpringSecurity.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Se una esta anotación para poder inyectar beans
@Configuration
// Esta clase es un inyector de beans de seguridad
// Contiene definiciones de beans que springboot debe registrar en su contexto
public class SecurityBeansInjector {

    @Autowired
    /* esta clase AuthenticationConfiguration ya es proveída por spring security también podria inyectarlo directamente en los
     * parametros del metodo authenticationManager nos proporciona una implementación más específica del AuthenticationManager(administrador de autenticaciones)
    */
    private AuthenticationConfiguration authenticationConfiguration;

    // Este bean hace que spring boot registre y administre el objeto AuthenticationManager que devuelve el método authenticationManager
    @Bean
    public AuthenticationManager authenticationManager()throws  Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    // Estrategia de authenticación
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider(); // necesita un password encoder porque las contraseñas estarán encriptadas
        authenticationStrategy.setPasswordEncoder(null); // necesita un codificador de contraseñas para comparar cuando se inicia sicion
        authenticationStrategy.setUserDetailsService(null);
        return authenticationStrategy;
    }

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService (){
        return (username) -> {
            return null;
        };
    }
}




//== AuthenticationProvider
//## es una interfaz de Spring Security que define un método authenticate()
//## que se utiliza para autenticar a un usuario. Spring Security proporciona implementaciones predeterminadas
//## como DaoAuthenticationProvider, que utiliza un UserDetailsService para cargar los detalles del usuario y
//## un PasswordEncoder para comparar contraseñas.

//== Bean
//## un "bean" es simplemente un objeto que es administrado por el contenedor de Spring.
//## El contenedor de Spring es responsable de instanciar, configurar y ensamblar estos objetos, también conocidos como beans.
//## Los beans son componentes de la aplicación que se benefician de las características proporcionadas por el contenedor de Spring,
//## como la inyección de dependencias, el ciclo de vida del bean y la configuración declarativa.