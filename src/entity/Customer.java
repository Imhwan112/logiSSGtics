package entity;

import lombok.*;

import java.security.Timestamp;

@Data
public class Customer {
    private int customer_id;
    private String user_id;
    private char name;
    private String phone;
    private String password;
    private String email;
    private char addr1;
    private char addr2;
    private int zipcode;
    private char sign_up_path;
    private boolean text_agree;
    private boolean email_agree;
    private boolean ad_agree;
    private enum UserStatus {
        DEACTIVATE,
        ACTIVATE,
        BANNED,
        REQUEST;
    };
    private Timestamp reg_date;

}
