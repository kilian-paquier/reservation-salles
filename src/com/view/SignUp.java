package com.view;

import javax.swing.*;

public class SignUp extends JFrame {
    private JPanel globalPanel;
    private JTextField prenomField;
    private JTextField nomField;
    private JTextField mailField;
    private JButton signupButton;
    private JPasswordField mdpField;

    public SignUp() {
        setContentPane(globalPanel);
        setTitle("Création de compte");
    }

    public void open() {

    }

    public JTextField getPrenomField() {
        return prenomField;
    }

    public JTextField getNomField() {
        return nomField;
    }

    public JTextField getMailField() {
        return mailField;
    }

    public JButton getSignupButton() {
        return signupButton;
    }

    public JPasswordField getMdpField() {
        return mdpField;
    }
}
