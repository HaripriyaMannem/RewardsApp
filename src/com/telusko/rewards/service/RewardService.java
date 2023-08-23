package com.telusko.rewards.service;

import com.telusko.rewards.dto.Rewards;
import com.telusko.rewards.dto.User;
import com.telusko.rewards.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static com.telusko.rewards.constants.Constants.CYAN;
import static com.telusko.rewards.constants.Constants.RESET;

public class RewardService {

    Util util = new Util();

    public void accessRewards(BlockingQueue<List<User>> sharedQueue)
    {
        boolean flag = true;
        while (flag)
        {
            List<User> users = new ArrayList<>();
            try
            {
                List<Rewards> rewards = util.setRewards();
                System.out.println(rewards.get(0));
                users = sharedQueue.take();
                flag= false;
            }
            catch (InterruptedException e)
            {
               System.out.println(e.getMessage());
               break;
            }
            displayRewards(users);
            System.out.println(users);
        }
    }

    private void displayRewards(List<User> users)
    {
        System.out.println(CYAN + "**************************************" + RESET);

    }
}
