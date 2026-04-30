package com.lyra_tarot.lyra.controller;

import com.lyra_tarot.lyra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lyra_tarot.lyra.dto.AuthenticationDTO;
import com.lyra_tarot.lyra.dto.LoginResponseDTO;
import com.lyra_tarot.lyra.dto.RegisterDTO;
import com.lyra_tarot.lyra.repository.UserRepository;
import com.lyra_tarot.lyra.config.security.TokenService;
import jakarta.validation.Valid;
import com.lyra_tarot.lyra.model.User;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        User newUser = new User(
            null, 
            data.nome(), 
            data.email(), 
            encryptedPassword, 
            data.role(), 
            true, 
            data.estado(), 
            data.cidade(), 
            data.dataNascimento(), 
            data.horaNascimento(), 
            null 
        );

        this.userService.salvarUsuario(newUser);

        return ResponseEntity.ok().build();
    }
}
