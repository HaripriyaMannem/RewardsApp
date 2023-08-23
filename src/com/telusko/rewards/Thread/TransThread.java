package com.telusko.rewards.Thread;

import com.telusko.rewards.dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class TransThread implements Runnable
{
    BlockingQueue<List<User>> sharedQueue;
    List<User> users;

    public TransThread(List<User> userList, BlockingQueue<List<User>> userQueue)
    {
        users = userList;
        sharedQueue = userQueue;
    }

    @Override
    public void run()
    {
        List<User> userList = new ArrayList<>();
        try
        {
            while(!Thread.currentThread().isInterrupted())
            {
                int max = 20000;
                int min = 10000;

                Random rand = new Random();
                User user = users.get(rand.nextInt(users.size()));

                int randomAmt = (rand.nextInt(max-min + 1) + min);
                user.setTransAmount(user.getTransAmount()+ randomAmt);

                int points = randomAmt/100;
                user.setPoints(user.getPoints() + points);

                userList.add(user);
                sharedQueue.put(userList);
                Thread.sleep(1000);
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
