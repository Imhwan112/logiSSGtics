package entity;

import Model.UserRole;
import Model.UserStatus;
import lombok.*;

import java.security.Timestamp;
import java.sql.Date;

@Data
public class User {
    private int seller_id;
    private UserRole userRole;
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
    private UserStatus userStatus;
    private Date reg_date;

}
