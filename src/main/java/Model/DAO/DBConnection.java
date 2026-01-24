package Model.DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static {
        try {
            // Load properties từ src/main/resources/db.properties
            Properties props = new Properties();
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("Không tìm thấy file db.properties trong resources/");
            }

            props.load(input);

            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

            // Load driver
            Class.forName(driver);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi load cấu hình DB", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
//    public static Connection getConnection() throws SQLException {
//        Connection con = DriverManager.getConnection(url, username, password);
//        System.out.println(">>> DB CONNECTED <<<");
//        return con;
//    }
//    public static void main(String[] args) {
//        try (Connection con = DBConnection.getConnection()) {
//            System.out.println("KẾT NỐI DATABASE THÀNH CÔNG!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
