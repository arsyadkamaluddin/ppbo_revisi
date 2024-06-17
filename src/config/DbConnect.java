package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnect {
    Connection conn=null;
    public Connection getConnection()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3307/disyfa","root","");
        }catch (ClassNotFoundException | SQLException e)
        {
        }
        return conn;
    }
}
