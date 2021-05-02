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

            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }


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


    public void SaveKraj(String ime_k, int posta) {
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO kraji(ime, post_st) VALUES ('" + ime_k + "', '" + posta + "')";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public ArrayList<String> Return_Vse_Kraje(){
        ArrayList<String> kraji = new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();

            String sql = "SELECT * FROM kraji";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String ime = rs.getString("ime");
                int post_st = rs.getInt("post_st");
                kraji.add(ime + "," + post_st);
                //System.out.println(ime);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return kraji;
    }

    public void SignUp(String name, String surname, String gender, String d, String number, String u, String mail, String Upass, String kraj){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();

            String sql = "INSERT INTO studenti(ime, priimek, spol, datum_roj, telefon, username, email, pass, kraj_id) VALUES ('" + name + "', '" + surname + "' , '" + gender + "'  , '" + d + "', '" + number + "', '" + u + "', '" + mail + "', '" + Upass + "', (SELECT id FROM kraji WHERE ime = '" + kraj +"'))";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

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
                        //System.out.println("JE TRUE KURBA");
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

    public boolean CheckStudent(String email, String geslo)
    {
        boolean isTrue = false;

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();

            String sql = "SELECT email, pass FROM studenti;";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String mail = rs.getString("email");
                String pass = rs.getString("pass");

                if (mail.equals(email))
                {
                    if (pass.equals(geslo))
                    {
                        Home.MailStudenta(mail);
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

                objave.add(Naziv + "," + Opis + "," + Placa + "," + Trajanje + "," + e + "," + g + "," + Plac + "," + Kraj + "," + Podjetje +  "," +"Naroči se");
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
                //System.out.println(ime);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return kraji;
    }
    public int Return_ProstaMesta(String a){
        int i = 1;
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM st_delovnih_mest_v_kraju('" + a + "')";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int prosto = rs.getInt(1);
                i = prosto;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }
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

    public ArrayList<String> Return_Vsa_Podjetja(){
        ArrayList <String> podjetja =  new ArrayList<>();

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT p.naslov,p.telefon,k.ime FROM podjetja p INNER JOIN kraji k ON k.id = p.kraj_id";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String naslov = rs.getString("naslov");
                String fon = rs.getString("telefon");
                String ime = rs.getString("ime");

                podjetja.add(naslov + "," + fon + "," + ime);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  podjetja;
    }

    public void Insert_Narocanja(Timestamp a, String b, String c){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO narocanja(datum_naroc, student_id, delovno_mesto_id) VALUES('" + a + "', (SELECT id FROM studenti WHERE email = '" + b +"'), (SELECT id FROM delovna_mesta WHERE sifra = '" + c +"')) ";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> Return_Narocanja(){
        ArrayList <String> narocanja =  new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT n.datum_naroc, s.email, dm.naziv, dm.sifra FROM narocanja n INNER JOIN studenti s ON s.id = n.student_id INNER JOIN delovna_mesta dm ON dm.id = n.delovno_mesto_id";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Timestamp naslov = rs.getTimestamp("datum_naroc");
                String mail = rs.getString("email");
                String dMesto = rs.getString("naziv");
                String sifra = rs.getString("sifra");

                narocanja.add(naslov + "," + mail + "," + dMesto + "," + sifra);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return narocanja;
    }
    public int Get_ID_Narocanja(Timestamp a, String b, String c, String d){
        int i = 0;
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT n.id FROM narocanja n INNER JOIN delovna_mesta dm ON dm.id = n.delovno_mesto_id INNER JOIN studenti s ON s.id = n.student_id WHERE (n.datum_naroc = '" + a + "') AND (s.email = '" + b + "') AND (dm.naziv = '" + c + "') AND (dm.sifra = '" + d +"') ";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt(1);
                i = id;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    public void Delete_Narocanje(int id){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "DELETE FROM narocanja WHERE id = '" + id + "' ";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> Return_Kraj_Podjetja(String kraj){
        ArrayList <String> podjetja =  new ArrayList<>();

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT p.naslov,p.telefon,k.ime FROM podjetja p INNER JOIN kraji k ON k.id = p.kraj_id WHERE (k.ime = '" + kraj + "')";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String naslov = rs.getString("naslov");
                String fon = rs.getString("telefon");
                String ime = rs.getString("ime");

                podjetja.add(naslov + "," + fon + "," + ime);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  podjetja;
    }

    public int Get_ID_Kraja(String g, int h){
        int i = 1;
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT id FROM kraji WHERE (ime = '" + g + "') AND (post_st = '" + h + "')";
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

    public void Posodabljanje_Kraja(int idK, String a, int b){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "UPDATE kraji SET ime = '" + a +"', post_st = '" + b + "' WHERE id = '" + idK + "' ";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void Posodobi_PMesta(int prosto, int id_D){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "UPDATE delovna_mesta SET prosta_mesta = '" + prosto +"' WHERE id = '" + id_D + "' ";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void Zbrisi_Kraj(int i){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "DELETE FROM kraji WHERE id = '" + i + "' ";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int Get_ID_Podjetja(String a, String b, String c){
        int i = 1;
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT p.id FROM podjetja p INNER JOIN kraji k ON k.id = p.kraj_id WHERE (p.naslov = '" + a + "') AND (p.telefon = '" + b +"') AND (k.ime = '" + c + "')";
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

    public void Posodobi_Podjetje(int id, String a, String b, String c)
    {
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "UPDATE podjetja SET naslov = '" + a + "', telefon = '" + b + "', kraj_id = (SELECT id FROM kraji WHERE ime = '" + c + "') WHERE id = '" + id +"' ";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void Brisi_Podjetje(int i){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "DELETE FROM podjetja WHERE id = '" + i +"' ";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

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

    public ArrayList<String> Return_Vsa_Narocanja(){
        ArrayList<String> n = new ArrayList<>();

        return n;
    }

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

    public void AddCompany(String a, String b, String c){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO podjetja(naslov, telefon, kraj_id) VALUES('" + a +"', '" + b +"', (SELECT id FROM kraji WHERE ime = '" + c +"'))";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

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
