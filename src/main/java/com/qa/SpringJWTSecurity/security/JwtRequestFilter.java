package com.qa.SpringJWTSecurity.security;

import com.qa.SpringJWTSecurity.dtos.user.ProfileDTO;
import com.qa.SpringJWTSecurity.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserService service;

    private JwtUtil jwtUtil;

    public JwtRequestFilter(){
        this.jwtUtil = new JwtUtil();
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Maybe use US spelling if error is thrown.
        final String authorisationHeader = request.getHeader("Authorization");
        System.out.println("HEADER ******************");
        System.out.println(authorisationHeader);
        String email;
        String token;

        if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")){
            token = authorisationHeader.substring(7);
            email = jwtUtil.extractEmail(token);

            System.out.println("EMAIL ******************");
            System.out.println(email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                ProfileDTO user = this.service.getProfileByEmail(email);

                System.out.println("PROFILE ******************");
                System.out.println(user.toString());

                if (jwtUtil.validateToken(token, user)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, null);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
