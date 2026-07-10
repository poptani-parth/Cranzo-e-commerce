package com.e_commerce.Cranzo.Service.ServiceImp;

import com.e_commerce.Cranzo.Config.Security.JwtUtil;
import com.e_commerce.Cranzo.Entity.Role;
import com.e_commerce.Cranzo.Entity.User;
import com.e_commerce.Cranzo.Enums.RoleName;
import com.e_commerce.Cranzo.Repository.RoleRepository;
import com.e_commerce.Cranzo.Repository.UserRepository;
import com.e_commerce.Cranzo.Service.AuthService;
import com.e_commerce.Cranzo.Service.CustomUserDetailsService;
import com.e_commerce.Cranzo.dto.AuthResponse;
import com.e_commerce.Cranzo.dto.LoginRequest;
import com.e_commerce.Cranzo.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImp  implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;

    @Override
    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found"));
        user.getRoles().add(role);
        user.setPhone(registerRequest.getPhone());
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        // user not found
        User user = userRepository.findByEmail(loginRequest.getEmail())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //Authenticat user details
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
        String jwtToken = jwtUtil.generateToken(userDetails);
        return new AuthResponse(jwtToken);
    }
}
