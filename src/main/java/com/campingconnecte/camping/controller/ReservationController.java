package com.campingconnecte.camping.controller;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.hibernate.mapping.List;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.service.ReservationService;

import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.service.SiteService;
import com.campingconnecte.camping.service.UserService;

//ajout
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

@Controller
@RequestMapping("/reservations")
public class ReservationController {	
	private final UserService userService;	
	private final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	private final SiteService siteService; //pour le choix du site
    private final ReservationService reservationService;
    
    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService, SiteService siteService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.siteService = siteService;
    }
    @GetMapping
    public String listerReservations(Model model) {
        // Récupérez la liste des réservations depuis le service
        List<Reservation> reservations = reservationService.getAllReservations();
        // Ajoutez les réservations à l'objet Model pour les afficher dans la vue correspondante
        model.addAttribute("reservations", reservations);
        return "listeReservations"; // Nom de la vue Thymeleaf (listeReservations.html)
        //return "administrateur"; 
    
    }    
  //afficher les dates et détails du site a réserver
    @GetMapping("/reservation")
    public String afficherPageReservation(
    		 Model model,
            @RequestParam("siteId") int siteId,
            @RequestParam(name = "dateDebut") String dateDebut,        
            @RequestParam(name = "dateFin") String dateFin
            ) {    	
    	logger.info("Récupération de la réservation pour le site ID: {}", siteId);
        logger.debug("Date de début reçue: {}", dateDebut);
   	    logger.debug("Date de fin reçue: {}", dateFin);
  
    	// Récupérer le site par son identifiant
        Site site = siteService.getSiteById(siteId);
        if (site == null) {
            return "redirect:/error";
        }        
     // Calculer le nombre de nuits entre dateDebut et dateFin
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateDebutParsed = LocalDate.parse(dateDebut, formatter);
        LocalDate dateFinParsed = LocalDate.parse(dateFin, formatter);
        long nombreDeNuits = ChronoUnit.DAYS.between(dateDebutParsed, dateFinParsed);

        // Calculer le coût de la réservation en fonction du nombre de nuits et du prix par nuit
        BigDecimal nombreDeNuitsBigDecimal = BigDecimal.valueOf(nombreDeNuits);
        BigDecimal coutReservation = nombreDeNuitsBigDecimal.multiply(site.getPrixParNuit()); // Assurez-vous que prixParNuit est aussi de type BigDecimal  
                
     // Calculer le montant de la TPS
        BigDecimal tps = coutReservation.multiply(BigDecimal.valueOf(0.05));
        tps = tps.setScale(2, RoundingMode.HALF_UP); // Arrondir à 2 chiffres décimaux

        // Calculer le montant de la TVQ
        BigDecimal tvq = coutReservation.multiply(BigDecimal.valueOf(0.09975));
        tvq = tvq.setScale(2, RoundingMode.HALF_UP); // Arrondir à 2 chiffres décimaux

     // Calculer le montant total des taxes
        BigDecimal montantTotalTaxes = tps.add(tvq);
        montantTotalTaxes = montantTotalTaxes.setScale(2, RoundingMode.HALF_UP); // Arrondir à 2 chiffres décimaux

        // Calculer le prix total
        BigDecimal prixTotal = coutReservation.add(tps).add(tvq);
        prixTotal = prixTotal.setScale(2, RoundingMode.HALF_UP); // Arrondir à 2 chiffres décimaux
      
        // Ajouter les informations au modèle pour les rendre accessibles dans la vue
        model.addAttribute("site", site);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("nombreDeNuits", nombreDeNuits);
        model.addAttribute("coutReservation", coutReservation);
        model.addAttribute("tps", tps);
        model.addAttribute("tvq", tvq);
        model.addAttribute("montantTotalTaxes", montantTotalTaxes);
        model.addAttribute("prixTotal", prixTotal);

        return "reservation";       
    }
  }
   