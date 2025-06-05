package com.example.ppo.gui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import com.example.ppo.manager.Manager;
import com.example.ppo.manager.ManagerService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ManagerAccountController {
    
    private final ManagerService managerService;
    //private final ProductService productService;

    ManagerAccountController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/manager/account")
    public String managerAccount(Model model, HttpSession session) {
        String login = (String) session.getAttribute("name");
        Manager manager = managerService.getManagerByLogin(login);
        model.addAttribute("manager", manager);
        model.addAttribute("login", login);
        
        return "manager";
    }
    
    @PostMapping("/manager/account/update")
    public String updateManagerProfile(@ModelAttribute Manager updatedManager, HttpSession session) {
        String login = (String) session.getAttribute("name");
        Manager manager = managerService.getManagerByLogin(login);
        
        
        if (updatedManager.getFio() != null) {
            manager.setFio(updatedManager.getFio());
        }
        
        if (updatedManager.getEmail() != null) {
            manager.setEmail(updatedManager.getEmail());
        }
        
        if (updatedManager.getTel() != null) {
            manager.setTel(updatedManager.getTel());
        }
        
        if (updatedManager.getPassword() != null && !updatedManager.getPassword().isBlank()) {
            manager.setPassword(updatedManager.getPassword());
        }

        return "redirect:/manager/account";
    }
}