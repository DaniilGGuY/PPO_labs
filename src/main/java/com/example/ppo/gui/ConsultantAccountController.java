package com.example.ppo.gui;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.example.ppo.consultant.Consultant;
import com.example.ppo.consultant.ConsultantService;
import com.example.ppo.order.OrderService;
import com.example.ppo.order.Order;
import jakarta.servlet.http.HttpSession;

@Controller
public class ConsultantAccountController {
    
    private final ConsultantService consultantService;
    private final OrderService orderService;

    public ConsultantAccountController(ConsultantService consultantService, OrderService orderService) {
        this.consultantService = consultantService;
        this.orderService = orderService;
    }

    @GetMapping("/consultant/account")
    public String showAccountPage(HttpSession session, Model model) {
        String login = (String) session.getAttribute("name");       
        Consultant consultant = consultantService.getConsultantByLogin(login);
        List<Order> orders = orderService.getAllOrders();
        
        model.addAttribute("consultant", consultant);
        model.addAttribute("orders", orders);
        model.addAttribute("login", login);

        return "consultant";
    }

    @PostMapping("/consultant/account/update")
    public String updateConsultantProfile(@ModelAttribute Consultant updatedConsultant, HttpSession session) {
        String login = (String) session.getAttribute("login");
        Consultant consultant = consultantService.getConsultantByLogin(login);
        

        consultant.setAbout(updatedConsultant.getAbout());

        if (updatedConsultant.getFio() != null) {
            consultant.setFio(updatedConsultant.getFio());
        }
        
        if (updatedConsultant.getEmail() != null) {
            consultant.setEmail(updatedConsultant.getEmail());
        }
        
        if (updatedConsultant.getTel() != null) {
            consultant.setTel(updatedConsultant.getTel());
        }
        
        if (updatedConsultant.getAge() != null && updatedConsultant.getAge() >= 18) {
            consultant.setAge(updatedConsultant.getAge());
        }
        
        if (updatedConsultant.getExperience() != null && updatedConsultant.getExperience() >= 0) {
            consultant.setExperience(updatedConsultant.getExperience());
        }

        if (updatedConsultant.getSpecialization() != null) {
            consultant.setSpecialization(updatedConsultant.getSpecialization());
        }

        if (updatedConsultant.getPassword() != null && !updatedConsultant.getPassword().isBlank()) {
            consultant.setPassword(updatedConsultant.getPassword());
        }
        
        consultantService.updateConsultant(consultant);
        return "redirect:/consultant/account";
    }
}
