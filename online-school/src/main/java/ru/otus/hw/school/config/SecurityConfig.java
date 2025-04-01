package ru.otus.hw.school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .oauth2Login(Customizer.withDefaults())
                .authorizeHttpRequests(c -> c
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/homeworks/**"
                        ).hasAnyRole("TEACHER", "STUDENT")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/courses/**",
                                "/api/teachers/**",
                                "/api/groups/**",
                                "/api/homeworks-info/**"
                        ).hasAnyRole("STUDENT", "TEACHER", "MANAGER")
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/courses/**", "/api/teachers/**", "/api/groups/**", "/api/homeworks-info/**"
                        ).hasRole("MANAGER")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/courses/**", "/api/teachers/**", "/api/groups/**", "/api/homeworks-info/**"
                        ).hasRole("MANAGER")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/courses/**",
                                "/api/teachers/**",
                                "/api/groups/**",
                                "/api/homeworks-info/**",
                                "/api/homeworks/**"
                        ).hasRole("MANAGER")
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/homeworks"
                        ).hasRole("STUDENT")
                        .requestMatchers(
                                HttpMethod.PUT, "/api/homeworks-status/sent"
                        ).hasRole("STUDENT")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/homeworks-status/checking",
                                "/api/homeworks-status/rework",
                                "/api/homeworks-status/accepted"
                        ).hasRole("TEACHER")
                        .anyRequest().authenticated())
                //.authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .sessionManagement(( session ) -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var roles = jwt.getClaimAsStringList("school_roles");

            return roles.stream()
                    .filter(role -> role.startsWith("ROLE"))
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList();
        });

        return converter;
    }

}
