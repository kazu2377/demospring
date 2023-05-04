package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Donut;
import com.example.demo.model.DonutDAO;

@Controller
@RequestMapping ("/Admin")
public class AdminUpdate {
    @Autowired
    private ResourceLoader resourceLoader;
    
    @GetMapping ("/Update")
    public String showUpdateForm (@RequestParam ("id") int id, Model model) throws SQLException {
        DonutDAO dao = new DonutDAO ();
        Donut donut = dao.findOne (id);
        model.addAttribute ("donut", donut);
        return "admin/update";
    }
    
    @PostMapping ("/Update")
    public String doPost (@RequestParam ("id") String id, @RequestParam ("name") String name,
            @RequestParam ("price") int price, @RequestParam ("orgname") String orgname, HttpServletRequest request,
            @RequestParam ("imgname") MultipartFile imgname, Model model, RedirectAttributes attributes)
            throws IOException, SQLException, URISyntaxException {
        
        // InputStream is = new ClassPathResource ("static").getInputStream ();
        
        String fileName = imgname.getOriginalFilename ();
        
        String filepath = "static/upload"; // src/main/resources 配下の相対パス
        Resource resource = resourceLoader.getResource ("classpath:" + filepath);
        
        File file = resource.getFile ();
        System.out.println ("file.exists(): " + file.exists ());
        System.out.println ("file.getAbsolutePath(): " + file.getAbsolutePath ());
        
        Path uploadPath = Paths.get (file.getAbsolutePath ());
        Path targetPath = uploadPath.resolve (fileName); // ファイル名を追加
        
        if (fileName == null || fileName.isEmpty ()) {
            fileName = orgname; // 任意のデフォルトファイル名
        } else {
            Files.copy (imgname.getInputStream (), targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        DonutDAO dao = new DonutDAO ();
        dao.updateOne (new Donut (Integer.parseInt (id), name, price, fileName));
        // List<Donut> list = dao.findAll ();
        // model.addAttribute ("list", list);
        // model.addAttribute ("msg", "1件更新しました");
        
        attributes.addFlashAttribute ("msg", "一件削除しました!");
        return "redirect:/Admin";
        // return "redirect:/admin/main";
        
        // return "admin/main";
    }
}