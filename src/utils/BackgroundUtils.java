package utils;

import java.sql.*;

/**
 * 背景工具类，提供修改用户背景图片的方法。
 */
public class BackgroundUtils {
    /**
     * 修改文件默认背景图片的方法。
     * 
     * @param username      要修改背景图片的用户名。
     * @param newBackground 新的背景图片名称。
     */
    public static void updateBackground(String username, String newBackground) {
        // SQL 更新语句，根据用户名更新用户的背景图片信息
        String updateBackgroundSql = "UPDATE users SET background = ? WHERE username = ?";

        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(updateBackgroundSql)) {

            // 设置 SQL 语句的第一个参数为新的背景图片名称
            stmt.setString(1, newBackground);
            // 设置 SQL 语句的第二个参数为用户名
            stmt.setString(2, username);

            // 执行更新操作，返回更新的行数
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // 如果更新成功，打印更新成功的信息
                System.out.println("背景图片更新成功: 用户名 " + username + " 新背景 " + newBackground);
            } else {
                // 如果更新失败，打印更新失败的信息
                System.err.println("背景图片更新失败: 用户名 " + username + " 不存在");
            }
        } catch (SQLException e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 打印更新背景图片时发生错误的信息
            System.err.println("更新背景图片时发生错误！");
        }
    }
}