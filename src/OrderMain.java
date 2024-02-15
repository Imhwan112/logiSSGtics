import service.OrderServiceImpl;

public class OrderMain {
    public static void main(String[] args) {
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
        orderServiceImpl.mainMenu();
    }
}
