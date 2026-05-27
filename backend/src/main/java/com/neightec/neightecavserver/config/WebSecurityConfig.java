package com.neightec.neightecavserver.config;

import com.neightec.neightecavserver.security.oauth2.OAuth2CustomFilter;
import com.neightec.neightecavserver.services.NeightecUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Log4j2
@Order(10)
@Profile({"dev"})
public class WebSecurityConfig {

    @Autowired
    private NeightecUserService neightecUserService;

    @Autowired
    private CorsEndpointProperties corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("WebSecurityConfig is active!");
        http
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(
                                corsConfigurationSource()
                        )
                )
                .authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )

                /*.oauth2ResourceServer(
                        oauth2ResourceServer -> oauth2ResourceServer.jwt(
                                jwt -> jwt.decoder(jwtDecoder)
                        )
                )*/;
        http.addFilterBefore(
                new OAuth2CustomFilter(neightecUserService),
                AnonymousAuthenticationFilter.class
        );
        return http.build();
    }

    @Bean(name = "corsConfig")
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsConfig.getAllowedOrigins());
        configuration.setAllowedMethods(corsConfig.getAllowedMethods());
        configuration.setAllowedHeaders(corsConfig.getAllowedHeaders());
        configuration.setExposedHeaders(corsConfig.getExposedHeaders());
        configuration.setAllowCredentials(corsConfig.getAllowCredentials());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
