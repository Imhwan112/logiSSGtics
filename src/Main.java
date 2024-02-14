import lib.DBConnection;
import serviceImpl.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        String userId = "ttda12";
        String email = "erge@naver.com";
//        System.out.println(userService.isValidUserId(userId));
        System.out.println(userService.emailValidator(email));

    }
}