package com.example.ppo.gui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ppo.client.*;
import com.example.ppo.consultant.ConsultantService;
import com.example.ppo.manager.ManagerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    private final ClientService clientService;
    private final ConsultantService consultantService;
    private final ManagerService managerService;

    AuthController(ClientService clientService, ConsultantService consultantService, ManagerService managerService) {
        this.clientService = clientService;
        this.consultantService = consultantService;
        this.managerService = managerService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth";
    }
    
    @GetMapping("/register")
    public String showRegisterPage() {
        return "auth";
    }

    @PostMapping("/register")
    public String registerClient(@ModelAttribute Client client, HttpSession session) {
        try {
            client = clientService.registerClient(client);
            session.setAttribute("name", client.getLogin());
            return "redirect:/clients";
        } catch (Exception e) {
            return "auth";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String login, @RequestParam String password, HttpSession session) {
        try {
            if (clientService.clientExists(login)) {
                if (clientService.getClientByLogin(login).getPassword().equals(password)) {
                    session.setAttribute("name", login);
                    return "redirect:/client";
                }
            }
            else if (consultantService.consultantExists(login)) {
                if (consultantService.getConsultantByLogin(login).getPassword().equals(password)) {
                    session.setAttribute("name", login);
                    return "redirect:/consultant/account";
                }
            }
            else if (managerService.managerExists(login)) {
                if (managerService.getManagerByLogin(login).getPassword().equals(password)) {
                    session.setAttribute("name", login);
                    return "redirect:/manager/account";
                }
            }
            return "auth";
        } catch (Exception e) {
            return "auth";
        }
    }

}
