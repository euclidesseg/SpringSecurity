package SpringSecurity.SpringSecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		System.out.println("Bienvenido a Spring Boot");
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	// ComandLineRunner sirve para ejecutar código justo despues de que el contexto
	// de la aplicación ha sido cargado y antes de que la aplicación comience a funcionar

}
