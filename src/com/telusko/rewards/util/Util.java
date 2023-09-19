package com.telusko.rewards.util;

import com.telusko.rewards.dto.Category;
import com.telusko.rewards.dto.Rewards;
import com.telusko.rewards.dto.User;
import com.telusko.rewards.repository.RewardsRepo;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.telusko.rewards.constants.Constants.*;

public class Util
{
    String[] rewardsCat = new String[]{"Electronics", "Fashion", "Furniture"};

    String[] elcCategory = new String[]{"Earbuds", "Camera ", "Tablet "};
    int[] elcPoints = new int[]{2000, 5000, 4000};

    String[] fashionCat = new String[]{"Sneakers", "Handbag", "Smartwatch"};
    int[] fashionPoints = new int[]{2000, 1000, 3000};

    String[] FurCat = new String[]{"StudyChair", "studyTable", "Sofaset"};
    int[] FurPoints = new int[]{3000, 4000, 5000};

    public String encryptPwd(String pwd)
    {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(pwd.getBytes());
    }

    public String decryptPwd(String encPwd)
    {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(encPwd));
    }

    public List<Rewards> setRewards()
    {
        RewardsRepo rewardsRepo = new RewardsRepo();

        List<Rewards> rewardsList = new ArrayList<>();

        for(int i=0; i<rewardsCat.length; i++)
        {
            Rewards rewards = new Rewards();
            rewards.setId(i+1);
            rewards.setName(rewardsCat[i]);
            rewardsRepo.insertRewards(rewards);
            List<Category> categories = new ArrayList<>();
            for(int j=0; j<rewardsCat.length; j++)
            {
                if(rewards.getName().equalsIgnoreCase("Electronics"))
                {
                    Category category = new Category();
                    category.setId(j+1);
                    category.setName(elcCategory[j]);
                    category.setPoints(elcPoints[j]);
                    rewardsRepo.insertCategories(category, rewards);
                    categories.add(category);
                }
                else if(rewards.getName().equalsIgnoreCase("Fashion"))
                {
                    Category category = new Category();
                    category.setId(j+1);
                    category.setName(fashionCat[j]);
                    category.setPoints(fashionPoints[j]);
                    rewardsRepo.insertCategories(category, rewards);
                    categories.add(category);
                }
                else
                {
                    Category category = new Category();
                    category.setId(j+1);
                    category.setName(FurCat[j]);
                    category.setPoints(FurPoints[j]);
                    rewardsRepo.insertCategories(category, rewards);
                    categories.add(category);
                }
                rewards.setCategoryList(List.copyOf(categories));
            }
            rewardsList.add(rewards);
        }
        return rewardsList;
    }

    public boolean validateId(int id)
    {
        return (id == 1 || id == 2 || id == 3);
    }
    
    public User getUser(List<User> users, int id)
    {
        User user = null;
        for (User user1 : users) 
        {
            if(user1.getId() == id)
            {
                user = user1;
                break;
            }
        }
        return user;
    }

    public String generateCouponCode()
    {
        SecureRandom random = new SecureRandom();
        byte[] codeBytes = new byte[16];
        random.nextBytes(codeBytes);
        return Base64.getEncoder().encodeToString(codeBytes);
    }
    
    public void msg1()
    {
        System.out.println(CYAN + "-----------------------------------------");
        System.out.println(BLUE + "Have a glance at Reward Categories below:" + RESET);
    }

    public void msg2()
    {
        System.out.println( "Enter which" + YELLOW + " Reward category" + RESET + ", " +
                "you want to explore: ");
    }

    public void msg3(int points)
    {
        System.out.println("You can " + YELLOW + "redeem" + RESET +
                " any item by using points available: " + BLUE + points + RESET);
    }

    public void msg4(String name, String couponCode)
    {
        System.out.println(GREEN + "Successfully you have redeemed item: " + name);
        System.out.println(PURPLE + "Gift Card Coupon code: " + couponCode);
        System.out.println(CYAN + "-------------------------------------------" + RESET);
    }

    public void msg5()
    {
        System.out.println(RED + "Redeem failed due to insufficient points.");
        System.out.println(CYAN + "----------------------------------------" +RESET);
    }

}
