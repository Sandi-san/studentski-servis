package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Home {
    ImageIcon ic = null;
    static String mail_admina;
    static int id_o;
    DatabaseConnection dc = new DatabaseConnection();
    private JPanel homePanel;
    private JButton Btn_Prijava;
    private JTable postsTable;
    private JScrollPane postsPane;
    private JComboBox krajiCombo;
    private JButton Btn_AddPost;
    private JButton brisanjeObjavButton;
    private JButton posodabljanjeObjavButton;
    private JTextField textField1;
    private JTextArea textArea1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JComboBox krajCombo;
    private JComboBox podjetjeCombo;
    private JButton dodajSlikoButton;
    private JLabel slika;
    private JLabel slika2;
    private JLabel display;
    private JButton signOutButton;
    private JButton Btn_Reg;
    private JLabel title;
    private JTabbedPane JTabPane1;

    public static void DobMail(String ab){
        mail_admina = ab;
    }

    public Home(){
        JFrame jframe = new JFrame("Home");
        jframe.setContentPane(homePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(1000, 700);
        jframe.setVisible(true);

        dc.Return_Kraje().forEach((e) -> krajiCombo.addItem(e));
        dc.Return_Kraje().forEach((e) -> krajCombo.addItem(e));
        dc.Return_Podjetja().forEach((e) -> podjetjeCombo.addItem(e));

        if(mail_admina == null)
            signOutButton.setVisible(false);
        else
        {
            Btn_Prijava.setVisible(false);
            Btn_Reg.setVisible(false);
        }
        setTable();
        slika2.setVisible(false);
        display.setVisible(false);

        krajiCombo.addItemListener(itemEvent -> {
            String ime = krajiCombo.getSelectedItem().toString();
            DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
            model.setRowCount(0);
            for(String line:dc.Return_Kraj_Objava(ime)){
                model.addRow(line.split(","));
            }
        });

        Btn_AddPost.addActionListener(actionEvent -> {
            String naziv = textField1.getText();
            String desc = textArea1.getText();
            String placa = textField2.getText();
            String trajanje = textField3.getText();
            String d = textField4.getText();
            String sifra = textField5.getText();
            int fraj = Integer.parseInt(textField6.getText());
            String kraj = krajCombo.getSelectedItem().toString();
            String podjetje = podjetjeCombo.getSelectedItem().toString();
            String name = mail_admina;

            DatabaseConnection db = new DatabaseConnection();
            db.CreatePost(naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje, name);
            AddRowToTable(new Object[]{naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje});

            textArea1.setText("");
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
            textField6.setText("");
        });

        brisanjeObjavButton.addActionListener(actionEvent -> {
            dc.Deletanje_Objav(id_o);

            textArea1.setText("");
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
            textField6.setText("");

            DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
            int index = postsTable.getSelectedRow();
            model.removeRow(index);
        });

        posodabljanjeObjavButton.addActionListener(actionEvent -> {
            DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
            if(postsTable.getSelectedRowCount() == 1){
                String naziv = textField1.getText();
                String desc = textArea1.getText();
                String placa = textField2.getText();
                String trajanje = textField3.getText();
                String d = textField4.getText();
                String sifra = textField5.getText();
                int fraj = Integer.parseInt(textField6.getText());
                String kraj = krajCombo.getSelectedItem().toString();
                String podjetje = podjetjeCombo.getSelectedItem().toString();

                dc.Updatanje_Objav(id_o, naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje);

                model.setValueAt(naziv,postsTable.getSelectedRow(), 0);
                model.setValueAt(desc,postsTable.getSelectedRow(), 1);
                model.setValueAt(placa,postsTable.getSelectedRow(), 2);
                model.setValueAt(trajanje,postsTable.getSelectedRow(), 3);
                model.setValueAt(d,postsTable.getSelectedRow(), 4);
                model.setValueAt(sifra,postsTable.getSelectedRow(), 5);
                model.setValueAt(fraj,postsTable.getSelectedRow(), 6);
                model.setValueAt(kraj,postsTable.getSelectedRow(), 7);
                model.setValueAt(podjetje,postsTable.getSelectedRow(), 8);

                textArea1.setText("");
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");
                textField5.setText("");
                textField6.setText("");
                postsTable.getSelectionModel().clearSelection();
            }
        });

        postsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                brisanjeObjavButton.setEnabled(true);
                posodabljanjeObjavButton.setEnabled(true);

                DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
                int index = postsTable.getSelectedRow();

                textField1.setText(model.getValueAt(index,0).toString());
                textArea1.setText(model.getValueAt(index,1).toString());
                textField2.setText(model.getValueAt(index,2).toString());
                textField3.setText(model.getValueAt(index,3).toString());
                textField4.setText(model.getValueAt(index,4).toString());
                textField5.setText(model.getValueAt(index,5).toString());
                textField6.setText(model.getValueAt(index,6).toString());
                krajCombo.setSelectedItem(model.getValueAt(index,7).toString());
                podjetjeCombo.setSelectedItem(model.getValueAt(index,8).toString());

                String naziv = model.getValueAt(index,0).toString();
                String opis = model.getValueAt(index,1).toString();
                String placa = model.getValueAt(index,2).toString();
                String trajanje = model.getValueAt(index,3).toString();
                String delovnik = model.getValueAt(index,4).toString();
                String sifra = model.getValueAt(index,5).toString();
                int prosto = Integer.parseInt(textField6.getText());
                String kraj = model.getValueAt(index,7).toString();
                String podjetje = model.getValueAt(index,8).toString();

                id_o = dc.Get_ID_Objave(naziv, opis, placa, trajanje, delovnik, sifra, prosto, kraj, podjetje);
            }
        });

        dodajSlikoButton.addActionListener(actionEvent -> {
            JFrame fr = new JFrame("Open file");
            FileDialog fd = new FileDialog(fr, "Naloži sliko", FileDialog.LOAD);

            fd.setDirectory("C:\\");
            //fd.setFilenameFilter();
            fd.setFile("*.jpg;*.png");
            //fd.setFilenameFilter((File dir, String name) -> name.endsWith(".jpg"));
            fd.setVisible(true);
            String filename = fd.getFile();
            if (filename == null)
                System.out.println("You cancelled the choice");
            else{
                String path = fd.getDirectory() + fd.getFile();
                File f = new File(path);
                ic = new ImageIcon(f.toString());
                Image image = ic.getImage(); // transform it
                Image newimg = image.getScaledInstance(290, 220,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                ic = new ImageIcon(newimg);
                display.setIcon(ic);
                dodajSlikoButton.setVisible(false);
                slika2.setVisible(true);
                slika.setVisible(false);
                dodajSlikoButton.setVisible(false);
                display.setVisible(true);
            }
        });
        signOutButton.addActionListener(actionEvent -> {
            mail_admina = null;
            Btn_Prijava.setVisible(false);
            Btn_Reg.setVisible(false);
        });

        Btn_Reg.addActionListener(actionEvent -> {
            //homePanel.setVisible(false);
            jframe.dispose();;
            new registracija();
        });

        Btn_Prijava.addActionListener(actionEvent -> {
            //homePanel.setVisible(false);
            jframe.dispose();
            new prijava();
        });
    }

    private void setTable(){
        String[] columns = {"Naziv", "Opis", "Plača", "Trajanje", "Delovnik", "Šifra", "Prosto", "Kraj", "Podjetje", "Slika", "Naročanje"};

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
