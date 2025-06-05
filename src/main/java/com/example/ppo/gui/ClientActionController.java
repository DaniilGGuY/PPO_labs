package com.example.ppo.gui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.consultant.ConsultantService;
import com.example.ppo.product.Product;
import com.example.ppo.review.Review;
import com.example.ppo.review.ReviewService;
import jakarta.servlet.http.HttpSession;
import com.example.ppo.product.ProductService;

@Controller
public class ClientActionController {
    private final ProductService productService;
    private final ConsultantService consultantService;
    private final ReviewService reviewService;

    public ClientActionController(ProductService productService, 
                        ConsultantService consultantService,
                        ReviewService reviewService) {
        this.productService = productService;
        this.consultantService = consultantService;
        this.reviewService = reviewService;
    }

    @GetMapping("/client")
    public String showMainPage(Model model, HttpSession session) {
        String login = (String)session.getAttribute("name");
        List<Product> services = productService.getAllProducts();
        List<Consultant> consultants = consultantService.findAllConsultants();
        List<Review> reviews = reviewService.getAllReviews();
        List<String> clientsLogin = new ArrayList<>();
        for (Review r : reviews) {
            Client client = r.getClient();
            if (client == null) {
                clientsLogin.add(r.getClientRef());
            }
            else {
                clientsLogin.add(client.getLogin());
            }
        }

        model.addAttribute("login", login);
        model.addAttribute("services", services);
        model.addAttribute("consultants", consultants);
        model.addAttribute("reviews", reviews);
        model.addAttribute("clients", clientsLogin);
               
        return "client-main";
    }
}
