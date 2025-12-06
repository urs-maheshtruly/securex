package com.ursmahesh.securex.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProviderPrincipal implements UserDetails {

    private final Provider provider;

    public ProviderPrincipal(Provider provider) {
        this.provider = provider;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){

        return Collections.singleton(new SimpleGrantedAuthority("PROVIDER"));
    }


    @Override
    public String getPassword() {
        return provider.getPassword();  // Return the provider's password
    }

    @Override
    public String getUsername() {
        return provider.getEmail();  // Email is used as the username for providers
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
