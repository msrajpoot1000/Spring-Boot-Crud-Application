package org.codingwallah.em_project.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain chain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    System.out.println("🔍 Authorization Header: " + authHeader);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      System.out.println("❌ No JWT Token Found!");
      chain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring(7);
    String username = null;
    System.out.print(username);
    try {
      username = jwtUtil.extractUsername(token);
      System.out.println("Extracted Username: " + username);
    } catch (Exception e) {
      System.out.println("❌ JWT Parsing Error: " + e.getMessage());
    }

    if (
      username != null &&
      SecurityContextHolder.getContext().getAuthentication() == null
    ) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      System.out.println("🔑 Loaded User: " + userDetails.getUsername());

      if (jwtUtil.validateToken(token, userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );
        authToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        System.out.println("✅ Authentication Successful: " + username);
      } else {
        System.out.println("❌ Invalid JWT Token!");
      }
    } else {
      System.out.println(
        "❌ Username is null or already authenticated!" + username
      );
    }

    chain.doFilter(request, response);
  }
}
