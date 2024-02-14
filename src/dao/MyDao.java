package dao;

import lib.DBConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyDao extends DBConnection {
    private DataSource dataSource;



    /**
     * constructor injection
     * 클래스 디커플링 위해서 쓰임
     * @param dataSource
     */
    public MyDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    // CRUD 만드는 곳!
    public void myDaoMethod() {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM your_table_name");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            // Process the result set
            while (resultSet.next()) {
                // Retrieve data from the result set
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // Print or process the retrieved data
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
        }
    }

}
