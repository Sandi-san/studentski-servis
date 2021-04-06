package com.company;

import java.sql.*;
import java.util.ArrayList;


public class Database {
    public static void main(String[] args) {

    }

    public Connection Connect() throws SQLException {
        Connection conn = null;
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


    public ArrayList<String> Return_Objave(){
        ArrayList <String> objave =  new ArrayList<>();

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT dm.naziv,dm.opis,dm.placa,dm.trajanje,dm.prosta_mesta,k.ime,p.naslov FROM delovna_mesta dm INNER JOIN kraji k ON dm.kraj_id = k.id INNER JOIN podjetja p ON dm.podjetje_id = p.id";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String Naziv = rs.getString("naziv");
                String Opis = rs.getString("opis");
                int Placa = rs.getInt("placa");
                String Trajanje = rs.getString("trajanje");
                int Plac = rs.getInt("prosta_mesta");
                String Kraj = rs.getString("ime");
                String Podjetje = rs.getString("naslov");

                objave.add(Naziv + "," + Opis + "," + Placa + "," + Trajanje + "," + Plac + "," + Kraj + "," + Podjetje);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return objave;
    }

    public ArrayList<String> Return_Kraje(){
        ArrayList <String> kraji = new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT ime FROM kraji";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String ime = rs.getString("ime");
                kraji.add(ime);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return kraji;
    }

    public ArrayList<String> Return_Kraj_Objava(String kraj){
        ArrayList <String> objave =  new ArrayList<>();

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT dm.naziv,dm.opis,dm.placa,dm.trajanje,dm.prosta_mesta,k.ime,p.naslov FROM delovna_mesta dm INNER JOIN kraji k ON dm.kraj_id = k.id INNER JOIN podjetja p ON dm.podjetje_id = p.id WHERE (k.ime = '" + kraj + "') ";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String Naziv = rs.getString("naziv");
                String Opis = rs.getString("opis");
                int Placa = rs.getInt("placa");
                String Trajanje = rs.getString("trajanje");
                int Plac = rs.getInt("prosta_mesta");
                String Kraj = rs.getString("ime");
                String Podjetje = rs.getString("naslov");

                objave.add(Naziv + "," + Opis + "," + Placa + "," + Trajanje + "," + Plac + "," + Kraj + "," + Podjetje);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return objave;
    }
}
