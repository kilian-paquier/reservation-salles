package com.view;

import javax.swing.*;

public class ForgotPassword extends JFrame {
    private JPanel globalPanel;
    private JTextField mailField;
    private JButton forgotButton;

    public ForgotPassword() {
        setContentPane(globalPanel);
        setTitle("Mot de passe oublié");
    }

    public JTextField getMailField() {
        return mailField;
    }

    public JButton getForgotButton() {
        return forgotButton;
    }
}
