package com.controler;

import com.model.Utilisateur;
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
            String mdp = Arrays.toString(connexion.getMdpField().getPassword()); // Mettre en MD5


            mainView = new MainView();
        });
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
            String motDePasse = Arrays.toString(mdp); // Mettre en MD5

            Utilisateur utilisateur = new Utilisateur(prenom, nom, mail, motDePasse);
            // Mettre dans la BDD
            signUp.dispose();
            opening();
        });
    }

    private void initListSalles() {
        DefaultListModel defaultListModel = new DefaultListModel();


    }

    private void initListReservations() {
        DefaultTableModel defaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


    }
}
