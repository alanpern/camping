package com.campingconnecte.camping.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campingconnecte.camping.model.Reservation;

@Controller
public class PaymentController {
    @GetMapping("/payment.html")
    public String showPaymentPage(
            Model model,
            @RequestParam(name = "prixTotal") BigDecimal prixTotal
    ) {
        // Vous pouvez ajouter des données au modèle si nécessaire
        // Par exemple, le montant à payer
        model.addAttribute("prixTotal", prixTotal);

        return "payment"; // Nom de la vue Thymeleaf (payment.html)
    }

    @PostMapping("/_processpayment")
    public String processPayment(
            @RequestParam("montant") Double montant,
            @RequestParam("nomCarte") String nomCarte,
            @RequestParam("numCarte") String numCarte,
            @RequestParam("dateExpiration") String dateExpiration,
            @RequestParam("cvv") String cvv
    ) {
        return "redirect:/payment/confirmation";
    }

    @GetMapping("/payment")
    public String showPaymentConfirmationPage() {
    	 return "payment"; 
    }
}
