package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库工具类，提供获取数据库连接的方法。
 */
public class DatabaseUtils {
    // 数据库连接的 URL，指定数据库的地址和名称
    private static final String URL = "jdbc:mysql://10.173.9.33:3306/weather_app";
    // 数据库用户名，需要替换为实际的用户名
    private static final String USER = "app_user";
    // 数据库密码，需要替换为实际的密码
    private static final String PASSWORD = "123456";

    /**
     * 获取数据库连接的方法。
     * 
     * @return 一个数据库连接对象。
     * @throws SQLException 如果在获取数据库连接时发生 SQL 异常。
     */
    public static Connection getConnection() throws SQLException {
        // 使用 DriverManager 类的 getConnection 方法获取数据库连接
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}