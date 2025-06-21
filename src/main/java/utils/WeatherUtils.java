package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 天气工具类，提供与天气信息相关的操作方法，如获取天气信息、加载城市名称、获取城市编码等。
 */
public class WeatherUtils {
    /**
     * 获取天气信息的方法。
     * 
     * @param cityCode 要查询的城市编码。
     * @return 该城市的天气信息；如果查询失败则返回“未知”或“错误”。
     */
    public static String fetchWeatherInfo(String cityCode) {
        // SQL 查询语句，根据城市编码查询天气信息（占位符）
        String fetchWeatherSql = "SELECT weather FROM region_weather WHERE code = ?";

        // PreparedStatement：使用预编译的 SQL 语句，防止 SQL 注入
        try (Connection conn = DatabaseUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(fetchWeatherSql)) {

            // 设置 SQL 语句的第一个参数为城市编码
            stmt.setString(1, cityCode);

            // 执行查询，获取结果集
            ResultSet resultSet = stmt.executeQuery();

            // 检查查询结果
            if (resultSet.next()) {
                // 如果结果集有数据，获取天气信息
                String weather = resultSet.getString("weather");
                // 打印查询成功的信息
                System.out.println("查询成功: 城市编码 " + cityCode + " 天气 " + weather);
                return weather;
            } else {
                // 如果结果集没有数据，打印未找到信息的错误信息
                System.err.println("未找到城市编码 " + cityCode + " 的天气信息");
                return "未知";
            }
        } catch (SQLException e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 打印查询天气信息时发生错误的信息
            System.err.println("查询天气信息时发生错误！");
            return "错误";
        }
    }

    /**
     * 加载城市名称的方法。
     * 
     * @return 包含所有城市名称的字符串数组。
     */
    public static String[] loadCityNames() {
        // 创建一个存储城市名称的列表
        List<String> cityList = new ArrayList<>();
        // SQL 查询语句，从 region_code 表中查询所有地区名称
        String sql = "SELECT region FROM region_code";

        // Connection程序可以与数据库建立连接，用于后续的数据库操作
        // Statement来执行静态 SQL 语句并返回结果集
        try (Connection conn = DatabaseUtils.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(sql)) {

            // 遍历结果集，将每个地区名称添加到列表中
            while (resultSet.next()) {
                cityList.add(resultSet.getString("region"));
            }
            // 打印成功加载的城市数量
            System.out.println("成功加载 " + cityList.size() + " 个城市");

        } catch (SQLException e) {
            // 打印异常堆栈信息
            e.printStackTrace();
        }

        // 将列表转换为数组并返回
        return cityList.toArray(new String[0]);
    }

    /**
     * 获取城市编码的方法。
     * 
     * @param selectedRegion 要查询的地区名称。
     * @return 该地区对应的城市编码；如果查询失败则返回 null。
     */
    public static String getCityCode(String selectedRegion) {
        // SQL 查询语句，根据地区名称查询城市编码
        String sql = "select code from region_code where region = ?";
        try (Connection conn = DatabaseUtils.getConnection()) {
            // 创建预编译的 SQL 语句对象
            PreparedStatement stmt = conn.prepareStatement(sql);
            // 设置 SQL 语句的第一个参数为地区名称
            stmt.setString(1, selectedRegion);
            // 执行查询，获取结果集
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                // 如果结果集有数据，获取城市编码并返回
                return resultSet.getString("code");
            } else {
                // 如果结果集没有数据，打印未找到城市编码的错误信息
                System.err.println("未找到地区对应的城市编码: " + selectedRegion);
                return null;
            }
        } catch (SQLException e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 打印获取城市编码时发生错误的信息
            System.err.println("获取城市编码时发生错误: " + e.getMessage());
            return null;
        }
    }
}