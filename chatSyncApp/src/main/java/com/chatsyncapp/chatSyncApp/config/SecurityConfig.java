package com.chatsyncapp.chatSyncApp.config;

import com.chatsyncapp.chatSyncApp.service.UserService;
import com.chatsyncapp.chatSyncApp.service.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserServiceImpl userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(
                                        "/registration**",
                                        "/js/**",
                                        "/css/**",
                                        "/img/**",
                                        "/webjars/**",
                                        "/checkSession/**",  // added to test user session is active or not
                                        "/api/v1/**"
                                ).permitAll().anyRequest().authenticated()
                )
                .formLogin(loginConfigure ->
                        loginConfigure
                                .loginPage("/login")
                                .successHandler((request, response, authentication) -> {
                                    User user = (User) authentication.getPrincipal();
                                    response.addCookie(new Cookie("loggedInUsername", user.getUsername()));
                                    response.addCookie(new Cookie("loggedInUserId", this.userService.getLoggedInUserId()));
                                    response.sendRedirect("/ChatSync/");
                                })
                                .permitAll()
                )
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .deleteCookies("loggedInUserId", "loggedInUsername")
                                .permitAll()
                );
        return http.build();
    }
}
