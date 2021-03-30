package com.company;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
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
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
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

            String sql = "INSERT INTO admini(email, pass) VALUES ('" + mail + "', '" + passs + "')";
            stmt.executeUpdate(sql);
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean CheckUser(String email, String geslo)
    {
        boolean isTrue = false;

        try(Connection connection = Connect()){
            Statement stmt = conn.createStatement();

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
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return isTrue;
    }

}

/*using (con)
            {
                con.Open();

                using (SQLiteCommand com = new SQLiteCommand(con))
                {
                    com.CommandText = "SELECT ime, geslo FROM admins;";
                    com.ExecuteNonQuery();

                    SQLiteDataReader listAll = com.ExecuteReader();

                    while (listAll.Read())
                    {
                        string ime = listAll.GetString(0);
                        string pass = listAll.GetString(1);

                        if (String.Equals(Apass, pass) && String.Equals(Aime, ime))
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }

                    listAll.Close();

                    com.Dispose();
                }

                con.Close();

                return false;*/
