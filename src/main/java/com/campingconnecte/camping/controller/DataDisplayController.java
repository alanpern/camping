package com.campingconnecte.camping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.campingconnecte.camping.service.UserService;
import com.campingconnecte.camping.service.ReservationService;
import com.campingconnecte.camping.service.SiteService;

@Controller
public class DataDisplayController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataDisplayController.class);

    private final UserService userService;
    private final ReservationService reservationService;
    private final SiteService siteService;

    @Autowired
    public DataDisplayController(UserService userService, ReservationService reservationService, SiteService siteService) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.siteService = siteService;
    }

    @GetMapping("/dataDisplay")
    public String showData(Model model) {
     logger.info("Entrée dans la méthode showData");
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("reservations", reservationService.getAllReservations());
        model.addAttribute("sites", siteService.getAllSites());
        return "dataDisplay"; // Nom de votre fichier vue Thymeleaf
    }
}
