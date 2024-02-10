package com.chatsync.chatSyncBackend.config.jwtConfig;

import com.chatsync.chatSyncBackend.service.Impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class jwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        System.out.println(requestTokenHeader);

        String username = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            // token is passed

            try{
                jwtToken = requestTokenHeader.substring(7);
                username = this.jwtUtils.extractUsername(jwtToken);
            }catch (ExpiredJwtException e){
                System.out.println("Jwt token has expired!!");
                e.printStackTrace();
            }catch (Exception e){
                System.out.println("Exception : ");
                e.printStackTrace();
            }

            // validate token
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if(this.jwtUtils.validateToken(jwtToken, userDetails)){
                    // token is valid
                    UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
                }
            }else{
                System.out.println("Token is not valid!!!");
            }

        }else{
            System.out.println("Jwt Token not passed or not valid!!");
        }

        filterChain.doFilter(request, response);
    }
}
