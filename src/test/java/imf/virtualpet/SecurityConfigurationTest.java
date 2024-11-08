package imf.virtualpet;

import imf.virtualpet.dto.RegisterRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class SecurityConfigurationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testPublicRoutesAccessibleWithoutAuth() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("NewUserForTesting")
                .password("password")
                .build();

        webTestClient.post().uri("/virtualpet/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDuplicateUserIsRejected() {
        RegisterRequestDTO request = RegisterRequestDTO.builder()
                .username("Admin")
                .password("password")
                .build();

        webTestClient.post().uri("/virtualpet/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
