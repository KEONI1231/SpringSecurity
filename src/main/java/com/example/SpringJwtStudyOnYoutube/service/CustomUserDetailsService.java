package com.example.SpringJwtStudyOnYoutube.service;

import com.example.SpringJwtStudyOnYoutube.domain.Users;
import com.example.SpringJwtStudyOnYoutube.dto.security.CustomUserDetails;
import com.example.SpringJwtStudyOnYoutube.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users userData = userRepository.findByUserName(username);
        System.out.println("디비 접근은 성공 : " + userData.getUserName());
        if(userData != null) {
            return new CustomUserDetails(userData);

        }

        return null;
    }
}
