package com.chatsyncapp.chatSyncApp.config;

import com.chatsyncapp.chatSyncApp.utils.RequestContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ThymeleafConfig {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ServletContext servletContext;

    public ThymeleafConfig(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
    }

    @Bean
    @RequestScope
    public RequestContext requestContext() {
        return new RequestContext(request, response, servletContext);
    }
}
