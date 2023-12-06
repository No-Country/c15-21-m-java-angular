package c1521mjavaangular.ecotienda.config;

import c1521mjavaangular.ecotienda.Filters.JwtAuthenticationFilter;
import c1521mjavaangular.ecotienda.models.Usuarios;
import c1521mjavaangular.ecotienda.services.UsuariosService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UsuariosService usuariosService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuariosService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .disable()
                )

                .authorizeHttpRequests(authRequest->
                        authRequest
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/api-docs.yaml").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v1/test/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/administrador/productos").permitAll()

                                .requestMatchers(HttpMethod.POST, "/v1/iniciarSesion", "/v1/registrarse").permitAll()

                                .requestMatchers(HttpMethod.PUT, "/v1/users/**").hasAnyRole("USER")
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.GET).hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.POST).hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT).hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")

                        .anyRequest().authenticated()
                )


                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}