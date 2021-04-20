package com.company;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class regStudenti {
    private JPanel panel1;
    private JLabel mainTitle;
    private JButton button1;
    private JButton button2;
    private JLabel spolButton;
    private JButton SignUp;
    private JTextField textFieldDatum;
    private JTextField textFieldTelefon;
    private JTextField textFieldUsername;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JTextField textFieldPriimek;
    private JTextField textFieldIme;
    private JComboBox PrijavakrajiCombo;

    public regStudenti() {
        JFrame jframe = new JFrame("Registracija");
        jframe.setContentPane(panel1);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(600, 400);
        jframe.setResizable(false);
        jframe.setVisible(true);
        mainTitle.setFont(new Font("TimesRoman", Font.PLAIN, 30));

        //Prikaž kraje v comboboxu
        DatabaseConnection dc = new DatabaseConnection();
        dc.Return_Kraje().forEach((e) -> PrijavakrajiCombo.addItem(e));

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
            //sJOptionPane.showMessageDialog(null, spol.toString());

            String ime = textFieldIme.getText();
            String priimek = textFieldPriimek.getText();
            //spol
            String datumroj = textFieldDatum.getText();
            /*try {
                Date realdatumroj = new SimpleDateFormat("yyyy-MM-dd").parse(datumroj);
                System.out.println("pru: " + datumroj + "Drug: \t" + realdatumroj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
             */
            String tel = textFieldTelefon.getText();
            String user = textFieldUsername.getText();
            String email = textField1.getText();
            char[] geslo = passwordField1.getPassword();
            String kraj = PrijavakrajiCombo.getSelectedItem().toString();

            DatabaseConnection con = new DatabaseConnection();
            try {
                String newPass = con.Encrypt(String.valueOf(geslo));

                con.SignUp(ime, priimek, spol.toString(), datumroj, tel, user, email, newPass, kraj);
                JOptionPane.showMessageDialog(null, "Dodaja uspešna");

                jframe.dispose();
                new Home();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new regStudenti();
    }
}
