package com.campingconnecte.camping.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.service.ReservationService;
import com.campingconnecte.camping.service.SiteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sites")
public class SiteController {
    private final SiteService siteService;
    private final ReservationService reservationService; // Ajoutez cette ligne

    @Autowired
    public SiteController(SiteService siteService, ReservationService reservationService) {
        this.siteService = siteService;
        this.reservationService = reservationService; // Injectez ReservationService
   // public SiteController(SiteService siteService) {
    //    this.siteService = siteService;
    }

  /*  @GetMapping
    public String listerSites(Model model) {
        // Récupérez la liste des sites depuis le service
        List<Site> sites = siteService.getAllSites();

        // Ajoutez les sites à l'objet Model pour les afficher dans la vue correspondante
        model.addAttribute("sites", sites);

        return "listeSites"; // Nom de la vue Thymeleaf (listeSites.html)
    }*/
    
 // Afficher la liste des sites
    @GetMapping
    public String listSites(Model model) {
        model
.addAttribute("sites", siteService.getAllSites());
return "listeSites"; // Vue Thymeleaf pour la liste des sites
}
 // Afficher le formulaire pour ajouter un site
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("site", new Site());
        return "addSite"; // Vue Thymeleaf pour ajouter un site
    }

    // Traiter le formulaire d'ajout de site
    @PostMapping("/add")
    public String addSite(@Valid Site site, BindingResult result) {
        if (result.hasErrors()) {
            return "addSite";
        }
        siteService.saveSite(site);
        return "redirect:/sites";
    }

    // Afficher le formulaire de modification de site
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Site site = siteService.getSiteById(id);
        model.addAttribute("site", site);
        return "updateSite"; // Vue Thymeleaf pour modifier un site
    }
    @PostMapping("/update/{id}")
    public String updateSite(@PathVariable("id") int id, @Valid Site site, BindingResult result) {
        if (result.hasErrors()) {
            site.setId(id);
            return "updateSite";
        }

        // Avant la mise à jour, récupérez l'ancienne valeur de prixParNuit
        BigDecimal oldPrice = siteService.getPrixParNuit(id);

        // Mettez à jour le champ prixParNuit
        siteService.updatePrixParNuit(id, site.getPrixParNuit());

        // Vous n'avez pas besoin de mettre à jour les prixTotals des réservations ici

        // Enregistrez les modifications
        siteService.saveSite(site);

        // Redirigez vers la liste des sites après la mise à jour
        return "redirect:/sites";
    }


//Supprimer un site
    @GetMapping("/delete/{id}")
    public String deleteSite(@PathVariable("id") int id) {
    
    	// Placez la logique de vérification ici.
        if (reservationService.siteInReservationTable(id)) {
            if (reservationService.reservationEndDateIsInFuture(id)) {      
    	   
          return "erreurDelete";
            }
        }
        
        // Si la vérification réussit, supprimez le site.
        siteService.deleteSite(id);
        
        // Redirigez l'utilisateur vers une page de confirmation ou une autre vue.
        return "/sites";
    }

} 
    

