package utils;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * 用户工具类，提供与用户相关的操作方法，如获取用户信息、验证用户、更新密码等。
 */
public class UserUtils {
    /**
     * 获取用户所有信息的方法。
     * 
     * @param username 要查询的用户名。
     * @return 包含用户信息的字符串数组，如用户名、地区、背景图片等；如果查询失败则返回 null。
     */
    public static String[] getUserInfo(String username) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            // SQL 查询语句，根据用户名查询用户信息
            String sql = "SELECT username,region,background FROM users WHERE username = ?";
            // 创建预编译的 SQL 语句对象
            PreparedStatement stmt = conn.prepareStatement(sql);
            // 设置 SQL 语句的第一个参数为用户名
            stmt.setString(1, username);
            // 执行查询，获取结果集
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // 如果结果集有数据，创建一个长度为 3 的字符串数组
                String[] userInfo = new String[3];
                // 将查询结果中的用户名赋值给数组的第一个元素
                userInfo[0] = resultSet.getString("username");
                // 将查询结果中的地区赋值给数组的第二个元素
                userInfo[1] = resultSet.getString("region");
                // 将查询结果中的背景图片赋值给数组的第三个元素
                userInfo[2] = resultSet.getString("background");
                return userInfo;
            }
            return null;
        } catch (SQLException e) {
            // 如果发生 SQL 异常，弹出错误提示框
            JOptionPane.showMessageDialog(null, "获取用户信息失败！", "错误", JOptionPane.ERROR_MESSAGE);
            // 打印异常堆栈信息
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证用户名和密码的方法。
     * 
     * @param username 要验证的用户名。
     * @param password 要验证的密码。
     * @return 如果用户名和密码匹配则返回 true，否则返回 false。
     */
    public static boolean validateUser(String username, String password) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            // SQL 查询语句，根据用户名和密码查询用户信息
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            // 创建预编译的 SQL 语句对象
            PreparedStatement stmt = conn.prepareStatement(sql);
            // 设置 SQL 语句的第一个参数为用户名
            stmt.setString(1, username);
            // 设置 SQL 语句的第二个参数为密码
            stmt.setString(2, password);
            // 执行查询，获取结果集
            ResultSet resultSet = stmt.executeQuery();

            // 如果结果集有数据，说明用户名和密码匹配，返回 true
            return resultSet.next();
        } catch (SQLException e) {
            // 如果发生 SQL 异常，弹出错误提示框
            JOptionPane.showMessageDialog(null, "数据库连接失败！", "错误", JOptionPane.ERROR_MESSAGE);
            // 打印异常堆栈信息
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证密保并更新密码的方法。
     * 
     * @param username         要更新密码的用户名。
     * @param securityQuestion 用户输入的密保问题答案。
     * @param newPassword      新的密码。
     * @return 如果更新成功则返回 true，否则返回 false。
     */
    public static boolean updatePassword(String username, String securityQuestion, String newPassword) {
        // SQL 更新语句，根据用户名和密保问题答案更新用户密码
        String sql = "UPDATE users SET password = ? WHERE username = ? AND security_question = ?";
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            // 设置 SQL 语句的第一个参数为新密码
            stmt.setString(1, newPassword);
            // 设置 SQL 语句的第二个参数为用户名
            stmt.setString(2, username);
            // 设置 SQL 语句的第三个参数为密保问题答案
            stmt.setString(3, securityQuestion);

            // 执行更新操作，返回更新的行数
            int rowsUpdated = stmt.executeUpdate();
            // 如果更新的行数大于 0，说明更新成功，返回 true
            return rowsUpdated > 0;
        } catch (SQLException e) {
            // 如果发生 SQL 异常，弹出错误提示框
            JOptionPane.showMessageDialog(null, "数据库操作失败！", "错误", JOptionPane.ERROR_MESSAGE);
            // 打印异常堆栈信息
            e.printStackTrace();
            return false;
        }
    }
}