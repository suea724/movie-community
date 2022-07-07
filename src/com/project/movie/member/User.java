package com.project.movie.member;

import com.project.movie.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
    public static void main(String[] args) {
        try {

            Connection conn;
            conn = DBUtil.open();
            Statement stat;
            ResultSet rs;

            String sql = "select * from tblUser";

            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            while(rs.next()) {
                System.out.println(rs.getString("id"));
            }


        } catch (Exception e) {

        }
    }
}
