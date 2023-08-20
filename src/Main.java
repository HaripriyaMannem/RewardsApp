import com.telusko.rewards.dto.User;
import com.telusko.rewards.exception.AuthException;
import com.telusko.rewards.login.Login;
import com.telusko.rewards.service.UserService;
import static com.telusko.rewards.constants.Constants.RED;
import static com.telusko.rewards.constants.Constants.RESET;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        //Fetching Users
        UserService userObj = new UserService();
        List<User> users = userObj.createUsers();

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
                System.out.println(RED + "you have exceeded the maximum number" +
                        " of attempts, please try again after some time.");
            }
        }
    }
}