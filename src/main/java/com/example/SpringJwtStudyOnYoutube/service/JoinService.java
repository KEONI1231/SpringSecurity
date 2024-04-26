package com.example.SpringJwtStudyOnYoutube.service;

import com.example.SpringJwtStudyOnYoutube.domain.Users;
import com.example.SpringJwtStudyOnYoutube.dto.request.JoinDto;
import com.example.SpringJwtStudyOnYoutube.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void joinProcess(JoinDto joinDto) {
        String username = joinDto.userName();
        String password = joinDto.password();
        String encodePassword = bCryptPasswordEncoder.encode(password);
        System.out.println(username);
        System.out.println(password);
        Boolean isExist = userRepository.existsByUserName(username);

        if(isExist) {
            return ;
        }

        Users users = Users.joinUser(username, encodePassword, "ROLE_ADMIN");

        userRepository.save(users);
    }
}
