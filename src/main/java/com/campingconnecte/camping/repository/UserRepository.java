package com.campingconnecte.camping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.campingconnecte.camping.model.User;

@Repository
	public interface UserRepository extends JpaRepository<User, Integer> {
	    User findByNomAndPrenomAndEmail(String nom, String prenom, String email);
	}

