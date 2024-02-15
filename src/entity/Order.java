package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString @AllArgsConstructor
public class Order {
    private int id;
    private String orderId;
    private int customer_Id;
    private int prodNo;
    private String shopId;
    private Date orderDate;
    private int orderQuantity;
    private String orderStatus;
    private String buyer;
    private String receiver;
    private String recipientPhone;
    private String recipientAddr;
    private int unitPrice;
    private int salesAmount;
    private int payment;
    private String courierName;
    private String deliveryCategory;


}
