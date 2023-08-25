package com.telusko.rewards.login;

import com.telusko.rewards.dto.User;
import com.telusko.rewards.exception.AuthException;
import com.telusko.rewards.util.Util;

import java.util.List;
import java.util.Scanner;

import static com.telusko.rewards.constants.Constants.*;

public class Login
{
    Util util = new Util();

    public Login()
    {
        System.out.println(CYAN + "**********************************");
        System.out.println("Welcome to Rewards Application!!!");
        System.out.println("**********************************" + RESET);
    }

    public int authentication(List<User> users) throws AuthException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your Name:");
        String name = sc.nextLine().trim();

        System.out.println("Please enter your Password:");
        String pwd = sc.nextLine().trim();
        String excMsg = "";

        //Validating user
        int userId = 0;
        for (User user : users)
        {
            String decPwd = util.decryptPwd(user.getPassword());

            if (user.getName().equalsIgnoreCase(name) && decPwd.equals(pwd))
            {
                System.out.println(GREEN + "Successfully logged in!!!" + RESET);
                userId = user.getId();
                excMsg = "";
                break;
            }
            else
            {
                excMsg = "Invalid credentials";
            }
        }
        if(!excMsg.isEmpty())
        {
            throw new AuthException(excMsg);
        }
        return userId;
    }
}
