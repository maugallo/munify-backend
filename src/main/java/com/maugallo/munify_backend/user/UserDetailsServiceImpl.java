package com.maugallo.munify_backend.user;

import com.maugallo.munify_backend.citizen.Citizen;
import com.maugallo.munify_backend.employee.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(getRole(user))
                .build();
    }

    private String getRole(User user) {
        if (user instanceof Employee) {
            return ((Employee) user).getRole();
        }
        if (user instanceof Citizen) {
            return "ROLE_CIUDADANO";
        }
        return "ROLE_USUARIO";
    }

}
