package service;

import com.mysql.cj.protocol.Resultset;
import entity.User;

public interface UserService {
    boolean isCompRegNumValid(int userId);
    boolean emailValidator(String email);
    boolean isValidPhoneNumber(String phone);
    boolean isValidUserId(String userID);
    void mainMenu();

}
