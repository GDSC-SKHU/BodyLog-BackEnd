package com.solutionchallenge.bodylog.controller;

import com.solutionchallenge.bodylog.domain.DTO.LoginDTO;
import com.solutionchallenge.bodylog.domain.DTO.TokenDTO;
import com.solutionchallenge.bodylog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginRequestDTO) {
        return memberService.login(loginRequestDTO);
    }
}