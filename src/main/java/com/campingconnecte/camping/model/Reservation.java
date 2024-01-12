package com.campingconnecte.camping.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "reservation")

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateDebut")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateFin")
    private Date dateFin;

    private int nombreDeNuits;
    
    @Column(name = "montantAvantTx", precision = 10, scale = 2)
    private BigDecimal montantAvantTx;

    @Column(name = "prixTotal", precision = 10, scale = 2)
    private BigDecimal prixTotal;

    //@Column(name = "nombreDeNuits")
   // private long nombreDeNuits;

    @Column(name = "status")
    private String status;

    // Constructeurs, getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
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

    public int getNombreDeNuits() {
        return nombreDeNuits;
    }

    public void setNombreDeNuits(int nombreDeNuits) {
        this.nombreDeNuits = nombreDeNuits;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

