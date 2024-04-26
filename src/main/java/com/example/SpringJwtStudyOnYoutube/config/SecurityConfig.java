package com.example.SpringJwtStudyOnYoutube.config;

import com.example.SpringJwtStudyOnYoutube.jwt.JWTUtil;
import com.example.SpringJwtStudyOnYoutube.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain fillterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable); //세션을 STATLESS로 관리하기 때문에 방어 전략 필요 없음.
        http
                .formLogin(AbstractHttpConfigurer::disable);
        http
                .httpBasic(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join", "/test").permitAll() //모든 권한 허용.
                        .requestMatchers("/admin").hasRole("Admin") // amdin url엔 Admin 역할 가진 사용자만 접근 가능.
                        .anyRequest().authenticated());

        //세션 설정 : jwt에서는 세션을 항상 statless 상태로 관리하게 된다.
        http //중요!!
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
       /* return http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/login", "/","/join").permitAll()
                        .requestMatchers("/admin").hasRole("Admin")
                        .anyRequest().authenticated()).getOrBuild();*/
    }
}
