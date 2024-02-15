package menu;

import controller.BrandController;
import controller.ProductController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Menu {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    BrandController brand = new BrandController();
    ProductController productController = new ProductController();
    //WarehouseController warehouseController = new WarehouseController();

    public static void menuList(){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BrandController brand = new BrandController();
        ProductController productController = new ProductController();
        System.out.println();
        System.out.println("1. 브랜드 관리 | 2. 상품 관리 | 3. 창고 관리");
        System.out.println("메뉴 선택:");


        try {
            String choose = in.readLine();

            switch (choose) {
                case "1" -> {
                    brand.mainMenu();
                }

                case "2" -> {
                    productController.mainMenu();
                }

                case "3" -> {
                    //warehouseController.mainMenu();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        menuList();
    }

}
