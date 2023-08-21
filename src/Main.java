import com.telusko.rewards.Thread.TransThread;
import com.telusko.rewards.dto.User;
import com.telusko.rewards.exception.AuthException;
import com.telusko.rewards.login.Login;
import com.telusko.rewards.service.UserService;

import java.util.List;

import static com.telusko.rewards.constants.Constants.*;

public class Main
{
    public static void main(String[] args)
    {
        //Creating and Fetching Users
        UserService userObj = new UserService();
        List<User> users = userObj.createUsers();

        //Background Thread for Transactions
        TransThread transThread = new TransThread(users);
        Thread thread = new Thread(transThread);
        thread.start();

        //User Authentication
        Login login = new Login();
        try
        {
            login.authentication(users);
        }
        catch (AuthException e)
        {
            System.out.println(RED + e.getMessage() + RESET);
            //Allowing User for Second Time Login
            try
            {
                login.authentication(users);
            }
            catch (AuthException ex)
            {
                System.out.println(RED + e.getMessage() + RESET);
                System.out.println(PURPLE + "you have exceeded the maximum number" +
                        " of attempts, please try again after some time.");
            }
        }
    }
}