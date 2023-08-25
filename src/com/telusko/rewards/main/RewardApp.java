package com.telusko.rewards.main;

import com.telusko.rewards.Thread.TransThread;
import com.telusko.rewards.dto.User;
import com.telusko.rewards.exception.AuthException;
import com.telusko.rewards.login.Login;
import com.telusko.rewards.service.RewardService;
import com.telusko.rewards.service.UserService;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.telusko.rewards.constants.Constants.*;

public class RewardApp
{
    public static void main(String[] args)
    {

        //Creating and Fetching Users
        UserService userObj = new UserService();
        List<User> users = userObj.createUsers();

        //Background Thread for Transactions
        BlockingQueue<List<User>> sharedQueue = new LinkedBlockingQueue<>();
        TransThread transThread = new TransThread(users, sharedQueue);
        Thread thread = new Thread(transThread);
        thread.start();

        Login login = new Login();
        RewardService rewardService = new RewardService();
        try
        {
            //User Authentication
            int userId = login.authentication(users);
            //show rewards
            rewardService.accessRewards(sharedQueue, userId);
        }
        catch (AuthException e)
        {
            System.out.println(RED + e.getMessage() + RESET);
            //Allowing User for Second Time Login
            try
            {
                //User Authentication
                int userId =  login.authentication(users);
                //show rewards
                rewardService.accessRewards(sharedQueue, userId);
            }
            catch (AuthException ex)
            {
                System.out.println(RED + e.getMessage() + RESET);
                System.out.println(PURPLE + "you have exceeded the maximum number" +
                        " of attempts, please try again after some time.");
            }
        }
    }

}