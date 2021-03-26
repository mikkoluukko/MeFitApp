package com.example.mefitapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // https://www.baeldung.com/spring-security-expressions
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Custom converter to show roles instead of scopes
        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(this.jwtGrantedAuthoritiesConverter());

        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/login").permitAll()
                /*
                 Need to find a way to authorize via roles - because we are not making the
                 principal ourselves, this is tricky. As authorities is the scope from keycloak.
                 You can see what is created at the /user/info/principal endpoint by passing a token.
                 Having a look at some documentation may help: https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2login-advanced-map-authorities
                 https://stackoverflow.com/questions/55609083/how-to-set-user-authorities-from-user-claims-return-by-an-oauth-server-in-spring/56259665
                 For now, we replaced our scope with our roles: https://stackoverflow.com/questions/58205510/spring-security-mapping-oauth2-claims-with-roles-to-secure-resource-server-endp/58234971#58234971
                */
                .antMatchers("/api/v1/*").hasRole("User")
//                .antMatchers("/api/resources/admin").hasRole("Administrator")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                // Using our converter
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }

    // Implementation of replacing authorities with our roles
    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        // You can use setAuthoritiesClaimName method
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        return jwtGrantedAuthoritiesConverter;
    }
}
