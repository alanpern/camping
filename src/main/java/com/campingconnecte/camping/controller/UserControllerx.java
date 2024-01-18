package com.campingconnecte.camping.controller;
import com.campingconnecte.camping.model.User;



import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.repository.UserRepository;
import com.campingconnecte.camping.repository.ReservationRepository;
import com.campingconnecte.camping.service.UserService;
import com.campingconnecte.camping.service.ReservationService;
import com.campingconnecte.camping.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SiteService siteService;

    // Méthode pour mettre à jour les informations de l'utilisateur et créer/mettre à jour une réservation
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, 
                             @RequestParam("siteId") int siteId,
                             @RequestParam("dateDebut") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateDebut,
                             @RequestParam("dateFin") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFin,
                             Model model) {
    	User savedUser = null; // Initialisation de savedUser avec null
        // Vérifier si l'utilisateur existe déjà
        User existingUser = userRepository.findByNomAndPrenomAndEmail(user.getNom(), user.getPrenom(), user.getEmail());

        if (existingUser != null) {
            // L'utilisateur existe, mise à jour de ses informations
            existingUser.setAdresse(user.getAdresse());
            existingUser.setVille(user.getVille());
            existingUser.setTelephone(user.getTelephone());
            savedUser = userRepository.save(existingUser); // Sauvegarde de l'utilisateur existant
           // userRepository.save(existingUser);
            logger.info("Mise à jour de l'utilisateur existant : {}", existingUser);
        } else {
        	 savedUser = userRepository.save(user); // Sauvegarde et récupération du nouvel utilisateur
        	  logger.info("Création d'un nouvel utilisateur : {}", user);
            // L'utilisateur n'existe pas, l'ajouter à la base de données
          //  userRepository.save(user);
        }
     // S'assurer que savedUser n'est pas null
        if (savedUser == null) {
        	logger.error("Erreur lors de la sauvegarde de l'utilisateur");
            // Gestion d'erreur si l'utilisateur n'est pas sauvegardé correctement
            return "redirect:/error";
        }
        
        Site site = siteService.getSiteById(siteId); // Récupérer le site par son ID
        if (site == null) {
            logger.error("Le site avec l'ID {} n'existe pas", siteId);
            // Gestion d'erreur si le site n'existe pas
            return "redirect:/error";
        }
        // Création ou mise à jour de la réservation
        Reservation reservation = new Reservation();
        
        reservation.setUser(savedUser); // Utiliser l'utilisateur enregistré
        //reservation.setUser(user); // Associer l'utilisateur à la réservation
        reservation.setSite(site);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        
        // Calcul du nombre de nuits
        long nombreDeNuits = ChronoUnit.DAYS.between(dateDebut.toInstant(), dateFin.toInstant());
       // définir le nombre de nuits dans l'objet Reservation avant de l'enregistrer 
        reservation.setNombreDeNuits((int) nombreDeNuits); 
  
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
     // Définir la valeur de prixTotal dans l'objet Reservation
        reservation.setPrixTotal(prixTotal);                    
       
     // Enregistrer la réservation
        Reservation savedReservation = reservationService.saveReservation(reservation);
        if (savedReservation == null) {
            logger.error("Erreur lors de la sauvegarde de la réservation");
            return "redirect:/error";
        }
        
     // Mettre à jour le statut de la réservation à "Confirmé"
        savedReservation.setStatus("Confirmé");
        reservationService.saveReservation(savedReservation); // Mettre à jour la réservation dans la base de données

      
     // Ajouter les informations au modèle pour la page de confirmation
        model.addAttribute("reservation", savedReservation);
        model.addAttribute("site", site); // Ajouter l'objet Site au modèle
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("nombreDeNuits", nombreDeNuits);
        model.addAttribute("prixTotal", prixTotal);

        logger.info("Réservation créée avec succès et statut mis à jour à 'Confirmé' : {}", savedReservation);
        return "confirmation"; // Rediriger vers la page de confirmation
    }   
}



