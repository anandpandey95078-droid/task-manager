package task_manager.controller;

import org.springframework.web.bind.annotation.*;

import task_manager.requestdto.RegisterRequest;
import task_manager.responsedto.RegisterResponse;
import task_manager.service.RegisterService;

@RestController
@RequestMapping("/auth")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public RegisterResponse register(
            @RequestBody RegisterRequest request) {

        return registerService.register(request);
    }
}