package exception;

import entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    ExceptionImpl errors = new ExceptionImpl();

    public void emailValidator(User user) {
        try {
            String email = br.readLine();
            if(errors.isValidEmail(email)) {
                throw new ExceptionOutput(ErrorCodeList.INVALID_INPUT_EMAIL);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
