package com.telusko.rewards.service;

import com.telusko.rewards.dto.User;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import static com.telusko.rewards.constants.Constants.PASSWORD;

public class UserService
{
    List<User> users = new ArrayList<>();
    String[] names = new String[]{"Afsari", "Hari", "Jay", "Janani", "JayRam", "Kalyan",
            "Ooha", "Sai", "Srinath", "Vyankatesh"};
    Base64.Encoder encoder = Base64.getEncoder();

    public List<User> createUsers()
    {
        for(int i=0; i<10; i++)
        {
            User user = new User();
            user.setId(i+1);
            user.setName(names[i]);
            user.setPassword(encoder.encodeToString(PASSWORD.getBytes()));
            users.add(user);
        }
        return users;
    }
}
