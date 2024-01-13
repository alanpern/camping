package com.campingconnecte.camping.model;
import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nom;

    @NotBlank
    @Size(min = 2, max = 100)
    private String prenom;

    private String adresse;

    private String ville;

    @Column(nullable = true)
    private String pays;

    @Email
    @Column(length = 50)
    private String email;

    @NotBlank
    @Column(length = 20)
    private String telephone;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

    // Constructeurs, getters et setters
    public User() {}

    // Getter et setter pour id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 // Exemple de getter et setter pour le champ 'nom'
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Exemple de getter et setter pour le champ 'prenom'
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Exemple de getter et setter pour le champ 'email'
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Exemple de getter et setter pour le champ 'adresse'
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Exemple de getter et setter pour le champ 'ville'
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
    // Exemple de getter et setter pour le champ 'telephone'
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    

    // Getters et setters pour les réservations
    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
}



/*

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Identifiant unique de l'utilisateur

    @NotBlank
    @Size(min = 2, max = 100)
    private String nom; // Nom de l'utilisateur

    @NotBlank
    @Size(min = 2, max = 100)
    private String prenom; // Prénom de l'utilisateur    
    private String adresse; // Adresse de l'utilisateur
    private String ville;   // Ville de l'utilisateur
    
    @Column(nullable = true)
    private String pays;
 

    @Email
    private String email; // Email de l'utilisateur

    @NotBlank
    private String telephone; // Téléphone de l'utilisateur
    
    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

    // Constructeurs, getters et setters
    // Constructeur par défaut
    public User() {}

    // Getter et setter pour id
    public int getId() {return id; }
    public void setId(int id) { this.id = id; }

    // Getter et setter pour nom
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter et setter pour prenom
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Getter et setter pour adresse
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // Getter et setter pour ville
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
    // Getters et setters pour 'pays'
    public String getPays() { return pays;   }
    public void setPays(String pays) { this.pays = pays; }

    // Getter et setter pour email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter et setter pour telephone
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
*/