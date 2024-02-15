package controller;

import service.ProductServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProductController {
    static ProductServiceImpl productService = new ProductServiceImpl();
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public void mainMenu() {
        System.out.println();
        System.out.println("1. 신규 | 2. 조회 | 3.수정");
        System.out.println("메뉴 선택:");

        try {
            String choose = in.readLine();

            switch (choose) {
                case "1" -> {
                    productService.add();
                    mainMenu();
                }
                case "2" -> {
                    productService.get();
                    mainMenu();
                }
                case "3" -> {
                    productService.modify();
                    mainMenu();
                }

                //case "3" -> productService.exit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
