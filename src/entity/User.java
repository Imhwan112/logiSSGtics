package entity;

import lombok.*;

import java.security.Timestamp;

@Data
public class User {
    private int seller_id;
    private enum UserRole {
        GENERAL_MANAGER,
        WAREHOUSE_MANAGER,
        DELIVERYMAN,
        USER,
        NON_USER;
    };
    private int comp_reg_num;
    private String comp_name;
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
