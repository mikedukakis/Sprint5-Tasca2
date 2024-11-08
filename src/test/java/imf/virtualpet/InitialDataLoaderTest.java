package imf.virtualpet;

import imf.virtualpet.domain.user.Role;
import imf.virtualpet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class InitialDataLoaderTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testAdminUserCreated() {
        StepVerifier.create(userRepository.findByUsername("Admin"))
                .expectNextMatches(user -> user.getRole().equals(Role.ROLE_ADMIN))
                .verifyComplete();
    }

    @Test
    void testManagerUserCreated() {
        StepVerifier.create(userRepository.findByUsername("Manager"))
                .expectNextMatches(user -> user.getRole().equals(Role.ROLE_MANAGER))
                .verifyComplete();
    }
}
