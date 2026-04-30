package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User salvarUsuario(User user) {
        if (user.getDataNascimento().getYear() > LocalDate.now().getYear() || user.getDataNascimento().getYear() < 1900) {
            throw new IllegalArgumentException("Ano de nascimento inválido.");
        }
        return repository.save(user);
    }
}