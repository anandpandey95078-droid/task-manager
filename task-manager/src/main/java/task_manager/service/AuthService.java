package task_manager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import task_manager.entity.User;
import task_manager.repository.UserRepository;
import task_manager.requestdto.LoginRequest;
import task_manager.responsedto.LoginResponse;
import task_manager.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

	private PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            JwtUtil jwtUtil,PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;

        this.jwtUtil = jwtUtil;
        
        this.passwordEncoder=passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid password");
        
        }

        String token =
                jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                "Login successful"
        );
    }
}