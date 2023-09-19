package com.telusko.rewards.repository;

import com.telusko.rewards.config.JdbcConfig;
import com.telusko.rewards.dto.Category;
import com.telusko.rewards.dto.Rewards;
import com.telusko.rewards.dto.User;

import java.sql.*;

public class RewardsRepo {

    Connection connect=null;
    PreparedStatement pstmnt=null;
    Statement stmt=null;
    ResultSet result=null;

    public void insertRewards(Rewards rewards)
    {
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                String sql="INSERT INTO rewards (rid, name) VALUES(?,?)";
                pstmnt=connect.prepareStatement(sql);

                pstmnt.setInt(1, rewards.getId());
                pstmnt.setString(2, rewards.getName());

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

    public void insertCategories(Category category, Rewards rewards)
    {
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                String sql="INSERT INTO category (cid, name, points, rid) VALUES(?,?,?,?)";
                pstmnt=connect.prepareStatement(sql);

                pstmnt.setInt(1, category.getId());
                pstmnt.setString(2, category.getName());
                pstmnt.setInt(3, category.getPoints());
                pstmnt.setInt(4, rewards.getId());

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
}
