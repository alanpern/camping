package com.campingconnecte.camping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.campingconnecte.camping.service.UserService;
import com.campingconnecte.camping.service.ReservationService;



import org.springframework.stereotype.Controller;

import com.campingconnecte.camping.model.Admin;
import com.campingconnecte.camping.repository.AdminRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class AdminLoginController {
	
	@GetMapping("/adminLogin.html")
    public String adminLoginPage() {
        return "adminLogin"; // Assurez-vous que "adminLogin" est le nom de votre modèle Thymeleaf
    }
	
	@GetMapping("/admAccueil")
    public String showAdmAccueilPage() {
        // logique éventuelle
        return "admAccueil";
    }

    @Autowired
    private AdminRepository adminRepository; // Injectez votre repository pour la table admin

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String adminLogin(@RequestParam String username, @RequestParam String password, Model model) {

        // Recherchez l'administrateur par nom d'utilisateur
        Admin admin = adminRepository.findByUsername(username);

        if (admin != null) {
            // Vérifiez le mot de passe
            if (admin.getPassword().equals(password)) {
                // Authentification réussie, redirigez vers le tableau de bord de l'administrateur        	              
                              
              // Ajoutez d'autres données nécessaires
           //	return "dataDisplay";
            	
            	return "admAccueil";

            }
        }

        // En cas d'erreur d'authentification, affichez un message d'erreur
        model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
        return "adminLogin";
    }
}
