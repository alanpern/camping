package com.campingconnecte.camping.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Identifiant unique de l'emplacement

    // Ajoutez d'autres champs en fonction des besoins
    private String numero; // Numéro de l'emplacement
    private int longueurMax; // Longueur maximale supportée
    private String services; // Services disponibles
    private BigDecimal prixParNuit; // Prix par nuit

    // Constructeur par défaut
    public Site() {}

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public int getLongueurMax() { return longueurMax; }
    public void setLongueurMax(int longueurMax) { this.longueurMax = longueurMax; }

    public String getServices() { return services; }
    public void setServices(String services) { this.services = services; }

    public BigDecimal getPrixParNuit() { return prixParNuit; }
    public void setPrixParNuit(BigDecimal prixParNuit) { this.prixParNuit = prixParNuit; }
}