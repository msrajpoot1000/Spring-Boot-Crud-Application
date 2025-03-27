package org.codingwallah.em_project.controller;

import java.util.Map;
import org.codingwallah.em_project.entity.JwtRequest;
import org.codingwallah.em_project.entity.JwtResponse;
import org.codingwallah.em_project.entity.UserEntity;
import org.codingwallah.em_project.repository.UserRepository;
import org.codingwallah.em_project.security.JwtHelper;
import org.codingwallah.em_project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private JwtHelper helper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private Logger logger = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
    System.out.print(request.getUsername() + " " + request.getPassword());
    this.doAuthenticate(request.getUsername(), request.getPassword());

    UserDetails userDetails = userDetailsService.loadUserByUsername(
        request.getUsername());
    String token = this.helper.generateToken(userDetails);

    JwtResponse response = JwtResponse
        .builder()
        .jwtToken(token)
        .username(userDetails.getUsername())
        .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private void doAuthenticate(String email, String password) {
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        email,
        password);
    try {
      manager.authenticate(authentication);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException(" Invalid Username or Password  !!");
    }
  }

  @ExceptionHandler(BadCredentialsException.class)
  public String exceptionHandler() {
    return "Credentials Invalid !!";
  }

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

 @PostMapping("/register")
public String register(@RequestBody Map<String, String> request) {
    String username = request.get("username");
    String password = passwordEncoder.encode(request.get("password"));
    String role = request.getOrDefault("role", "USER"); // Default role: USER

    UserEntity user = new UserEntity();
    user.setUsername(username);
    user.setPassword(password);
    user.setRole(role); // ✅ Set role

    userRepository.save(user);
    return "User registered successfully!";
}

}



// package org.codingwallah.em_project.controller;
// import org.codingwallah.em_project.entity.UserEntity;
// import org.codingwallah.em_project.repository.UserRepository;
// import org.codingwallah.em_project.security.JwtUtil;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.*;
// @RestController
// @RequestMapping("/auth")
// public class AuthController {
//   @Autowired
//   private AuthenticationManager authenticationManager;
//   @Autowired
//   private JwtUtil jwtUtil;
//   @Autowired
//   private UserRepository userRepository;
//   @Autowired
//   private PasswordEncoder passwordEncoder;
//   @PostMapping("/register")
//   public String register(@RequestBody Map<String, String> request) {
//     String username = request.get("username");
//     String password = passwordEncoder.encode(request.get("password"));
//     UserEntity user = new UserEntity();
//     user.setUsername(username);
//     user.setPassword(password);
//     userRepository.save(user);
//     return "User registered successfully!";
//   }
//   @PostMapping("/login")
//   public String generateToken(@RequestBody Map<String, String> request) {
//     authenticationManager.authenticate(
//       new UsernamePasswordAuthenticationToken(
//         request.get("username"),
//         request.get("password")
//       )
//     );
//     return jwtUtil.generateToken(request.get("username"));
//   }
// }
