package entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Category {
    private int categoryNo;
    private String categoryName;
    private Timestamp reg_date_category;
}
