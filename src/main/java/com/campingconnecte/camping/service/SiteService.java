package com.campingconnecte.camping.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.repository.SiteRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*Il offre des méthodes pour ajouter, rechercher, mettre à jour et supprimer des sites. */
@Service
public class SiteService {
    private final SiteRepository siteRepository;
 /*Constructeur pour l'injection de dépendance de SiteRepository.
   @param siteRepository Le repository pour les opérations de base de données liées aux sites. */
    @Autowired
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;        
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
