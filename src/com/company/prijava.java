package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prijava {
    DatabaseConnection dc = new DatabaseConnection();

    private JButton Btn_SignIn;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel panel1;
    private JPasswordField passwordField1;

    public prijava() {
        JFrame jframe = new JFrame("Prijava");
        jframe.setContentPane(panel1);
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

                DatabaseConnection con = new DatabaseConnection();

                try {
                    String newPass = con.Encrypt(String.valueOf(geslo));

                    System.out.println(newPass);

                    //con.CheckUser(email, newPass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        new prijava();
    }
}
