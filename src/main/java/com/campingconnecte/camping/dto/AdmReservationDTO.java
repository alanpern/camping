package com.campingconnecte.camping.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AdmReservationDTO {
    private int id;
    private int userId; // Id de l'utilisateur
    private int siteId; // Id du site
    private Date dateDebut; // Date de début de la réservation
    private Date dateFin; // Date de fin de la réservation
    private int nombreDeNuits; // Nombre de nuits de la réservation
    private BigDecimal prixTotal; // Prix total de la réservation
    private String status; // Statut de la réservation (par exemple, "confirmée", "annulée")

    // Constructeur par défaut
    public AdmReservationDTO() {
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNombreDeNuits() {
        return nombreDeNuits;
    }

    public void setNombreDeNuits(int nombreDeNuits) {
        this.nombreDeNuits = nombreDeNuits;
    }
    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
