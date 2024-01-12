package com.campingconnecte.camping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.campingconnecte.camping.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire
}
