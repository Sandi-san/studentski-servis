package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Home {
    Database dc = new Database();
    private JPanel homePanel;
    private JButton Btn_Prijava;
    private JButton Btn_Registracija;
    private JTable postsTable;
    private JScrollPane postsPane;
    private JComboBox krajiCombo;

    public Home(){
        JFrame jframe = new JFrame("Home");
        jframe.setContentPane(homePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(800, 500);
        jframe.setVisible(true);

        dc.Return_Kraje().forEach((e) -> krajiCombo.addItem(e));

        setTable();

        Btn_Prijava.addActionListener(actionEvent -> {
            homePanel.setVisible(false);
            jframe.setVisible(false);
            new prijava();
        });

        Btn_Registracija.addActionListener(actionEvent -> {
            homePanel.setVisible(false);
            jframe.setVisible(false);
            new registracija();
        });

        krajiCombo.addItemListener(itemEvent -> {
            String ime = krajiCombo.getSelectedItem().toString();
            DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
            model.setRowCount(0);
            for(String line:dc.Return_Kraj_Objava(ime)){
                model.addRow(line.split(","));
            }
        });
    }

    private void setTable(){
        //JOptionPane.showOptionDialog(null, data);
        String[] columns = {"Naziv", "Opis", "Plača", "Trajanje", "Prosta mesta", "Kraj", "Podjetje"};

        postsTable.setModel(new DefaultTableModel(
                null,
                columns
        ));

        DefaultTableModel model = (DefaultTableModel)postsTable.getModel();

        for(String line:dc.Return_Objave()){
            model.addRow(line.split(","));
        }

    }

    /*public void AddRowToTable(Object[] data){
        DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
        model.addRow(data);
    }*/
}
