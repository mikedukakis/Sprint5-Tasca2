package imf.virtualpet.security.configuration;

import imf.virtualpet.domain.user.Role;
import static imf.virtualpet.domain.user.Permission.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/virtualpet/user/register",
            "/virtualpet/user/login",
            "/virtualpet/auth/**",
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final ReactiveAuthenticationManager authenticationManager;
    private final ServerLogoutHandler logoutHandler = new SecurityContextServerLogoutHandler();

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager manager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(passwordEncoder);
        return manager;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/virtualpet/management/**"));

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers(WHITE_LIST_URL).permitAll()
                                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                                .pathMatchers("/virtualpet/management/**").hasAuthority(Role.ROLE_ADMIN.name())
                                .pathMatchers(GET, "/virtualpet/management/**").hasAuthority(ADMIN_READ.name())
                                .pathMatchers(POST, "/virtualpet/management/**").hasAuthority(ADMIN_CREATE.name())
                                .pathMatchers(PUT, "/virtualpet/management/**").hasAuthority(ADMIN_UPDATE.name())
                                .pathMatchers(DELETE, "/virtualpet/management/**").hasAuthority(ADMIN_DELETE.name())
                                .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager)
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .logout(logout -> logout
                        .logoutUrl("/virtualpet/auth/logout")
                        .logoutHandler(logoutHandler)
                        .logoutSuccessHandler((exchange, authentication) ->
                                Mono.just("logout")
                                        .doOnTerminate(ReactiveSecurityContextHolder::clearContext)
                                        .then()
                        )
                )
                .build();
    }
}