package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import java.util.Properties;

public class Database {
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

    public ArrayList<String> Return_Objave(){
        ArrayList <String> objave =  new ArrayList<>();

        try(Connection connection = Connect()){
            Statement stmt = conn.createStatement();
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

                /*da vsako v novo vrsto v vnasanju v tabelo
                String vse = Naziv + "," + Opis + "," + Placa + "," + Trajanje + "," + Plac + "," + Kraj + "," + Podjetje;
                Collections.addAll(objave, vse.split("\\s*,\\s*"));
                objave = Stream.of(vse.split(",")).collect(Collectors.toCollection(ArrayList<String>::new));*/
            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return objave;
    }

    public ArrayList<String> Return_Kraje(){
        ArrayList <String> kraji = new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = conn.createStatement();
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
}
