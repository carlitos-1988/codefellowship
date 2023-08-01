package com.codefellowship.start.configs;

import com.codefellowship.start.models.ApplicationUser;
import com.codefellowship.start.repositories.CodeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    CodeUserRepository codeUserRepository;


    @Override
    public ApplicationUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        return codeUserRepository.findByUserName(userName);
    }
}
