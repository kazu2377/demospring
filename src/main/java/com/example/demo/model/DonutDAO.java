package com.example.demo.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class DonutDAO {
    
    private Connection db;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public void connect () throws NamingException, SQLException {
        
        // DatabaseConnectorを生成
        DatabaseConnector connector = new DatabaseConnector ();
        
        // try {
        // データベースに接続する
        db = connector.getConnection ();
        //
        // // SQLを実行する
        // ResultSet resultSet = connector.executeQuery ("SELECT * FROM donuts");
        //
        // // 結果を出力する
        // while (resultSet.next ()) {
        // int id = resultSet.getInt ("id");
        // String name = resultSet.getString ("name");
        // int price = resultSet.getInt ("price");
        // System.out.println (id + ": " + name + " - " + price);
        // }
        // } finally {
        // // データベースから切断する
        // connector.closeConnection ();
        // }
    }
    
    public void insertOne (Donut donut) throws SQLException {
        try {
            this.connect ();
            ps = db.prepareStatement ("INSERT INTO donuts(name,price,imgname) VALUES(?,?,?)");
            ps.setString (1, donut.getName ());
            ps.setInt (2, donut.getPrice ());
            ps.setString (3, donut.getImgname ());
            ps.execute ();
        } catch (NamingException | SQLException e) {
            e.printStackTrace ();
        } finally {
            db.close ();
        }
    }
    
    public Donut findOne (int id) throws SQLException {
        Donut donut = null;
        try {
            this.connect ();
            PreparedStatement ps = db.prepareStatement ("SELECT * FROM donuts WHERE id=?");
            ps.setInt (1, id);
            ResultSet rs = ps.executeQuery ();
            if (rs.next ()) {
                String name = rs.getString ("name");
                int price = rs.getInt ("price");
                String imgname = rs.getString ("imgname");
                donut = new Donut (id, name, price, imgname);
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace ();
        } finally {
            db.close ();
        }
        return donut;
    }
    
    public List<Donut> findAll () throws SQLException {
        List<Donut> list = new ArrayList<> ();
        try {
            this.connect ();
            ps = db.prepareStatement ("SELECT * FROM donuts");
            rs = ps.executeQuery ();
            while (rs.next ()) {
                int id = rs.getInt ("id");
                String name = rs.getString ("name");
                int price = rs.getInt ("price");
                String imgname = rs.getString ("imgname");
                list.add (new Donut (id, name, price, imgname));
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace ();
        } finally {
            db.close ();
        }
        return list;
    }
    
    public void deleteOne (int id) throws SQLException {
        try {
            this.connect ();
            
            PreparedStatement ps = db.prepareStatement ("DELETE FROM donuts WHERE id=?");
            ps.setInt (1, id);
            ps.execute ();
        } catch (NamingException | SQLException e) {
            e.printStackTrace ();
        } finally {
            db.close ();
        }
    }
    
    public void updateOne (Donut donut) throws SQLException {
        try {
            this.connect ();
            PreparedStatement ps = db.prepareStatement ("UPDATE donuts SET name=?,price=?,imgname=? WHERE id =?");
            ps.setString (1, donut.getName ());
            ps.setInt (2, donut.getPrice ());
            ps.setString (3, donut.getImgname ());
            ps.setInt (4, donut.getId ());
            ps.executeUpdate ();
        } catch (NamingException | SQLException e) {
            e.printStackTrace ();
        } finally {
            db.close ();
        }
    }
    
}
