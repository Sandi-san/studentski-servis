package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class regStudenti {
    private JPanel panel1;
    private JLabel mainTitle;
    private JButton button1;
    private JButton button2;
    private JLabel spolButton;
    private JButton SignUp;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField1;

    public regStudenti() {
        JFrame jframe = new JFrame("Registracija");
        jframe.setContentPane(panel1);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(600, 400);
        jframe.setResizable(false);
        jframe.setVisible(true);
        mainTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        //textField1.setPreferredSize(new Dimension(10,5));

        AtomicReference<Character> spol = new AtomicReference<>('M');
        spolButton.setText("Moški");

        button2.addActionListener(actionEvent ->
        {
            spol.set('Ž');
            spolButton.setText("Ženska");
        });

        button1.addActionListener(actionEvent ->
        {
            spol.set('M');
            spolButton.setText("Moški");
        });

        SignUp.addActionListener(actionEvent -> {
            JOptionPane.showMessageDialog(null, spol.toString());
            /*
            String email = textField1.getText();
            char[] geslo = passwordField1.getPassword();

            DatabaseConnection con = new DatabaseConnection();
            try {
                String newPass = con.Encrypt(String.valueOf(geslo));

                con.AdminReg(email, newPass);
                JOptionPane.showMessageDialog(null, "Dodaja uspešna");

                jframe.dispose();
                new Home();

            } catch (Exception e) {
                e.printStackTrace();
            }
            */
        });
    }

    public static void main(String[] args) {
        new regStudenti();
    }
}
