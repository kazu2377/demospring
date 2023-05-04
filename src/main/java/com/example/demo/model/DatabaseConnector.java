package com.example.demo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties ("logging")
public class DatabaseConnector {
    
    @Value ("${spring.datasource.url}")
    private String url = "jdbc:mysql://localhost:3306/donutapp";
    
    @Value ("${spring.datasource.username}")
    private String username = "root";
    
    @Value ("${spring.datasource.password}")
    private String password = "root";
    
    @Value ("${spring.datasource.driver-class-name}")
    private String driver = "com.mysql.jdbc.Driver";
    
    private Connection connection;
    
    public Connection getConnection () throws SQLException {
        if (connection == null || connection.isClosed ()) {
            try {
                Class.forName (driver);
                connection = DriverManager.getConnection (url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace ();
            }
        }
        return connection;
    }
    
    public void closeConnection () throws SQLException {
        if (connection != null && !connection.isClosed ()) {
            connection.close ();
        }
    }
    
    /**
     * SQLを実行する
     * 
     * @param  sql          SQL文
     * @throws SQLException
     */
    public ResultSet executeQuery (String sql) throws SQLException {
        // ステートメントオブジェクトを生成
        Statement statement = connection.createStatement ();
        
        // SQLを実行し、結果を返す
        return statement.executeQuery (sql);
    }
}
