package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Home {
    ImageIcon ic = null;
    static String mail_admina;
    static int id_o, id_p, id_k;
    static int p_Mesta;
    static String mail_studenta;
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
    private JButton signOutButton1;
    private JButton signInButton;
    private JButton signUpButton;
    private JComboBox comboBox1;
    private JTextField textField8;
    private JComboBox comboBox2;
    private JButton Btn_AddCompany;
    private JButton posodobiButtonC;
    private JButton deleteButtonC;
    private JTable companyTable;
    private JLabel krajiLabel;
    private JButton signOutButton2;
    private JButton signInButton1;
    private JButton signUpButton1;
    private JTextField textField9;
    private JButton dodajButtonK;
    private JButton zbrisiButtonK;
    private JButton posodobiButtonK;
    private JTable krajiTable;
    private JTextField textField10;
    private JLabel narocanjeLabel;
    private JTextField textField11;
    private JTextField textField12;
    private JButton signOutButton3;
    private JButton signInButton2;
    private JButton signUpButton2;
    private JTextField textField13;
    private JTable narocanjeTable;
    private JButton zbrisiButtonN;
    private JButton Btn_StudentPrijava;
    private JButton Btn_StudentReg;

    public static void DobMail(String ab){
        mail_admina = ab;
    }
    public static void MailStudenta(String a){mail_studenta = a;}

    public Home(){
        setTables();
        slika2.setVisible(false);
        display.setVisible(false);
        if(mail_admina == null){
            signOutButton.setVisible(false);
            signOutButton1.setVisible(false);
            signOutButton2.setVisible(false);
        }
        else
        {
            Btn_Prijava.setVisible(false);
            Btn_Reg.setVisible(false);
            signInButton.setVisible(false);
            signUpButton.setVisible(false);
            signInButton1.setVisible(false);
            signUpButton1.setVisible(false);
            signUpButton2.setVisible(false);
            signInButton2.setVisible(false);
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
            if(ime.equals("Vse")){
                for(String line:dc.Return_Objave()){
                    model.addRow(line.split(","));
                    postsTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
                    postsTable.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JTextField()));
                }
            }
            else{
                for(String line:dc.Return_Kraj_Objava(ime)){
                    model.addRow(line.split(","));
                    postsTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
                    postsTable.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JTextField()));
                }
            }

        });


        title.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        podjetjeLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        krajiLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        narocanjeLabel.setFont(new Font("TimesRoman", Font.PLAIN, 30));

        JFrame jframe = new JFrame("Home");
        jframe.setContentPane(homePanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(1000, 700);
        jframe.setVisible(true);

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

                DatabaseConnection db = new DatabaseConnection();
                db.CreatePost(naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje, name);
                AddRowToTables(new Object[]{naziv, desc, placa, trajanje, d, sifra, fraj, kraj, podjetje});

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
                    for(String line:dc.Return_Objave()){
                        modelPosts.addRow(line.split(","));
                    }
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
            JFrame fr = new JFrame("Open file");
            FileDialog fd = new FileDialog(fr, "Naloži sliko", FileDialog.LOAD);
            fd.setDirectory("C:\\");
            fd.setFile("*.jpg;*.png");

            fd.setVisible(true);
            String filename = fd.getFile();
            if (filename == null)
                JOptionPane.showMessageDialog(null,"Preklicali ste izbiro");
            else{
                String path = fd.getDirectory() + fd.getFile();
                File f = new File(path);
                ic = new ImageIcon(f.toString());
                Image image = ic.getImage();
                Image newimg = image.getScaledInstance(290, 220,  java.awt.Image.SCALE_SMOOTH);
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
            Btn_Prijava.setVisible(true);
            Btn_Reg.setVisible(true);
            signUpButton1.setVisible(true);
            signInButton1.setVisible(true);
            signInButton.setVisible(true);
            signUpButton.setVisible(true);
            signInButton2.setVisible(true);
            signUpButton2.setVisible(true);

            signOutButton.setVisible(false);
            signOutButton1.setVisible(false);
            signOutButton2.setVisible(false);
            signOutButton3.setVisible(false);
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
                AddRowToTables(new Object[]{naslov, telefon, kraj});

                textField7.setText("");
                textField8.setText("");
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });
        signInButton.addActionListener(actionEvent -> {
            jframe.dispose();
            new prijava();
        });

        signUpButton.addActionListener(actionEvent -> {
            jframe.dispose();
            new registracija();
        });

        signOutButton1.addActionListener(actionEvent -> {
            mail_admina = null;
            Btn_Prijava.setVisible(true);
            Btn_Reg.setVisible(true);
            signUpButton1.setVisible(true);
            signInButton1.setVisible(true);
            signInButton.setVisible(true);
            signUpButton.setVisible(true);
            signInButton2.setVisible(true);
            signUpButton2.setVisible(true);

            signOutButton.setVisible(false);
            signOutButton1.setVisible(false);
            signOutButton2.setVisible(false);
            signOutButton3.setVisible(false);
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
                AddRowToTables(new Object[]{ime, posta});

                textField9.setText("");
                textField10.setText("");
            }
            else
                JOptionPane.showMessageDialog(null, "Morate biti prijavljeni");
        });

        signUpButton1.addActionListener(actionEvent -> {
            jframe.dispose();
            new registracija();
        });

        signInButton1.addActionListener(actionEvent -> {
            jframe.dispose();
            new prijava();
        });

        signOutButton2.addActionListener(actionEvent -> {
            mail_admina = null;
            Btn_Prijava.setVisible(true);
            Btn_Reg.setVisible(true);
            signUpButton1.setVisible(true);
            signInButton1.setVisible(true);
            signInButton.setVisible(true);
            signUpButton.setVisible(true);
            signInButton2.setVisible(true);
            signUpButton2.setVisible(true);

            signOutButton.setVisible(false);
            signOutButton1.setVisible(false);
            signOutButton2.setVisible(false);
            signOutButton3.setVisible(false);
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

        signInButton2.addActionListener(actionEvent -> {
            jframe.dispose();
            new prijava();
        });

        signUpButton2.addActionListener(actionEvent -> {
            jframe.dispose();
            new registracija();
        });
    }

    private void setTables(){
        String[] columnsPosts = {"Naziv", "Opis", "Plača", "Trajanje", "Delovnik", "Šifra", "Prosto", "Kraj", "Podjetje", "Naročanje"};
        String[] columnsCompany = {"Naslov", "Telefon", "Kraj"};
        String[] columnsKraji = {"Ime", "Poštna številka"};
        String[] columnsNarocanja = {"Datum", "Študent", "Delovno mesto"};

        postsTable.setModel(new DefaultTableModel(
                null,
                columnsPosts
        ));
        DefaultTableModel modelPosts = (DefaultTableModel)postsTable.getModel();
        for(String line:dc.Return_Objave()){
            modelPosts.addRow(line.split(","));
        }
        postsTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
        postsTable.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JTextField()));

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
        else if(data.length == 5){
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

            btn=new JButton();
            btn.setOpaque(true);

            btn.addActionListener(e -> fireEditingStopped());
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
            if(prosto > 0){
                if(clicked)
                {
                    //String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS").format(new Timestamp(System.currentTimeMillis()));
                    String sifra = textField5.getText();
                    //DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
                    java.util.Date utilDate = new java.util.Date();
                    Timestamp datum = new Timestamp(utilDate.getTime());
                    String s = datum.toString().split("\\.")[0];
                    Timestamp ts = Timestamp.valueOf(s);

                    JOptionPane.showMessageDialog(null, ts);
                    //dc.Insert_Narocanja(ts, mail_studenta, sifra);
                }

                clicked=false;
            }
            else
                btn.setEnabled(false);

            return new String(lbl);
        }

        @Override
        public boolean stopCellEditing() {
            clicked=false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            // TODO Auto-generated method stub
            super.fireEditingStopped();
        }
    }

}
