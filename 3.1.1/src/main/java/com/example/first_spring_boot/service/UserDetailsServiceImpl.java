package com.example.first_spring_boot.service;

import com.example.first_spring_boot.model.Role;
import com.example.first_spring_boot.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        com.example.first_spring_boot.model.User user = userRepository.getUserByEmail(name);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrandtedAuthority(user));
    }

    private Collection<GrantedAuthority> getGrandtedAuthority(com.example.first_spring_boot.model.User user){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : user.getRoles()){
            if(role.getName().equalsIgnoreCase("role_admin")){
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}
