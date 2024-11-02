package imf.virtualpet.configuration.init;

import imf.virtualpet.domain.user.entity.Role;
import imf.virtualpet.domain.user.entity.User;
import imf.virtualpet.domain.user.repository.UserRepository;
import imf.virtualpet.security.auth.service.AuthenticationService;
import imf.virtualpet.security.auth.dto.RegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class InitialDataLoader {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Mono<User> adminMono = userRepository.findByUsername("Admin")
                    .switchIfEmpty(Mono.defer(() -> {
                        var admin = RegisterRequestDTO.builder()
                                .username("Admin")
                                .password(passwordEncoder.encode("password"))
                                .role(Role.ROLE_ADMIN)
                                .build();
                        return authenticationService.register(admin)
                                .doOnNext(response -> System.out.println("Admin token: " + response.getAccessToken()))
                                .then(userRepository.findByUsername("Admin"));
                    }));

            Mono<User> managerMono = userRepository.findByUsername("Manager")
                    .switchIfEmpty(Mono.defer(() -> {
                        var manager = RegisterRequestDTO.builder()
                                .username("Manager")
                                .password(passwordEncoder.encode("password"))
                                .role(Role.ROLE_MANAGER)
                                .build();
                        return authenticationService.register(manager)
                                .doOnNext(response -> System.out.println("Manager token: " + response.getAccessToken()))
                                .then(userRepository.findByUsername("Manager"));
                    }));

            Mono.when(adminMono, managerMono).subscribe();
        };
    }
}