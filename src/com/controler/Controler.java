package com.controler;

import com.model.Reservation;
import com.model.Salle;
import com.model.Utilisateur;
import com.model.Utils;
import com.view.Connexion;
import com.view.ForgotPassword;
import com.view.MainView;
import com.view.SignUp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Controler {
    private MainView mainView;
    private Utilisateur utilisateur;
    private Connexion connexion;

    public Controler() {
        Utils.connection();
        opening();
    }

    private void opening() {
        connexion = new Connexion();
        connexion.getMailField().setText(Utils.getLastMail());
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

        connexion.getMdpField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    logIn();
            }
        });

        connexion.getSeConnecterButton().addActionListener(e -> logIn());

        connexion.open();
    }

    private void logIn() {
        String mail = connexion.getMailField().getText();
        String mdp = Utils.hashPassword(Arrays.toString(connexion.getMdpField().getPassword()));

        utilisateur = Utils.connectUser(mail, mdp);
        if (utilisateur == null)
            JOptionPane.showMessageDialog(null, "Mot de passe ou mail invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
        else {
            try {
                Utils.getProperties().setProperty("lastMail", mail);
                Utils.getProperties().storeToXML(new FileOutputStream("properties.xml"), "Sauvegarde");
            } catch (IOException e) {
                e.printStackTrace();
            }
            connexion();
            connexion.dispose();
        }
    }

    private void init() {
        initListReservations();
        initListSalles();
        initReservations();
        initBoxReservations();
        initBoxSalles();
        initDaysHours();

        mainView.getDeleteReservation().addActionListener(e -> deleteReservation());
        mainView.getAddReservation().addActionListener(e -> addReservation());
    }

    private void actionForgot() {
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.getForgotButton().addActionListener(e -> {
            String mail = forgotPassword.getMailField().getText();
            System.out.println(Utils.getPassword(mail));
        });

        forgotPassword.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connexion.setVisible(true);
            }
        });

        forgotPassword.getMailField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String mail = forgotPassword.getMailField().getText();
                    System.out.println(Utils.getPassword(mail));
                }
            }
        });

        forgotPassword.open();
    }

    private void actionSignUp() {
        SignUp signUp = new SignUp();
        signUp.getSignupButton().addActionListener(e -> register(signUp));
        signUp.getMdpField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    register(signUp);
            }
        });

        signUp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connexion.setVisible(true);
            }
        });
        signUp.open();
    }

    private void register(SignUp signUp) {
        String prenom = signUp.getPrenomField().getText();
        String nom = signUp.getNomField().getText();
        String mail = signUp.getMailField().getText();
        String motDePasse = Utils.hashPassword(Arrays.toString(signUp.getMdpField().getPassword()));

        utilisateur = new Utilisateur(prenom, nom, mail, motDePasse);
        try {
            Utils.registerUser(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getMail(), utilisateur.getMotdepasse());
            try {
                Utils.getProperties().setProperty("lastMail", mail);
                Utils.getProperties().storeToXML(new FileOutputStream("properties.xml"), "Sauvegarde");
            } catch (IOException e) {
                e.printStackTrace();
            }
            signUp.dispose();
            connexion();
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, "L'email est déjà utilisé par un autre utilisateur", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void connexion() {
        mainView = new MainView();
        init();
        mainView.open();
    }

    private void initListSalles() {
        DefaultListModel<String> defaultListModel = new DefaultListModel<>();

        List<Salle> salles = Utils.getSalles();

        for (Salle salle : salles) {
            defaultListModel.addElement(salle.getNomSalle());
        }

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

        List<Reservation> reservations = Utils.checkAllReservations();

        for (Reservation reservation : reservations) {
            Vector<String> data = new Vector<>();
            data.add(reservation.getUtilisateur().getPrenom() + " " + reservation.getUtilisateur().getNom());
            data.add(reservation.getSalle().getNomSalle());
            data.add(reservation.getDateDebut().toString());
            data.add(reservation.getDateFin().toString());
            defaultTableModel.addRow(data);
        }

        mainView.getTableReservations().setModel(defaultTableModel);
    }

    private void addReservation() {

    }

    private void initDaysHours() {
        for (int i = 0; i < 30; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            mainView.getBoxJourDebut().addItem(Date.valueOf(date.toString()).toString());
            mainView.getBoxJourFin().addItem(Date.valueOf(date.toString()).toString());
        }

        for (int i = 0; i < 23; i++) {
            LocalTime time = LocalTime.of(0,0).plusHours(i);
            mainView.getBoxHeureDebut().addItem(time.toString());
        }

        // todo à corriger et prendre en compte la date
        mainView.getBoxHeureDebut().addItemListener(e -> {
            LocalTime time = LocalTime.parse((CharSequence) Objects.requireNonNull(mainView.getBoxHeureDebut().getSelectedItem()));
            int i = 0;
            while (i <= 18) {
                time = time.plusHours(1);
                mainView.getBoxHeureFin().addItem(time.toString());
                i++;
            }
        });
    }

    private void initBoxSalles() {
        for (Salle salle : Utils.getSalles()) {
            mainView.getBoxSalle().addItem(salle.getNomSalle());
        }
    }

    private void initBoxReservations() {
        for (Reservation reservation : utilisateur.getReservations()) {
            mainView.getReservationBox().addItem(reservation.toString());
        }
    }

    private void deleteReservation() {
        String[] reservation = String.valueOf(mainView.getReservationBox().getSelectedItem()).split("le");
        String nomSalle = reservation[0].trim();
        String dateDebut = reservation[1].trim();
        Date debut = Date.valueOf(dateDebut);

        utilisateur.deleteReservation(nomSalle, debut);
    }

    private void initReservations() {
        for (Reservation reservation : utilisateur.getReservations()) {
            mainView.getReservationBox().addItem(reservation.toString());
        }
    }
}
