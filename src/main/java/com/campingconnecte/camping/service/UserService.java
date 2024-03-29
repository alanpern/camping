package com.campingconnecte.camping.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.campingconnecte.camping.model.User;
import com.campingconnecte.camping.repository.ReservationRepository;
import com.campingconnecte.camping.repository.UserRepository;

import jakarta.transaction.Transactional;
import java.sql.Date;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    
    @Autowired
    private ReservationRepository reservationRepository;

   
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUserByNomPrenomEmail(String nom, String prenom, String email) {
        return userRepository.findByNomAndPrenomAndEmail(nom, prenom, email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
    
    public boolean userHasFutureReservations(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Date currentDate = new Date(System.currentTimeMillis()); // Convertir java.util.Date en java.sql.Date
            return reservationRepository.existsByUserAndDateFinAfter(user.get(), currentDate);
        } else {
            return false;
        }
    }
 
    
    @Transactional
    public User createUser(String nom, String prenom, String email, String telephone) {
        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setTelephone(telephone);

        // Vous pouvez également définir d'autres propriétés de l'utilisateur si nécessaire.

        return userRepository.save(user);
    }
}
