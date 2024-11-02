package imf.virtualpet.security.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import imf.virtualpet.configuration.JwtService;
import imf.virtualpet.domain.user.entity.Role;
import imf.virtualpet.domain.user.entity.User;
import imf.virtualpet.domain.user.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import imf.virtualpet.security.auth.dto.AuthenticationRequestDTO;
import imf.virtualpet.security.auth.dto.AuthenticationResponseDTO;
import imf.virtualpet.security.auth.RegisterRequest;
import imf.virtualpet.token.entity.Token;
import imf.virtualpet.token.repository.TokenRepository;
import imf.virtualpet.token.entity.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ReactiveAuthenticationManager authenticationManager;

    public Mono<AuthenticationResponseDTO> register(RegisterRequest request) {
        Role userRole = request.getRole() != null ? request.getRole() : Role.USER;

        return repository.findByUsername(request.getUsername())
                .flatMap(existingUser -> Mono.<AuthenticationResponseDTO>error(new RuntimeException("Username is already taken")))  // Explicitly cast to Mono<AuthenticationResponse>
                .switchIfEmpty(Mono.defer(() -> {
                    var user = User.builder()
                            .username(request.getUsername())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .role(userRole)
                            .build();

                    return repository.save(user)
                            .flatMap(savedUser -> {
                                var jwtToken = jwtService.generateToken(savedUser);
                                var refreshToken = jwtService.generateRefreshToken(savedUser);

                                return Mono.fromRunnable(() -> saveUserToken(savedUser, jwtToken))
                                        .then(Mono.just(AuthenticationResponseDTO.builder()
                                                .accessToken(jwtToken)
                                                .refreshToken(refreshToken)
                                                .build()));
                            });
                }));
    }

    public Mono<AuthenticationResponseDTO> authenticate(AuthenticationRequestDTO request) {
        return Mono.fromRunnable(() ->
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        request.getUsername(),
                                        request.getPassword()
                                )
                        )
                )
                .then(repository.findByUsername(request.getUsername())
                        .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                        .flatMap(user -> {
                            var jwtToken = jwtService.generateToken(user);
                            var refreshToken = jwtService.generateRefreshToken(user);
                            return revokeAllUserTokens(user)
                                    .then(saveUserToken(user, jwtToken))
                                    .thenReturn(AuthenticationResponseDTO.builder()
                                            .accessToken(jwtToken)
                                            .refreshToken(refreshToken)
                                            .build());
                        })
                );
    }

    private Mono<Void> saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .userId(user.getId())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        return tokenRepository.save(token).then();
    }

    private Mono<Void> revokeAllUserTokens(User user) {
        return tokenRepository.findByUserIdAndExpiredFalseOrRevokedFalse(user.getId())
                .flatMap(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                    return tokenRepository.save(token);
                })
                .then();
    }

    public Mono<Void> refreshToken(
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.empty();
        }
        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);

        return repository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .filter(user -> jwtService.isTokenValid(refreshToken, user))
                .flatMap(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return revokeAllUserTokens(user)
                            .then(saveUserToken(user, accessToken))
                            .then(Mono.defer(() -> {
                                AuthenticationResponseDTO authResponse = AuthenticationResponseDTO.builder()
                                        .accessToken(accessToken)
                                        .refreshToken(refreshToken)
                                        .build();
                                try {
                                    byte[] responseBytes = new ObjectMapper().writeValueAsBytes(authResponse);
                                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                    return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBytes)));
                                } catch (JsonProcessingException e) {
                                    return Mono.error(new RuntimeException("Error writing response", e));
                                }
                            }));
                });
    }

}
