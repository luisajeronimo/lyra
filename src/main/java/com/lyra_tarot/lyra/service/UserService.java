package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User salvarUsuario(User user) {
        user.setDataHoje(LocalDateTime.now());
        return repository.save(user);
    }
}