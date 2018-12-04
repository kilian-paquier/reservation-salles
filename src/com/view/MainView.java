package com.view;

import javax.swing.*;

public class MainView extends JFrame {
    private JPanel globalPanel;
    private JList<String> listSalle;
    private JTable tableReservations;
    private JComboBox<String> boxSalle;
    private JComboBox<String> boxJourDebut;
    private JComboBox<String> boxJourFin;
    private JButton addReservation;
    private JComboBox<String> boxHeureDebut;
    private JComboBox<String> boxHeureFin;
    private JComboBox<String> reservationBox;
    private JButton deleteReservation;

    public MainView() {
        setContentPane(globalPanel);
        setTitle("Gestion des réservations");
    }

    public JList<String> getListSalle() {
        return listSalle;
    }

    public JTable getTableReservations() {
        return tableReservations;
    }

    public JComboBox<String> getBoxSalle() {
        return boxSalle;
    }

    public JComboBox<String> getBoxJourDebut() {
        return boxJourDebut;
    }

    public JComboBox<String> getBoxJourFin() {
        return boxJourFin;
    }

    public JButton getAddReservation() {
        return addReservation;
    }

    public JComboBox<String> getBoxHeureDebut() {
        return boxHeureDebut;
    }

    public JComboBox<String> getBoxHeureFin() {
        return boxHeureFin;
    }

    public JComboBox<String> getReservationBox() {
        return reservationBox;
    }

    public JButton getDeleteReservation() {
        return deleteReservation;
    }
}
