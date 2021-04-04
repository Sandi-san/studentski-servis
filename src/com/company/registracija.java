package com.company;

import javax.swing.*;

public class registracija {
    private JButton Btn_Bck;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton Btn_SignUp;
    private JPanel regPanel;

    public registracija(){
        JFrame jframe = new JFrame("Registracija");
        jframe.setContentPane(regPanel);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setSize(1050, 400);
        jframe.setVisible(true);
    }
}
