package com.company;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseConnection {
    public static void main(String[] args) {

    }

    //KRIPTIRANJE GESEL
    public  static  String Encrypt(String passwordToHash)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    //POVEZAVA Z REMOTE BAZO
    public Connection Connect() throws SQLException {
        Connection conn = null;
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

    //INSERT KRAJ
    public void InsertKraj(String ime_k, int posta) throws SQLException {
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO kraji(ime, post_st) VALUES ('" + ime_k + "', '" + posta + "')";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    //INSERT STUDENTI
    public void SignUp(String name, String surname, String gender, String d, int number, String u, String mail, String Upass, String kraj){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO studenti(ime, priimek, spol, datum_roj, telefon, username, email, pass, kraj_id) VALUES ('" + name + "', '" + surname + "' , '" + gender + "'  , '" + d + "', '" + number + "', '" + u + "', '" + mail + "', '" + Upass + "', (SELECT ime FROM kraji WHERE ime = '" + kraj +"'))";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //INSERT ADMIN
    public void AdminReg(String mail, String passs) throws SQLException{
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO admini(email, pass) VALUES ('" + mail + "', '" + passs + "')";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //PREVERJANJE, ČE SE PODATKI VPISANEGA ADMINA UJEMAJO V BAZI
    public boolean CheckUser(String email, String geslo)
    {
        boolean isTrue = false;

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();

            String sql = "SELECT email, pass FROM admini;";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String mail = rs.getString("email");
                String pass = rs.getString("pass");

                //System.out.println("Iz baze: " + mail + " " + pass);

                if (mail.equals(email))
                {
                    if (pass.equals(geslo))
                    {
                        isTrue = true;
                    }
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return isTrue;
    }

    //PRIKAZ VSEH KRAJEV OB ZAGONU APLIKACIJE
    public ArrayList<String> Return_Objave(){
        ArrayList <String> objave =  new ArrayList<>();

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT dm.naziv,dm.opis,dm.placa,dm.trajanje,dm.delovnik, dm.sifra, dm.prosta_mesta,k.ime,p.naslov FROM delovna_mesta dm INNER JOIN kraji k ON dm.kraj_id = k.id INNER JOIN podjetja p ON dm.podjetje_id = p.id";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String Naziv = rs.getString("naziv");
                String Opis = rs.getString("opis");
                String Placa = rs.getString("placa");
                String Trajanje = rs.getString("trajanje");
                String e = rs.getString("delovnik");
                String g = rs.getString("sifra");
                int Plac = rs.getInt("prosta_mesta");
                String Kraj = rs.getString("ime");
                String Podjetje = rs.getString("naslov");

                objave.add(Naziv + "," + Opis + "," + Placa + "," + Trajanje + "," + e + "," + g + "," + Plac + "," + Kraj + "," + Podjetje);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return objave;
    }

    //PRIKAZ KRAJEV V COMBOBOXU PRI KREIRANJU OBJAV
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

    //PRIKAZ PODJETJU V COMBOBOXU PRI KREIRANJU OBJAV
    public ArrayList<String> Return_Podjetja(){
        ArrayList <String> podjetja = new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT naslov FROM podjetja";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String ime = rs.getString("naslov");
                podjetja.add(ime);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return podjetja;
    }

    //PRIKAZ DELOVNIH MEST GLEDE NA IZBRAN KRAJ V COMBOBOX (V HOME)
    public ArrayList<String> Return_Kraj_Objava(String kraj){
        ArrayList <String> objave =  new ArrayList<>();

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT dm.naziv,dm.opis,dm.placa,dm.trajanje, dm.delovnik, dm.sifra,dm.prosta_mesta,k.ime,p.naslov FROM delovna_mesta dm INNER JOIN kraji k ON dm.kraj_id = k.id INNER JOIN podjetja p ON dm.podjetje_id = p.id WHERE (k.ime = '" + kraj + "') ";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String Naziv = rs.getString("naziv");
                String Opis = rs.getString("opis");
                String Placa = rs.getString("placa");
                String Trajanje = rs.getString("trajanje");
                String d = rs.getString("delovnik");
                String s = rs.getString("sifra");
                int Plac = rs.getInt("prosta_mesta");
                String Kraj = rs.getString("ime");
                String Podjetje = rs.getString("naslov");

                objave.add(Naziv + "," + Opis + "," + Placa + "," + Trajanje + "," + d + "," + s + "," + Plac + "," + Kraj + "," + Podjetje);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return objave;
    }

    //INSERT OBJAVE
    public void CreatePost(String a, String b, String c, String d, String g, String z, int h, String k, String p, String j){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO delovna_mesta(naziv, opis, placa, trajanje, delovnik, sifra, prosta_mesta, kraj_id, podjetje_id, admin_id) VALUES('" + a + "', '" + b + "', '" + c + "', '" + d + "', '" + g +"', '" + z + "', '" + h +"', (SELECT id FROM kraji WHERE ime = '" + k +"') , (SELECT id FROM podjetja WHERE naslov = '" + p + "'), (SELECT id FROM admini WHERE email = '" + j + "') )";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //PRIDOBIVANJE ID-JA OBJAVE ZA BRISANJE
    public int Get_ID_Objave(String a, String b, String c, String d, String l, String f, int h, String k, String p){
        //ArrayList <int> objave =  new ArrayList<>();
        int i = 1;
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT dm.id FROM delovna_mesta dm INNER JOIN kraji k ON k.id = dm.kraj_id INNER JOIN podjetja p ON p.id = dm.podjetje_id WHERE(dm.naziv = '" + a +"') AND (dm.opis = '" + b + "') AND (dm.placa = '" + c +"') AND (dm.trajanje = '" + d + "') AND (dm.delovnik = '" + l +"') AND (dm.sifra = '" + f +"') AND (dm.prosta_mesta = '" + h + "') AND (k.ime = '" + k +"') AND (p.naslov = '" + p + "') ";
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

    //UPDATE OBJAVE
    public void Updatanje_Objav(int id_o, String a, String b, String c, String d, String r, String f, int h, String k, String p){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "UPDATE delovna_mesta SET naziv = '" + a + "', opis = '" + b + "', placa = '" + c + "', trajanje = '" + d + "', prosta_mesta = '" + h + "', delovnik = '" + r + "', sifra = '" + f + "', kraj_id = (SELECT id FROM kraji WHERE ime = '" + k + "'), podjetje_id = (SELECT id FROM podjetja WHERE naslov = '" + p +"') WHERE (id = '" + id_o +"') ";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //DELETE OBJAVE
    public void Deletanje_Objav(int id_o){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "DELETE FROM delovna_mesta WHERE id = '" + id_o + "' ";
            stmt.executeUpdate(sql);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
