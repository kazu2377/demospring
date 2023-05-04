package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Donut;
import com.example.demo.model.DonutDAO;

@Controller
public class Main {
    @Autowired
    private DonutDAO dao;
    
    @GetMapping ("/Main")
    public String showMainPage (Model model) throws SQLException {
        List<Donut> list = dao.findAll ();
        model.addAttribute ("list", list);
        return "view/main";
    }
}
