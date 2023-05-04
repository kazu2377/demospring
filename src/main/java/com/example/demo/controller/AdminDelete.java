package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.DonutDAO;

@Controller
@RequestMapping ("/Admin")
public class AdminDelete {
    @Autowired
    private DonutDAO dao;
    
    @GetMapping ("/Delete")
    public String doDelete (@RequestParam ("id") String id1, Model model, RedirectAttributes attributes)
            throws ServletException, IOException, NumberFormatException, SQLException {
        if (id1 != null && !id1.isEmpty ()) {
            dao.deleteOne (Integer.parseInt (id1));
            attributes.addFlashAttribute ("msg", "一件削除しました!");
        }
        
        return "redirect:/Admin";
        
    }
}
