package it.unisalento.pas2425.positionservice.service;

import it.unisalento.pas2425.positionservice.domain.User;
import it.unisalento.pas2425.positionservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
                                                                                                      //.get(). perchè usiamo un Optional
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.get().getEmail()).password(user.get().getPassword()).roles(user.get().getRole().name()).build();

        return userDetails;
    }
}
