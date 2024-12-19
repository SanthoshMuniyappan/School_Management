package com.example.schoolmanagementsystem.controller;

import com.example.schoolmanagementsystem.dto.ResponseDTO;
import com.example.schoolmanagementsystem.dto.UserSignInRequestDTO;
import com.example.schoolmanagementsystem.dto.UserSignUpRequestDTO;
import com.example.schoolmanagementsystem.service.JwtService;
import com.example.schoolmanagementsystem.service.UserService;
import com.example.schoolmanagementsystem.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api/user")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserController(final UserService userService, JwtService jwtService, final AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody final UserSignUpRequestDTO userSignUpRequestDTO) {
        return this.userService.createUser(userSignUpRequestDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable final String id, @RequestBody final UserSignUpRequestDTO userSignUpRequestDTO) {
        return this.userService.updateUser(id, userSignUpRequestDTO);
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO retrieve(@PathVariable final String id) {
        return this.userService.retrieveUser(id);
    }

    @GetMapping("/retrieve-all")
    public ResponseDTO retrieveAll() {
        return this.userService.retrieveAll();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO remove(@PathVariable final String id) {
        return this.userService.removeUser(id);
    }

    @PostMapping("/authenticate")
    public ResponseDTO authenticateAndGetToken(@RequestBody final UserSignInRequestDTO userSignInRequestDTO){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSignInRequestDTO.getUserName(),userSignInRequestDTO.getPassword()));
        if (authentication.isAuthenticated()){
            String token=jwtService.generateToken(userSignInRequestDTO.getUserName());
            return new ResponseDTO(Constants.TOKEN,token, HttpStatus.OK.getReasonPhrase());
        }else{
            throw new UsernameNotFoundException(Constants.GIVE_VALID_USER);
        }
    }
}
