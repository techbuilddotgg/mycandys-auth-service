package com.feri.sua.auth.config;

import com.feri.sua.auth.common.errors.ApiError;
import com.feri.sua.auth.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.feri.sua.auth.config.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final TokenRepository tokenRepository;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    System.out.println(request.getServletPath());
    if (WhiteList.isPresent(request.getServletPath())) {
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.length() < 8) {
      createErrorResponse(response, "No token found in the header", HttpStatus.BAD_REQUEST);
      filterChain.doFilter(request, response);
      return;
    }
    String jwt = authHeader.substring(7);
    String userEmail;

    try {
      userEmail = jwtService.extractUsername(jwt);
    }
    catch (Exception e) {
      createErrorResponse(response, "Token is not valid", HttpStatus.UNAUTHORIZED);
      filterChain.doFilter(request, response);
      return;
    }


    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      var isTokenValid = tokenRepository.findByToken(jwt)
          .map(t -> !t.isExpired() && !t.isRevoked())
          .orElse(false);

      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
      else {
        createErrorResponse(response, "Token is not valid", HttpStatus.UNAUTHORIZED);
        filterChain.doFilter(request, response);
        return;
      }
    }
    else {
      createErrorResponse(response, "Token is not valid", HttpStatus.BAD_REQUEST);
      filterChain.doFilter(request, response);
      return;
    }
    filterChain.doFilter(request, response);
  }

  private void createErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws IOException {
    response.setContentType("application/json");
    response.setStatus(status.value());
    ApiError error = new ApiError(status, message);
    response.getWriter().write(error.serializeToJson());
  }
}
