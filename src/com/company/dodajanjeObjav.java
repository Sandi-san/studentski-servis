package com.company;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dodajanjeObjav {
    Database dc = new Database();
    Home hom =  new Home();
    private JPanel panel;
    private JTextField textField2;
    private JTextArea textArea1;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox krajCombo;
    private JComboBox podjetjeCombo;
    private JButton dodajButton;
    private JLabel krajLbl;
    private JLabel pLbl;
    private JTextField textField5;
    private JTextField textField6;
    private JButton nazajButton;
    private JTextField textField7;

    public dodajanjeObjav(){
        JFrame jframe = new JFrame("Insert");
        jframe.setContentPane(panel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(800, 500);
        jframe.setVisible(true);
        dc.Return_Kraje().forEach((e) -> krajCombo.addItem(e));

        dodajButton.addActionListener(actionEvent -> {
            String naziv = textField2.getText();
            String desc = textArea1.getText();
            String placa = textField1.getText();
            String trajanje = textField3.getText();
            String d = textField5.getText();
            String sifra = textField6.getText();
            int fraj = Integer.parseInt(textField4.getText());
            String kraj = krajCombo.getSelectedItem().toString();
            String podjetje = textField7.getText();
            int admin = 1;

            Database db = new Database();
            db.CreatePost(naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje, admin);
            hom.AddRowToTable(new Object[]{naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje});
            panel.setVisible(false);
            jframe.setVisible(false);
            new Home();
        });

        nazajButton.addActionListener(actionEvent -> {
            panel.setVisible(false);
            jframe.setVisible(false);
            new Home();
        });
    }
}
