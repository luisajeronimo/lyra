package com.lyra_tarot.lyra.service;
import com.lyra_tarot.lyra.model.User;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {

    User salvarUsuario(User user);
    UserDetails findByEmail(String email);

}
