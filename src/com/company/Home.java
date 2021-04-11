package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home {
    static int id_o;
    Database dc = new Database();
    private JPanel homePanel;
    private JButton Btn_Prijava;
    private JButton Btn_Registracija;
    private JTable postsTable;
    private JScrollPane postsPane;
    private JComboBox krajiCombo;
    private JButton Btn_AddPost;
    private JButton brisanjeObjavButton;
    private JButton posodabljanjeObjavButton;

    public Home(){
        JFrame jframe = new JFrame("Home");
        jframe.setContentPane(homePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(800, 500);
        jframe.setVisible(true);

        dc.Return_Kraje().forEach((e) -> krajiCombo.addItem(e));

        setTable();

        /*int column = 0;
        int row = postsTable.getSelectedRow();
        String value = postsTable.getModel().getValueAt(row, column).toString();
        System.out.println(value);*/

        /*postsTable.getSelectionModel().addListSelectionListener(listSelectionEvent -> {
            System.out.println(postsTable.getValueAt(postsTable.getSelectedRow(), 0).toString());
        });*/

        //JOptionPane.showOptionDialog(null, value);
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

        Btn_AddPost.addActionListener(actionEvent -> {
            homePanel.setVisible(false);
            jframe.setVisible(false);
            new dodajanjeObjav();
        });

        brisanjeObjavButton.addActionListener(actionEvent -> {
            dc.Deletanje_Objav(id_o);
        });

        posodabljanjeObjavButton.addActionListener(actionEvent -> {

        });
;
        postsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
                int index = postsTable.getSelectedRow();
                String naziv = model.getValueAt(index,0).toString();
                String opis = model.getValueAt(index,1).toString();
                String placa = model.getValueAt(index,2).toString();
                String trajanje = model.getValueAt(index,3).toString();
                int prosto = Integer.parseInt(model.getValueAt(index,4).toString());
                String kraj = model.getValueAt(index,5).toString();
                String podjetje = model.getValueAt(index,6).toString();

                System.out.println(dc.Get_ID_Objave(naziv, opis, placa, trajanje, prosto, kraj, podjetje));
                super.mouseClicked(mouseEvent);
            }
        });
    }

    private void setTable(){
        //JOptionPane.showOptionDialog(null, data);
        String[] columns = {"Naziv", "Opis", "Plača", "Trajanje", "Prosto", "Kraj", "Podjetje", "Naročanje"};

        postsTable.setModel(new DefaultTableModel(
                null,
                columns
        ));

        DefaultTableModel model = (DefaultTableModel)postsTable.getModel();

        for(String line:dc.Return_Objave()){
            model.addRow(line.split(","));
        }

    }

    public void AddRowToTable(Object[] data){
        DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
        model.addRow(data);
    }
}
