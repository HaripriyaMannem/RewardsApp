package com.telusko.rewards.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConfig
{
    static
    {
        //load and register Driver
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getDbConnection() throws SQLException
    {
        // EstablishConnection
        String url ="jdbc:mysql://localhost:3306/teluskodb";
        String userName="root";
        String password="Password1";

        return DriverManager.getConnection(url, userName, password);
    }

    public static void closeResource(ResultSet rs, Statement st, Connection c) throws SQLException
    {
        if(rs!=null)
            rs.close();
        if(st!=null)
            st.close();
        if(c!=null)
            c.close();
    }
}
