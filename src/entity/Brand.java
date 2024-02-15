package entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Brand {
    private int brandNo;
    private String brandName;
    private Timestamp reg_date;
}
