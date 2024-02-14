package lib;

import Model.UserStatus;
import dao.UserDao;
import entity.User;


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
    private User curUser;

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
     * 유저 로그아웃
     */
    public void logoutUser() {
        curUser = null;
    }

    /**
     * 현재 로그인한 유저의 정보
     * @return 현재 로그인한 유저의 정보
     */
    public User getCurUser() {
        return curUser;
    }

    /**
     * 유저가 로그인이 되어 있는지 안되어 있는지 확인하는 메소드
     *
     * @return  true : 로그인 상태
     *          false : 로그아웃 상태
     */
    public boolean isLoggedIn() {
        return curUser != null;
    }

    /**
     * 유저의 계정 상태를 체크를 해줍니다.
     * 유저가 ACTIVATE 상태 일 때만 로그인을 해줍니다.
     *
     * @param user : 로그인 유저
     * @return user.getUserStatus() : ACTIVATE
     *         NULL                 : DEACTIVATE, BANNED, REQUEST
     */
    public static UserStatus statusChecker(User user) {

        String status = user.getUserStatus().toString().toUpperCase();
        try {
            if (status.equals("REQUEST")) {
                System.out.println("유저 인증을 기다리는 상태입니다. 관리자에게 문의하세요");
                return null;
            } else if (status.equals("DEACTIVATE")) {
                System.out.println("휴면계정 상태입니다. 관리자에게 문의하세요");
                return null;
            } else if (status.equals("BANNED")) {
                System.out.println("계정이 정지된 상태입니다. 관리자에게 문의하세요");
            } else if (status.equals("ACTIVATE")) {
                System.out.println("계정이 활성화 상태입니다. 로그인을 진행합니다.");
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
