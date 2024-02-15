package entity;

import lombok.Data;

@Data
public class Stock {
    private int stockNo;
    private int whCode;
    private int prodNo;
    private String insertCode;
    private String prodName;
    private int stockQuantity;
    private int salesStatus;
    private int insertQuantity;
}
