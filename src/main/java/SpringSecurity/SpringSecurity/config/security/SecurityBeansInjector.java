package SpringSecurity.SpringSecurity.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

// Se una esta anotación para poder inyectar beans
@Configuration
// Esta clase es un inyector de beans de seguridad
// Contiene definiciones de beans que springboot debe registrar en su contexto
public class SecurityBeansInjector {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    // Este bean hace que spring boot registre y administre el objeto AuthenticationManager que devuelve el método authenticationManager
    @Bean
    public AuthenticationManager authenticationManager()throws  Exception{
        return authenticationConfiguration.getAuthenticationManager();
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