package com.example.SpringJwtStudyOnYoutube.controller;


import com.example.SpringJwtStudyOnYoutube.dto.request.JoinDto;
import com.example.SpringJwtStudyOnYoutube.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDto joinDto) {
        joinService.joinProcess(joinDto);
        return "ok";
        //401, 402, 404 등등..
    }
}

