package com.view;

import javax.swing.*;

public class MainView extends JFrame {
    private JPanel globalPanel;
    private JList listSalle;
    private JTable tableReservations;
    private JComboBox boxSalle;
    private JComboBox boxJourDebut;
    private JComboBox boxJourFin;
    private JButton addReservation;
    private JComboBox boxHeureDebut;
    private JComboBox boxHeureFin;
    private JComboBox reservationBox;

    public MainView() {
        setContentPane(globalPanel);
        setTitle("Gestion des réservations");
    }

    public JList getListSalle() {
        return listSalle;
    }

    public JTable getTableReservations() {
        return tableReservations;
    }

    public JComboBox getBoxSalle() {
        return boxSalle;
    }

    public JComboBox getBoxJourDebut() {
        return boxJourDebut;
    }

    public JComboBox getBoxJourFin() {
        return boxJourFin;
    }

    public JButton getAddReservation() {
        return addReservation;
    }

    public JComboBox getBoxHeureDebut() {
        return boxHeureDebut;
    }

    public JComboBox getBoxHeureFin() {
        return boxHeureFin;
    }

    public JComboBox getReservationBox() {
        return reservationBox;
    }
}
