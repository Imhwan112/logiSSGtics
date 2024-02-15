package service;


import entity.Order;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//추가해야될거 update관련하여 주문번호 없을시 없다는 표시 출력 및 잘못된 문자 입력시 전화면으로 되돌아가야함
public class OrderServiceImpl implements OrderService {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private DataSource dataSource;

//    public OrderServiceImpl() {
//        this.dataSource = DBConnection.getDataSource();
//    }

    @Override
    public void mainMenu() {
        while (true) {
            try {
                System.out.printf("%30s%n", "주문 관리 시스템");
                System.out.println("1.주문 등록 | 2.단건 주문 조회 | 3.주문 확정 | 4.종료 ");

                int inputNum = Integer.parseInt(br.readLine());
                switch (inputNum) {
                    case 1 -> {
                        System.out.println("주문 등록 실행");
                        orderDao.createOrder();
                    }
                    case 2 -> {
                        System.out.println("주문번호로 주문을 조회");
                        retrieveOrder();
                    }
                    case 3 -> {
                        System.out.println("수집한 주문들 확정");
                        orderConfirm();
                    }
                    case 4 -> {
                        exit();
                    }
                    default -> {
                        System.out.println("올바른 숫자를 입력하시오");
                    }
                }
            } catch (NumberFormatException n) {
                System.out.println("문자가 아닌 숫자를 입력하시오");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("메인 메뉴 출력 오류 발생");
            }

        }
    }

    @Override
    public void subList() {
        try {
            System.out.println("1.주문 수정 | 2.주문 삭제 | 3.되돌아가기");
            int num = Integer.parseInt(br.readLine());
            switch (num) {
                case 1 -> {
                    updateOrder();
                }
                case 2 -> {
                    retrieveDeleteOrder();
                }
                case 3 -> {
                    return;
                }
                default -> {
                    System.out.println("잘못 입력하셨습니다.");
                }
            }
        } catch (NumberFormatException n) {
            System.out.println("제대로된 숫자를 입력하시오");
            n.printStackTrace();
        } catch (IOException i) {
            System.out.println("서브 메뉴 출력 오류");
            i.printStackTrace();
        }
    }



    @Override
    public void retrieveOrder() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders WHERE OrderId = ?")) {

            System.out.println("조회할 주문의 주문번호를 입력하시오");
            String input = br.readLine();
            pstmt.setString(1, input);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String orderId = resultSet.getString("orderId");
                    int customerId = resultSet.getInt("customer_Id");
                    int prodNo = resultSet.getInt("prodNo");
                    String shopId = resultSet.getString("shopId");
                    Date orderDate = resultSet.getDate("orderDate");
                    int orderQuantity = resultSet.getInt("orderQuantity");
                    String orderStatus = resultSet.getString("orderStatus");
                    String buyer = resultSet.getString("buyer");
                    String receiver = resultSet.getString("receiver");
                    String recipientPhone = resultSet.getString("recipientPhone");
                    String recipientAddr = resultSet.getString("recipientAddr");
                    int unitPrice = resultSet.getInt("unitPrice");
                    int salesAmount = resultSet.getInt("salesAmount");
                    int payment = resultSet.getInt("payment");
                    String courierName = resultSet.getString("courierName");
                    String deliveryCategory = resultSet.getString("DeliveryCategory");
                    Order order = new Order(id, orderId, customerId, prodNo, shopId, orderDate, orderQuantity, orderStatus, buyer, receiver, recipientPhone, recipientAddr, unitPrice, salesAmount, payment, courierName, deliveryCategory);

                    System.out.println(order);
                    subList();
                } else {
                    System.out.println("주문번호가 틀렸습니다");
                    return;
                }
            }
        } catch (SQLException | IOException s) {
            s.printStackTrace();
        }
    }


    @Override
    public void orderConfirm() {
        try (Connection conn = dataSource.getConnection()) {
            if (conn == null) {
                System.out.println("데이터베이스 연결에 실패했습니다.");
                return;
            }

            System.out.println("시작일을 입력하세요 (YYYY-MM-DD):");
            String startDateStr = br.readLine();
            System.out.println("종료일을 입력하세요 (YYYY-MM-DD):");
            String endDateStr = br.readLine();

            try {
                String sql = "SELECT * FROM orders WHERE OrderDate BETWEEN ? AND ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false); // 날짜 형식 검증을 위해 설정
                Date startDate = sdf.parse(startDateStr);
                Date endDate = sdf.parse(endDateStr);

                // PreparedStatement에 날짜 형식으로 파라미터 설정
                pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
                pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

                ResultSet resultSet = pstmt.executeQuery(); // try-with-resources 블록 내부로 이동
                boolean foundOrders = false;

                while (resultSet.next()) {
                    foundOrders = true;
                    String orderId = resultSet.getString("OrderId");
                    System.out.println("Confirming OrderId: " + orderId);
                    changeOrderStatusToConfirmed(orderId);
                }
                if (!foundOrders) {
                    System.out.println("해당 기간에 주문이 없습니다.");
                }
            } catch (ParseException e) {
                System.out.println("올바른 날짜 형식을 입력하세요 (YYYY-MM-DD)");
                return; // 메서드 종료
            }
        } catch (SQLException | IOException s) {
            s.printStackTrace();
        }
    }

    private void changeOrderStatusToConfirmed(String orderId) throws SQLException {
        String sql = "UPDATE orders SET orderStatus = 'Completed' WHERE OrderId = ? AND orderStatus <> 'Completed'";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orderId); // 주문 ID 설정
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("주문 확정 완료");
            } else {
                System.out.println("이미 확정된 주문이거나 해당 주문을 찾을 수 없습니다.");
            }
        }
    }


    @Override
    public void exit() {
        System.out.println("프로그램 종료");
        System.exit(0);
    }

    @Override
    public void retrieveDeleteOrder() {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("삭제할 주문의 주문번호를 입력하세요:");
            String input = br.readLine();

            // 주문이 존재하는지 확인하는 쿼리 실행
            String sql = "SELECT * FROM orders WHERE OrderId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, input);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // 주문이 존재하면 삭제 진행
                String orderId = rs.getString("orderId");
                System.out.println("삭제하시려면 '삭제'를 입력하세요:");
                String confirmDelete = br.readLine();
                if (confirmDelete.equalsIgnoreCase("삭제")) {
                    String delSql = "DELETE FROM orders WHERE OrderId = ?";
                    PreparedStatement delPstmt = conn.prepareStatement(delSql);
                    delPstmt.setString(1, orderId);
                    int rows = delPstmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("주문 삭제 성공");
                    } else {
                        System.out.println("주문 삭제 실패");
                    }
                    delPstmt.close();
                } else {
                    System.out.println("삭제가 취소되었습니다.");
                }
            } else {
                System.out.println("주문번호가 존재하지 않습니다.");
            }
            // 리소스 해제
            rs.close();
            pstmt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrder() {
        updateSubOrder();
    }

    @Override
    public void updateSubOrder() {
        try {
            System.out.println("1.구매자 정보 수정 | 2.수령인 정보 수정 | 3.배송상태 수정 | 4.되돌아가기");
            System.out.print("숫자를 입력하시오 : ");
            int num = Integer.parseInt(br.readLine());
            switch (num) {
                case 1 -> {
                    updateBuyerInfo();
                }
                case 2 -> {
                    updateReceiverInfo();
                }
                case 3 -> {
                    updateShipStatus();
                }
                case 4 -> {
                    return;
                }
                default -> {
                    System.out.println("제대로된 숫자를 입력하시오");
                }
            }
        } catch (NumberFormatException n) {
            System.out.println("문자 대신 숫자를 입력하시오");
            n.printStackTrace();
        } catch (IOException i) {
            System.out.println("update 서브메뉴 출력 오류");
            i.printStackTrace();
        }
    }

    @Override
    public void updateReceiverInfo() {
        try {
            Connection conn = dataSource.getConnection();
            System.out.print("수정할 주문의 주문번호를 입력하세요: ");
            String input = br.readLine();
            String nameSQL = "UPDATE orders SET receiver = ? WHERE orderId = ? ";
            PreparedStatement namePstmt = conn.prepareStatement(nameSQL);
            System.out.println("수령인 이름을 수정하시겠습니까? Y/N");
            String upName = br.readLine();
            if (upName.equalsIgnoreCase("y")) {
                System.out.println("수정할 수령인 이름을 입력하시오(10글자 제한)");
                String nameInput = br.readLine();
                namePstmt.setString(1, nameInput);
                namePstmt.setString(2, input);
                int rows = namePstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("수령인 이름 수정완료");
                } else {
                    System.out.println("수정인 이름 수정실패");
                }
            } else if (upName.equalsIgnoreCase("n")) {
                String phoneSql = "UPDATE orders SET RecipientPhone = ? WHERE orderId = ? ";
                PreparedStatement phonePstmt = conn.prepareStatement(phoneSql);
                System.out.println("수령인 번호를 수정하시겠습니까? Y/N");
                String inputCase = br.readLine();
                if (inputCase.equalsIgnoreCase("y")) {
                    System.out.println("수정할 번호를 입력하시오 양식(010-xxxx-xxxx)");
                    String phoneInput = br.readLine();
                    phonePstmt.setString(1, phoneInput);
                    phonePstmt.setString(2, input);
                    int rows = phonePstmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("수령인 번호가 수정되었습니다");
                    } else {
                        System.out.println("수령인 번호 수정 실패");
                    }
                } else if (inputCase.equalsIgnoreCase("n")) {
                    String addrSql = "UPDATE orders SET RecipientAddr = ? WHERE orderId = ? ";
                    PreparedStatement addrPstmt = conn.prepareStatement(addrSql);
                    System.out.println("수령인 주소를 수정하시겠습니까? Y/N");
                    String inputAddr = br.readLine();
                    if (inputAddr.equalsIgnoreCase("y")) {
                        System.out.println("수정할 주소를 입력하시오 (상세 주소를 입력하시오(도로명 주소 or 지번 + 동호수)");
                        String addrInput = br.readLine();
                        addrPstmt.setString(1, addrInput);
                        addrPstmt.setString(2, input);
                        int rows = addrPstmt.executeUpdate();
                        if (rows > 0) {
                            System.out.println("주소가 수정되었습니다");
                        } else {
                            System.out.println("주소 수정 실패");
                        }
                    } else if (inputAddr.equalsIgnoreCase("n")) {
                        updateSubOrder();
                    }
                } else {
                    updateSubOrder();
                }
            }

        } catch (SQLException | IOException s) {
            s.printStackTrace();
        }
    }

    @Override
    public void updateShipStatus() {
        try {
            Connection conn = dataSource.getConnection();
            System.out.print("수정할 주문의 주문번호를 입력하세요: ");
            String input = br.readLine();
            String sql = "UPDATE orders SET DEliveryCategory = ? WHERE orderId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            System.out.println("배송 상태를 수정하시겠습니까? Y/N");
            String inputShip = br.readLine();
            if (inputShip.equalsIgnoreCase("y")) {
                System.out.print("수정할 주문의 배송상태(무료,착불,선불)를 입력하세요: ");
                String shipInput = br.readLine();
                pstmt.setString(1, shipInput);
                pstmt.setString(2, input);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("배송상태 변경완료");
                } else {
                    System.out.println("배송상태 변경 실패");
                }
            } else if (inputShip.equalsIgnoreCase("n")) {
                subList();
            } else {
                subList();
            }

        } catch (SQLException s) {
            System.out.println("배송 상태 수정 SQL문 실패");
            s.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateBuyerInfo() {
        try {
            //구매자 정보,
            Connection conn = dataSource.getConnection();
            System.out.print("수정할 주문의 주문번호를 입력하세요: ");
            String input = br.readLine();
            // 주문이 존재하는지 확인하는 쿼리 실행
            String nameSql = "UPDATE orders o JOIN Customer c on o.customer_id = c.customer_id SET c.name = ? WHERE o.orderID = ? ";
            PreparedStatement namePstmt = conn.prepareStatement(nameSql);
            System.out.println("구매자 이름을 수정하시겠습니까? Y/N");
            String upName = br.readLine();
            if (upName.equalsIgnoreCase("y")) {
                System.out.println("수정할 구매자 이름을 입력하시오(최대 5글자)");
                String nameInput = br.readLine();
                namePstmt.setString(1, nameInput);
                namePstmt.setString(2, input);
                int rows = namePstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("구매자 이름이 수정되었습니다");
                } else {
                    System.out.println("구매자 이름 수정 실패");
                }
            } else if (upName.equalsIgnoreCase("n")) {
                String phoneSql = "UPDATE orders o JOIN Customer c on o.customer_id = c.customer_id SET c.phone = ? WHERE o.orderID = ? ";
                PreparedStatement phonePstmt = conn.prepareStatement(phoneSql);
                System.out.println("구매자 번호를 수정하시겠습니까? Y/N");
                String inputCase = br.readLine();
                if (inputCase.equalsIgnoreCase("y")) {
                    System.out.println("수정할 번호를 입력하시오 양식(010-xxxx-xxxx)");
                    String phoneInput = br.readLine();
                    phonePstmt.setString(1, phoneInput);
                    phonePstmt.setString(2, input);
                    int rows = phonePstmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("구매자 번호가 수정되었습니다");
                    } else {
                        System.out.println("구매자 번호 수정 실패");
                    }
                } else {
                    subList();
                }
            } else {
                subList();
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goBack() {
        return;
    }

    public void myDaoMethod() {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM your_table_name");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            // Process the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // Print or process the retrieved data
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
    }

}

