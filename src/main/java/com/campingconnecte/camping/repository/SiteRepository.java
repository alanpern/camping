package com.campingconnecte.camping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.campingconnecte.camping.model.Site;
import java.util.Date;
import java.util.List;

/* Interface du repository pour les entités Site.
 * Elle étend JpaRepository, offrant des opérations de base de données pour l'entité Site. */
public interface SiteRepository extends JpaRepository<Site, Integer> {

/* Recherche des sites disponibles en fonction de la date de début, de la fin de la période de réservation
   */	
	@Query(value = "SELECT s.* FROM site s WHERE s.id NOT IN " +
            "(SELECT r.site_id FROM reservation r " +
            "WHERE (r.dateDebut <= :dateFin AND r.dateFin >= :dateDebut)) " +
            "AND s.services = :services " +
            "AND s.longueurMax >= :longueurEquipement " +
            "ORDER BY s.id", nativeQuery = true)
	List<Site> findAvailableSites(Date dateDebut, Date dateFin, String services, int longueurEquipement);
}
	
	

