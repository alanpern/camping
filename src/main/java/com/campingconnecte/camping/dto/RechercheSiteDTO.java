package com.campingconnecte.camping.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/* Classe DTO (Data Transfer Object) pour la recherche de sites de camping.
 * Cette classe est utilisée pour capturer les données de recherche de l'utilisateur
 * à partir du formulaire de recherche sur le site web. */
public class RechercheSiteDTO {
    // Date de début souhaitée pour la réservation	
	  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;

    // Date de fin souhaitée pour la réservation
	  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;

    // Les services désirés pour le site de camping (par exemple: électricité, eau, etc.)
    private String services;

    private int longueurEquipement;
    /* Constructeur par défaut.   */
    public RechercheSiteDTO() {
    }

    /*  * Obtient la date de début. * @return la date de début    */
    public Date getDateDebut() {
        return dateDebut;
    }

    /* Définit la date de début.  @param dateDebut la date de début à définir    */
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    /*   Obtient la date de fin. @return la date de fin    */
    public Date getDateFin() {
        return dateFin;
    }

    /* Définit la date de fin  @param dateFin la date de fin à définir   */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /*  Obtient les services désirés. @return les services désirés    */
    public String getServices() {
        return services;
    }

    /* Définit les services désirés.  @param services les services à définir  */
    public void setServices(String services) {
        this.services = services;
    }
    
    /* Obtient la longueur de l'équipement. @return la longueur de l'équipement en pieds  */
    public int getLongueurEquipement() {
        return longueurEquipement;
    }

    /*  Définit la longueur de l'équipement.@param longueurEquipement la longueur de l'équipement en pieds  */
    public void setLongueurEquipement(int longueurEquipement) {
        this.longueurEquipement = longueurEquipement;
    }             
}
