package imf.virtualpet;

import imf.virtualpet.configuration.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @MockBean
    private UserDetails mockUserDetails;

    @Test
    void testGenerateToken() {
        when(mockUserDetails.getUsername()).thenReturn("testuser");
        String token = jwtService.generateToken(mockUserDetails);

        assertNotNull(token);
        assertEquals("testuser", jwtService.extractUsername(token));
    }

    @Test
    void testValidateToken() {
        when(mockUserDetails.getUsername()).thenReturn("testuser");
        String token = jwtService.generateToken(mockUserDetails);

        assertTrue(jwtService.isTokenValid(token, mockUserDetails));
    }
}

