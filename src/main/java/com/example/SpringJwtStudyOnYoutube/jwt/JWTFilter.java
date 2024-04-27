package com.example.SpringJwtStudyOnYoutube.jwt;

import com.example.SpringJwtStudyOnYoutube.domain.Users;
import com.example.SpringJwtStudyOnYoutube.dto.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // request 에서 Authorization 헤더를 찾음
        String authorizations = request.getHeader("Authorization");

        if (authorizations == null || !authorizations.startsWith("Bearer ")){
            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return ;
        }

        String token = authorizations.split(" ")[1];
        if(jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return ;
        }

        String userName = jwtUtil.getUserName(token);
        String role = jwtUtil.getRole(token);
        Users users = Users.jwtSetter(userName, "tempPassword", role);

        CustomUserDetails customUserDetails = new CustomUserDetails(users);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

}
