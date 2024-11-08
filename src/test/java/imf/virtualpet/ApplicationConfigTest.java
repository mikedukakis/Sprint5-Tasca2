package imf.virtualpet;

import imf.virtualpet.domain.user.User;
import imf.virtualpet.dto.AuthenticationRequestDTO;
import imf.virtualpet.repository.UserRepository;
import imf.virtualpet.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ApplicationConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindUserByNameLoadsUser() {
        AuthenticationRequestDTO requestDTO = AuthenticationRequestDTO.builder()
                .username("testUser")
                .password("password")
                .build();

        when(userRepository.findByUsername(requestDTO.getUsername()))
                .thenReturn(Mono.just(new User(requestDTO.getUsername(), passwordEncoder.encode(requestDTO.getPassword()))));

        Mono<User> userMono = userService.getUserByUsername(requestDTO.getUsername());

        StepVerifier.create(userMono)
                .expectNextMatches(user -> user.getUsername().equals("testUser"))
                .verifyComplete();
    }

    @Test
    void testPasswordEncoder() {
        String rawPassword = "myPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
