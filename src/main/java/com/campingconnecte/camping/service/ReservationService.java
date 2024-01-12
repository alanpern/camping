package com.campingconnecte.camping.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.model.User;
import com.campingconnecte.camping.repository.ReservationRepository;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import com.campingconnecte.camping.service.ReservationService;

@Service
public class ReservationService {
	
    private final ReservationRepository reservationRepository;
    private final UserService userService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, UserService userService) {
        this.reservationRepository = reservationRepository;
        this.userService = userService;
    }
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

   // public ReservationService(ReservationRepository reservationRepository) {
   //     this.reservationRepository = reservationRepository;
   
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public Reservation createReservationWithUser(String nom, String prenom, String email, Site site, Date dateDebut, Date dateFin, BigDecimal montantAvantTx, String status) {
        // Créez d'abord l'utilisateur en utilisant le service UserService
        User user = userService.createUser(nom, prenom, email, "numéro de téléphone ici");

        // Associez l'utilisateur à la réservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSite(site);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        reservation.setMontantAvantTx(montantAvantTx);
        reservation.setStatus(status);
        
     // Calcul du nombre de nuits
        long nombreDeNuits = ChronoUnit.DAYS.between(dateDebut.toInstant(), dateFin.toInstant());
        reservation.setNombreDeNuits((int) nombreDeNuits);

        // Calcul du montant total avant taxes
        BigDecimal coutReservation = BigDecimal.valueOf(nombreDeNuits).multiply(site.getPrixParNuit());

        // Calcul des taxes (TPS et TVQ)
        BigDecimal tps = coutReservation.multiply(BigDecimal.valueOf(0.05));
        BigDecimal tvq = coutReservation.multiply(BigDecimal.valueOf(0.09975));

        // Calcul du montant total incluant les taxes
        BigDecimal montantTotalTaxes = tps.add(tvq);
        BigDecimal prixTotal = coutReservation.add(montantTotalTaxes);

        reservation.setPrixTotal(prixTotal);

        // Calcul du statut en fonction de la logique de votre application (par exemple, paiement effectué)
        // Vous pouvez définir le statut en fonction de vos conditions et de la logique métier de votre application
        reservation.setStatus("payée");
        
        
     // Mettez à jour le statut de la réservation       
       // reservationService.saveReservation(reservation);
        saveReservation(reservation); // Utilisez directement la méthode saveReservation de votre service
        logger.info("Paiement effectué avec succès pour la réservation avec l'ID {}. Statut mis à jour comme payée.", reservation.getId());
        
        return reservation;
       // model.addAttribute("message", "Paiement effectué avec succès ! On vous attend le : [insérer date ici]");
        //return "paymentconfirm"; // Rediriger vers la page de confirmation
    }

        // Insertion de la réservation dans la base de données
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);// Appel à la méthode save du repository pour persister la réservation dans la base de données
        
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }
}


