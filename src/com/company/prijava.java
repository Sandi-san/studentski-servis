package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prijava {
    private JButton Btn_SignIn;
    private JTextField textField1;
    private JPanel panel1;
    private JPasswordField passwordField1;
    private JLabel maintitle;

    public prijava() {
        JFrame jframe = new JFrame("Prijava");
        jframe.setContentPane(panel1);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(600, 400);
        jframe.setResizable(false);
        jframe.setVisible(true);
        maintitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));

        Btn_SignIn.addActionListener(actionEvent -> {
            String email = textField1.getText();
            char[] geslo = passwordField1.getPassword();
            //JOptionPane.showMessageDialog(null, "Hello world");

            DatabaseConnection con = new DatabaseConnection();

            try {
                String newPass = con.Encrypt(String.valueOf(geslo));
                boolean yes = con.CheckUser(email, newPass);
                if (yes == true) {
                    JOptionPane.showMessageDialog(null, "Vpis uspešen");
                    Home.DobMail(email);
                    new Home();
                    panel1.setEnabled(false);
                    jframe.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Vpis NI uspel");
                }
                //con.CheckUser(email, newPass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new prijava();
    }
}
