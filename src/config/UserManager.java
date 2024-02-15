package config;

import entity.Customer;
import lombok.Getter;
import model.UserStatus;
import dao.UserDao;
import entity.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * 메인메뉴에서 맨 처음에 로그인을 할 때 생성을 해서 불러줍니다
 * UserManager usermanager = new UserManager();
 *
 * 싱글톤을 사용하여 각 유저들이 1개의 인스턴스만 생성을 하고 필요할때
 * 불러서 사용을 해줍니다.
 *
 * method :
 *          loginUser() : 유저 로그인
 */
public class UserManager {
    // 싱글톤 인스턴스 생성
    private static volatile  UserManager instance;
    /**
     * -- GETTER --
     *  현재 로그인한 유저의 정보
     *
     * @return 현재 로그인한 유저의 정보
     */
    @Getter
    private User curUser;
    /**
     * -- GETTER --
     *  현재 로그인한 구매자의 정보
     *
     * @return 현재 로그인한 유저의 정보
     */
    @Getter
    private Customer curCustomer;
    private Customer customer;
    public static UserDao userDao = new UserDao();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public UserManager(){}

    // 생성한 인스턴스를 가져오기
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * 유저 로그인
     * @param user : 새로 받은 유저
     */
    public void loginUser(User user) {
        curUser = user;
    }

    /**
     * 구매자 로그인
     * @param user : 새로 받은 유저
     */
    public void loginCustomer(Customer user) {
        curCustomer = user;
    }

    /**
     * 유저 로그아웃
     */
    public void logoutUser() {
        curUser = null;
    }

    /**
     * 구매자 로그아웃
     */
    public void logoutCustomer() {
        curCustomer = null;
    }

    /**
     * 유저가 로그인이 되어 있는지 안되어 있는지 확인하는 메소드
     *
     * @return  true : 로그인 상태
     *          false : 로그아웃 상태
     */
    public boolean isUserLoggedIn() {
        return curUser != null;
    }

    /**
     * 고객이 로그인이 되어 있는지 안되어 있는지 확인하는 메소드
     *
     * @return  true : 로그인 상태
     *          false : 로그아웃 상태
     */
    public boolean isCustomerLoggedIn() {
        return curCustomer != null;
    }

    /**
     * 유저의 계정 상태를 체크를 해줍니다.
     * (구매자는 승인 받지 않습니다)
     * 유저가 ACTIVATE 상태 일 때만 로그인을 해줍니다.
     *
     * @param user : 로그인 유저
     * @return user.getUserStatus() : ACTIVATE
     *         NULL                 : DEACTIVATE, BANNED, REQUEST, REJECT
     */
    public static UserStatus statusChecker(User user) {
        // TODO::reject 사유 column 값 추가해야댐
        String status = user.getUserStatus().toString().toUpperCase();
        try {
            if (status.equals("REQUEST")) {
                System.out.println("유저 승인이 필요합니다. 요청하시겠습니까? (Y/N)");
                String answer = br.readLine().toUpperCase();
                if (answer.equals("Y")) {
                    // TODO:: 관리자일경우에만 허락 가능 하게!
                    userDao.approveUser(user);
                    System.out.println("로그인을 다시 진행해주세요");
                    return null;
                } else if(answer.equals("N")) {
                    System.out.println("유저 승인을 진행하지 않았습니다. 로그인 페이지로 다시 돌아갑니다");
                    return null;
                } else {
                    System.out.println("잘못된 값입니다 다시 로그인을 해주세요");
                }

            } else if (status.equals("DEACTIVATE")) {
                System.out.println("휴면계정 상태입니다. 관리자에게 문의하세요");
                System.out.println("=========================================================================");
                return null;
            } else if (status.equals("BANNED")) {
                System.out.println("계정이 정지된 상태입니다. 관리자에게 문의하세요");
                System.out.println("=========================================================================");
            } else if (status.equals("ACTIVATE")) {
                System.out.println("계정이 활성화 상태입니다. 로그인을 진행합니다.");
                System.out.println("=========================================================================");
                return user.getUserStatus();
            } else {
                System.out.println("엥?");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
