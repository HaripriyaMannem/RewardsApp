package com.telusko.rewards.repository;

import com.telusko.rewards.config.JdbcConfig;
import com.telusko.rewards.dto.Category;
import com.telusko.rewards.dto.GiftCard;
import com.telusko.rewards.dto.Rewards;
import com.telusko.rewards.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Rewards> fetchRewards()
    {
        List<Rewards> rewards = new ArrayList<>();
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                stmt=connect.createStatement();
                String query = "select * from rewards";
                ResultSet result = stmt.executeQuery(query);

                while (result.next())
                {
                    Rewards reward = new Rewards();
                    reward.setId(result.getInt(1));
                    reward.setName(result.getString(2));
                    rewards.add(reward);
                }
            }
        }
        catch (SQLException e) {
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
        return rewards;
    }

    public List<Category> getCategories(int id)
    {
        List<Category> categories = new ArrayList<>();
        try
        {
            connect=JdbcConfig.getDbConnection();

            if(connect!=null)
            {

                String query="SELECT cid, name, points from category where rid=?";
                pstmnt=connect.prepareStatement(query);
                pstmnt.setInt(1, id);
                ResultSet result = pstmnt.executeQuery(query);

                while (result.next())
                {
                    Category category = new Category();
                    category.setId(result.getInt(1));
                    category.setName(result.getString(2));
                    category.setPoints(result.getInt(3));
                    categories.add(category);
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
        return categories;
    }

    public int saveGiftCards(GiftCard giftCard, int id)
    {
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                String sql="INSERT INTO giftcard (userId, name, points, couponCode) VALUES(?,?,?,?)";
                pstmnt=connect.prepareStatement(sql);

                pstmnt.setInt(1, id);
                pstmnt.setString(2, giftCard.getName());
                pstmnt.setInt(3, giftCard.getPoints());
                pstmnt.setString(4, giftCard.getCouponCode());

                return pstmnt.executeUpdate();
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
        return 0;
    }

    public void updateUserPoints(User user)
    {
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                String sql="UPDATE users set redeemPoints=? where userId=?";
                pstmnt=connect.prepareStatement(sql);

                pstmnt.setInt(1, user.getPoints());
                pstmnt.setInt(2, user.getId());
                pstmnt.executeUpdate();
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

    public List<GiftCard> getGiftCards(User user)
    {
        List<GiftCard> giftCards = new ArrayList<>();
        try
        {
            connect=JdbcConfig.getDbConnection();

            if(connect!=null)
            {
                String query="SELECT name, points, couponCode from giftcard where userId=?";
                pstmnt=connect.prepareStatement(query);
                pstmnt.setInt(1, user.getId());
                ResultSet result = pstmnt.executeQuery(query);

                while (result.next())
                {
                    GiftCard giftCard = new GiftCard();
                    giftCard.setName(result.getString(1));
                    giftCard.setPoints(result.getInt(2));
                    giftCard.setCouponCode(result.getString(3));
                    giftCards.add(giftCard);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
        return giftCards;
    }
}
