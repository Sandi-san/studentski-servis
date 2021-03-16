package com.company;

import java.sql.*;
import java.util.Properties;

public class Database {

    public static void main(String[] args) {

    }

    private Connection connection;
    public Database()
    {
        String url = "jdbc:postgresql://ec2-52-213-167-210.eu-west-1.compute.amazonaws.com:5432/d72om3lphmskj1";

        Properties parameters = new Properties();
        parameters.put("user", "agzigsarirffns");
        parameters.put("password", "0bbb063c271cbf8b6ad1108669ae17fe0e978ab530c693b62fdc6b4debae87ca");

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, parameters);
            System.out.println("Povezava vzpostavljena");



            //connection.close(); //close dej v file iz kirga ga kliče
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Error pri povezavi");
            throwables.printStackTrace();
        }
    }

    public void Izpis() throws SQLException {
        Database db = new Database();
        Statement stmt = connection.createStatement();

        String sql = "SELECT id, ime, post_st FROM kraji";
        ResultSet rs = stmt.executeQuery(sql);
        //STEP 5: Extract data from result set
        while(rs.next()) {
            //Retrieve by column name
            int id = rs.getInt("id");
            String ime = rs.getString("ime");
            int post_st = rs.getInt("post_st");
            //Display values
            System.out.print("ID: " + id);
            System.out.print(", Ime: " + ime);
            System.out.println(", Poštna št.: " + post_st);
        }
        connection.close();
    }

    public void Vnos(String imek, int postst) throws SQLException {
        Database db = new Database();
        Statement stmt = connection.createStatement();

        String sql = "INSERT INTO kraji(ime, post_st) VALUES ('" + imek + "', '" + postst + "')";
        stmt.executeQuery(sql);


        connection.close();
    }
}




