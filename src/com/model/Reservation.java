package com.model;

import java.time.LocalDateTime;

public class Reservation {
    private Utilisateur utilisateur;
    private Salle salle;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    public Reservation(Utilisateur utilisateur, Salle salle, LocalDateTime dateDebut, LocalDateTime dateFin) {
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

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return salle.getNomSalle() + " le " + dateDebut.toString();
    }
}
