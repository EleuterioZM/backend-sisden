/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration.security;


import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class UserAuthentication extends UsernamePasswordAuthenticationToken {
    private User user;

    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, User user) {
        super(principal, credentials, authorities);
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserAuthentication{" +
                "principal=" + this.getPrincipal() +
                ", details=" + this.getDetails() +
                ", authorities=" + this.getAuthorities() +
                ", user=" + user +
                '}';
    }
}
