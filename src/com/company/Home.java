package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    DatabaseConnection dc = new DatabaseConnection();
    private JPanel homePanel;
    private JButton Btn_Prijava;
    private JButton Btn_Registracija;
    private JTable TablePosts;


    public Home(){
        JFrame jframe = new JFrame("Home");
        jframe.setContentPane(homePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(800, 500);
        jframe.setVisible(true);

        Btn_Prijava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                homePanel.setVisible(false);
                jframe.setVisible(false);
                new prijava();
            }
        });

        Btn_Registracija.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                homePanel.setVisible(false);
                jframe.setVisible(false);
                new registracija();
            }
        });
        //prikaž objave s tabelo(glavne informacija - prosta mesta, kratek opis, kontakt,..)
        //dc.Izpis_Objav();

    }
}
