package org.example.productservice.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final List<String> ALLOWED_ORIGINS      = List.of(
            "http://localhost:3000",
            "http://localhost:3001",
            "http://27.79.130.203:3000",
            "http://27.79.130.203:3001",
            "http://haui-megatech.shop:3000",
            "http://haui-megatech.shop:3001",
            "http://27.79.153.57:3000",
            "http://27.79.153.57:3001"
    );
    private final List<String> ALLOWED_HTTP_METHODS = List.of(
            GET.toString(),
            POST.toString(),
            PUT.toString(),
            PATCH.toString(),
            DELETE.toString()
    );
    private final List<String> ALLOWED_HEADERS      = List.of(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.ACCEPT_LANGUAGE,
            HttpHeaders.CONTENT_TYPE
    );
    private final List<String> EXPOSED_HEADERS      = List.of(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.ACCEPT_LANGUAGE
    );

    @Bean
    public SecurityFilterChain configure(
            HttpSecurity http,
            CorsConfigurationSource corsConfigurationSource,
            AuthenticationProvider authenticationProvider
    ) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .cors(cors -> cors.configurationSource(corsConfigurationSource))
                   .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                   .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authenticationProvider(authenticationProvider)
                   .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowedMethods(ALLOWED_HTTP_METHODS);
        configuration.setAllowedHeaders(ALLOWED_HEADERS);
        configuration.setExposedHeaders(EXPOSED_HEADERS);
        configuration.setMaxAge((long) (24 * 60 * 60));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
