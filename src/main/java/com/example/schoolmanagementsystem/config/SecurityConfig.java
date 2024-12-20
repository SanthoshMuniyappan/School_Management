package com.example.schoolmanagementsystem.config;

import com.example.schoolmanagementsystem.filter.JwtAuthFilter;
import com.example.schoolmanagementsystem.repository.UserRepository;
import com.example.schoolmanagementsystem.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoUserDetailsService(userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/api/user/**").permitAll()
                    .requestMatchers("/api/school/**").hasAuthority("ROLE_SUPER_ADMIN")
                        .requestMatchers("/api/standard/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/section/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/teacher/**").hasAuthority("ROLE_HR")
                        .requestMatchers("/api/student/**").hasAuthority("ROLE_OFFICE_ADMIN")
                        .requestMatchers("/api/teacherSection/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/subject/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/exam/**").hasAuthority("ROLE_EOC")
                        .requestMatchers("/api/fees/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/mark/**").hasAuthority("ROLE_TEACHER"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
