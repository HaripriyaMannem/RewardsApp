package com.telusko.rewards.util;

import com.telusko.rewards.dto.Category;
import com.telusko.rewards.dto.Rewards;
import com.telusko.rewards.dto.User;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Util
{
    String[] rewardsCat = new String[]{"Electronics", "Fashion", "Furniture"};

    String[] elcCategory = new String[]{"Headphones", "Camera", "Tablet"};
    int[] elcPoints = new int[]{200, 500, 300};

    String[] fashionCat = new String[]{"Sneakers", "Handbag", "Smartwatch"};
    int[] fashionPoints = new int[]{200, 100, 300};

    String[] FurCat = new String[]{"OfficeChair", "studyTable", "Sofa"};
    int[] FurPoints = new int[]{200, 300, 500};

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
        List<Rewards> rewardsList = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        for(int i=0; i<3; i++)
        {
            Rewards rewards = new Rewards();
            rewards.setId(i+1);
            rewards.setName(rewardsCat[i]);

            for(int j=0; j<3; j++)
            {

                if(rewards.getName().equalsIgnoreCase("Electronics"))
                {
                    Category category = new Category();
                    category.setId(j+1);
                    category.setName(elcCategory[j]);
                    category.setPoints(elcPoints[j]);
                    categories.add(category);
                }
                else if(rewards.getName().equalsIgnoreCase("Fashion"))
                {
                    Category category = new Category();
                    category.setId(j+1);
                    category.setName(fashionCat[j]);
                    category.setPoints(fashionPoints[j]);
                    categories.add(category);
                }
                else{
                    Category category = new Category();
                    category.setId(j+1);
                    category.setName(FurCat[j]);
                    category.setPoints(FurPoints[j]);
                    categories.add(category);
                }
                rewards.setCategoryList(List.copyOf(categories));
            }
            rewardsList.add(rewards);
        }
        return rewardsList;
    }
}
