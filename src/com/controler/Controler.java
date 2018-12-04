package com.controler;

import com.model.Reservation;
import com.model.Salle;
import com.model.Utilisateur;
import com.model.Utils;
import com.view.Connexion;
import com.view.MainView;
import com.view.SignUp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Controler {
    private MainView mainView;
    private Utilisateur utilisateur;

    public Controler(String title) {
        opening();
    }

    private void opening() {
        Connexion connexion = new Connexion();
        connexion.getMdpForgot().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                actionForgot();
                connexion.dispose();
            }
        });

        connexion.getSignUp().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                actionSignUp();
                connexion.dispose();
            }
        });

        connexion.getSeConnecterButton().addActionListener(e -> {
            String mail = connexion.getMailField().getText();
            String mdp = Utils.hashPassword(Arrays.toString(connexion.getMdpField().getPassword()));


            mainView = new MainView();
            init();
        });
    }

    private void init() {
        initListReservations();
        initListSalles();
        initReservations();

        mainView.getDeleteReservation().addActionListener(e -> deleteReservation());
        mainView.getAddReservation().addActionListener(e -> addReservation());
    }

    private void actionForgot() {

    }

    private void actionSignUp() {
        SignUp signUp = new SignUp();
        signUp.getSignupButton().addActionListener(e -> {
            String prenom = signUp.getPrenomField().getText();
            String nom = signUp.getNomField().getText();
            String mail = signUp.getMailField().getText();
            char[] mdp = signUp.getMdpField().getPassword();
            String motDePasse = Utils.hashPassword(Arrays.toString(mdp));

            utilisateur = new Utilisateur(prenom, nom, mail, motDePasse);
            Utils.registerUser(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getMail(), utilisateur.getMotdepasse());
            signUp.dispose();

            mainView = new MainView();
            init();
        });
    }

    private void initListSalles() {
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();

        // Faire liste

        mainView.getListSalle().setModel(defaultListModel);
    }

    private void initListReservations() {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        defaultTableModel.addColumn("Utilisateur");
        defaultTableModel.addColumn("Salle");
        defaultTableModel.addColumn("Début");
        defaultTableModel.addColumn("Fin");

        // Faire liste

        mainView.getTableReservations().setModel(defaultTableModel);
    }

    private void addReservation() {

    }

    private void deleteReservation() {
        String nomSalle = String.valueOf(mainView.getReservationBox().getSelectedItem()).split("le")[0];
        nomSalle = nomSalle.trim();
        Salle salle = utilisateur.getSalle(nomSalle);

    }

    private void initReservations() {
        for (Reservation reservation : utilisateur.getReservations()) {
            mainView.getReservationBox().addItem(reservation.toString());
        }
    }
}
