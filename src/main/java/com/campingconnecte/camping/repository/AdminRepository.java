package com.campingconnecte.camping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campingconnecte.camping.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // Ajoutez ici des méthodes personnalisées si nécessaire
    Admin findByUsername(String username);
}
