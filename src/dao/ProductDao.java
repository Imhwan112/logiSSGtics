package dao;

import config.DBConnection;
import entity.Category;
import entity.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends DBConnection {
    private final DataSource dataSource;

    public ProductDao() {
        this.dataSource = DBConnection.getDataSource();
    }


    public void add_db(Product product){
        String sql = "insert into Product " +
                "(categoryNo, brandNo, prodName, salesPrice, prodDescription) " +
                "values (?, ?, ?, ?, ?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        )
        {
            pstmt.setInt(1, product.getCategoryNo());
            pstmt.setInt(2, product.getBrandNo());
            pstmt.setString(3, product.getProdName());
            pstmt.setInt(4, product.getSalesPrice());
            pstmt.setString(5, product.getProdDescription());
            pstmt.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public List<Category> get_categpry() {
        List<Category> categories = new ArrayList<>();
        String sql = "select categoryNo, categoryName from product_category";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryNo(rs.getInt("categoryNo"));
                category.setCategoryName(rs.getString("categoryName"));
                categories.add(category);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return categories;
    }

    public List<Product> get_all_db() {
        List<Product> products = new ArrayList<>();
        String sql = "select p.prodNo, c.categoryName, p.prodName, p.salesPrice, p.prodDescription, p.salesState, p.reg_date, b.brandName " +
                "from product p inner join Brand b on p.brandNo = b.brandNo inner join product_category c on p.categoryNo = c.categoryNo where isProdDisplay = 1";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        )
        {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProdNo(rs.getInt("prodNo"));
                product.setCategoryName(rs.getString("categoryName"));
                product.setProdName(rs.getString("prodName"));
                product.setSalesPrice(rs.getInt("salesPrice"));
                product.setProdDescription(rs.getString("prodDescription"));
                product.setSalesState(rs.getString("salesState"));
                product.setReg_date(rs.getTimestamp("reg_date"));
                product.setProdBrand(rs.getString("brandName"));
                products.add(product);
            }
            rs.close();

        } catch (Exception e) {

        }
        return products;
    }

    public Product get_one_db(int prodNo) {
        Product product = new Product();
        String sql = "select p.prodNo, c.categoryName, p.prodName, p.salesPrice, p.prodDescription, p.salesState, p.reg_date, b.brandName " +
                "from product p inner join Brand b on p.brandNo = b.brandNo inner join product_category c on p.categoryNo = c.categoryNo where isProdDisplay = 1 and prodNo = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        )
        {
            pstmt.setInt(1, prodNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                product.setProdNo(rs.getInt("prodNo"));
                product.setCategoryName(rs.getString("categoryName"));
                product.setProdName(rs.getString("prodName"));
                product.setSalesPrice(rs.getInt("salesPrice"));
                product.setProdDescription(rs.getString("prodDescription"));
                product.setSalesState(rs.getString("salesState"));
                product.setReg_date(rs.getTimestamp("reg_date"));
                product.setProdBrand(rs.getString("brandName"));
            }
            rs.close();

        } catch (Exception e) {

        }
        return product;
    }



    public void modify_db(int prodNo, Product product){
        String sql = new StringBuilder()
                .append("update product set ")
                .append("categoryNo=?, ")
                .append("prodName=?, ")
                .append("categoryNo=?, ")
                .append("brandNo=?, ")
                .append("salesPrice=?, ")
                .append("prodDescription=?, ")
                .append("salesState=?, ")
                .append("isProdDisplay=? ")
                .append("where prodNo=?")
                .toString();

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        )
        {
            pstmt.setInt(1, product.getCategoryNo());
            pstmt.setString(2, product.getProdName());
            pstmt.setInt(3, product.getCategoryNo());
            pstmt.setInt(4, product.getBrandNo());
            pstmt.setInt(5, product.getSalesPrice());
            pstmt.setString(6, product.getProdDescription());
            pstmt.setString(7, product.getSalesState());
            pstmt.setInt(8, product.getIsProdDisplay());
            pstmt.setInt(9, prodNo);
            pstmt.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

