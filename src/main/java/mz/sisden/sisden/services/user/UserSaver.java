/*
 <!--
  ~  Copyright (c) 2025
  ~  EXI Limitada
  ~  All rights reserved.
  ~
  ~   Created by Eleuterio Zacarias Mabecuane 
  -->

 */

package mz.sisden.sisden.services.user;

import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.configuration.RollBackTransactional;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@RollBackTransactional
public class UserSaver {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) {
        if(Objects.isNull(user)){
            return;
        }

        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        this.userRepository.save(user);
    }
}
