package com.FoodOrderingApp.foodorder.configaurtions.securityConfigs;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String SYSTEM_ADMIN = "system_admin";
    public static final String SYSTEM_CUSTOMER = "system_user";
    public static final String RESTAURANT_ADMIN = "restaurant_admin";


    private final JwtConverter jwtConverter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

           http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, "/customers").permitAll()
                        .requestMatchers(HttpMethod.POST, "/restaurants").permitAll()
                        .requestMatchers(HttpMethod.GET, "/restaurants/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/restaurants/*/menu/**").permitAll()
                        .requestMatchers("/customers/*/cart/**").hasRole(SYSTEM_CUSTOMER)
                        .requestMatchers(HttpMethod.GET,"/orders/customer/*").hasAnyRole(SYSTEM_CUSTOMER,RESTAURANT_ADMIN)
                        .requestMatchers("/orders/**").hasRole(SYSTEM_CUSTOMER)
                        .requestMatchers("/customers/**").hasAnyRole(SYSTEM_CUSTOMER,SYSTEM_ADMIN)
                        .requestMatchers("/restaurants/*/menu/**").hasRole(RESTAURANT_ADMIN)
                        .requestMatchers("/restaurants/**").hasAnyRole(RESTAURANT_ADMIN,SYSTEM_ADMIN)
                        .requestMatchers("/item-reviews/**").hasRole(SYSTEM_CUSTOMER)
                        .anyRequest().authenticated()
                );


        /*        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(HttpMethod.GET,"/hello/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/hello/**").permitAll()



                // .requestMatchers(HttpMethod.POST, "/customers").permitAll()
                // .requestMatchers(HttpMethod.PUT, "/customers/**").hasRole(SYSTEM_CUSTOMER)
                .anyRequest().authenticated());
*/
        http.sessionManagement(sess -> sess.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

        return http.build();
    }
}