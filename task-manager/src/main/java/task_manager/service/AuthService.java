package task_manager.service;

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

    public AuthService(
            UserRepository userRepository,
            JwtUtil jwtUtil) {

        this.userRepository = userRepository;

        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {

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