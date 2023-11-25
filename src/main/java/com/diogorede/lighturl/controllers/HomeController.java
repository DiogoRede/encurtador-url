package com.diogorede.lighturl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping({"/", "/home", "/index"})
    public String home(){
        return "index";
    }

}
