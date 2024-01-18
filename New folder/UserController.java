package com.campingconnecte.camping.controller;
import com.campingconnecte.camping.model.User;



import com.campingconnecte.camping.model.Reservation;
import com.campingconnecte.camping.model.Site;
import com.campingconnecte.camping.repository.UserRepository;
import com.campingconnecte.camping.repository.ReservationRepository;
import com.campingconnecte.camping.service.UserService;
import com.campingconnecte.camping.service.ReservationService;
import com.campingconnecte.camping.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private UserService userService;

    
    //Méthode pour gérer l'affichage de la liste des utilisateurs.
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new User()); 
        return "users"; // Nom du template (user.html)
    }
    //18h00
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User newUser, RedirectAttributes redirectAttributes) {
        try {
            userRepository.save(newUser);
            logger.info("Nouvel utilisateur ajouté : {}", newUser);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur ajouté avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout de l'utilisateur : {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout de l'utilisateur.");
        }
        return "redirect:/users";
    }
    
    //18h30 
    @GetMapping("/editUser/{id}")
    public String showEditUserForm(@PathVariable("id") int userId, Model model) {
        User userToEdit = userService.getUserById(userId);
        if (userToEdit != null) {
            model.addAttribute("user", userToEdit);
            return "editUser"; // Nom du template pour le formulaire de modification (editUser.html)
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé (par exemple, redirection vers une page d'erreur ou la liste des utilisateurs)
            return "redirect:/users";
        }
    }
   
    
    //updateUsers pour la liste des users
    @PostMapping("/updateUsers")
    public String updateUsers(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userRepository.save(user);
            logger.info("Utilisateur mis à jour : {}", user);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur mis à jour avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de l'utilisateur : {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur.");
        }
        return "redirect:/users";
    }

    
    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUsers"; // Le nom du fichier HTML est "addUsers.html"
    }

 
    
    
  /*  @GetMapping("/showAddUserForm")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUsers"; // Le nom du fichier HTML doit être "addUsers.html" dans le dossier templates
    }*/


    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        User savedUser = userRepository.findByNomAndPrenomAndEmail(user.getNom(), user.getPrenom(), user.getEmail());
        if (savedUser != null) {
            // Mise à jour des informations existantes de l'utilisateur
            savedUser.setAdresse(user.getAdresse());
            savedUser.setVille(user.getVille());
            savedUser.setPays(user.getPays());
            savedUser.setEmail(user.getEmail());
            savedUser.setTelephone(user.getTelephone());
            // Ajoutez d'autres champs à mettre à jour si nécessaire
        } else {
            // Création d'un nouvel utilisateur si non existant
            savedUser = user;
        }
        userRepository.save(savedUser);
        logger.info("Utilisateur mis à jour : {}", savedUser);
        return "redirect:/users"; // Redirigez vers la liste des utilisateurs ou la page appropriée
    }
        
  /*  @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users"; // ou le nom de votre page contenant le formulaire
    }*/
    
    
    @GetMapping("/deleteUser/{id}")
   // public String deleteUser(@PathVariable("id") int userId, RedirectAttributes redirectAttributes) {
    public String deleteUser(@RequestParam("id") int userId, RedirectAttributes redirectAttributes) {  
    if (!userService.userHasFutureReservations(userId)) {
            userService.deleteUser(userId);
            logger.info("Utilisateur avec l'ID {} supprimé", userId);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur supprimé avec succès.");
        } else {
            logger.info("L'utilisateur avec l'ID {} a des réservations futures et ne peut pas être supprimé", userId);
            redirectAttributes.addFlashAttribute("errorMessage", "L'utilisateur ne peut pas être supprimé car il a des réservations futures.");
        }
        return "redirect:/users";
    }

  /*  @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int userId, Model model) {
        if (!userService.userHasFutureReservations(userId)) {
            userService.deleteUser(userId);
            logger.info("Utilisateur avec l'ID {} supprimé", userId);
        } else {
            logger.info("L'utilisateur avec l'ID {} a des réservations futures et ne peut pas être supprimé", userId);
            // Gérer la situation où l'utilisateur ne peut pas être supprimé
            // Par exemple, ajouter un message d'erreur au modèle
        }
        return "redirect:/users";
    }*/
    
    // Méthode pour mettre à jour les informations de l'utilisateur et créer/mettre à jour une réservation
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, 
                             @RequestParam("siteId") int siteId,
                             @RequestParam("dateDebut") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateDebut,
                             @RequestParam("dateFin") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFin,
                             Model model) {
    	User savedUser = null; // Initialisation de savedUser avec null
        // Vérifier si l'utilisateur existe déjà
        User existingUser = userRepository.findByNomAndPrenomAndEmail(user.getNom(), user.getPrenom(), user.getEmail());

        if (existingUser != null) {
            // L'utilisateur existe, mise à jour de ses informations
            existingUser.setAdresse(user.getAdresse());
            existingUser.setVille(user.getVille());
            existingUser.setTelephone(user.getTelephone());
            savedUser = userRepository.save(existingUser); // Sauvegarde de l'utilisateur existant
           // userRepository.save(existingUser);
            logger.info("Mise à jour de l'utilisateur existant : {}", existingUser);
        } else {
        	 savedUser = userRepository.save(user); // Sauvegarde et récupération du nouvel utilisateur
        	  logger.info("Création d'un nouvel utilisateur : {}", user);
            // L'utilisateur n'existe pas, l'ajouter à la base de données
          //  userRepository.save(user);
        }
     // S'assurer que savedUser n'est pas null
        if (savedUser == null) {
        	logger.error("Erreur lors de la sauvegarde de l'utilisateur");
            // Gestion d'erreur si l'utilisateur n'est pas sauvegardé correctement
            return "redirect:/error";
        }
        
        Site site = siteService.getSiteById(siteId); // Récupérer le site par son ID
        if (site == null) {
            logger.error("Le site avec l'ID {} n'existe pas", siteId);
            // Gestion d'erreur si le site n'existe pas
            return "redirect:/error";
        }
        
        
        // Création ou mise à jour de la réservation
        Reservation reservation = new Reservation();
        
        reservation.setUser(savedUser); // Utiliser l'utilisateur enregistré
        //reservation.setUser(user); // Associer l'utilisateur à la réservation
        reservation.setSite(site);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        
        // Calcul du nombre de nuits
        long nombreDeNuits = ChronoUnit.DAYS.between(dateDebut.toInstant(), dateFin.toInstant());
       // définir le nombre de nuits dans l'objet Reservation avant de l'enregistrer 
        reservation.setNombreDeNuits((int) nombreDeNuits); 
  
        // Calculer le coût de la réservation en fonction du nombre de nuits et du prix par nuit
        BigDecimal nombreDeNuitsBigDecimal = BigDecimal.valueOf(nombreDeNuits);
        BigDecimal coutReservation = nombreDeNuitsBigDecimal.multiply(site.getPrixParNuit()); // Assurez-vous que prixParNuit est aussi de type BigDecimal  
                
     // Calculer le montant de la TPS
        BigDecimal tps = coutReservation.multiply(BigDecimal.valueOf(0.05));
        tps = tps.setScale(2, RoundingMode.HALF_UP); // Arrondir à 2 chiffres décimaux

        // Calculer le montant de la TVQ
        BigDecimal tvq = coutReservation.multiply(BigDecimal.valueOf(0.09975));
        tvq = tvq.setScale(2, RoundingMode.HALF_UP); // Arrondir à 2 chiffres décimaux

     // Calculer le montant total des taxes
        BigDecimal montantTotalTaxes = tps.add(tvq);
        montantTotalTaxes = montantTotalTaxes.setScale(2, RoundingMode.HALF_UP); // Arrondir à 2 chiffres décimaux

        // Calculer le prix total
        BigDecimal prixTotal = coutReservation.add(tps).add(tvq);
        prixTotal = prixTotal.setScale(2, RoundingMode.HALF_UP); // Arrondir à 2 chiffres décimaux
     // Définir la valeur de prixTotal dans l'objet Reservation
        reservation.setPrixTotal(prixTotal);                    
       
     // Enregistrer la réservation
        Reservation savedReservation = reservationService.saveReservation(reservation);
        if (savedReservation == null) {
            logger.error("Erreur lors de la sauvegarde de la réservation");
            return "redirect:/error";
        }
        
     // Mettre à jour le statut de la réservation à "Confirmé"
        savedReservation.setStatus("Confirmé");
        reservationService.saveReservation(savedReservation); // Mettre à jour la réservation dans la base de données

      
     // Ajouter les informations au modèle pour la page de confirmation
        model.addAttribute("reservation", savedReservation);
        model.addAttribute("site", site); // Ajouter l'objet Site au modèle
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("nombreDeNuits", nombreDeNuits);
        model.addAttribute("prixTotal", prixTotal);

        logger.info("Réservation créée avec succès et statut mis à jour à 'Confirmé' : {}", savedReservation);
        return "confirmation"; // Rediriger vers la page de confirmation
    }   
}



