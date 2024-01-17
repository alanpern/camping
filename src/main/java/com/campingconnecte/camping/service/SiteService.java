package com.campingconnecte.camping.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.repository.ReservationRepository;
import com.campingconnecte.camping.repository.SiteRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/*Il offre des méthodes pour ajouter, rechercher, mettre à jour et supprimer des sites. */
@Service
public class SiteService {
    private final SiteRepository siteRepository;
    private final ReservationRepository reservationRepository;
 /*Constructeur pour l'injection de dépendance de SiteRepository.
   @param siteRepository Le repository pour les opérations de base de données liées aux sites. */
    //@Autowired
   // public SiteService(SiteRepository siteRepository) {
     //   this.siteRepository = siteRepository;     
    @Autowired
    public SiteService(SiteRepository siteRepository, ReservationRepository reservationRepository) {
        this.siteRepository = siteRepository;
        this.reservationRepository = reservationRepository;
    }
    public boolean site_in_reservation_table(int siteId) {
        // Vérifiez si le site existe dans la table des réservations en fonction de son ID.
        Optional<Site> site = siteRepository.findById(siteId);
        if (site.isPresent()) {
            // Si le site existe, vérifiez s'il est lié à une réservation.
            Site siteToCheck = site.get();
            return reservationRepository.existsBySite(siteToCheck);
        } else {
            // Le site n'existe pas, donc il ne peut pas être dans la table des réservations.
            return false;
        }
    }
    public boolean reservation_end_date_is_in_future(int reservationId) {
        // Vérifiez si la date de fin de la réservation est à venir en fonction de son ID.
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            Reservation reservationToCheck = reservation.get();
            Date currentDate = new Date(); // Date actuelle
            Date endDate = reservationToCheck.getDateFin(); // Date de fin de la réservation
            return endDate.after(currentDate); // Vérifiez si la date de fin est après la date actuelle.
        } else {
            // La réservation n'existe pas, donc nous ne pouvons pas vérifier la date de fin.
            return false;
        }
    }
    public BigDecimal getPrixParNuit(int id) {
        // Implémentez la logique pour récupérer le prixParNuit du site par son ID
        // Utilisez le repository pour accéder à la base de données et récupérer la valeur
        // Cette méthode doit retourner un BigDecimal
        // Remplacez par la logique de récupération réelle
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Site not found with id " + id));
        return site.getPrixParNuit();
    }

    public void updatePrixParNuit(int id, BigDecimal newPrice) {
        // Implémentez la logique pour mettre à jour le prixParNuit du site par son ID
        // Utilisez le repository pour accéder à la base de données et effectuer la mise à jour
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Site not found with id " + id));
        site.setPrixParNuit(newPrice);
        siteRepository.save(site);
    }
 /*Récupère tous les sites de la base de données.
     @return Liste de tous les sites.  */
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }
/*Recherche des sites disponibles basés sur les critères spécifiés.
  */
    public List<Site> findAvailableSites(Date dateDebut, Date dateFin, String services, int longueurEquipement) {
        return siteRepository.findAvailableSites(dateDebut, dateFin, services, longueurEquipement);
    }   
    public Site getSiteById(int id) {
        // Utilisez la méthode findById() du repository et renvoyez le résultat.
        // La méthode findById() renvoie un Optional, donc vous devez gérer cela.
        return siteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Site not found with id " + id));
    }
/* Enregistre ou met à jour un site dans la base de données.
     */
    public Site saveSite(Site site) {
        return siteRepository.save(site);
    }
    /*Supprime un site par son identifiant.    
     * @param id L'identifiant du site à supprimer. */
    public void deleteSite(int id) {
        siteRepository.deleteById(id);
    }
}
