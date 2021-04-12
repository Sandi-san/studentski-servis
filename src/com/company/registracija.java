package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registracija {
    DatabaseConnection dc = new DatabaseConnection();

    private JButton Btn_SignUp;
    private JTextField textField1;
    private JLabel mainTitle;
    private JPanel panel1;
    private JPasswordField passwordField1;

    public registracija() {
        JFrame jframe = new JFrame("Registracija");
        jframe.setContentPane(panel1);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(600, 400);
        jframe.setResizable(false);
        jframe.setVisible(true);
        mainTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        //textField1.setPreferredSize(new Dimension(10,5));

        Btn_SignUp.addActionListener(actionEvent -> {
            String email = textField1.getText();
            char[] geslo = passwordField1.getPassword();
            //JOptionPane.showMessageDialog(null, "Hello world");

            DatabaseConnection con = new DatabaseConnection();

            try {
                String newPass = con.Encrypt(String.valueOf(geslo));

                //con.CheckUser(email, newPass);

                con.AdminReg(email, newPass);
                JOptionPane.showMessageDialog(null, "Dodaja uspešna");

                //Odobri dostop na naslednji form
                //new Home();
                panel1.setEnabled(false);
                jframe.dispose();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new registracija();
    }
}
