package Service;

import Dao.WarehouseDao;
import Entity.Warehouse;
import Entity.WarehouseAddress;
import Entity.WarehouseCategory;
import Menu.WarehouseMenu;
import Service.WarehouseService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WarehouseServiceImpl implements WarehouseService {

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private List<Warehouse> whList = new ArrayList<Warehouse>();

    WarehouseDao dao = new WarehouseDao();

    public void addWarehouse() {

        Warehouse wh = new Warehouse();
        WarehouseDao whDao = new WarehouseDao();
        System.out.println("--------------------------------------------------- 창고 신규 등록 ---------------------------------------------------");
        WarehouseMenu menu = new WarehouseMenu();

        try {
            System.out.println("분류 코드를 입력하세요");

            for(WarehouseCategory wc : whDao.getClassification()) {
                System.out.println(wc.getTopCategoryNum() + "\t" + wc.getTopCategoryName());
            }

            int classifyCode = Integer.parseInt(br.readLine());

            wh.setTopCategoryNum(classifyCode);
            System.out.println("창고명을 입력하세요.");
            String warehouseName = br.readLine();
            wh.setWhName(warehouseName);

            System.out.println("주소를 선택하세요");

            for(WarehouseAddress wa : whDao.getAddrList()) {
                System.out.println(wa.getWhAddressNo() + "\t" + wa.getWhAddressName());
            }
            int addressNo = Integer.parseInt(br.readLine());
            wh.setWhAddrNo(addressNo);

            System.out.println("담당자 연락처를 입력하세요");
            String tel = br.readLine();
            wh.setManagerPhone(tel);
            System.out.println();

            System.out.println("창고를 생성하시겠습니까? Y/N");
            String s = br.readLine();

            if(s.charAt(0)=='Y') {
                dao.add(wh);
                System.out.println("창고가 생성되었습니다.");
            } else {
                menu.mainMenu();
            }


        }catch(Exception e) {

        }


    }

    public void getWarehouse() {
        try{
            System.out.println("1. 분류별 조회");
            System.out.println("2. 소재지별 조회");
            System.out.println("3. 창고명별 조회");
            System.out.println("4. 전체 조회");

            int categoryNum = Integer.parseInt(br.readLine());

            switch(categoryNum) {
                case 1 ->  ClassifyWarehouse();
                case 2 ->  ClassifyWarehouseAddr();
                case 4 ->  {
                    System.out.println("--------------------------------------------------- 창고 리스트 출력 ---------------------------------------------------\n");
                    getWarehouseAll();
                }
            }

            System.out.println();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void getWarehouseAll() {
        WarehouseDao whDao = new WarehouseDao();
        List<Warehouse> whList;

        //SimpleDateFormat date = new SimpleDateFormat("yyyy-mm-dd");

        System.out.println("|창고 코드|   분류 코드   |   분류명   |      창고명      |      주소      |         창고 등록일         |   창고 담당 부서 연락처   |");
        whList = whDao.get();

        for(Warehouse wh: whList) {
            System.out.printf("\t%-10s%-13s%-10s%-18s%-13s%-30s%-15s\n", wh.getWhCode(), wh.getTopCategoryNum(), wh.getTopCategoryName()
                                ,wh.getWhName(), wh.getWhAddrName(), wh.getWhRegistDate(), wh.getManagerPhone());
            System.out.println();
        }

    }
    //분류 코드 리스트 조회

    public void ClassifyWarehouse() {

        WarehouseDao whDao = new WarehouseDao();
        List<WarehouseCategory> whCategory;
        whCategory = whDao.getClassification();

        try {
            for(WarehouseCategory whC: whCategory) {
                System.out.println(whC.getTopCategoryNum() + "\t" + whC.getTopCategoryName());
            }
            System.out.println("분류 코드를 입력하세요");
            int categoryNum = Integer.parseInt(br.readLine());
            ClassifyDetailedWarehouse(categoryNum);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }



    //분류 코드별 리스트 조회
    public void ClassifyDetailedWarehouse(int categoryNum) {

        WarehouseDao whDao = new WarehouseDao();

        //List<Warehouse> wh = whDao.getCategoryClassify(categoryNum);


        System.out.println("|창고 코드|   분류 코드   |   분류명   |      창고명      |      주소      |         창고 등록일         |   창고 담당 부서 연락처   |");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        whList = whDao.getCategoryClassify(categoryNum);

        for(Warehouse wh: whList) {

            System.out.printf("\t%-10d%-13d%-10s%-18s%-13s%-30s%-15s\n", wh.getWhCode(), wh.getTopCategoryNum(), wh.getTopCategoryName()
                    ,wh.getWhName(), wh.getWhAddrName(), wh.getWhRegistDate(), wh.getManagerPhone());
            System.out.println();
        }
    }


    public void ClassifyWarehouseAddr() {
        WarehouseDao whDao = new WarehouseDao();

        try{
            System.out.println("조회하고 싶은 창고의 소재지 코드를 입력하세요.");

            for(WarehouseAddress wh : whDao.getAddrList()) {
                System.out.println(wh.getWhAddressNo() + "\t" + wh.getWhAddressName());
            }

            int addr = Integer.parseInt(br.readLine());

            System.out.println("|창고 코드|   분류 코드   |   분류명   |      창고명      |      주소      |         창고 등록일         |   창고 담당 부서 연락처   |");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            whList = whDao.getAddrClassify(addr);

            for(Warehouse wh: whList) {
                System.out.printf("\t%-10s%-13s%-10s%-18s%-13s%-30s%-15s\n", wh.getWhCode(), wh.getTopCategoryNum(), wh.getTopCategoryName()
                        ,wh.getWhName(), wh.getWhAddrName(), wh.getWhRegistDate(), wh.getManagerPhone());
                System.out.println();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


    public void modifyWarehouse() {

    }

    public void removeWarehouse() {

        try {
            getWarehouseAll();
            WarehouseMenu menu = new WarehouseMenu();
            System.out.println("삭제할 창고의 창고 코드를 입력해주세요. ");
            int code = Integer.parseInt(br.readLine());
            WarehouseDao dao = new WarehouseDao();

            System.out.printf("%d번 창고를 삭제하시겠습니까?\t1. 네\t 2. 아니요\n", code);
            int confirm = Integer.parseInt(br.readLine());
            if(confirm ==1) {
                dao.removeWarehouse(code);
                System.out.println("삭제완료");
            }else if (confirm ==2){
                menu.mainMenu();
            }else {
                System.out.println("1번 또는 2번 창고를 선택해주세요");
                removeWarehouse();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
