package com.telusko.rewards.login;

import com.telusko.rewards.dto.User;
import com.telusko.rewards.exception.AuthException;
import com.telusko.rewards.util.Util;

import java.util.List;
import java.util.Scanner;

import static com.telusko.rewards.constants.Constants.CYAN;
import static com.telusko.rewards.constants.Constants.RESET;

public class Login
{
    Util util = new Util();
    public Login()
    {
        System.out.println(CYAN + "**********************************");
        System.out.println("Welcome to Rewards Application!!!");
        System.out.println("**********************************" + RESET);
    }

    public void authentication(List<User> users) throws AuthException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your Name:");
        String name = sc.nextLine().trim();

        System.out.println("Please enter your Password:");
        String pwd = sc.nextLine().trim();
        String excMsg = "";

        //Validating user
        for (User user : users)
        {
            String decPwd = util.decryptPwd(user.getPassword());

            if (user.getName().equalsIgnoreCase(name) && decPwd.equals(pwd))
            {
                System.out.println("Successfully logged in!!!");
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
    }
}
