package controller;

import service.BrandServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BrandController {
//    private static BrandServiceImpl brandService = BrandServiceImpl.getInstance();
    BrandServiceImpl brandService = new BrandServiceImpl();
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public void mainMenu() {
        System.out.println();
        System.out.println("1. 신규 | 2. 조회 | 3. 수정 | 4. 삭제");
        System.out.println("메뉴 선택:");

        try {
            String choose = in.readLine();

            switch (choose) {
                case "1" -> {
                    brandService.add();
                    mainMenu();
                }

                case "2" -> {
                    brandService.get();
                    mainMenu();
                }

                case "3" -> {
                    brandService.modify();
                    mainMenu();
                }

                case "4" -> {
                    brandService.remove();
                    mainMenu();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
