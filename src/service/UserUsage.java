package service;

import model.UserRole;

public class UserUsage {
    public static void main(String[] args) {
        UserRole userRole5 = UserRole.GENERAL_MANAGER;
        UserRole userRole1 = UserRole.WAREHOUSE_MANAGER;
        UserRole userRole2 = UserRole.DELIVERYMAN;
        UserRole userRole3 = UserRole.USER;
        UserRole userRole4 = UserRole.NON_USER;


        // 첫번째 예시
        if(userRole5.isGeneralManager()) {
            System.out.println("user is GM");
        }

        // 유저별 권한 나누는 두번째 예시
        switch (userRole2) {
            case GENERAL_MANAGER -> {
                System.out.println("user is GM");
                // method1();
                // ...
                break;
            }
            case WAREHOUSE_MANAGER -> {
                // ...
            }
        }
    }

}
