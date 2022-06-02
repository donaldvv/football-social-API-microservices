package com.don.usersservice.service.impl;

import com.don.usersservice.dto.UserDetailsImpl;
import com.don.usersservice.model.User;
import com.don.usersservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Donald Veizi
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with the email: %s", email)));

        // will need id, so better to have an Implementation of the UserDetails
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                getAuthority(user)
        );
    }

    private Collection<SimpleGrantedAuthority> getAuthority(final User user) {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
    }

}
