package utils;
// package utils;

// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import java.sql.*;
// import java.util.Scanner;

// /**
//  * 天气信息获取与管理系统
//  * 该程序通过网络爬虫从中国天气网获取城市天气信息，并将其存储在本地数据库中
//  * 支持天气信息更新、城市添加和删除等功能
//  */
// public class WeatherInfoManager {
//     /**
//      * 程序入口点
//      * 
//      * @param args 命令行参数
//      */
//     public static void main(String[] args) {
//         try (Scanner scanner = new Scanner(System.in)) {
//             // 主菜单循环，允许用户连续进行操作
//             while (true) {
//                 printMenu();
//                 int choice = getValidatedUserChoice(scanner);

//                 switch (choice) {
//                     case 1:
//                         updateWeatherInfo();
//                         System.out.println("天气信息更新完成！");
//                         break;
//                     case 2:
//                         addCity(scanner);
//                         System.out.println("城市添加完成！");
//                         break;
//                     case 3:
//                         deleteCity(scanner);
//                         System.out.println("城市删除完成！");
//                         break;
//                     case 4:
//                         System.out.println("程序已退出！");
//                         return;
//                     default:
//                         System.out.println("无效的选项，请重新输入！");
//                 }
//             }
//         } catch (Exception e) {
//             System.err.println("程序运行发生错误: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }

//     /**
//      * 打印操作菜单
//      */
//     private static void printMenu() {
//         System.out.println("\n请选择操作：");
//         System.out.println("1. 更新天气信息");
//         System.out.println("2. 添加城市");
//         System.out.println("3. 删除城市");
//         System.out.println("4. 退出程序");
//         System.out.print("请输入选项(1-4): ");
//     }

//     /**
//      * 获取并验证用户输入的选项
//      * 
//      * @param scanner Scanner对象，用于读取用户输入
//      * @return 合法的选项值(1-4)
//      */
//     private static int getValidatedUserChoice(Scanner scanner) {
//         int choice = scanner.nextInt();
//         scanner.nextLine(); // 消耗换行符，避免影响后续输入
//         return choice;
//     }

//     /**
//      * 更新所有城市的天气信息
//      * 从数据库获取所有城市编码，然后通过网络爬虫获取对应的天气信息并更新到数据库
//      */
//     private static void updateWeatherInfo() {
//         // SQL查询语句：获取所有城市编码
//         String selectCityCodesSQL = "SELECT code FROM region_code";
//         // SQL更新语句：更新天气信息
//         String updateWeatherSQL = "UPDATE region_weather SET weather = ? WHERE code = ?";

//         try (Connection conn = DatabaseUtils.getConnection();
//                 PreparedStatement selectStmt = conn.prepareStatement(selectCityCodesSQL);
//                 PreparedStatement updateStmt = conn.prepareStatement(updateWeatherSQL)) {
//             int sum = 0;
//             // 查询所有城市的编码
//             try (ResultSet rs = selectStmt.executeQuery()) {
//                 // 遍历城市编码并获取天气信息
//                 while (rs.next()) {
//                     long cityCode = rs.getLong("code");
//                     String weather = fetchWeatherInfo(String.valueOf(cityCode));

//                     if (weather != null && !weather.isEmpty()) {
//                         // 更新天气信息到 region_weather 表
//                         updateStmt.setString(1, weather);
//                         updateStmt.setLong(2, cityCode);

//                         int rowsUpdated = updateStmt.executeUpdate();
//                         if (rowsUpdated > 0) {
//                             System.out.println("更新成功: 城市编码 " + cityCode + " 天气 " + weather);
//                             // 统计成功更新的记录数
//                             sum++;
//                         } else {
//                             System.err.println("更新失败: 城市编码 " + cityCode + "（可能不存在于 region_weather 表中）");
//                         }
//                     } else {
//                         System.err.println("未能获取城市编码 " + cityCode + " 的天气信息");
//                     }
//                 }
//                 System.out.println("共更新 " + sum + " 条天气记录。");
//             }
//         } catch (SQLException e) {
//             System.err.println("数据库操作错误: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }

//     /**
//      * 根据城市编码从中国天气网获取天气信息
//      * 
//      * @param cityCode 城市编码
//      * @return 天气信息字符串，如果获取失败则返回null
//      */
//     private static String fetchWeatherInfo(String cityCode) {
//         try {
//             // 构造查询 URL，使用中国天气网的移动版页面
//             String url = "https://wap.weather.com.cn/mweather15d/" + cityCode + ".shtml";
//             // 模拟浏览器访问
//             Document doc = Jsoup.connect(url).get();

//             // 使用 CSS 选择器提取温度和天气状况元素
//             Element temperatureDiv = doc.selectFirst("div.h15listtem.h15k");
//             Element weatherElement = doc
//                     .selectFirst("div.h15listbody > ul > li:nth-child(1) > div.h15tiao > div:nth-child(2) > p");

//             // 提取文本内容，确保两个元素都存在时才返回有效数据
//             if (temperatureDiv != null && weatherElement != null) {
//                 return temperatureDiv.text() + " " + weatherElement.text();
//             } else {
//                 return "未找到目标元素";
//             }
//         } catch (Exception e) {
//             System.err.println("获取天气信息失败，城市编码: " + cityCode + ", 错误: " + e.getMessage());
//             return null;
//         }
//     }

//     /**
//      * 添加新城市到系统中
//      * 
//      * @param scanner Scanner对象，用于读取用户输入
//      */
//     private static void addCity(Scanner scanner) {
//         // 插入城市编码表的SQL语句
//         String insertCityCodeSQL = "INSERT INTO region_code (code, region) VALUES (?, ?)";
//         // 插入天气信息表的SQL语句
//         String insertWeatherSQL = "INSERT INTO region_weather (code) VALUES (?)";

//         try (Connection conn = DatabaseUtils.getConnection();
//                 PreparedStatement cityCodeStmt = conn.prepareStatement(insertCityCodeSQL);
//                 PreparedStatement weatherStmt = conn.prepareStatement(insertWeatherSQL)) {

//             System.out.print("请输入城市编码: ");
//             String cityCode = scanner.nextLine();
//             System.out.print("请输入城市名称: ");
//             String cityName = scanner.nextLine();

//             // 插入城市编码到 region_code 表
//             cityCodeStmt.setString(1, cityCode);
//             cityCodeStmt.setString(2, cityName);
//             int cityCodeInsertedRows = cityCodeStmt.executeUpdate();

//             // 插入天气记录到 region_weather 表
//             weatherStmt.setString(1, cityCode);
//             int weatherInsertedRows = weatherStmt.executeUpdate();

//             if (cityCodeInsertedRows > 0 && weatherInsertedRows > 0) {
//                 System.out.println("城市添加成功！");
//             } else {
//                 System.out.println("城市添加失败！");
//             }
//         } catch (SQLException e) {
//             System.err.println("添加城市失败: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }

//     /**
//      * 从系统中删除指定城市
//      * 使用数据库事务确保数据一致性，先删除天气信息，再删除城市编码
//      * 
//      * @param scanner Scanner对象，用于读取用户输入
//      */
//     private static void deleteCity(Scanner scanner) {
//         // 删除天气信息表记录的SQL语句
//         String deleteWeatherSQL = "DELETE FROM region_weather WHERE code = ?";
//         // 删除城市编码表记录的SQL语句
//         String deleteCityCodeSQL = "DELETE FROM region_code WHERE code = ?";

//         try (Connection conn = DatabaseUtils.getConnection()) {
//             // 开启事务
//             conn.setAutoCommit(false);

//             try (PreparedStatement weatherStmt = conn.prepareStatement(deleteWeatherSQL);
//                     PreparedStatement cityCodeStmt = conn.prepareStatement(deleteCityCodeSQL)) {

//                 System.out.print("请输入要删除的城市编码: ");
//                 String cityCode = scanner.nextLine();

//                 // 先删除天气表中的记录
//                 weatherStmt.setString(1, cityCode);
//                 int weatherDeletedRows = weatherStmt.executeUpdate();

//                 // 再删除城市表中的记录
//                 cityCodeStmt.setString(1, cityCode);
//                 int cityCodeDeletedRows = cityCodeStmt.executeUpdate();

//                 // 提交事务
//                 conn.commit();

//                 if (cityCodeDeletedRows > 0) {
//                     System.out.println("城市删除成功！共删除 " + (weatherDeletedRows + cityCodeDeletedRows) + " 条记录");
//                 } else {
//                     System.out.println("城市删除失败！未找到对应城市编码");
//                 }
//             } catch (SQLException e) {
//                 // 回滚事务
//                 conn.rollback();
//                 System.err.println("删除城市失败: " + e.getMessage());
//                 e.printStackTrace();
//             } finally {
//                 // 恢复自动提交模式
//                 conn.setAutoCommit(true);
//             }
//         } catch (SQLException e) {
//             System.err.println("数据库连接错误: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }
// }