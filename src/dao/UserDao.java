package dao;

import com.mysql.cj.protocol.Resultset;
import exception.ErrorCodeList;
import exception.ExceptionImpl;
import exception.ExceptionOutput;
import lib.DBConnection;
import entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao extends DBConnection {
    private DataSource dataSource;

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

}
