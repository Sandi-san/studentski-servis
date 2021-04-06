package com.company;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world");
        DatabaseConnection dc = new DatabaseConnection();
        String z = "bla bla";
        int u = 2000;
        /*
        try {
            dc.Save(z,u);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            dc.Izpis();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        */

        //new prijava();
        new registracija();
    }
}
