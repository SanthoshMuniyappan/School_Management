package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.UserInfoUserDetailsDTO;
import com.example.schoolmanagementsystem.entity.UserInfo;
import com.example.schoolmanagementsystem.repository.UserRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserInfoUserDetailsService(final UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepository.findByUserName(userName);
        return userInfo.map(UserInfoUserDetailsDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USER_NOT_FOUND + userName));
    }
}
