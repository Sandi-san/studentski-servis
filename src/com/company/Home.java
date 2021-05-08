package com.company;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import java.awt.Color;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class Home {
    String filename = null;
    ImageIcon ic = null;
    File f = null;
    static String mail_studenta, mail_admina;
    static int id_o, id_p, id_k, id_n;
    static int p_Mesta;

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
    private JLabel podjetjeLabel;
    private JTextField textField7;
    private JComboBox comboBox1;
    private JTextField textField8;
    private JComboBox comboBox2;
    private JButton Btn_AddCompany;
    private JButton posodobiButtonC;
    private JButton deleteButtonC;
    private JTable companyTable;
    private JLabel krajiLabel;
    private JTextField textField9;
    private JButton dodajButtonK;
    private JButton zbrisiButtonK;
    private JButton posodobiButtonK;
    private JTable krajiTable;
    private JTextField textField10;
    private JLabel narocanjeLabel;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTable narocanjeTable;
    private JButton zbrisiButtonN;
    private JButton Btn_StudentPrijava;
    private JButton Btn_StudentReg;
    private JButton studentSignOutButton;
    private JTextField textField14;
    private JButton spremeniSliko;
    private JLabel slikaIzBaze;
    private JButton btn_ShowChart;

    public static void DobMail(String ab){ mail_admina = ab; }
    public static void MailStudenta(String a){mail_studenta = a;}

    public Home(){
        setTables();
        spremeniSliko.setVisible(false);
        slika2.setVisible(false);
        display.setVisible(false);
        slikaIzBaze.setVisible(false);

        if(mail_admina == null){
            signOutButton.setVisible(false);
        }
        else
        {
            Btn_Prijava.setVisible(false);
            Btn_Reg.setVisible(false);
        }

        if(mail_studenta == null)
            studentSignOutButton.setVisible(false);
        else if(mail_studenta != null){
            studentSignOutButton.setVisible(true);
            Btn_StudentPrijava.setVisible(false);
            Btn_StudentReg.setVisible(false);
        }

        krajiCombo.addItem("Vse");
        comboBox1.addItem("Vse");

        dc.Return_Kraje().forEach((e) -> krajiCombo.addItem(e));
        dc.Return_Kraje().forEach((e) -> krajCombo.addItem(e));
        dc.Return_Kraje().forEach((e) -> comboBox2.addItem(e));
        dc.Return_Podjetja().forEach((e) -> podjetjeCombo.addItem(e));
        dc.Return_Kraje().forEach((e) -> comboBox1.addItem(e));

        krajiCombo.addItemListener(itemEvent -> {
            String ime = krajiCombo.getSelectedItem().toString();
            DefaultTableModel model = (DefaultTableModel)postsTable.getModel();
            model.setRowCount(0);
            String n = "Naroči se";
            if(ime.equals("Vse")){
                for(DelovnoMesto item:dc.Return_Objave()){
                    model.addRow(new Object[]{item.Naziv, item.Opis, item.Placa, item.Trajanje, item.Delovnik, item.Sifra, item.Prosto, item.Kraj, item.Podjetje, slikaIzBaze, n});
                }
            }
            else{
                for(DelovnoMesto item :dc.Return_Kraj_Objava(ime)){
                    model.addRow(new Object[]{item.Naziv, item.Opis, item.Placa, item.Trajanje, item.Delovnik, item.Sifra, item.Prosto, item.Kraj, item.Podjetje, slikaIzBaze, n});
                }
            }
            postsTable.getColumn("Slika").setCellRenderer(new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable jTable, Object o, boolean b, boolean b1, int i, int i1) {
                    TableColumn cm = jTable.getColumn("Slika");
                    cm.setMaxWidth(60);
                    jTable.setRowHeight(60);
                    return (Component) o;
                }
            });
            postsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
            postsTable.getColumnModel().getColumn(10).setCellEditor(new ButtonEditor(new JTextField()));
        });

        title.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        podjetjeLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        krajiLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        narocanjeLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));

        JFrame jframe = new JFrame("Home");
        jframe.setContentPane(homePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(1200, 900);
        //jframe.setSize(1500, 1000);
        jframe.setVisible(true);

        textField11.setEditable(false);
        textField12.setEditable(false);
        textField13.setEditable(false);
        textField14.setEditable(false);

        Btn_AddPost.addActionListener(actionEvent -> {
            if(mail_admina != null){
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
                String n = "Naroči se";

                if(naziv == "" || desc== "" || placa== "" || trajanje== "" || d== "" || sifra== "" || textField6.toString()== "" || ic == null)
                    JOptionPane.showMessageDialog(null, "Moraš vse vnesti");
                else{
                    DatabaseConnection db = new DatabaseConnection();

                    //infinite errors ker iz baze vzamem pot panisem convertu pot v actual sliko
                    //AddRowToTables(new Object[]{naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje, display, n});
                    File saveFile = new File("src/images/"+ filename);
                    try {
                        if(filename.toLowerCase().endsWith(".jpg")){
                            BufferedImage originalImage = ImageIO.read(f);
                            ImageIO.write(originalImage, "jpg", saveFile);
                        }
                        else if(filename.toLowerCase().endsWith(".png")){
                            BufferedImage originalImage = ImageIO.read(f);
                            ImageIO.write(originalImage, "png", saveFile);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    postsTable.getColumn("Slika").setCellRenderer(new TableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable jTable, Object o, boolean b, boolean b1, int i, int i1) {
                            TableColumn cm = jTable.getColumn("Slika");
                            cm.setMaxWidth(60);
                            jTable.setRowHeight(60);
                            return (Component) o;
                        }
                    });
                    db.CreatePost(naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje, name, saveFile.toString());
                }

                textArea1.setText("");
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");
                textField5.setText("");
                textField6.setText("");
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });

        brisanjeObjavButton.addActionListener(actionEvent -> {
            if(mail_admina != null){
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
                brisanjeObjavButton.setEnabled(false);
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });

        posodabljanjeObjavButton.addActionListener(actionEvent -> {
            if(mail_admina != null){
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
                    //postsTable.getSelectionModel().clearSelection();
                    DefaultTableModel modelPosts = (DefaultTableModel)postsTable.getModel();
                    model.setRowCount(0);
                    /*for(String line:dc.Return_Objave()){
                        modelPosts.addRow(line.split(","));
                    }*/
                    postsTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
                    postsTable.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JTextField()));
                    posodabljanjeObjavButton.setEnabled(false);
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
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

                p_Mesta = dc.Return_ProstaMesta(sifra);

                id_o = dc.Get_ID_Objave(naziv, opis, placa, trajanje, delovnik, sifra, prosto, kraj, podjetje);
            }
        });

        dodajSlikoButton.addActionListener(actionEvent -> {
            JFrame fr = new JFrame("Odpri sliko");
            FileDialog fd = new FileDialog(fr, "Naloži sliko", FileDialog.LOAD);
            fd.setDirectory("C:\\");
            fd.setFile("*.jpg;*.png");

            fd.setVisible(true);
            filename = fd.getFile();
            if (filename == null)
                JOptionPane.showMessageDialog(null,"Preklicali ste izbiro");
            else{
                String path = fd.getDirectory() + fd.getFile();
                f = new File(path); //pot do slike
                ic = new ImageIcon(f.toString());
                Image newimg = ic.getImage().getScaledInstance(60, 60,  Image.SCALE_SMOOTH);
                display.setIcon(new ImageIcon(newimg));
                dodajSlikoButton.setVisible(false);
                slika2.setVisible(true);
                slika.setVisible(false);
                dodajSlikoButton.setVisible(false);
                spremeniSliko.setVisible(true);
                display.setVisible(true);
            }
        });
        signOutButton.addActionListener(actionEvent -> {
            mail_admina = null;
            Btn_Prijava.setVisible(true);
            Btn_Reg.setVisible(true);
            signOutButton.setVisible(false);
        });

        Btn_Reg.addActionListener(actionEvent -> {
            jframe.dispose();
            new registracija();
        });

        Btn_Prijava.addActionListener(actionEvent -> {
            jframe.dispose();
            new prijava();
        });

        Btn_AddCompany.addActionListener(actionEvent -> {
            if(mail_admina != null){
                String naslov = textField7.getText();
                String telefon = textField8.getText();
                String kraj = comboBox2.getSelectedItem().toString();

                dc.AddCompany(naslov, telefon, kraj);
                //AddRowToTables(new Object[]{naslov, telefon, kraj});

                textField7.setText("");
                textField8.setText("");
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });


        companyTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                deleteButtonC.setEnabled(true);
                posodobiButtonC.setEnabled(true);

                DefaultTableModel model = (DefaultTableModel)companyTable.getModel();
                int index = companyTable.getSelectedRow();

                textField7.setText(model.getValueAt(index,0).toString());
                textField8.setText(model.getValueAt(index,1).toString());
                comboBox2.setSelectedItem(model.getValueAt(index,2).toString());

                String naslov = model.getValueAt(index,0).toString();
                String fonska = model.getValueAt(index,1).toString();
                String k = model.getValueAt(index,2).toString();
                id_p = dc.Get_ID_Podjetja(naslov, fonska, k);
            }
        });

        posodobiButtonC.addActionListener(actionEvent -> {
            if(mail_admina != null){
                String naslov = textField7.getText();
                String telefon = textField8.getText();
                String kraj = comboBox2.getSelectedItem().toString();

                dc.Posodobi_Podjetje(id_p, naslov, telefon, kraj);

                DefaultTableModel model = (DefaultTableModel)companyTable.getModel();
                model.setRowCount(0);
                textField7.setText("");
                textField8.setText("");
                for(String line:dc.Return_Vsa_Podjetja()){
                    model.addRow(line.split(","));
                }
                posodobiButtonC.setEnabled(false);
                deleteButtonC.setEnabled(false);
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });

        comboBox1.addItemListener(itemEvent -> {
            String ime = comboBox1.getSelectedItem().toString();
            DefaultTableModel model = (DefaultTableModel)companyTable.getModel();
            model.setRowCount(0);
            if(ime.equals("Vse")){
                for(String line:dc.Return_Vsa_Podjetja()){
                    model.addRow(line.split(","));
                }
            }
            else{
                for(String line:dc.Return_Kraj_Podjetja(ime)){
                    model.addRow(line.split(","));
                }
            }
        });

        deleteButtonC.addActionListener(actionEvent -> {
            if(mail_admina != null){
                dc.Brisi_Podjetje(id_p);
                textField7.setText("");
                textField8.setText("");
                DefaultTableModel modelCompany = (DefaultTableModel)companyTable.getModel();
                int index = companyTable.getSelectedRow();
                modelCompany.removeRow(index);
                deleteButtonC.setEnabled(false);
                posodobiButtonC.setEnabled(false);
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });

        dodajButtonK.addActionListener(actionEvent -> {
            if(mail_admina != null){
                String ime = textField9.getText();
                int posta = Integer.parseInt(textField10.getText());

                dc.SaveKraj(ime, posta);
                //AddRowToTables(new Object[]{ime, posta});

                textField9.setText("");
                textField10.setText("");
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });

        krajiTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                zbrisiButtonK.setEnabled(true);
                posodobiButtonK.setEnabled(true);

                DefaultTableModel model = (DefaultTableModel)krajiTable.getModel();
                int index = krajiTable.getSelectedRow();

                textField9.setText(model.getValueAt(index,0).toString());
                textField10.setText(model.getValueAt(index,1).toString());

                String a = model.getValueAt(index,0).toString();
                int b = Integer.parseInt(textField10.getText());

                id_k = dc.Get_ID_Kraja(a, b);
            }
        });

        posodobiButtonK.addActionListener(actionEvent -> {
            if(mail_admina != null)
            {
                String ime = textField9.getText();
                int posta = Integer.parseInt(textField10.getText());

                dc.Posodabljanje_Kraja(id_k, ime, posta);
                DefaultTableModel model = (DefaultTableModel)krajiTable.getModel();
                model.setRowCount(0);
                textField9.setText("");
                textField10.setText("");
                for(String line:dc.Return_Vse_Kraje()){
                    model.addRow(line.split(","));
                }
                posodobiButtonK.setEnabled(false);
                zbrisiButtonK.setEnabled(false);
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });

        zbrisiButtonK.addActionListener(actionEvent -> {
            if(mail_admina != null){
                dc.Zbrisi_Kraj(id_k);
                textField9.setText("");
                textField10.setText("");
                DefaultTableModel modelCompany = (DefaultTableModel)krajiTable.getModel();
                int index = krajiTable.getSelectedRow();
                modelCompany.removeRow(index);
                posodobiButtonK.setEnabled(false);
                zbrisiButtonK.setEnabled(false);
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");

        });

        narocanjeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                zbrisiButtonN.setEnabled(true);
                DefaultTableModel model = (DefaultTableModel)narocanjeTable.getModel();
                int index = narocanjeTable.getSelectedRow();

                textField11.setText(model.getValueAt(index,0).toString());
                textField12.setText(model.getValueAt(index,1).toString());
                textField13.setText(model.getValueAt(index,2).toString());
                textField14.setText(model.getValueAt(index,3).toString());

                String datum = model.getValueAt(index,0).toString();
                String student = model.getValueAt(index,1).toString();
                String d_mesto = model.getValueAt(index,2).toString();
                String sifra = model.getValueAt(index,3).toString();
                Timestamp ts = Timestamp.valueOf(datum);

                id_n = dc.Get_ID_Narocanja(ts, student, d_mesto, sifra);
            }
        });
        Btn_StudentPrijava.addActionListener(actionEvent -> {
            new prijavaStudenti();
            jframe.dispose();
        });
        Btn_StudentReg.addActionListener(actionEvent -> {
            new regStudenti();
            jframe.dispose();
        });

        zbrisiButtonN.addActionListener(actionEvent -> {
            if(mail_admina != null){
                dc.Delete_Narocanje(id_n);
                DefaultTableModel modelNarocanje = (DefaultTableModel)narocanjeTable.getModel();
                int index = narocanjeTable.getSelectedRow();
                modelNarocanje.removeRow(index);

                textField11.setText(null);
                textField12.setText(null);
                textField13.setText(null);
                textField14.setText(null);
            }
            else
                JOptionPane.showMessageDialog(null, "Morate imeti administratorske pravice da izvedete to akcijo");

            zbrisiButtonN.setEnabled(false);
        });

        studentSignOutButton.addActionListener(actionEvent -> {
            mail_studenta = null;
            studentSignOutButton.setVisible(false);
            Btn_StudentReg.setVisible(true);
            Btn_StudentPrijava.setVisible(true);
        });
        spremeniSliko.addActionListener(actionEvent -> {
            JFrame fr = new JFrame("Odpri sliko");
            FileDialog fd = new FileDialog(fr, "Naloži sliko", FileDialog.LOAD);
            fd.setDirectory(f.toString());
            fd.setFile("*.jpg;*.png");

            fd.setVisible(true);
            filename = fd.getFile();
            if (filename == null)
                JOptionPane.showMessageDialog(null,"Preklicali ste izbiro");
            else{
                String path = fd.getDirectory() + fd.getFile();
                f = new File(path); //pot do slike
                ic = new ImageIcon(f.toString());
                Image newimg = ic.getImage().getScaledInstance(60, 60,  Image.SCALE_SMOOTH);
                display.setIcon(new ImageIcon(newimg));
            }
        });
        btn_ShowChart.addActionListener(actionEvent -> {
            //if - else(če je combo box text "Vse" nardi graf za skupno število delovnih, študentov pa število študentov naročenih
            DefaultCategoryDataset chartset = new DefaultCategoryDataset();
            chartset.setValue(20, "Št. delovnih mest v tem kraju", "a");
            chartset.setValue(24, "Št. študentov v tem kraju", "b" );
            chartset.setValue(60, "Št. študentov naročenih na delovna mesta v tem kraju", "c" );
            //chartset.setValue(26, "test", "krneki" );

            JFreeChart jchart = ChartFactory.createBarChart(krajiCombo.getSelectedItem().toString(),
                    "Informacije o kraju", "Število", chartset, PlotOrientation.VERTICAL,
                    true, true, false);
            CategoryPlot plot = jchart.getCategoryPlot();
            plot.setRangeGridlinePaint(Color.red);

            ChartFrame chartfrm = new ChartFrame("Delovna mesta", jchart, true);
            chartfrm.setVisible(true);
            chartfrm.setSize(1000, 600);
        });
    }

    private void setTables(){
        String[] columnsPosts = {"Naziv", "Opis", "Plača", "Trajanje", "Delovnik", "Šifra", "Prosto", "Kraj", "Podjetje", "Slika", "Naročanje"};
        String[] columnsCompany = {"Naslov", "Telefon", "Kraj"};
        String[] columnsKraji = {"Ime", "Poštna številka"};
        String[] columnsNarocanja = {"Datum", "Študent", "Delovno mesto", "Šifra"};

        postsTable.setModel(new DefaultTableModel(
                null,
                columnsPosts
        ));

        ArrayList<DelovnoMesto> seznam = dc.Return_Objave();
        DefaultTableModel modelPosts = (DefaultTableModel)postsTable.getModel();
        String n = "Naroči se";
        for(DelovnoMesto item: seznam){
            File p = new File(item.FilePath);
            ImageIcon ih = new ImageIcon(p.toString());
            Image newimg = ih.getImage().getScaledInstance(60, 60,  Image.SCALE_SMOOTH);
            slikaIzBaze.setIcon(new ImageIcon(newimg));
            //label se overwrita pa ne dela, možno rešitev: label za vsako sliko
            modelPosts.addRow(new Object[]{item.Naziv, item.Opis, item.Placa, item.Trajanje, item.Delovnik, item.Sifra, item.Prosto, item.Kraj, item.Podjetje, slikaIzBaze, n});
        }

        postsTable.getColumn("Slika").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jTable, Object o, boolean b, boolean b1, int i, int i1) {
                TableColumn cm = jTable.getColumn("Slika");
                cm.setMaxWidth(60);
                jTable.setRowHeight(60);
                return (Component) o;
            }
        });
        postsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
        postsTable.getColumnModel().getColumn(10).setCellEditor(new ButtonEditor(new JTextField()));

        companyTable.setModel(new DefaultTableModel(
                null,
                columnsCompany
        ));
        DefaultTableModel modelCompany = (DefaultTableModel)companyTable.getModel();
        for(String line:dc.Return_Vsa_Podjetja()){
            modelCompany.addRow(line.split(","));
        }

        krajiTable.setModel(new DefaultTableModel(
                null,
                columnsKraji
        ));
        DefaultTableModel modelKraji = (DefaultTableModel)krajiTable.getModel();
        for(String line:dc.Return_Vse_Kraje()){
            modelKraji.addRow(line.split(","));
        }

        narocanjeTable.setModel(new DefaultTableModel(
                null,
                columnsNarocanja
        ));
        DefaultTableModel modelNarocanja = (DefaultTableModel)narocanjeTable.getModel();
        for(String line:dc.Return_Narocanja()){
            modelNarocanja.addRow(line.split(","));
        }

    }
    private void AddRowToTables(Object[] data){
        if(data.length == 2){
            DefaultTableModel modelKraji = (DefaultTableModel)krajiTable.getModel();
            modelKraji.addRow(data);
        }
        else if(data.length == 3){
            DefaultTableModel modelCompany = (DefaultTableModel)companyTable.getModel();
            modelCompany.addRow(data);
        }
        else if(data.length == 4){
            DefaultTableModel narocanjeModel = (DefaultTableModel)narocanjeTable.getModel();
            narocanjeModel.addRow(data);
        }
        else if(data.length == 10){
            DefaultTableModel modelPosts = (DefaultTableModel)postsTable.getModel();
            modelPosts.addRow(data);
        }
        else if(data.length == 11){
            DefaultTableModel modelPosts = (DefaultTableModel)postsTable.getModel();
            modelPosts.addRow(data);
        }
    }
    class ButtonRenderer extends JButton implements TableCellRenderer
    {
        public ButtonRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row, int col) {
            setText((obj==null) ? "":obj.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor
    {
        protected JButton btn;
        private String lbl ;
        private Boolean clicked;

        public ButtonEditor(JTextField txt) {
            super(txt);
            txt.setEditable(false);

            btn=new JButton();
            btn.setOpaque(true);

            btn.addActionListener(actionEvent -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {
            lbl=(obj==null) ? "":obj.toString();
            btn.setText(lbl);
            clicked=true;
            return btn;
        }

        @Override
        public Object getCellEditorValue() {
            int prosto = Integer.parseInt(textField6.getText());
            if(mail_studenta != null){
                if(prosto > 0){
                    if(clicked)
                    {
                        //trigger za prosta mesta treba fiksat
                        DefaultTableModel postsModel = (DefaultTableModel)postsTable.getModel();
                        int index = postsTable.getSelectedRow();
                        String naziv = postsModel.getValueAt(index,0).toString();
                        String sifra = postsModel.getValueAt(index,5).toString();
                        java.util.Date utilDate = new java.util.Date();
                        Timestamp datum = new Timestamp(utilDate.getTime());
                        String s = datum.toString().split("\\.")[0];
                        Timestamp ts = Timestamp.valueOf(s);

                        dc.Insert_Narocanja(ts, mail_studenta, sifra);
                        AddRowToTables(new Object[]{ts, mail_studenta, naziv, sifra});
                        //nared da se zmanjsajo prosta mesta ko se en naroci
                    }
                    clicked = false;
                }

            }
            else
                JOptionPane.showMessageDialog(null, "Niste prijavljeni");
            return new String(lbl);
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}

