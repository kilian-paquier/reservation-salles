package com.view;

import javax.swing.*;

public class Connexion extends JFrame {
    private JPanel globalPanel;
    private JTextField mailField;
    private JPasswordField mdpField;
    private JButton seConnecterButton;
    private JLabel mdpForgot;
    private JLabel signUp;

    public Connexion() {
        setContentPane(globalPanel);
        setTitle("Connexion");
    }

    public void open() {

    }

    public JTextField getMailField() {
        return mailField;
    }

    public JPasswordField getMdpField() {
        return mdpField;
    }

    public JButton getSeConnecterButton() {
        return seConnecterButton;
    }

    public JLabel getMdpForgot() {
        return mdpForgot;
    }

    public JLabel getSignUp() {
        return signUp;
    }
}
