package com.project.movie;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static Connection conn = null;

    public static Connection open() {

        String url = "jdbc:oracle:thin:@oraclecloud_medium?TNS_ADMIN=C://Wallet_OracleCloud";
        String id = "admin";
        String pw = "Javajava1234";

        try {

            // JDBC 드라이버 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");

            conn = DriverManager.getConnection(url, id, pw);

            return conn;


        } catch (Exception e) {
            System.out.println("DBUtill.open");
            e.printStackTrace();
        }

        return null;
    }//open

    public static void close() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.print("DBUtil.close");
            e.printStackTrace();
        }
    }//close

    public static Connection open(String server, String id, String pw) {

        String url = "jdbc:oracle:thin:@" + server + ":1522:xe";

        try {

            // JDBC 드라이버 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");

            conn = DriverManager.getConnection(url, id, pw);

            return conn;


        } catch (Exception e) {
            System.out.println("DBUtill.open");
            e.printStackTrace();
        }

        return null;
    }//open
}
