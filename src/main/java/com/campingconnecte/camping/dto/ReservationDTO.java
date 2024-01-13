package com.campingconnecte.camping.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ReservationDTO {
    private int id;
    private UserDTO user;
    private SiteDTO site;
    private Date dateDebut;
    private Date dateFin;
   // private int nbAdultes;
 //   private int nbEnfants;
    private BigDecimal montantAvantTx;
  //  private BigDecimal tps;
  //  private BigDecimal tvq;
    private BigDecimal prixTotal;
    private String status;

    // Constructeur par défaut
    public ReservationDTO() {
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public SiteDTO getSite() {
        return site;
    }

    public void setSite(SiteDTO site) {
        this.site = site;
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

    public BigDecimal getMontantAvantTx() {
        return montantAvantTx;
    }

    public void setMontantAvantTx(BigDecimal montantAvantTx) {
        this.montantAvantTx = montantAvantTx;
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
