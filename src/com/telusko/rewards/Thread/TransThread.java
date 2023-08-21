package com.telusko.rewards.Thread;

import com.telusko.rewards.dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransThread implements Runnable
{
    List<User> users;
    public TransThread(List<User> userList)
    {
        users = userList;
    }

    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                int max = 20000;
                int min = 1000;

                Random rand = new Random();
                User user = users.get(rand.nextInt(users.size()));

                int randomAmt = (rand.nextInt(max-min + 1) + min);
                user.setTransAmount(randomAmt);

                int points = randomAmt/100;
                user.setPoints(user.getPoints() + points);

                Thread.sleep(3000);
                System.out.println(user);
            }
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
