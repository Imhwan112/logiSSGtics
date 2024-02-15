import config.UserManager;
import service.UserServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        UserServiceImpl userService = new UserServiceImpl();

        String userId = "ttda12";
        String email = "erge@naver.com";
        String phone = "010-2122-8150";
//        System.out.println(userService.isValidUserId(userId));
//        System.out.println(userService.emailValidator(email));
//        System.out.println(userService.isValidPhoneNumber(phone));

        /**
         * === Login Flow ===
         * = userLogin(user_id, pw) =
         *      -> user_id 와 password 를 받아와서 authUser 를 불러서 로그인 인증을 합니다
         *      = authUser(user_id, pw) =
         *          -> 유저에게 받은 user_id로 DB user 테이블에서 값이 있는지 확인합니다
         *          -> 값이 없다면 해당 아이디를 쓰는 유저가 없음으로 null 값 return + [로그인 실패]
         *              -> 값이 있다면 아이디를 쓰는 사람이 있음으로 인증완료 문구출력
         *              -> storeUser 호출
         *          = storeUser =
         *              -> 해당 아이디로 DB 에서 찾아온 모든 User 값인 rs를 User에 저장
         *              -> 값 저장 실패시 "값 저장 실패" 문구 출력
         *              -> user return
         *          -> 성공적으로 저장을 했으면 인증완료 문구 출력
         *          = statusChecker(user) =
         *              -> 로그인 인증이 끝난 로그인 할 유저의 계정의 status(상태)를 점검
         *                  -> request : 승인요청 가능, 요청 할건지 물어봄
         *                      -> Y 일시
         *                          = approveUser(user) !관리자전용! =
         *                              -> user 에서 status 를 request 에서 activate로 변경
         *                      -> N 일시 뒤로가기
         *                  -> DEACTIVATE, BANNED 는 로그인 불가능 및 문구 출력
         *                  -> ACTIVATE : 유일한 로그인 가능
         *              -> 로그인시 UserStatus 로 return, 실패시 null return
         *          -> statusChecker 의 return 값이 null 이 아닐시 [user return]
         *          -> null 값일 시 [null return]
         *      -> auth 의 return 값이 null 이 아닐경우
         *          = loginUser(loggedInUser) =
         *              -> 싱글톤 instance 를 생성해서 curUser = 로그인한유저 로그인
         *              -> 해당 유저값 출력
         *      -> return 값이 null 일 경우 "유저 로그인 실패" 출력
         */
        // TODO:: seller, customer 구분하기
        try {
            // 구매자로 로그인할지 판매자로 할지
            // 판매자 1, 구매자 2
            String choice = br.readLine();
            userService.userLogin(choice, "user3", "password3");
            if (choice.equals("1"))
                System.out.println(UserManager.getInstance().getCurUser() + " yyyyyyyyyyyyyyyy");
            else if (choice.equals("2"))
                System.out.println(UserManager.getInstance().getCurCustomer() + " yyyyyyyyyyyyyyyy");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}