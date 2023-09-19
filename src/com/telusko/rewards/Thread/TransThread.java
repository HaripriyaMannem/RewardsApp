package com.telusko.rewards.Thread;

import com.telusko.rewards.dto.User;
import com.telusko.rewards.repository.UserRepo;

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
        UserRepo userRepo = new UserRepo();
        List<User> userList = new ArrayList<>();
        try
        {
            while(!Thread.currentThread().isInterrupted())
            {

                int max = 100000;
                int min = 50000;

                Random rand = new Random();
                User user = users.get(rand.nextInt(users.size()));

                int randomAmt = (rand.nextInt(max-min + 1) + min);
                user.setTransAmount(user.getTransAmount()+ randomAmt);

                int points = randomAmt/100;
                user.setPoints(user.getPoints() + points);

                if(!userRepo.updateUser(user)){
                    userList.add(user);
                    sharedQueue.put(userList);
                }

                Thread.sleep(3000);
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
