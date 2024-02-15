package Entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Getter
@NoArgsConstructor
public class Warehouse {

    private int whCode;
    private int topCategoryNum;
    private String topCategoryName;
    private String whName;
    private int whAddrNo;
    private String whAddrName;
    private Timestamp whRegistDate;
    private String managerPhone;
}
