package com.company;

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
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
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

}
