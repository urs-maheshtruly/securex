package com.ursmahesh.securex.service;

import com.ursmahesh.securex.model.Users;
import com.ursmahesh.securex.model.Provider;
import com.ursmahesh.securex.model.UserPrincipal;
import com.ursmahesh.securex.model.ProviderPrincipal;
import com.ursmahesh.securex.repo.UsersRepo;
import com.ursmahesh.securex.repo.ProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private ProviderRepo providerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to load from Users table
        Users user = usersRepo.findByUsername(username);
        if (user != null) {
            return new UserPrincipal(user); // Custom principal for Users
        }

        // Try to load from Providers table using email
        Optional<Provider> providerOpt = providerRepo.findByEmail(username);
        if (providerOpt.isPresent()) {
            return new ProviderPrincipal(providerOpt.get()); // Custom principal for Providers
        }

        // If not found in either
        throw new UsernameNotFoundException("User or Provider not found with username/email: " + username);
    }
}
