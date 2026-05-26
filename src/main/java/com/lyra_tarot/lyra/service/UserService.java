package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.dto.RegisterDTO;
import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.model.UserRole;
import com.lyra_tarot.lyra.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;

@Service
public class UserService implements IUserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User salvarUsuario(User user) {
        if (user.getDataNascimento().getYear() > LocalDate.now().getYear() || user.getDataNascimento().getYear() < 1900) {
            throw new IllegalArgumentException("Ano de nascimento inválido.");
        }
        return repository.save(user);
    }

    @Override
    public UserDetails findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void registerUser(RegisterDTO data) {
        if (this.findByEmail(data.email()) != null) {
            throw new IllegalArgumentException("Usuário já existe");
        }
        
        String encryptedPassword = passwordEncoder.encode(data.senha());
        User newUser = new User(
            null, 
            data.nome(), 
            data.email(), 
            encryptedPassword, 
            UserRole.USER, 
            true, 
            data.estado(), 
            data.cidade(), 
            data.dataNascimento(), 
            data.horaNascimento(), 
            null 
        );

        this.salvarUsuario(newUser);
    }
}