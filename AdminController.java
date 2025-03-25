package codingtechniques.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

   
    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "adminlogin"; 
    }

    @GetMapping("/user/login")
    public String showUserLoginPage() {
        return "userlogin";
    }
   
    @PostMapping("/admin/login")
    public String handleAdminLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

      
        if ("admin".equals(username) && "admin".equals(password)) {
            return "redirect:/admin/home"; 
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "adminlogin"; 
        }
    }

    
    @GetMapping("/admin/home")
    public String showAdminHomePage() {
        return "adminhome"; 
    }
}