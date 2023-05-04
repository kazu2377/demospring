package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Donut;
import com.example.demo.model.DonutDAO;

@Controller
@MultipartConfig
public class Admin {
    
    @Autowired
    private DonutDAO dao;
    
    @GetMapping ("/Admin")
    public String admin (Model model) throws SQLException {
        List<Donut> list = dao.findAll ();
        model.addAttribute ("list", list);
        return "admin/main";
    }
    
    @PostMapping ("/Admin")
    public String doPost (@RequestParam ("name") String name, @RequestParam ("price") int price,
            HttpServletRequest request, @RequestParam ("imgname") MultipartFile imgname, Model model)
            throws IOException, SQLException {
        
        String fileName = imgname.getOriginalFilename ();
        String uploadDir = request.getServletContext ().getRealPath ("/upload");
        Path uploadPath = Paths.get (uploadDir);
        
        Files.createDirectories (uploadPath); // ディレクトリが存在しない場合、作成
        Path filePath = uploadPath.resolve (fileName);
        Files.copy (imgname.getInputStream (), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        DonutDAO dao = new DonutDAO ();
        dao.insertOne (new Donut (name, price, fileName));
        List<Donut> list = dao.findAll ();
        model.addAttribute ("list", list);
        model.addAttribute ("msg", "1件追加しました");
        return "admin/main";
    }
}