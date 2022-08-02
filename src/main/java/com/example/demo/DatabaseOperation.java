package com.example.demo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.data.History;


public class DatabaseOperation{
    private Connection connection = null;
    private Statement statement = null;

    public DatabaseOperation(){


        try {
            Class.forName("org.sqlite.JDBC");

            // データベースのPATHを指定。相対パスでも絶対パスでも行けるようです
            connection = DriverManager.getConnection("jdbc:sqlite:db/database.db");
            statement = connection.createStatement();
            String sql = "select * from history";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1)+rs.getString(2));
            }
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<History> getHistory(String user){
        try{
            String sql = "select * from history where user='"+user+"'";
            ResultSet rs = statement.executeQuery(sql);
            List<History> histories = new ArrayList<History>();
            while (rs.next()) {
                System.out.println("OK");
                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                Date date;
                try {
                    date = sdFormat.parse(rs.getString(5));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    date = new Date(0);
                }
                History tmp = new History(Long.parseLong(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), date);
                histories.add(tmp);
            }
            return histories;
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}