package service;

import dao.StockDao;
import dao.UserDao;

public interface StockService {
    final StockDao stockDao = new StockDao();
    void get();
}
