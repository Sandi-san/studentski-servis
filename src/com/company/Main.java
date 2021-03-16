package com.company;

import java.sql.*;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Database db = new Database();

        try {
            db.Vnos("Skem", 9999);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            db.Izpis();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
