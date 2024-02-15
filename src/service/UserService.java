package service;

import com.mysql.cj.protocol.Resultset;
import dao.UserDao;
import entity.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public interface UserService {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final UserDao userDao = new UserDao();

    boolean isCompRegNumValid(int userId);
    boolean emailValidator(String email);
    boolean isValidPhoneNumber(String phone);
    boolean isValidUserId(String userID);
    void userLogin(String choice, String user_id, String pw);

}
