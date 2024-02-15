package service;

import dao.BrandDao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public interface BrandService {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    BrandDao brandDao = new BrandDao();

    void add();
    void get();
    void modify();
    void remove();
    void confirm();
    void cancel();
    void exit();
}
