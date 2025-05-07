// package fst.GestionRessource.Configuration;

// import java.util.Arrays;
// import lombok.RequiredArgsConstructor;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import fst.GestionRessource.User.model.Role;
// import jakarta.servlet.http.HttpServletResponse;

// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class SecurityConfiguration {

//     private final JwtAuthenticationFilter jwtAuthFilter;
//     private final AuthenticationProvider authenticationProvider;

//     /* === Endpoints ======================================================= */

//     private static final String AUTH_PATH      = "/api/auth/**";
//     private static final String USERS_PATH     = "/api/user/**";
//     private static final String RESOURCES_PATH = "/api/resource/**";
//     private static final String SUPPLIER_PATH  = "/api/supplier/**";
//     private static final String PANIC_PATH     = "/api/panic-reports/**";
//     private static final String PROPOSALS_PATH = "/api/proposal/**";
//     private static final String TENDERS_PATH   = "/api/tender/**";
//     private static final String REQUESTS_PATH  = "/api/resource-request/**";

//     /* === Security filter chain ========================================== */

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//         http
//                 /* ---- CORS / CSRF ------------------------------------------- */
//                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                 .csrf(csrf -> csrf.disable())

//                 /* ---- Authorisation ----------------------------------------- */
//                 .authorizeHttpRequests(auth -> auth

//                         /* --- 1. PUBLIC ------------------------------------------- */
//                         .requestMatchers(AUTH_PATH).permitAll()

//                         /* --- 2. SUPER-ADMIN -------------------------------------- */
//                         .requestMatchers(USERS_PATH).hasAuthority(Role.SUPER_ADMIN.name())
//                         .requestMatchers("/api/**").hasAuthority(Role.SUPER_ADMIN.name())


//                         /* --- 3. ENSEIGNANT --------------------------------------- */
//                         .requestMatchers(HttpMethod.POST, PANIC_PATH).hasAuthority(Role.TEACHER.name())
//                         .requestMatchers(HttpMethod.POST, REQUESTS_PATH).hasAuthority(Role.TEACHER.name())

//                         /* --- 4. DÉPARTEMENT & ENSEIGNANT (lecture ressources) ---- */
//                         .requestMatchers(HttpMethod.GET, RESOURCES_PATH)
//                         .hasAnyAuthority(Role.TEACHER.name(),
//                                 Role.DEPARTMENT_HEAD.name(),
//                                 Role.RESOURCE_MANAGER.name())

//                         /* --- 5. FOURNISSEUR -------------------------------------- */
//                         .requestMatchers(HttpMethod.POST, PROPOSALS_PATH).hasAuthority(Role.SUPPLIER.name())
//                         .requestMatchers(SUPPLIER_PATH).hasAuthority(Role.SUPPLIER.name())

//                         /* --- 6. RESOURCE MANAGER --------------------------------- */
//                         .requestMatchers(HttpMethod.POST, RESOURCES_PATH).hasAuthority(Role.RESOURCE_MANAGER.name())
//                         .requestMatchers(PANIC_PATH, PROPOSALS_PATH, TENDERS_PATH, REQUESTS_PATH)
//                         .hasAuthority(Role.RESOURCE_MANAGER.name())

//                         /* --- 7. TOUT LE RESTE ------------------------------------ */
//                         .anyRequest().authenticated()
//                 )

//                 /* ---- Stateless session / JWT ------------------------------ */
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

//                 .authenticationProvider(authenticationProvider)
//                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

//                 /* ---- Gestion propre des erreurs JSON ---------------------- */
//                 .exceptionHandling(exceptions -> exceptions
//                         .authenticationEntryPoint((req, res, ex) -> {
//                             res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                             res.setContentType("application/json");
//                             res.getWriter().printf("{\"error\":\"Authentication failed: %s\"}", ex.getMessage());
//                         })
//                         .accessDeniedHandler((req, res, ex) -> {
//                             res.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                             res.setContentType("application/json");
//                             res.getWriter().printf("{\"error\":\"Access denied: %s\"}", ex.getMessage());
//                         })
//                 );

//         return http.build();
//     }

//     /* === CORS ============================================================ */
//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
//         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//         configuration.setAllowCredentials(true);
//         configuration.setMaxAge(3600L);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
//     }
// }
package fst.GestionRessource.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
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

    /* === Endpoints ======================================================= */

    private static final String AUTH_PATH      = "/api/auth/**";
    private static final String USERS_PATH     = "/api/user/**";
    private static final String RESOURCES_PATH = "/api/resource/**";
    private static final String SUPPLIER_PATH  = "/api/supplier/**";
    private static final String PANIC_PATH     = "/api/panic-reports/**";
    private static final String PROPOSALS_PATH = "/api/proposal/**";
    private static final String TENDERS_PATH   = "/api/tender/**";
    private static final String REQUESTS_PATH  = "/api/resource-request/**";
    private static final String GET_TEACHERS_PATH = "/api/user/teachers/**";

    /* === Security filter chain ========================================== */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            /* ---- CORS / CSRF ------------------------------------------- */
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())

            /* ---- Authorisation ----------------------------------------- */
            .authorizeHttpRequests(auth -> auth

                /* 1. PUBLIC */
                .requestMatchers(AUTH_PATH).permitAll().
                requestMatchers("/api/supplier/register").permitAll()

                /* 2. SUPER-ADMIN only for managing users */
//                .requestMatchers(USERS_PATH).hasAuthority(Role.SUPER_ADMIN.name())

                /* 3. TEACHER actions */
                .requestMatchers(HttpMethod.GET, PANIC_PATH).hasAnyAuthority(withSuperAdmin(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name(), Role.TECHNICIAN.name()))
                .requestMatchers(HttpMethod.POST, PANIC_PATH).hasAnyAuthority(withSuperAdmin(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name()))
                .requestMatchers(HttpMethod.PUT, PANIC_PATH).hasAnyAuthority(withSuperAdmin(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name()))
                .requestMatchers(HttpMethod.DELETE, PANIC_PATH).hasAnyAuthority(withSuperAdmin(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name()))
              
                .requestMatchers(HttpMethod.GET, REQUESTS_PATH).hasAnyAuthority(withSuperAdmin(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name(), Role.RESOURCE_MANAGER.name(), Role.SUPPLIER.name()))
                .requestMatchers(HttpMethod.POST, REQUESTS_PATH).hasAnyAuthority(withSuperAdmin(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name()))
                .requestMatchers(HttpMethod.PUT, REQUESTS_PATH).hasAnyAuthority(withSuperAdmin(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name()))
                .requestMatchers(HttpMethod.DELETE, REQUESTS_PATH).hasAnyAuthority(withSuperAdmin(Role.TEACHER.name(), Role.DEPARTMENT_HEAD.name()))
                /* 4. READ access to resources: Teacher, Department, Manager */
                .requestMatchers(HttpMethod.GET, RESOURCES_PATH)
                    .hasAnyAuthority(withSuperAdmin(
                        Role.TEACHER.name(),
                        Role.DEPARTMENT_HEAD.name(),
                        Role.RESOURCE_MANAGER.name()
                    ))

                /* 5. SUPPLIER actions */
                .requestMatchers(HttpMethod.POST, PROPOSALS_PATH).hasAnyAuthority(withSuperAdmin(Role.SUPPLIER.name()))
                .requestMatchers(SUPPLIER_PATH).hasAnyAuthority(withSuperAdmin(Role.SUPPLIER.name(), Role.RESOURCE_MANAGER.name()))

                /* 6. RESOURCE MANAGER actions */
                .requestMatchers(HttpMethod.GET, GET_TEACHERS_PATH).hasAnyAuthority(withSuperAdmin(Role.RESOURCE_MANAGER.name(), Role.DEPARTMENT_HEAD.name()))
                .requestMatchers(HttpMethod.POST, RESOURCES_PATH).hasAnyAuthority(withSuperAdmin(Role.RESOURCE_MANAGER.name()))
                .requestMatchers(PANIC_PATH, PROPOSALS_PATH, TENDERS_PATH, REQUESTS_PATH)
                    .hasAnyAuthority(withSuperAdmin(Role.RESOURCE_MANAGER.name()))

                /* 7. EVERYTHING ELSE: Must be authenticated */
                .anyRequest().authenticated()
            )

            /* ---- Stateless session / JWT ------------------------------ */
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

            /* ---- Custom JSON Error Handling --------------------------- */
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json");
                    res.getWriter().printf("{\"error\":\"Authentication failed: %s\"}", ex.getMessage());
                })
                .accessDeniedHandler((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    res.setContentType("application/json");
                    res.getWriter().printf("{\"error\":\"Access denied: %s\"}", ex.getMessage());
                })
            );

        return http.build();
    }

    /* === CORS Configuration ============================================= */
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

    /* === Helper Method: Add SUPER_ADMIN automatically ================== */
    private String[] withSuperAdmin(String... roles) {
        List<String> list = new ArrayList<>(Arrays.asList(roles));
        list.add(Role.SUPER_ADMIN.name());
        return list.toArray(new String[0]);
    }
}
