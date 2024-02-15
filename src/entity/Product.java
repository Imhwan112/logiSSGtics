package entity;

import lombok.*;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class Product {
    private int prodNo;
    private int categoryNo;
    private String categoryName;
    private int brandNo;
    private String prodName;
    private String prodBrand;
    private int salesPrice;
    private String prodDescription;
    private Timestamp reg_date;
    private String salesState;
    private int isProdDisplay;
}
