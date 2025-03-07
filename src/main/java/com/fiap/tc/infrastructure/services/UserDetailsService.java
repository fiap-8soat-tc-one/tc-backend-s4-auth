package com.fiap.tc.infrastructure.services;

import com.fiap.tc.infrastructure.core.security.models.SystemUser;
import com.fiap.tc.infrastructure.persistence.repositories.UserRepository;
import com.fiap.tc.infrastructure.persistence.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByLogin(login);
        UserEntity user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User Authentication Error"));
        return new SystemUser(user, getRoles(user));
    }

    private Collection<? extends GrantedAuthority> getRoles(UserEntity user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        Collection<String> roles = userRepository.getRoles(user.getId());
        roles.forEach(perm -> authorities.add(new SimpleGrantedAuthority(perm)));
        return authorities;
    }

}
