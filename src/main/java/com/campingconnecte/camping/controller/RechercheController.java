package com.campingconnecte.camping.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.campingconnecte.camping.dto.RechercheSiteDTO;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.service.SiteService;
import java.util.List;
//ajout
import java.text.SimpleDateFormat;
import java.util.Locale;

@Controller
//@RequestMapping("/recherche")
@RequestMapping("/")
public class RechercheController {

    private final SiteService siteService;

    @Autowired
    public RechercheController(SiteService siteService) {
        this.siteService = siteService;    
    }

    @GetMapping
    public String afficherFormulaireRecherche(Model model) {
        model.addAttribute("rechercheSiteDTO", new RechercheSiteDTO());
        return "formulaireRecherche";
    }
  
    @PostMapping
    public String effectuerRecherche(@ModelAttribute RechercheSiteDTO rechercheSiteDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Gérer les erreurs de validation ici
            return "formulaireRecherche";
        }
        List<Site> sitesDisponibles = siteService.findAvailableSites(
            rechercheSiteDTO.getDateDebut(), 
            rechercheSiteDTO.getDateFin(), 
            rechercheSiteDTO.getServices(),
            rechercheSiteDTO.getLongueurEquipement());
        // Ajouter les sites et les dates au modèle
        model.addAttribute("sitesDisponibles", sitesDisponibles);
        model.addAttribute("dateDebut", rechercheSiteDTO.getDateDebut());
        model.addAttribute("dateFin", rechercheSiteDTO.getDateFin());
        return "resultatsRecherche";
    }
}
   