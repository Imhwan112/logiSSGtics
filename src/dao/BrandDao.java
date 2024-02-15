package dao;

import config.DBConnection;
import entity.Brand;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandDao extends DBConnection {
    private DataSource dataSource;
    public BrandDao() {
        this.dataSource = DBConnection.getDataSource();
    }
    public void add_db(Brand brand) {
        String sql = "insert into brand (brandName) values (?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                )
        {
            pstmt.setString(1, brand.getBrandName());
            pstmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public List<Brand> get_db(){
        List<Brand> brands = new ArrayList<>();
        String sql = "select * from brand";
        try(
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                )
        {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Brand brand = new Brand();
                brand.setBrandNo(rs.getInt("brandNo"));
                brand.setBrandName(rs.getString("brandName"));
                brand.setReg_date(rs.getTimestamp("reg_date"));

                brands.add(brand);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    public void modify_db(int brandNo, Brand brand) {
        String sql = "update brand set brandName=? where brandNo=?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                )
        {
            pstmt.setString(1, brand.getBrandName());
            pstmt.setInt(2, brandNo);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void remove_db(int brandNo) {
        String sql = "delete from brand where brandNo=?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        )
        {
            pstmt.setInt(1, brandNo);
            pstmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
