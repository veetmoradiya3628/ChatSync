package com.chatsyncapp.chatSyncApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/login", "/css/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
        ).formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
        ).logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));
        return http.build();
    }
}
