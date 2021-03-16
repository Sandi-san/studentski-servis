package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://ec2-52-213-167-210.eu-west-1.compute.amazonaws.com:5432/d72om3lphmskj1";

        Properties parameters = new Properties();
        parameters.put("user", "agzigsarirffns");
        parameters.put("password", "0bbb063c271cbf8b6ad1108669ae17fe0e978ab530c693b62fdc6b4debae87ca");

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, parameters);
            System.out.println("Povezava vzpostavljena");

            connection.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Error pri povezavi");
            throwables.printStackTrace();
        }
    }
}




