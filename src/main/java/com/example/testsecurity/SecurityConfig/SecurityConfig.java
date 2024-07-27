package com.example.testsecurity.SecurityConfig;

import com.example.testsecurity.Services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;


    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;

    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(personDetailsService)
                .passwordEncoder(passwordEncoder());
        http
                .authorizeHttpRequests(auth -> auth.requestMatchers("/adminPage").hasRole("ADMIN")
                        .requestMatchers("/loginPage","/error","/register","Styles/registerPage.css")
                        .permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN"))
                .formLogin(formLogin -> formLogin.loginPage("/loginPage")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/MyPage", true)
                        .failureUrl("/loginPage?error"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/loginPage"));





        return http.build();
    }
}
