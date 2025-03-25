package codingtechniques.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import codingtechniques.model.Employee;
import codingtechniques.service.EmailService;
import codingtechniques.service.EmployeeService;
import codingtechniques.service.OTPService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;
    @GetMapping("/employees")
    public String employees(Model model) {
        model.addAttribute("employees", employeeService.findEmployees());
        return "employees"; 
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("employee", new Employee());
        return "registration"; 
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(Model model, @PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id); 
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get()); 
            return "updateForm"; 
        } else {
            return "redirect:/employees"; 
        }
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
    
    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/admin/login"; 
    }

    @PostMapping("/user/login")
    public String handleAdminLogin(
            @RequestParam String username,
            @RequestParam String email,
            Model model,
            HttpSession session) {

        
        List<Employee> employee = employeeService.findByFirstname(username);

       
        if (!employee.isEmpty() && employee.get(0).getEmail().equals(email)) {
        	 String otp = otpService.generateOTP(); 
             otpService.storeOTP(email, otp); 
             emailService.sendOTPEmail(email, otp); 

             session.setAttribute("email", email);
             session.setAttribute("otp", otp); 
             
             return "otp-verification"; 
        } else {
            model.addAttribute("error", "Invalid username or email");
            return "userlogin";
        }
    }

    
    @PostMapping("/user/verify-otp")
    public String handleOTPVerification(
            @RequestParam String otp,
            HttpSession session,
            Model model) {

        String email = (String) session.getAttribute("email");

        if (email != null && otpService.validateOTP(email, otp)) {
            session.removeAttribute("email"); 
            session.setAttribute("user", employeeService.findByemail(email).get(0)); // Store user in session
            return "redirect:/user/home"; 
        } else {
            model.addAttribute("error", "Invalid OTP");
            return "otp-verification"; 
        }
    }

    @GetMapping("/user/home")
    public String userHome(HttpSession session, Model model) {
        Employee user = (Employee) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "userhome"; 
        } else {
            return "redirect:/user/login"; 
        }
    }
    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
     
        Employee user = (Employee) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user); 
            return "profile"; 
        } else {
            return "redirect:/user/login"; 
        }
    }
}
