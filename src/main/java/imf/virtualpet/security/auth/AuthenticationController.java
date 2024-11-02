package imf.virtualpet.security.auth;

import imf.virtualpet.security.auth.dto.AuthenticationRequestDTO;
import imf.virtualpet.security.auth.dto.AuthenticationResponseDTO;
import imf.virtualpet.security.auth.dto.RegisterRequestDTO;
import imf.virtualpet.security.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/virtualpet/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthenticationResponseDTO>> register(@RequestBody RegisterRequestDTO request) {
        return authenticationService.register(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthenticationResponseDTO>> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return authenticationService.authenticate(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/refresh-token")
    public Mono<Void> refreshToken(ServerHttpRequest request, ServerHttpResponse response) {
        return authenticationService.refreshToken(request, response);
    }


}

