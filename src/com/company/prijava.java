package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prijava {
    DatabaseConnection dc = new DatabaseConnection();

    private JButton Btn_SignIn;
    private JTextField textField1;
    private JTextField textField2;

    public prijava() {
        Btn_SignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String email = textField1.getText();
                String geslo = textField2.getText();
                JOptionPane.showMessageDialog(null, "Hello world");
            }
        });
    }

    public static void main(String[] args) {
        //JFrame frame = new JFrame("Prijava");

    }
}
