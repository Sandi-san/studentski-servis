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
            String jdbcURL = "jdbc:postgresql://ec2-174-129-225-160.compute-1.amazonaws.com:5432/dfb9ne0r15jl31";
            String username = "lkdruotybqiygj";
            String password = "d3a76b0edb876149390875f7906938fd1b3e23dc4bdd4680b41c65f3b17ce772";
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

            String sql = "INSERT INTO admini(username, pass) VALUES ('" + mail + "', '" + passs + "')";
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

            String sql = "SELECT username, pass FROM admini;";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String mail = rs.getString("username");
                String pass = rs.getString("pass");

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

    public ArrayList<DelovnoMesto> Return_Objave(){
        ArrayList <DelovnoMesto> objave =  new ArrayList<>();

        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT dm.naziv,dm.opis,dm.placa,dm.trajanje,dm.delovnik, dm.sifra, dm.prosta_mesta,k.ime,p.naslov, dm.slika_dmesta FROM delovna_mesta dm INNER JOIN kraji k ON dm.kraj_id = k.id INNER JOIN podjetja p ON dm.podjetje_id = p.id";
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
                String Slika = rs.getString("slika_dmesta");

                DelovnoMesto dm = new DelovnoMesto(Naziv, Opis, Placa, Trajanje, e, g, Plac, Kraj, Podjetje, Slika);

                objave.add(dm);
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

    public ArrayList<Kraj> Return_Kraj_Info(String kraj){
        ArrayList <Kraj> info =  new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = null;

            if(kraj == "Vse")
                sql = "SELECT (SELECT SUM(st_delovnih_mest) FROM kraji) st_delovnih_mest_v_kraju, (SELECT COUNT(id) FROM narocanja) st_narocanj FROM kraji;";
            else
                sql = "SELECT (SELECT SUM(st_delovnih_mest) from kraji WHERE ime = 'Arti??e') st_delovnih_mest_v_kraju," +
                        "(SELECT COUNT(n.id) from narocanja n LEFT JOIN studenti s ON n.student_id = s.id " +
                        "RIGHT JOIN kraji k ON k.id = s.kraj_id WHERE (n.delovno_mesto_id IN (SELECT id FROM delovna_mesta WHERE kraj_id = (SELECT id FROM " +
                        "kraji WHERE ime = '" + kraj + "')) )) stevilo_narocanj FROM kraji WHERE ime = '" + kraj + "';";

            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int st_de = rs.getInt(1);
                int st_ns = rs.getInt(2);

                Kraj k = new Kraj(st_de, st_ns);
                info.add(k);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return info;
    }
    public ArrayList<Kraj> Return_Kraj_InfoN(){
        ArrayList <Kraj> info =  new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT (SELECT COUNT(id) FROM kraji) st_krajev, (SELECT COUNT(id) FROM podjetja) st_podjetij FROM kraji";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int st_de = rs.getInt(1);
                int st_ns = rs.getInt(2);

                Kraj k = new Kraj(st_de, st_ns);
                info.add(k);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return info;
    }
    public ArrayList<Podjetje> Return_Podjetje_Info(String kraj){
        ArrayList <Podjetje> info =  new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = null;

            if(kraj == "Vse")
                sql = "SELECT (SELECT COUNT(id) FROM podjetja) st_podjetij_v_kraju, (SELECT COUNT(id) FROM delovna_mesta) st_delovnih_mest_ki_jih_imajo_v_kraju FROM kraji";
            else
                sql = "SELECT (SELECT COUNT(p.id) FROM podjetja p inner join kraji k ON k.id = p.kraj_id WHERE ime = '" + kraj + "') st_podjetij_v_kraju," +
                        "(SELECT count(dm.id) FROM podjetja p left JOIN kraji k  ON k.id = p.kraj_id LEFT JOIN " +
                        "delovna_mesta dm ON dm.podjetje_id = p.id  WHERE (dm.kraj_id IN (SELECT id from kraji where ime = '" + kraj + "'))) st_delovnih_mest_ki_jih_imajo_v_kraju " +
                        "FROM kraji where ime = '" + kraj + "'";

            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int st_p = rs.getInt(1);
                int st_dm = rs.getInt(2);

                Podjetje k = new Podjetje(st_p, st_dm);
                info.add(k);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return info;
    }
    public ArrayList<DelovnoMesto> Return_Kraj_Objava(String kraj){
        ArrayList <DelovnoMesto> objave =  new ArrayList<>();
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT dm.naziv,dm.opis,dm.placa,dm.trajanje, dm.delovnik, dm.sifra,dm.prosta_mesta,k.ime,p.naslov, dm.slika_dmesta FROM delovna_mesta dm INNER JOIN kraji k ON dm.kraj_id = k.id INNER JOIN podjetja p ON dm.podjetje_id = p.id WHERE (k.ime = '" + kraj + "') ";
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
                String Slika = rs.getString("slika_dmesta");

                DelovnoMesto dm = new DelovnoMesto(Naziv, Opis, Placa, Trajanje, d, s, Plac, Kraj, Podjetje, Slika);
                objave.add(dm);
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

    public void CreatePost(String a, String b, String c, String d, String g, String z, int h, String k, String p, String j, String file){
        try(Connection connection = Connect()){
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO delovna_mesta(naziv, opis, placa, trajanje, delovnik, sifra, prosta_mesta, kraj_id, podjetje_id, admin_id, slika_dmesta) VALUES('" + a + "', '" + b + "', '" + c + "', '" + d + "', '" + g +"', '" + z + "', '" + h +"', (SELECT id FROM kraji WHERE ime = '" + k +"') , (SELECT id FROM podjetja WHERE naslov = '" + p + "'), (SELECT id FROM admini WHERE username = '" + j + "'), '" + file + "')";
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
