import lib.DBConnection;
import lib.UserManager;
import serviceImpl.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        String userId = "ttda12";
        String email = "erge@naver.com";
        String phone = "010-2122-8150";
//        System.out.println(userService.isValidUserId(userId));
//        System.out.println(userService.emailValidator(email));
//        System.out.println(userService.isValidPhoneNumber(phone));
        userService.userLogin("user3", "password3");
    }
}