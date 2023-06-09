package com.example.demo;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {
    
    @GetMapping ("/greeting")
    public String greetingForm (Model model) throws SQLException {
        
        return "greeting";
    }
    
    @PostMapping ("/greeting")
    public String greetingSubmit () {
        return "result";
    }
    
}
