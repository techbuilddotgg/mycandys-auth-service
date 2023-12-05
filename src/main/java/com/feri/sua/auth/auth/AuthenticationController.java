package com.feri.sua.auth.auth;

import com.feri.sua.auth.auth.dto.VerifyTokenResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("/verify")
    public ResponseEntity<VerifyTokenResponseDto> verify(
        @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(service.verify(token));
    }


  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping("/refresh-token")
  public AuthenticationResponse refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    return service.refreshToken(request, response);
  }


}
