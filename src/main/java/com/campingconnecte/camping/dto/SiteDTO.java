package com.campingconnecte.camping.dto;

import java.math.BigDecimal;

public class SiteDTO {
    private int id;
    private String numero;
    private int longueurMax;
    private String services;
    private BigDecimal prixParNuit;

    // Constructeur par défaut
    public SiteDTO() {}

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
