package com.feri.sua.auth.config;

import com.feri.sua.auth.common.errors.ApiError;
import com.feri.sua.auth.config.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import static com.feri.sua.auth.user.Permission.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(java.util.List.of("*"));
                    corsConfiguration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(java.util.List.of("*"));
                    corsConfiguration.setMaxAge(3600L);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WhiteList.URLS)
                                .permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers(POST, "/users/**").hasAuthority(ADMIN_CREATE.getPermission())
                                .requestMatchers(PUT, "/users/**").hasAuthority(ADMIN_CREATE.getPermission())
                                .requestMatchers(DELETE, "/users/**").hasAuthority(ADMIN_DELETE.getPermission())
                                .requestMatchers(GET, "/users/emails").hasAuthority(ADMIN_READ.getPermission())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .headers(headers ->
                        headers.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
                                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"))
                                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "*"))
                                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Max-Age", "3600"))
                )



        ;

        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            System.out.println(exception.getMessage());
            ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid username or password");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(apiError.serializeToJson());
        };
    }
}
