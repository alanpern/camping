package com.campingconnecte.camping.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.model.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire
	// boolean existsBySiteId(int siteId);
	// Dans votre ReservationRepository, mettez à jour la méthode findBySiteId pour qu'elle retourne une liste de réservations
	List<Reservation> findBySiteId(int siteId);

	  boolean existsBySite(Site site);
	 // Reservation findBySiteId(int siteId); 
	  boolean existsBySiteId(int siteId); 
	  
	  boolean existsByUserAndDateFinAfter(User user, Date currentDate);
}
