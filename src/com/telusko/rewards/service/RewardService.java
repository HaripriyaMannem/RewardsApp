package com.telusko.rewards.service;

import com.telusko.rewards.dto.Category;
import com.telusko.rewards.dto.GiftCard;
import com.telusko.rewards.dto.Rewards;
import com.telusko.rewards.dto.User;
import com.telusko.rewards.main.RewardApp;
import com.telusko.rewards.repository.RewardsRepo;
import com.telusko.rewards.repository.UserRepo;
import com.telusko.rewards.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import static com.telusko.rewards.constants.Constants.*;

public class RewardService {

    Util util = new Util();
    List<GiftCard> giftCards = new ArrayList<>();
    RewardsRepo rewardsRepo = new RewardsRepo();
    UserRepo userRepo = new UserRepo();
    public void accessRewards(BlockingQueue<List<User>> sharedQueue, int userId)
    {
        boolean flag = true;
        while (flag)
        {
            List<User> users;
            try
            {
                //fetch all Rewards
                List<Rewards> rewards = rewardsRepo.fetchRewards();

                //fetch users from Background Transactions Thread
                //users = sharedQueue.take();

                //Show Rewards to the user
                users = userRepo.fetchUsers();
                displayRewards(rewards, users, userId);

                flag= false;
            }
            catch (Exception e)
            {
               System.out.println(e.getMessage());
               break;
            }
        }
    }

    private void displayRewards(List<Rewards> rewards, List<User> users, int userId)
    {
        User user = util.getUser(users, userId);
        if(user != null)
        {
            //show Rewards
            util.msg1();
            for (Rewards reward : rewards)
            {
                System.out.println(reward.getId() + ". " + reward.getName());
            }
            util.msg2();

            Scanner sc = new Scanner(System.in);
            int id = sc.nextInt();
            try
            {
                if (util.validateId(id))
                {
                    //Show Rewards Categories
                    try
                    {
                        displayCat(user, id);
                    }
                    catch(Exception e)
                    {
                        System.out.println(RED + e.getMessage() + RESET);
                        displayCat(user, id);
                    }

                    redeemItems(rewards, users, user, userId);
                }
                else
                {
                    throw new IllegalArgumentException("input is only valid if 1,2 or 3");
                }
            }
            catch (Exception e)
            {
                System.out.println(RED + e.getMessage() + RESET);
                displayRewards(rewards, users, userId);
            }
        }
        else
        {
            System.out.println(PURPLE + "No Transactions made till now by thread, try re login." + RESET);
            //Creating users, Background Transaction thread again when user didn't get chance first time
            try
            {
                RewardApp.main(null);
            }
            catch (Exception e)
            {
                System.out.println(RED + e.getMessage() + RESET);
            }
        }

    }

    private void displayCat(User user, int id)
    {
        List<Category> categories = rewardsRepo.getCategories(id);
        System.out.println("Number \tCategory \tPoints");

        for (Category category : categories)
        {
            System.out.println(category.getId() + "  \t\t" + category.getName()
                    + " \t" + category.getPoints());
        }

        util.msg3(user.getPoints());
        System.out.println("Enter Which " + YELLOW + "category" + RESET + " you want to redeem: ");

        Scanner sc = new Scanner(System.in);
        int catId = sc.nextInt();
        if (util.validateId(catId))
        {
            Category category = categories.get(catId - 1);
            if (user.getPoints() >= category.getPoints())
            {
                //Generating Coupon Code when item redeemed
                GiftCard giftCard = new GiftCard();
                giftCard.setCouponCode(util.generateCouponCode());
                giftCard.setName(category.getName());
                giftCard.setPoints(category.getPoints());

                int insertCount = rewardsRepo.saveGiftCards(giftCard, user.getId());
                if(insertCount > 0)
                {
                    util.msg4(category.getName(), giftCard.getCouponCode());
                    user.setPoints(user.getPoints() - category.getPoints());
                    rewardsRepo.updateUserPoints(user);
                }

                giftCards.add(giftCard);
                user.setGiftCards(giftCards);
            }
            else
            {
                util.msg5();
            }
        }
        else
        {
            throw new IllegalArgumentException("input is only valid if 1,2 or 3");
        }
    }

    private void redeemItems(List<Rewards> rewards, List<User> users, User user, int userId)
    {
        System.out.println("Do you want redeem items again: " + YELLOW +  "Y/N" + RESET);
        Scanner sc = new Scanner(System.in);
        String status = sc.nextLine().trim();

        if(status.equalsIgnoreCase("Y"))
        {
            displayRewards(rewards, users, userId);
        }
        else
        {
            System.out.println(CYAN + "*************************************" +
                    "*********************************" + RESET);
            System.out.println(BLUE + "Summary of all redeemed Rewards:" + RESET);
            if(user.getGiftCards() != null && user.getGiftCards().size() >=1)
            {
                displayGiftCards(user);
            }
            else{
                System.out.println(YELLOW + "Sorry!! No Gift Cards Redeemed." + RESET);
                System.out.println(CYAN + "*************************************" +
                        "*********************************" + RESET);
            }
        }
    }

    private void displayGiftCards(User user)
    {
        List<GiftCard> giftCards1 = rewardsRepo.getGiftCards(user);
        int i = 0;

        for(GiftCard giftCard1 : giftCards1)
        {
            i++;
            System.out.println(YELLOW + i + "." + giftCard1.getName() + " Coupon code "
                    + giftCard1.getCouponCode() + " worth of "+ giftCard1.getPoints()  +" points"+  RESET);
        }

        System.out.println(PURPLE + "Use codes at www.teluskorewards.com website to purchase them.");
        System.out.println("Thanks for visiting Rewards App, see you again." + RESET);
        System.out.println(CYAN + "*****************************************" +
                "***************************" + RESET);
    }
}
