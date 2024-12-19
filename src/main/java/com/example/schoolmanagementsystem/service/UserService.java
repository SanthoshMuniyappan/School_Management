package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.UserSignUpRequestDTO;
import com.example.schoolmanagementsystem.entity.UserInfo;
import com.example.schoolmanagementsystem.exception.BadRequestServiceAlertException;
import com.example.schoolmanagementsystem.repository.UserRepository;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService  {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(final PasswordEncoder passwordEncoder, final UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseDTO createUser(final UserSignUpRequestDTO userSignUpRequestDTO) {
        final UserInfo userInfo = UserInfo.builder()
                .userName(userSignUpRequestDTO.getUserName())
                .password(passwordEncoder.encode(userSignUpRequestDTO.getPassword()))
                .email(userSignUpRequestDTO.getEmail())
                .roles(userSignUpRequestDTO.getRoles())
                .build();
        return new ResponseDTO(Constants.CREATED, this.userRepository.save(userInfo), HttpStatus.CREATED.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO updateUser(final String id, final UserSignUpRequestDTO userSignUpRequestDTO) {
        final UserInfo userInfo = this.userRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        if (userSignUpRequestDTO.getUserName() != null) {
            userInfo.setUserName(userSignUpRequestDTO.getUserName());
        }
        if (userSignUpRequestDTO.getPassword() != null) {
            userInfo.setPassword(userSignUpRequestDTO.getPassword());
        }
        if (userSignUpRequestDTO.getRoles() != null) {
            userInfo.setRoles(userSignUpRequestDTO.getRoles());
        }
        return new ResponseDTO(Constants.UPDATED, this.userRepository.save(userInfo), HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO retrieveUser(final String id) {
        final UserInfo userInfo = this.userRepository.findById(id).orElseThrow(() -> new BadRequestServiceAlertException(Constants.ID_NOT_NULL));
        return new ResponseDTO(Constants.RETRIEVED, userInfo, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO retrieveAll() {
        final List<UserInfo> users = this.userRepository.findAll();
        return new ResponseDTO(Constants.RETRIEVED, users, HttpStatus.OK.getReasonPhrase());
    }

    @Transactional
    public ResponseDTO removeUser(final String id) {
        if (this.userRepository.existsById(id)) {
            throw new BadRequestServiceAlertException(Constants.ID_NOT_NULL);
        }
        this.userRepository.deleteById(id);
        return new ResponseDTO(Constants.REMOVED, id, HttpStatus.OK.getReasonPhrase());
    }
}
