/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.RollBackTransactional;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.repositories.UserRepository;
import mz.sisden.sisden.utils.Texter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@RollBackTransactional
@RequiredArgsConstructor
public class SisClubeAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String usernameOrEmail = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Optional<User> userOptional = this.userRepository.findByUsernameOrEmail(StringUtils.trimToEmpty(usernameOrEmail));
        if (userOptional.isEmpty()) {
            log.debug("User with username or email {} not found!", usernameOrEmail);
            throw new UsernameNotFoundException("User not found!");
        }
        User user = userOptional.get();

        if (BooleanUtils.isFalse(user.getActive())) {
            log.debug("User {} is not active!", usernameOrEmail);
            throw new DisabledException("Account is not active!");
        }

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            user.setTries(user.getTries() - 1);
            this.userRepository.save(user);
            log.debug("User {} failed password, left tries {}", usernameOrEmail, user.getTries());

            if (user.getTries() == 0) {
                log.debug("User {} exceeded max tries", usernameOrEmail);
            }
            throw new BadCredentialsException("");
        }

        user.setTries(5);
        log.debug(Texter.format("User {} authenticated successfully.", usernameOrEmail));

        //get user groups, permissions, and modules
        List<SimpleGrantedAuthority> authoritiesList = user.getAuthorityList();

        return new UserAuthentication(usernameOrEmail, password, authoritiesList, user);
    }
}
