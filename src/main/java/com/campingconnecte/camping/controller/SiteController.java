package com.campingconnecte.camping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.service.SiteService;

@Controller
@RequestMapping("/sites")
public class SiteController {
    private final SiteService siteService;

    @Autowired
    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @GetMapping
    public String listerSites(Model model) {
        // Récupérez la liste des sites depuis le service
        List<Site> sites = siteService.getAllSites();

        // Ajoutez les sites à l'objet Model pour les afficher dans la vue correspondante
        model.addAttribute("sites", sites);

        return "listeSites"; // Nom de la vue Thymeleaf (listeSites.html)
    }
}
