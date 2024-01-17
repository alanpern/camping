package com.campingconnecte.camping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.campingconnecte.camping.dto.AdmReservationDTO;
import com.campingconnecte.camping.service.AdmReservationService;

@Controller
@RequestMapping("/admin/reservations")
public class AdmReservationController {

    @Autowired
    private AdmReservationService reservationService;

    // Liste des réservations
    @GetMapping
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "administrateur"; // Vue avec la liste des réservations
    }

    // Formulaire d'ajout de réservation
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("reservation", new AdmReservationDTO());
        return "addReservation"; // Vue pour ajouter une réservation
    }

    // Enregistrer une nouvelle réservation
    @PostMapping("/add")
    public String addReservation(@ModelAttribute AdmReservationDTO reservationDTO) {
        reservationService.saveReservation(reservationDTO);
        return "redirect:/admin/reservations";
    }

    // Formulaire de mise à jour de la réservation
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        AdmReservationDTO reservationDTO = reservationService.getReservationById(id);
        model.addAttribute("reservation", reservationDTO);
        return "updateReservation"; // Vue pour mettre à jour une réservation
    }

    // Mettre à jour une réservation
    @PostMapping("/update/{id}")
    public String updateReservation(@PathVariable("id") int id, @ModelAttribute AdmReservationDTO reservationDTO) {
        reservationService.saveReservation(reservationDTO);
        return "redirect:/admin/reservations";
    }

    // Supprimer une réservation
    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") int id) {
        reservationService.deleteReservation(id);
        return "redirect:/admin/reservations";
    }
}
