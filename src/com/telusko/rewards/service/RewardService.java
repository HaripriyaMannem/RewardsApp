package com.telusko.rewards.service;

import com.telusko.rewards.dto.Category;
import com.telusko.rewards.dto.GiftCard;
import com.telusko.rewards.dto.Rewards;
import com.telusko.rewards.dto.User;
import com.telusko.rewards.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

import static com.telusko.rewards.constants.Constants.*;

public class RewardService {

    Util util = new Util();
    GiftCard giftCard = new GiftCard();
    List<GiftCard> giftCards = new ArrayList<>();

    public void accessRewards(BlockingQueue<List<User>> sharedQueue, int userId)
    {
        boolean flag = true;
        while (flag)
        {
            List<User> users;
            try
            {
                //Prepare all Rewards
                List<Rewards> rewards = util.setRewards();
                //fetch users from Background Transactions Thread
                users = sharedQueue.take();

                //Show Rewards to the user
                List<User> uniqueUsers = users.stream().distinct().collect(Collectors.toList());
                displayRewards(rewards, uniqueUsers, userId);

                flag= false;
            }
            catch (InterruptedException e)
            {
               System.out.println(e.getMessage());
               break;
            }
        }
    }

    private void displayRewards(List<Rewards> rewards, List<User> users, int userId)
    {

        util.msg1();
        for (Rewards reward : rewards)
        {
            System.out.println(reward.getId() + ". " + reward.getName());
        }

        util.msg2();
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();

        User user = util.getUser(users, userId);
        System.out.println(user);

        if(user != null)
        {
            util.msg3(user.getPoints());
            List<Category> categories = rewards.get(id-1).getCategoryList();
            System.out.println("Number \t Category   \t Points");

            for (Category category : categories)
            {
                System.out.println(category.getId() + "  \t\t" + category.getName()
                        + " \t\t"+ category.getPoints());
            }

            System.out.println("Enter Which " + YELLOW + "category" + RESET + " you want to redeem: ");
            int catId = sc.nextInt();
            Category category = categories.get(catId-1);

            if(user.getPoints() >= category.getPoints())
            {
                giftCard.setCouponCode(util.generateCouponCode());
                util.msg4(category.getName(), giftCard.getCouponCode());
                user.setPoints(user.getPoints() - category.getPoints());

                giftCards.add(giftCard);
                user.setGiftCards(giftCards);
            }
            else
            {
                util.msg5(user.getPoints());
            }
            redeemItems(rewards, users, userId);
        }
        else
        {
            System.out.println(PURPLE + "No Transactions made by you, try again after some time!!");
            redeemItems(rewards, users, userId);
        }
    }

    private void redeemItems(List<Rewards> rewards, List<User> users, int userId)
    {
        System.out.println("Do you want redeem items again " + YELLOW +  "Y/N" + RESET);
        Scanner sc = new Scanner(System.in);
        String status = sc.nextLine().trim();

        if(status.equalsIgnoreCase("Y")){
            displayRewards(rewards, users, userId);
        }
        else
        {
            System.out.println("done");
            //util.msg6(user);
        }
    }
}
