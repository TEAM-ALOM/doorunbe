package com.alom.dorundorunbe.domain.auth.dto;

import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class PrincipalUserDetails implements UserDetails, OAuth2User {

    private final User principalUser;
    private final Map<String, Object> attributes;

    public PrincipalUserDetails(User user) {
        this(user, null);
    }

    public PrincipalUserDetails(User user, Map<String, Object> attributes) {
        this.principalUser = user;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(principalUser.getId());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return principalUser.getEmail();
    }
}
