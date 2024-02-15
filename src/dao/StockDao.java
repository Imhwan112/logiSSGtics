package dao;

import config.DBConnection;
import entity.Stock;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDao extends DBConnection {
    private DataSource dataSource;

    public StockDao() {
        this.dataSource = DBConnection.getDataSource();
    }
    public void insertStock() {
        String sql = "update stock s inner join order_insert o on s.insertCode = o.insertCode set s.stockQuantity = s.stockQuantity + o.insertQuantity";
        try(
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        )
        {} catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void outStock() {

    }

    public List<Stock> get_db() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "";
        try(
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                )
        {


        } catch (SQLException se) {
            se.printStackTrace();
        }

        return stocks;
    }
}
