package serviceImpl;

import com.mysql.cj.protocol.Resultset;
import dao.UserDao;
import entity.User;
import exception.ErrorCodeList;
import exception.ExceptionImpl;
import exception.ExceptionOutput;
import lib.DBConnection;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final UserDao userDao = new UserDao();

    @Override
    public boolean isCompRegNumValid(int userId) {
        return false;
    }

    /**
     * 이메일 정규식에서 벗어난 형식이면 ENUM 파일에서 유효하지 않는 이메일 형식이라는 에러문구를 출력합니다.
     *
     * @param email : 유저가 입력한 이메일 값
     * @return true  : 이메일 형식일 경우
     * false : 이메일 형식이 아닐 경우
     */
    public boolean emailValidator(String email) {
        try {
            String regex = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(email);
            if (!m.matches()) {
                // 유효하지 않는 이메일 형식입니다.
                throw new ExceptionOutput(ErrorCodeList.INVALID_INPUT_EMAIL);
            }
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * 핸드폰 정규식에서 벗어난 형식이면 ENUM 파일에서 유효하지 않는 핸드폰 형식이라는 에러문구를 출력합니다.
     *
     * @param phone : 유저가 입력한 핸드폰 값
     * @return true  : 핸드폰 형식일 경우
     * false : 핸드폰 형식이 아닐 경우
     * @throws : INVALID_INPUT_PHONENUMBER : 유효하지 않는 전화번호 형식입니다.
     * @author : Tae Jin Kim
     * @date : 2024-02-13
     */
    public boolean isValidPhoneNumber(String phone) {
        try {
            /**
             * == 첫 세자리 == ^01(?:0|1|[6-9])
             *      정규식 패턴을 01로 시작
             *      01 다음에 오는 값으로 0,1 또는 6~9 사이 값으로 지정
             *
             * == 두 번째 자리 == (\d{3}|\d{4})
             *     3자리 또는 4자리의 숫자를 허용 하는 것으로 지정
             *
             * == 세 번째 자리 == (\d{4})
             *      마지막은 4자리 숫자로 지정
             *
             * == 종료 == $
             *      종료를 알리는 $
             *
             * == 구분 값 [-] ==
             * . 또는 - 값이 없거나 단 한개만 존재 하는 것
             */
            String regex = "^01(?:0|1|[6-9])[-]?(\\d{3}|\\d{4})[-]?(\\d{4})$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);

            if (m.matches()) {
                // 유효하지 않는 전화번호 형식입니다..
                throw new ExceptionOutput(ErrorCodeList.INVALID_INPUT_PHONENUMBER);
            }
            return true;
        } catch (Exception e) {
            System.out.println("핸드폰 입력에 실패했는데 이유가 뭘까용");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 유저가 회원가입을 할 시 생성이 가능한 아이디인지 확인을 해주는 메소드 입니다.
     * <p>
     * userId 값은 공백 없이 영문+숫자 8자리 이하이여야 합니다.
     *
     * @param userID
     * @return true  : 유저가 입력한 아이디가 정규식에 맞음으로 생성이 가능합니다
     * false : 유저가 입력한 아이디가 생성이 불가능 합니다.
     * @throws : ID_CONTAINS_SPACE               : 아이디는 공백을 포함 할 수 없습니다
     *           ID_MAXIMUM_LENGTH_EXCCEDED      : 아이디가 8자를 초과하였습니다.
     *           ID_CONTAINS_NON_ALPHABET_NUMBER : 아이디는 영어와 숫자만 입력이 가능합니다.
     * @author
     * @date
     */
    public boolean isValidUserId(String userID) {
        try {
            String regex = ".*[^a-zA-Z0-9].*";
            // 아이디가 공백을 포함
            if (userID.contains(" ")) {
                throw new ExceptionOutput(ErrorCodeList.ID_CONTAINS_SPACE);
                // 아이디가 8자리 초과
            } else if (userID.length() > 8) {
                throw new ExceptionOutput(ErrorCodeList.ID_MAXIMUM_LENGTH_EXCCEDED);
                // 아이다가 영어와 숫자를 제외 한 값
            } else if (userID.matches(regex)) {
                throw new ExceptionOutput((ErrorCodeList.ID_CONTAINS_NON_ALPHABET_NUMBER));
            }
            // if userIDValidator is true ==> 만약에 DB 에 유저의 값이 없다면,
            if (userDao.userIDValidator(userID)) {
                System.out.println("해당 아이디로 생성이 가능합니다.");
                return true;
            } else {
                System.out.println("뭔가 이상");
                return false;
            }
        } catch (Exception e) {

            return false;
        }
    }

    public void mainMenu() {

    }


}
