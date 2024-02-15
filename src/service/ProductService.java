package service;

import dao.ProductDao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;

public interface ProductService {
    final ProductDao productDao = new ProductDao();

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    //    private static BrandServiceImpl brandService = BrandServiceImpl.getInstance();
    BrandServiceImpl brandService = new BrandServiceImpl();

    void add();
    void get();
    void get_one(int prodNo);
    void modify();
    void confirm();
    void cancel();
}
