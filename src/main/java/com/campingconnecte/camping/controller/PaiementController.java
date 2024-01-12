package com.campingconnecte.camping.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PaiementController {
//	@Autowired
//	private com.campingconnecte.camping.service.ReservationService reservationService;

    @Autowired
   private ReservationService reservationService;
    
    private static final Logger logger = LoggerFactory.getLogger(PaiementController.class);

    @GetMapping("/confirmation")
    public String afficherFormulairePaiement(
        Model model,
        @RequestParam(name = "reservationId") int reservationId
    ) {
        Reservation reservation = reservationService.getReservationById(reservationId);

        if (reservation == null) {
            // Gestion de l'erreur si la réservation n'est pas trouvée
         logger.error("La réservation avec l'ID {} n'a pas été trouvée.", reservationId);
            model.addAttribute("message", "Erreur : Réservation introuvable");
            return "error"; // Rediriger vers la page d'erreur
        }
        logger.info("Affichage du formulaire de paiement pour la réservation avec l'ID {}.", reservationId);

        model.addAttribute("reservation", reservation);
        return "paiement"; // Nom de la vue Thymeleaf (paiement.html)
    }

    @PostMapping("/processpayment")
    public String processPayment(
            @RequestParam("montant") String montant,
            @RequestParam("reservationId") int reservationId,
            Model model
    ) {
        Reservation reservation = reservationService.getReservationById(reservationId);

        if (reservation == null) {
            // Gestion de l'erreur si la réservation n'est pas trouvée
            logger.error("La réservation avec l'ID {} n'a pas été trouvée lors du traitement du paiement.", reservationId);
            model.addAttribute("message", "Erreur : Réservation introuvable");
            return "error"; // Rediriger vers la page d'erreur
        }

        // Mettez à jour le statut de la réservation
        reservation.setStatus("payée");
        reservationService.saveReservation(reservation);
        
        logger.info("Paiement effectué avec succès pour la réservation avec l'ID {}. Statut mis à jour comme payée.", reservationId);

        model.addAttribute("message", "Paiement effectué avec succès ! On vous attend le : [insérer date ici]");
        return "paymentconfirm"; // Rediriger vers la page de confirmation
    }
}
