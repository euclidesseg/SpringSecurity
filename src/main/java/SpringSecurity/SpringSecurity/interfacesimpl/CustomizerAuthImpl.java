package SpringSecurity.SpringSecurity.interfacesimpl;

import SpringSecurity.SpringSecurity.persistance.util.RolePermissionEnum;
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
                .hasAuthority(RolePermissionEnum.READ_ALL_PRODUCTS.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
                .hasAuthority(RolePermissionEnum.READ_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasAuthority(RolePermissionEnum.CREATE_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAuthority(RolePermissionEnum.UPDATE_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{" +
                        "productId}/disabled")
                .hasAuthority(RolePermissionEnum.DISABLE_ONE_PRODUCT.name());


        /*Autorizacion de enpoints de categorias*/
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAuthority(RolePermissionEnum.READ_ALL_CATEGORIES.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/category")
                .hasAuthority(RolePermissionEnum.READ_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/category/{categoryId}")
                .hasAuthority(RolePermissionEnum.CREATE_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/category/{categoryId}")
                .hasAuthority(RolePermissionEnum.UPDATE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/category/{categoryId}/disabled")
                .hasAuthority(RolePermissionEnum.DISABLE_ONE_CATEGORY.name());


        /*Autorizacion de enpoints públicos*/
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        authReqConfig.anyRequest().authenticated();
    }
}
