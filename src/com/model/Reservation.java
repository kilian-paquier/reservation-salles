package com.model;

import java.sql.Date;

public class Reservation {
    private Utilisateur utilisateur;
    private Salle salle;
    private Date dateDebut;
    private Date dateFin;

    public Reservation(Utilisateur utilisateur, Salle salle, Date dateDebut, Date dateFin) {
        this.utilisateur = utilisateur;
        this.salle = salle;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Salle getSalle() {
        return salle;
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

    @Override
    public String toString() {
        return salle.getNomSalle() + " le " + dateDebut.toString();
    }
}
