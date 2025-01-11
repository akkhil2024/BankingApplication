package com.tolopolgyservice.Tolopolgyservice.cockroachdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloCRDB {

    public static void main(String[] args) {
        Connection cdb = null;
        try {
            Class.forName("org.postgresql.Driver");
            String connectionURL = "jdbc:postgresql://localhost:26257/?sslmode=disable";
            String userName = "root";
            String passWord = "";
            cdb = DriverManager.getConnection(connectionURL, userName, passWord);
            Statement stmt = cdb.createStatement();
            ResultSet rs = stmt
                    .executeQuery("SELECT CONCAT('Hello from CockroachDB at',"
                            + "CAST (NOW() as STRING)) AS hello");
            rs.next();
            System.out.println(rs.getString("hello"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
