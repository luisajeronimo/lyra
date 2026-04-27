package com.lyra_tarot.lyra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyra_tarot.lyra.service.IUserService;
import com.lyra_tarot.lyra.model.User;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping("/informarDados")
        @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pessoa informada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Pessoa não informada")
    })
    public ResponseEntity<User> informarDados(@Valid @RequestBody User user) {
        User userInformado = userService.salvarUsuario(user);        
        return ResponseEntity.status(201).body(userInformado);
    }
    

}
