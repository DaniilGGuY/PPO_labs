package com.example.ppo.gui;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import com.example.ppo.client.Client;
import com.example.ppo.client.ClientService;
import com.example.ppo.order.OrderService;
import com.example.ppo.order.Order;
import jakarta.servlet.http.HttpSession;


@Controller
public class ClientAccountController {
    
    private final ClientService clientService;
    private final OrderService orderService;

    public ClientAccountController(ClientService clientService, OrderService orderService) {
        this.clientService = clientService;
        this.orderService = orderService;
    }

    @GetMapping("/client/account")
    public String showAccountPage(HttpSession session, Model model) {
        String login = (String) session.getAttribute("name");       
        Client client = clientService.getClientByLogin(login);
        List<Order> orders = orderService.getByClient(client);
        
        model.addAttribute("client", client);
        model.addAttribute("orders", orders);
        model.addAttribute("login", login);

        return "client";
    }

    @PostMapping("/client/account/update")
    public String updateProfile(@ModelAttribute Client updatedClient, HttpSession session) {
        String login = (String) session.getAttribute("name");  
        Client client = clientService.getClientByLogin(login);

        if (updatedClient.getFio() != null) {
            client.setFio(updatedClient.getFio());
        }
        
        if (updatedClient.getEmail() != null) {
            client.setEmail(updatedClient.getEmail());
        }
        
        if (updatedClient.getTel() != null) {
            client.setTel(updatedClient.getTel());
        }
        
        if (updatedClient.getCompany() != null) {
            client.setCompany(updatedClient.getCompany());
        }
        
        if (updatedClient.getPassword() != null && !updatedClient.getPassword().isBlank()) {
            client.setPassword(updatedClient.getPassword());
        }
        
        clientService.updateClient(client);
        return "redirect:/client/account";
    }
}
