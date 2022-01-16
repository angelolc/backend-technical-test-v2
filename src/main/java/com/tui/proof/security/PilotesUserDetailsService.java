package com.tui.proof.security;

import com.tui.proof.model.User;
import com.tui.proof.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class PilotesUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findOne(Example.of(new User().setUsername(username)));
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return new PilotesUserDetails(optionalUser.get());
    }
}
