package Dao;

import Entity.Warehouse;
import Entity.WarehouseAddress;
import Entity.WarehouseCategory;
import config.DBConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDao extends DBConnection {

    private DataSource dataSource;

    public WarehouseDao() {
        this.dataSource = DBConnection.getDataSource();
    }

    public void add(Warehouse warehouse) {
        String sql =  "Insert into Warehouse (topCategoryNum, topCategoryName, whName, whAddrNo, whAddrName, managerPhone) values(?,?,?,?,?,?)";
        String sql2 = "Select topCategoryName from Warehouse_Category where topCategoryNum = ?";
        String sql3 = "Select whAddressName from Warehouse_Address where whAddressNo = ?";

        // try-with-resource
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                PreparedStatement ps3 = connection.prepareStatement(sql3);
        ) {
            ps2.setInt(1, warehouse.getTopCategoryNum());
            ResultSet rs2 = ps2.executeQuery();

            ps3.setInt(1, warehouse.getWhAddrNo());

            ResultSet rs3 = ps3.executeQuery();

            String categoryName;
            String address;

            //입력받은 카테고리 번호
            if (rs2.next()) {
                categoryName = rs2.getString("topCategoryName");
                warehouse.setTopCategoryName(categoryName);
            }

            if (rs3.next()) {
                address = rs3.getString("whAddressName");
                warehouse.setWhAddrName(address);
            }

            ps.setInt(1, warehouse.getTopCategoryNum());
            ps.setString(2, warehouse.getTopCategoryName());
            ps.setString(3, warehouse.getWhName());
            ps.setInt(4, warehouse.getWhAddrNo());
            ps.setString(5, warehouse.getWhAddrName());
            ps.setString(6, warehouse.getManagerPhone());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Warehouse> get() {

        List<Warehouse> whList = new ArrayList<>();
        String sql = "select w.whCode, w.topCategoryNum, wc.topCategoryName, w.whName, wa.whAddressName, w.whRegistDate, w.managerPhone " +
                "from Warehouse w inner join Warehouse_Address wa on w.whAddrNo = wa.whAddressNo " +
                "inner join Warehouse_Category wc on wc.topCategoryNum = w.topCategoryNum ";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Warehouse wh = new Warehouse();
                wh.setWhCode(rs.getInt("whCode"));
                wh.setTopCategoryNum(rs.getInt("topCategoryNum"));
                wh.setTopCategoryName(rs.getString("topCategoryName"));
                wh.setWhName(rs.getString("whName"));
                wh.setWhAddrName(rs.getString("whAddressName"));
                wh.setWhRegistDate(rs.getTimestamp("whRegistDate"));
                wh.setManagerPhone(rs.getString("managerPhone"));
                whList.add(wh);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return whList;
    }


    public List<WarehouseCategory> getClassification() {
        String sql = "select topCategoryNum, topCategoryName from Warehouse_Category";
        List<WarehouseCategory> whC = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                WarehouseCategory whCategory = new WarehouseCategory();
                whCategory.setTopCategoryNum(rs.getInt("topCategoryNum"));
                whCategory.setTopCategoryName(rs.getString("topCategoryName"));
                whC.add(whCategory);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return whC;
    }


    public List<Warehouse> getCategoryClassify(int addrNo) {
        String sql = "select w.whCode, w.topCategoryNum, wc.topCategoryName, w.whName, wa.whAddressName, w.whRegistDate, w.managerPhone from Warehouse w inner join Warehouse_Address wa on w.whAddrNo = wa.whAddressNo " +
                "inner join Warehouse_Category wc on wc.topCategoryNum = w.topCategoryNum where wc.topCategoryNum = ?";
        List<Warehouse> whList = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, addrNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Warehouse wh = new Warehouse();
                wh.setWhCode(rs.getInt("whCode"));
                wh.setTopCategoryNum(rs.getInt("topCategoryNum"));
                wh.setTopCategoryName(rs.getString("topCategoryName"));
                wh.setWhName(rs.getString("whName"));
                wh.setWhAddrName(rs.getString("whAddressName"));
                wh.setWhRegistDate(rs.getTimestamp("whRegistDate"));
                wh.setManagerPhone(rs.getString("managerPhone"));
                whList.add(wh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return whList;
    }


    public List<WarehouseAddress> getAddrList() {
        String sql = "select whAddressNo, whAddressName from Warehouse_Address";
        List<WarehouseAddress> whList = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                WarehouseAddress wh = new WarehouseAddress();
                wh.setWhAddressNo(rs.getInt("whAddressNo"));
                wh.setWhAddressName(rs.getString("whAddressName"));
                whList.add(wh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return whList;
    }


    public List<Warehouse> getAddrClassify(int addrNo) {
        String sql = "select w.whCode, w.topCategoryNum, wc.topCategoryName, w.whName, wa.whAddressName, w.whRegistDate, w.managerPhone from Warehouse w inner join Warehouse_Address wa on w.whAddrNo = wa.whAddressNo " +
                "inner join Warehouse_Category wc on wc.topCategoryNum = w.topCategoryNum where wa.whAddressNo like ?";
        List<Warehouse> whList = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + addrNo + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Warehouse wh = new Warehouse();
                wh.setWhCode(rs.getInt("whCode"));
                wh.setTopCategoryNum(rs.getInt("topCategoryNum"));
                wh.setTopCategoryName(rs.getString("topCategoryName"));
                wh.setWhName(rs.getString("whName"));
                wh.setWhAddrName(rs.getString("whAddressName"));
                wh.setWhRegistDate(rs.getTimestamp("whRegistDate"));
                wh.setManagerPhone(rs.getString("managerPhone"));
                whList.add(wh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return whList;
    }


    public void removeWarehouse(int warehouseCode) {
        String sql = "delete from Warehouse where whCode = ?";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, warehouseCode);
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
