package SpringSecurity.SpringSecurity.interfacesimpl;

import SpringSecurity.SpringSecurity.persistance.util.RolePermission;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class CustomizerAuthImpl implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistryCustomizer) {
        buildRequestMatchers(authorizationManagerRequestMatcherRegistryCustomizer);
    }

    private static void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*Autorizacion de enpoints de productos*/
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAuthority(RolePermission.READ_ALL_PRODUCTS.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
                .hasAuthority(RolePermission.READ_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasAuthority(RolePermission.CREATE_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAuthority(RolePermission.UPDATE_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{" +
                        "productId}/disabled")
                .hasAuthority(RolePermission.DISABLE_ONE_PRODUCT.name());


        /*Autorizacion de enpoints de categorias*/
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAuthority(RolePermission.READ_ALL_CATEGORIES.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/category")
                .hasAuthority(RolePermission.READ_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/category/{categoryId}")
                .hasAuthority(RolePermission.CREATE_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/category/{categoryId}")
                .hasAuthority(RolePermission.UPDATE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/category/{categoryId}/disabled")
                .hasAuthority(RolePermission.DISABLE_ONE_CATEGORY.name());


        /*Autorizacion de enpoints públicos*/
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        authReqConfig.anyRequest().authenticated();
    }
}
