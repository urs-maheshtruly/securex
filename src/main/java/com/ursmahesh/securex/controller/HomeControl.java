package com.ursmahesh.securex.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeControl {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "hello this is your session id" + request.getSession().getId();
    }
}
