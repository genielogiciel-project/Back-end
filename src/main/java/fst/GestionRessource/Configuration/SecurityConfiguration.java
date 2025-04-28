package fst.GestionRessource.Configuration;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fst.GestionRessource.User.model.Role;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // Constantes pour les chemins d'endpoints
    private static final String AUTH_PATH = "/api/auth/**";
    // private static final String SWAGGER_PATH = "/swagger-ui/**";
    // private static final String API_DOCS_PATH = "/v3/api-docs/**";
    // private static final String ACTUATOR_PATH = "/actuator/health";
    private static final String RESOURCES_PATH = "/api/resource/**";
    private static final String PANIC_REPORTS_PATH = "/api/panic-reports/**";
    private static final String PROPOSALS_PATH = "/api/proposal/**";
    private static final String TENDERS_PATH = "/api/tender/**";
    private static final String REQUESTS_PATH = "/api/resource-request/**";
    private static final String USERS_PATH = "/api/user/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .cors().and().csrf()
//                 .disable()
//                 .authorizeHttpRequests()
//                 .requestMatchers("api/auth/**")
// //                .requestMatchers("/api/v1/**")
//                 .permitAll()
//                 .requestMatchers("/api/**").hasAnyAuthority("SUPER_ADMIN")
//                 .anyRequest()
//                 .authenticated()
//                 .and()
//                 .sessionManagement()
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 .and()
//                 .authenticationProvider(authenticationProvider)
//                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//         ;

                http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints publics
                        // .requestMatchers(AUTH_PATH, SWAGGER_PATH, API_DOCS_PATH, ACTUATOR_PATH)
                        .requestMatchers(AUTH_PATH)
                        .permitAll()
                        .requestMatchers("/api/**","/api/supplier/**")
                        .hasAuthority(Role.SUPER_ADMIN.name())
                        .requestMatchers(USERS_PATH)
                        .hasAnyAuthority(Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, RESOURCES_PATH)
                        .hasAnyAuthority(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name(), Role.RESOURCE_MANAGER.name())
                        .requestMatchers(HttpMethod.POST, RESOURCES_PATH)
                        .hasAuthority(Role.RESOURCE_MANAGER.name())
                        .requestMatchers(HttpMethod.POST, PANIC_REPORTS_PATH)
                        .hasAuthority(Role.TEACHER.name())
                        .requestMatchers(PANIC_REPORTS_PATH)
                        .hasAuthority(Role.RESOURCE_MANAGER.name())
                        .requestMatchers(HttpMethod.POST, PROPOSALS_PATH)
                        .hasAuthority(Role.SUPPLIER.name())
                        .requestMatchers(PROPOSALS_PATH)
                        .hasAuthority(Role.RESOURCE_MANAGER.name())
                        .requestMatchers(TENDERS_PATH)
                        .hasAuthority(Role.RESOURCE_MANAGER.name())
                        .requestMatchers(HttpMethod.POST, REQUESTS_PATH)
                        .hasAuthority(Role.TEACHER.name())
                        .requestMatchers(REQUESTS_PATH)
                        .hasAuthority(Role.RESOURCE_MANAGER.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Authentication failed: " + authException.getMessage() + "\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Access denied: " + accessDeniedException.getMessage() + "\"}");
                        })
                );

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

