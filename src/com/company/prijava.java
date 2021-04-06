package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prijava {
    Database dc = new Database();

    private JButton Btn_SignIn;
    private JTextField textField1;
    private JPanel prijavaPanel;
    private JPasswordField passwordField1;

    public prijava() {
        JFrame jframe = new JFrame("Prijava");
        jframe.setContentPane(prijavaPanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(1050, 400);
        jframe.setVisible(true);


        Btn_SignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String email = textField1.getText();
                char[] geslo = passwordField1.getPassword();
                //JOptionPane.showMessageDialog(null, "Hello world");
            }
        });
    }

    public static void main(String[] args) {
        //JFrame frame = new JFrame("Prijava");
    }
}
