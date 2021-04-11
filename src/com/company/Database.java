package com.company;

import javax.swing.plaf.nimbus.State;
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
                String Placa = rs.getString("placa");
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
                String Placa = rs.getString("placa");
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

    public void CreatePost(String a, String b, String c, String d, String g, String z, int h, String k, String p, int j){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO delovna_mesta(naziv, opis, placa, trajanje, delovnik, sifra, prosta_mesta, kraj_id, podjetje_id, admin_id) VALUES('" + a + "', '" + b + "', '" + c + "', '" + d + "', '" + g +"', '" + z + "', '" + h +"', (SELECT id FROM kraji WHERE ime = '" + k +"') , (SELECT id FROM podjetja WHERE naslov = '" + p + "'), '" + j + "')";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int Get_ID_Objave(String a, String b, String c, String d, int h, String k, String p){
        //ArrayList <int> objave =  new ArrayList<>();
        int i = 1;
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT dm.id FROM delovna_mesta dm INNER JOIN kraji k ON k.id = dm.kraj_id INNER JOIN podjetja p ON p.id = dm.podjetje_id WHERE(dm.naziv = '" + a +"') AND (dm.opis = '" + b + "') AND (dm.placa = '" + c +"') AND (dm.trajanje = '" + d + "') AND (dm.prosta_mesta = '" + h + "') AND (k.ime = '" + k +"') AND (p.naslov = '" + p + "') ";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                i = id;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    public void Updatanje_Objav(int id_o, String a, String b, String c, String d, String g, String z, int h, String k, String p){

    }

    public void Deletanje_Objav(int id_o){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "DELETE FROM objave WHERE id = '" + id_o + "' ";
            stmt.executeUpdate(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
