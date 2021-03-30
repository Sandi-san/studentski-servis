package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;
import java.sql.*;
//import java.util.Properties;

public class DatabaseConnection {
    public static void main(String[] args) {

    }

    private Connection conn;

    public Connection Connect() throws SQLException {
        try {
            String jdbcURL = "jdbc:postgresql://ec2-52-213-167-210.eu-west-1.compute.amazonaws.com:5432/d72om3lphmskj1";
            String username = "agzigsarirffns";
            String password = "0bbb063c271cbf8b6ad1108669ae17fe0e978ab530c693b62fdc6b4debae87ca";
            conn = DriverManager.getConnection(jdbcURL, username, password);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }


    public void Save(String ime_k, int posta) throws SQLException {
        try(Connection connection = Connect()){
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO kraji(ime, post_st) VALUES ('" + ime_k + "', '" + posta + "')";
            stmt.executeUpdate(sql);
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void Izpis() throws SQLException{
        try(Connection connection = Connect()){
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM kraji";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                int id = rs.getInt("id");
                String ime = rs.getString("ime");
                int post_st = rs.getInt("post_st");

                System.out.print("ID: " + id);
                System.out.print(", Ime: " + ime);
                System.out.println(", Poštna št.: " + post_st);
            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void SignUp(String name, String surname, String gender, String d, int number, String u, String mail, String Upass, String kraj){
        try(Connection connection = Connect()){
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO studenti(ime, priimek, spol, datum_roj, telefon, username, email, pass, kraj_id) VALUES ('" + name + "', '" + surname + "' , '" + gender + "'  , '" + d + "', '" + number + "', '" + u + "', '" + mail + "', '" + Upass + "', (SELECT ime FROM kraji WHERE ime = '" + kraj +"'))";
            stmt.executeUpdate(sql);
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void AdminSignIn(String mail, String passs) throws SQLException{
        try(Connection connection = Connect()){
            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO admini(username, pass) VALUES ('" + mail + "', '" + passs + "')";
            stmt.executeUpdate(sql);
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void Izpis_Objav(){

        try(Connection connection = Connect()){
            Statement stmt = conn.createStatement();
            String sql = "SELECT dm.naziv,dm.opis,dm.placa,dm.trjanje,dm.prosta_mesta,k.ime,p.ime FROM delovna_mesta dm INNER JOIN kraji k ON dm.kraj_id = k.id INNER JOIN podjetja p ON dm.podjetje_id = p.id";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String Naziv = rs.getString("naziv");
                String Opis = rs.getString("opis");
                int Placa = rs.getInt("placa");
                String Trajanje = rs.getString("trajanje");
                int Plac = rs.getInt("prosta_mesta");
                String Kraj = rs.getString("ime");
                String Podjetje = rs.getString("naslov");

            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
