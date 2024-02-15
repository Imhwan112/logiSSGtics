package dao;

import config.DBConnection;
import entity.Order;
import lombok.Data;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

@Data
public class OrderDao {
    private DataSource dataSource;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public OrderDao(){

        this.dataSource = DBConnection.getDataSource();
    }

    public int getOrderId() {
        String sql = "SELECT id FROM ORDERS ORDER BY id desc limit 1";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                )
        {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getInt("id") + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void createOrder() {
        String sql = "SELECT c.customer_Id, p.prodNo, s.shopId, p.salesPrice AS unitPrice, st.stockQuantity AS Quantity " +
                "FROM Customer c " +
                "JOIN Orders o ON o.customer_Id = c.customer_Id " +
                "JOIN ShoppingMall s ON s.shopid = o.shopid " +
                "JOIN Product p ON p.prodNo = o.prodNo " +
                "JOIN order_insert oi ON oi.prodNo = p.prodNo " +
                "JOIN Stock st ON st.insertCode = oi.insertCode " +
                "WHERE st.stockQuantity > 0";
        String insertSql = "INSERT INTO orders (orderId, customer_Id, prodNo, shopId, OrderDate, OrderQuantity, OrderStatus, buyer, receiver, RecipientPhone, RecipientAddr, UnitPrice, SalesAmount, Payment, CourierName, DeliveryCategory) " +
                "VALUES (?, ?, ?, ?, now(), ?, 'New Registration', ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement selPstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement insPstmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
                ResultSet resultSet = selPstmt.executeQuery();
        ) {
            if (resultSet.next()) {
                int customerId = resultSet.getInt("customer_Id");
                String shopId = resultSet.getString("shopId");
                int prodNo = resultSet.getInt("prodNo");
                int unitPrice = resultSet.getInt("unitPrice");
                String orderIdString = "ORD" + getOrderId();
                insPstmt.setString(1, orderIdString);
                insPstmt.setInt(2, customerId);
                insPstmt.setInt(3, prodNo);
                insPstmt.setString(4, shopId);
                System.out.println("주문 수량을 입력하시오");
                int orderQuantity = Integer.parseInt(br.readLine());
                insPstmt.setInt(5, orderQuantity);
                System.out.println("구매자를 입력하시오");
                String buyer = br.readLine();
                insPstmt.setString(6, buyer);
                System.out.println("수령인을 입력하시오");
                String receiver = br.readLine();
                insPstmt.setString(7, receiver);
                System.out.println("수령인 번호를 입력하시오 (ex 010-xxxx-xxxx)");
                String recipientPhone = br.readLine();
                insPstmt.setString(8, recipientPhone);
                System.out.println("수령인 주소를 입력하시오 (도로명주소 or 지번 + 동호수)");
                String recipientAddr = br.readLine();
                insPstmt.setString(9, recipientAddr);
                insPstmt.setInt(10, unitPrice);
                int salesAmount = orderQuantity * unitPrice;
                insPstmt.setInt(11, salesAmount);
                insPstmt.setInt(12, salesAmount); // Payment 열 값 설정
                System.out.println("택배사를 입력하시오");
                String courierName = br.readLine();
                insPstmt.setString(13, courierName);
                System.out.println("배송 방법을 선택하시오(무료, 착불, 선불)");
                String deliveryCategory = br.readLine();
                insPstmt.setString(14, deliveryCategory);


                int rows = insPstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("주문생성 성공");
                } else {
                    System.out.println("실패1");
                }
             } else {
                System.out.println("주문 생성 실패2");
            }
        } catch (SQLException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

