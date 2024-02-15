package entity;

import lombok.*;
import model.UserRole;
import model.UserStatus;

import java.security.Timestamp;
import java.sql.Date;

@Data
public class Customer {
    private int customer_id;
    private UserRole userRole;
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
    private UserStatus userStatus;
    private Date reg_date;

}
