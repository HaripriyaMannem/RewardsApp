package com.telusko.rewards.repository;

import com.telusko.rewards.config.JdbcConfig;
import com.telusko.rewards.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo
{
    Connection connect=null;
    PreparedStatement pstmnt=null;
    Statement stmt=null;
    ResultSet result=null;

    //one time activity
    public void insertUsers(User user)
    {
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                String sql="INSERT INTO users (userId, username, userPwd) VALUES(?,?,?)";
                pstmnt=connect.prepareStatement(sql);

                pstmnt.setInt(1, user.getId());
                pstmnt.setString(2, user.getName());
                pstmnt.setString(3, user.getPassword());

                int rowsAffected = pstmnt.executeUpdate();
                if(rowsAffected==1)
                {
                    System.out.println("Insertion Successful");
                }
                else
                {
                    System.out.println("Insertion failed!");
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                JdbcConfig.closeResource(result, pstmnt, connect);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public List<User> fetchUsers()
    {
        List<User> users = new ArrayList<>();
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                stmt=connect.createStatement();
                String query = "select * from users";
                ResultSet result = stmt.executeQuery(query);

                while (result.next())
                {
                    User user = new User();
                    user.setId(result.getInt(1));
                    user.setName(result.getString(2));
                    user.setPassword(result.getString(3));
                    user.setTransAmount(result.getInt(4));
                    user.setPoints(result.getInt(5));
                    users.add(user);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean updateUser(User user)
    {
        try
        {
            connect=JdbcConfig.getDbConnection();
            if(connect != null)
            {
                String sql="UPDATE users set transAmount=?, redeemPoints=? where userId=?";
                pstmnt=connect.prepareStatement(sql);

                pstmnt.setInt(1, user.getTransAmount());
                pstmnt.setInt(2, user.getPoints());
                pstmnt.setInt(3, user.getId());

                return pstmnt.execute();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return false;
    }
}
