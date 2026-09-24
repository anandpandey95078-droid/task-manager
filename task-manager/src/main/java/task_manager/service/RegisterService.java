package task_manager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import task_manager.entity.User;
import task_manager.repository.UserRepository;
import task_manager.requestdto.RegisterRequest;
import task_manager.responsedto.RegisterResponse;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Plain password ko BCrypt hash karo
        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        user.setPassword(encodedPassword);

        user.setRole(request.getRole());

        userRepository.save(user);

        return new RegisterResponse(
                "Registration successful",
                user.getEmail(),
                user.getRole()
        );
    }
}