package service;

import dao.OrderDao;

public interface OrderService {
    OrderDao orderDao = new OrderDao();
    void mainMenu();

    void subList();



    void retrieveOrder();

    void orderConfirm();

    void exit();

    void retrieveDeleteOrder();
    void updateOrder();

    void updateBuyerInfo();

    void updateSubOrder();

    void updateReceiverInfo();

    void updateShipStatus();
    void goBack();

}
