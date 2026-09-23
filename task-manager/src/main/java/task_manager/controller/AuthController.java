package task_manager.controller;

import org.springframework.web.bind.annotation.*;

import task_manager.requestdto.LoginRequest;
import task_manager.responsedto.LoginResponse;
import task_manager.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}