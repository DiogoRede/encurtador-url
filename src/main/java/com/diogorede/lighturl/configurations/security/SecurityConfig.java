package com.diogorede.lighturl.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf -> csrf.disable())
                   .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/auth/**").permitAll()
                                                .anyRequest().authenticated()
                                            )
                    .formLogin(
                        form -> form
                            .loginPage("/auth/login")
                            .defaultSuccessUrl("/")
                            .failureUrl("/auth/login-error")
                            .permitAll()
                    )                   
                    .logout(logout -> logout
                                        .logoutUrl("/auth/logout")
                                        .logoutSuccessUrl("/")
                                        .permitAll()
                    ) 
                   .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

        // Abaixo é o do curso, mas na documentação está na indicando para seguir a de cima
        // return http.getSharedObject(AuthenticationManagerBuilder.class)
        //            .userDetailsService(usuarioService)
        //            .passwordEncoder(passwordEncoder)
        //            .and()
        //            .build(); 
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}