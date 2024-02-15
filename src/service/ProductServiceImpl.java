package service;

import dao.ProductDao;
import entity.Category;
import entity.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ProductServiceImpl implements ProductService{
    @Override
    public void add() {

        try {
            Product product = new Product();

            System.out.println("[상품-신규 등록]");

            System.out.print("\n상품명: ");
            product.setProdName(in.readLine());

            System.out.println("\n[카테고리]");
            System.out.println("--".repeat(25));
            get_category();
            System.out.println("--".repeat(25));
            System.out.print("상품의 카테고리 번호를 선택해주세요. ");

            product.setCategoryNo(Integer.parseInt(in.readLine()));

            System.out.println("\n[브랜드]");
            System.out.println("--".repeat(25));
            brandService.get();
            System.out.println("--".repeat(25));
            System.out.print("상품의 브랜드 번호를 선택해주세요. ");
            product.setBrandNo(Integer.parseInt(in.readLine()));

            System.out.print("\n판매가: ");
            product.setSalesPrice(Integer.parseInt(in.readLine()));

            System.out.print("\n상품 설명: ");
            String description = "";
            StringBuilder stringBuilder = new StringBuilder();
            while((description = in.readLine()) != null)
            {
                if(description.equals("exit")) break;
                stringBuilder.append(description).append("\n");
            }

            product.setProdDescription(stringBuilder.toString());
            productDao.add_db(product);
        } catch (IOException io) {
            io.printStackTrace();
        }


    }

    @Override
    public void get() {
        List<Product> products = productDao.get_all_db();

        System.out.println("[상품 목록]");
        System.out.println("No \t 상품명 \t 카테고리 \t 브랜드 \t 판매가 \t 상품설명 \t 등록일");
        for(Product product: products) {
            String des = product.getProdDescription();
            if(des.length() > 10) {
                des = des.substring(0, 5) + "...";
            }
            System.out.println(product.getProdNo() + " " + product.getProdName() + " " +
                    product.getCategoryName() + " " + product.getProdBrand() + " " + product.getSalesPrice() + " " +
                    des + " " + product.getReg_date());

        }

        System.out.println("조회하실 상품의 번호를 입력해 주세요.");
        System.out.println("prodNo: ");
        try {
            int prodNo = Integer.parseInt(in.readLine());
            get_one(prodNo);

        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    @Override
    public void get_one(int prodNo) {
        Product product = productDao.get_one_db(prodNo);

        System.out.println("No \t 상품명 \t 카테고리 \t 브랜드 \t 판매가 \t 상품설명 \t 등록일");
        System.out.println(product.getProdNo() + " " + product.getProdName() + " " +
                product.getCategoryName() + " " + product.getProdBrand() + " " + product.getSalesPrice() + " " +
                product.getProdDescription() + " " + product.getReg_date());
    }

    private void get_category() {
        List<Category> categories = productDao.get_categpry();


        System.out.println("No \t 카테고리명");
        for(Category category: categories) {
            System.out.printf("%-5d%s\n", category.getCategoryNo(), category.getCategoryName());
        }
    }

    @Override
    public void modify() {
        try {
            Product product = new Product();

            System.out.println("[상품-수정]");
            get();
            System.out.println("수정하시려는 상품의 번호를 입력해 주세요.");
            System.out.print("prodNo: ");
            int prodNo = Integer.parseInt(in.readLine());

            System.out.print("상품명: ");
            product.setProdName(in.readLine());

            System.out.println("[카테고리]");
            get_category();
            System.out.print("상품의 카테고리 번호를 선택해주세요. ");
            product.setCategoryNo(Integer.parseInt(in.readLine()));

            System.out.println("[브랜드]");
            brandService.get();
            System.out.print("상품의 브랜드 번호를 선택해주세요. ");
            product.setBrandNo(Integer.parseInt(in.readLine()));

            System.out.print("판매가: ");
            product.setSalesPrice(Integer.parseInt(in.readLine()));

            System.out.print("상품 설명: ");
            String description = "";
            StringBuilder stringBuilder = new StringBuilder();
            while((description = in.readLine()) != null)
            {
                if(description.equals("exit")) break;
                stringBuilder.append(description).append("\n");
            }

            product.setProdDescription(stringBuilder.toString());

            System.out.println("[판매상태]");
            System.out.println("1. 판매중 | 2. 판매중지");
            System.out.println("판매상태를 입력해 주세요.");
            String state = in.readLine();

            switch (state) {
                case "1" -> {
                    product.setSalesState("판매중");
                    product.setIsProdDisplay(1);
                }
                case "2" -> {
                    product.setSalesState("판매중지");
                    product.setIsProdDisplay(0);
                }
                default -> System.out.println("잘못 입력 하셨습니다. 다시 입력해 주세요.");
            }

            productDao.modify_db(prodNo, product);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    public void confirm() {

    }

    @Override
    public void cancel() {

    }




}
