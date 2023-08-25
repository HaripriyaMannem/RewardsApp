package com.telusko.rewards.service;

import com.telusko.rewards.dto.User;
import com.telusko.rewards.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.telusko.rewards.constants.Constants.PASSWORD;

public class UserService
{
    Util util = new Util();
    List<User> users = new ArrayList<>();
    String[] names = new String[]{"Afsari", "Hari", "Jay", "Janani", "JayRam", "Kalyan",
            "Ooha", "Sai", "Srinath", "Vyankatesh"};


    public List<User> createUsers()
    {
        for(int i=0; i<names.length; i++)
        {
            User user = new User();
            user.setId(i+1);
            user.setName(names[i]);
            user.setPassword(util.encryptPwd(PASSWORD));
            users.add(user);
        }
        return users;
    }

}