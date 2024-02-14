package dao;

import Model.UserRole;
import Model.UserStatus;
import com.mysql.cj.protocol.Resultset;
import exception.ErrorCodeList;
import exception.ExceptionImpl;
import exception.ExceptionOutput;
import lib.DBConnection;
import entity.User;
import lib.UserManager;
import serviceImpl.UserServiceImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao extends DBConnection {
    private DataSource dataSource;
//    private final UserServiceImpl userService = new UserServiceImpl();

    public UserDao() {
        this.dataSource = DBConnection.getDataSource();
    }


    /**
     * 유저가 회원가입을 할 시 생성이 가능한 아이디인지 확인을 해주는 메소드 입니다.
     *
     * 중복된 userID가 있는지 확인을 해줍니다.
     *
     * @author Tae Jin Kim
     * @date 2024-02-13
     * @param userID : 유저가 원하는 회원가입 아이디
     * @return  false : 해당 아이디가 이미 존재
     *          true : 해당 아이디가 없음으로 생성가능
     *
     * @throws : ID_ALREADY_EXISTS               : 이미 존재하는 아이디입니다.
     */
    public boolean userIDValidator(String userID) {
        String sql = "SELECT name FROM logissgtics.User where name = ?";
        // try-with-resource clause where automatically closes connection
        try (
                // Only autoClosable variables can be allowed in ().
                // 여기서 connection 을 열어주면 따로 닫아줄 필요가 없음 Java 7 이후로 생김
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            // sql 에 ? 값에 name 넣어주기
            pstmt.setString(1, String.valueOf(userID));
            ResultSet rs = pstmt.executeQuery();

            // select 해온 값이 있다면 DB 안에 유저가 원하는 ID 값이 있다는 뜻이니 에러 출력
            if (rs.next()) {
                throw new ExceptionOutput(ErrorCodeList.ID_ALREADY_EXISTS);
                    // 중복된 값은 없지만 회원 가입이 되는지 확인을 합니다.
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("아이디 체크 실패햇네요?");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("아이디 체크 또 실패햇네요?");

            return false;
        }
    }


//    public static boolean isExistNumber(String phone) {
//
//    }



    public void requestReg(int userId) {

    }

    public void registerUser(User user) {

    }

    /**
     * user 테이블에서 id 와 password 를 체크를 해주고 값이 맞는다면 로그인을 진행하게끔
     * 해당 유저의 값으로 돌아가줍니다.
     *
     * UserManager 에서 statusCheck 를 가져와서 유저가 활성화 되어 있는지를 확인하고
     * 활성화가 아니라면 null 값으로 리턴합니다.
     *
     * @param user_id : 아이디
     * @param password : 비번
     * @return  user : 로그인시 (DB 에 값이 있을 시)
     *          null : 로그인 실패시 (DB 에 값이 없을 시)
     */
    public User authUser(String user_id, String password) {
        String sql = "SELECT * FROM logissgtics.User WHERE user_id = ? AND password = ?";
//        System.out.println(sql);
        try (
                Connection conn = getDataSource().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, user_id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
//                System.out.println("값이 있다!");
                User user = storeUser(rs);

                System.out.println(
                        "아이디 비밀번호 인증 완료!\n"
                        + "아이디: " + user.getUser_id()
                        + " 비빌번호: " + user.getPassword()
                        + " 권한: " + user.getUserRole()
                        + " 상태: " + user.getUserStatus()
                );

                // 유저의 상태가 활성화 일 시
                if(UserManager.statusChecker(user) != null) {
                    return user;
                    // 유저가 활성화가 아닐 시
                } else {
                    return null;
                }
                // 로그인 된 유저값으로

            } else {
                System.out.println("값이 없다");
                return null; // Authentication failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * SELECT 를 해서 User 에게 넣어주는 역활을 해줍니다.
     * 해당 기능은 authUser 에서 사용이 되고 검증이 끝난 아이디 값은
     * UserServiceImpl 로 넘어가서 userLogin 에서 불리고
     * UserManager 를 통해서 user Singleton 에 저장이 됩니다.
     * @author : Tae Jin Kim
     * @date : 2024-02-14
     * @param rs
     * @return user : DB 에서 받아온 값을 저장하는 유저
     *
     * @throws : SQL_SELECT_FAIL : 값 저장 실패
     */
    public User storeUser(ResultSet rs) {
        User user = new User();
        try {
            user.setSeller_id(rs.getInt("seller_id"));
            user.setUserRole(UserRole.valueOf(rs.getString("role")));
            user.setComp_reg_num(rs.getInt("comp_reg_num"));
            user.setUser_id(rs.getString("user_id"));
            user.setName(rs.getString("name").charAt(0));
            user.setPhone(rs.getString("phone"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setAddr1(rs.getString("addr1").charAt(0));
            user.setAddr2(rs.getString("addr2").charAt(0));
            user.setZipcode(rs.getInt("zipcode"));
            user.setSign_up_path(rs.getString("sign_up_path").charAt(0));
            user.setText_agree(rs.getBoolean("text_agree"));
            user.setEmail_agree(rs.getBoolean("email_agree"));
            user.setAd_agree(rs.getBoolean("ad_agree"));
            user.setUserStatus(UserStatus.valueOf(rs.getString("status").toUpperCase()));
            user.setReg_date(rs.getDate("reg_date"));

            return user;
        } catch (SQLException e) {
            throw new ExceptionOutput(ErrorCodeList.SQL_SELECT_FAIL);
        } catch (NullPointerException e) {
            throw new ExceptionOutput(ErrorCodeList.NULL_POINTER_EXCEPTION);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("값 저장 실패");
        }
        return user;
    }



}
