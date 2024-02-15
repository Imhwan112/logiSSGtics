package Menu;

import Service.WarehouseServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.exit;

public class WarehouseMenu {
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    WarehouseServiceImpl whServiceImpl = new WarehouseServiceImpl();

    public void mainMenu(){

        try {
            System.out.println("--------------------------------------------------- 창고 관리 메뉴 ---------------------------------------------------");
            System.out.println("|    1. 창고 생성    |    2. 창고 조회    |    3. 창고 수정    |    4. 창고 삭제    |     5. 돌아가기     |     6. 종료     |" );

            int choice = Integer.parseInt(br.readLine());
            switch(choice) {
                case 1 -> {whServiceImpl.addWarehouse(); mainMenu();}
                case 2 -> {whServiceImpl.getWarehouse(); mainMenu();}
                case 3 -> {whServiceImpl.modifyWarehouse(); mainMenu();}
                case 4 -> {whServiceImpl.removeWarehouse(); mainMenu();}
                case 5 -> {} //전 페이지로 돌아가기
                case 6 -> exit(0);
            }


        } catch(IOException i){
            i.printStackTrace();
        }

    }

}
