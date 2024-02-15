package service;

import dao.BrandDao;
import entity.Brand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class BrandServiceImpl implements BrandService{
//    private static BrandServiceImpl instance;
//    public static BrandServiceImpl getInstance() {
//        if (instance == null) {
//            instance = new BrandServiceImpl();
//        }
//        return instance;
//    }

    @Override
    public void add() {
        try {
            Brand brand = new Brand();
            System.out.println("[브랜드-신규 등록]");
            System.out.println("추가하시려는 브랜드명을 입력해 주세요.");
            System.out.print("브랜드명: ");
            brand.setBrandName(in.readLine());
            brandDao.add_db(brand);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    public void get() {
        System.out.println("[브랜드 목록]");
        System.out.println("No \t 브랜드명 \t 등록일");
        List<Brand> brands = brandDao.get_db();

        for(Brand brand: brands) {
            System.out.printf("%-5s%-12s%-16s\n", brand.getBrandNo(), brand.getBrandName(), brand.getReg_date());
        }

    }

    @Override
    public void modify() {
        try {
            Brand brand = new Brand();
            System.out.println("[브랜드-수정]");
            get();
            System.out.println("수정하시려는 브랜드 번호를 입력해 주세요.");
            int brandNo = Integer.parseInt(in.readLine());

            System.out.print("브랜드명: ");
            brand.setBrandName(in.readLine());

            brandDao.modify_db(brandNo, brand);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    public void remove() {
        try {
            Brand brand = new Brand();
            System.out.println("[브랜드-삭제]");
            get();
            System.out.println("삭제하시려는 브랜드 번호를 입력해 주세요.");
            int brandNo = Integer.parseInt(in.readLine());

            brandDao.remove_db(brandNo);

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

    @Override
    public void exit() {

    }
}
