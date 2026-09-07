package SpringSecurity.SpringSecurity.interfacesimpl;


import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

public class CustomizeImpl implements Customizer<CsrfConfigurer<HttpSecurity>> {

    @Override
    public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
        httpSecurityCsrfConfigurer.disable();
    }
}
