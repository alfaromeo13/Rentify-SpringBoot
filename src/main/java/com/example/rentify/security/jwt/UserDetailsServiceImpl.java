package com.example.rentify.security.jwt;

import com.example.rentify.entity.User;
import com.example.rentify.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        //first we find user with his roles
        User user = userRepository.findByUsername(username);
        if (user != null) { //if that user exists we get his roles
            List<GrantedAuthority> grantedAuthorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList()); // (ROLE_ADMIN, ROLE_REGISTERED)
            return new org.springframework.security.core.userdetails.User(
                    username,
                    user.getPassword(),
                    grantedAuthorities
            );
        } else throw new UsernameNotFoundException("User with given username not found!");
    }
}