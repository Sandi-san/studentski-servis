package com.company;

import java.sql.*;
//import java.util.Properties;

public class DatabaseConnection {
    public static void main(String[] args) {


    }

    private Connection conn;
    String jdbcURL = "jdbc:postgresql://ec2-52-213-167-210.eu-west-1.compute.amazonaws.com:5432/d72om3lphmskj1";
    String username = "agzigsarirffns";
    String password = "0bbb063c271cbf8b6ad1108669ae17fe0e978ab530c693b62fdc6b4debae87ca";

    /*public Connection getConnection() throws SQLException {
        try {
            String jdbcURL = "jdbc:postgresql://ec2-52-213-167-210.eu-west-1.compute.amazonaws.com:5432/d72om3lphmskj1";
            String username = "agzigsarirffns";
            String password = "0bbb063c271cbf8b6ad1108669ae17fe0e978ab530c693b62fdc6b4debae87ca";
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            if (conn == null) {
                conn = DriverManager.getConnection(jdbcURL, username, password);
            }


        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }*/


    public void Save(String ime_k, int posta) throws SQLException {
        conn = DriverManager.getConnection(jdbcURL, username, password);
        Statement stmt = conn.createStatement();

        String sql = "INSERT INTO kraji(ime, post_st) VALUES ('" + ime_k + "', '" + posta + "')";
        stmt.executeUpdate(sql);

        conn.close();
    }

    public void Izpis() throws SQLException{
        conn = DriverManager.getConnection(jdbcURL, username, password);
        Statement stmt = conn.createStatement();

        String sql = "SELECT * FROM kraji";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            int id = rs.getInt("id");
            String ime = rs.getString("ime");
            int post_st = rs.getInt("post_st");
            //Display values
            System.out.print("ID: " + id);
            System.out.print(", Ime: " + ime);
            System.out.println(", Poštna št.: " + post_st);
        }
        conn.close();
    }
}
