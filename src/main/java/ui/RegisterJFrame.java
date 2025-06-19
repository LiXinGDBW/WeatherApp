package ui;

import javax.swing.*;
import utils.DatabaseUtils;
import utils.WeatherUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * 注册界面类，继承自 JFrame，用于创建用户注册窗口。
 */
public class RegisterJFrame extends JFrame {
    /**
     * 构造函数，初始化注册界面并使其可见。
     */
    public RegisterJFrame() {
        // 初始化 JFrame 的各项属性和组件
        initJFrame();
        // 设置窗口可见
        setVisible(true);
    }

    /**
     * 初始化 JFrame 的方法，包括设置窗口标题、大小、关闭操作、布局等，
     * 并添加用户名、密码、确认密码、密保问题、地区输入框和注册、返回按钮。
     */
    private void initJFrame() {
        // 设置窗口标题为“用户注册”
        setTitle("用户注册");
        // 设置窗口大小为 400x350 像素
        setSize(400, 350);
        // 设置窗口关闭时的操作，即退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 将窗口居中显示
        setLocationRelativeTo(null);
        // 设置窗口的布局管理器为 null，使用绝对定位
        setLayout(null);

        // 创建用户名标签，显示“用户名:”
        JLabel userLabel = new JLabel("用户名:");
        // 设置用户名标签的位置和大小
        userLabel.setBounds(40, 25, 125, 25);
        // 将用户名标签添加到窗口中
        add(userLabel);

        // 创建用户名输入框，初始长度为 20
        JTextField userText = new JTextField(20);
        // 设置用户名输入框的位置和大小
        userText.setBounds(160, 25, 165, 25);
        // 将用户名输入框添加到窗口中
        add(userText);

        // 创建密码标签，显示“密码:”
        JLabel passwordLabel = new JLabel("密码:");
        // 设置密码标签的位置和大小
        passwordLabel.setBounds(40, 75, 125, 25);
        // 将密码标签添加到窗口中
        add(passwordLabel);

        // 创建密码输入框，初始长度为 20
        JPasswordField passwordText = new JPasswordField(20);
        // 设置密码输入框的位置和大小
        passwordText.setBounds(160, 75, 165, 25);
        // 将密码输入框添加到窗口中
        add(passwordText);

        // 创建确认密码标签，显示“确认密码:”
        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        // 设置确认密码标签的位置和大小
        confirmPasswordLabel.setBounds(40, 125, 125, 25);
        // 将确认密码标签添加到窗口中
        add(confirmPasswordLabel);

        // 创建确认密码输入框，初始长度为 20
        JPasswordField confirmPasswordText = new JPasswordField(20);
        // 设置确认密码输入框的位置和大小
        confirmPasswordText.setBounds(160, 125, 165, 25);
        // 将确认密码输入框添加到窗口中
        add(confirmPasswordText);

        // 创建密保标签，显示“密保问题（学号）:”
        JLabel securityQuestionLabel = new JLabel("密保问题（学号）:");
        // 设置密保标签的位置和大小
        securityQuestionLabel.setBounds(40, 175, 125, 25);
        // 将密保标签添加到窗口中
        add(securityQuestionLabel);

        // 创建密保问题输入框，初始长度为 20
        JTextField securityQuestionText = new JTextField(20);
        // 设置密保问题输入框的位置和大小
        securityQuestionText.setBounds(160, 175, 165, 25);
        // 将密保问题输入框添加到窗口中
        add(securityQuestionText);

        // 创建注册按钮，显示“注册”
        JButton registerButton = new JButton("注册");
        // 设置注册按钮的位置和大小
        registerButton.setBounds(80, 265, 80, 25);
        // 将注册按钮添加到窗口中
        add(registerButton);

        // 创建返回按钮，显示“返回”
        JButton backButton = new JButton("返回");
        // 设置返回按钮的位置和大小
        backButton.setBounds(240, 265, 80, 25);
        // 将返回按钮添加到窗口中
        add(backButton);

        // 创建地区标签，显示“地区:”
        JLabel regionLabel = new JLabel("地区:");
        // 设置地区标签的位置和大小
        regionLabel.setBounds(40, 215, 125, 25);
        // 将地区标签添加到窗口中
        add(regionLabel);

        // 调用 WeatherUtils 类的 loadCityNames 方法加载城市名称
        String[] cityNames = WeatherUtils.loadCityNames();
        // 创建地区选择框
        JComboBox<String> regionComboBox = new JComboBox<>(cityNames);
        // 添加默认选项“请选择地区”
        regionComboBox.addItem("请选择地区");
        regionComboBox.setSelectedItem("请选择地区");
        // 设置地区选择框的位置和大小
        regionComboBox.setBounds(160, 215, 165, 25);
        // 将地区选择框添加到窗口中
        add(regionComboBox);

        // 为注册按钮添加事件监听器
        registerButton.addActionListener(new ActionListener() {
            /**
             * 当注册按钮被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的用户名
                String username = userText.getText();
                // 获取用户输入的密码
                String password = new String(passwordText.getPassword());
                // 获取用户输入的确认密码
                String confirmPassword = new String(confirmPasswordText.getPassword());
                // 获取用户输入的密保问题答案
                String securityQuestionLabel = new String(securityQuestionText.getText());
                // 获取用户选择的地区
                String region = (String) regionComboBox.getSelectedItem();
                // 验证输入
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                        || securityQuestionLabel.isEmpty() || region.equals("请选择地区")) {
                    // 如果有输入为空，弹出错误提示框
                    JOptionPane.showMessageDialog(null, "所有字段均为必填项！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    // 如果两次输入的密码不一致，弹出错误提示框
                    JOptionPane.showMessageDialog(null, "两次输入的密码不一致！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 调用 registerUser 方法进行用户注册
                registerUser(username, password, securityQuestionLabel, region);
            }
        });

        // 为返回按钮添加事件监听器
        backButton.addActionListener(new ActionListener() {
            /**
             * 当返回按钮被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建登录界面的实例
                new LoginJFrame();
                // 关闭当前注册窗口
                dispose();
            }
        });
    }

    /**
     * 用户注册的方法，将用户信息插入到数据库中。
     * 
     * @param username         用户的用户名。
     * @param password         用户的密码。
     * @param securityQuestion 用户的密保问题答案。
     * @param region           用户选择的地区。
     */
    private void registerUser(String username, String password, String securityQuestion, String region) {
        try (Connection conn = DatabaseUtils.getConnection()) {
            // SQL 插入语句，将用户信息插入到 users 表中
            String sql = "INSERT INTO users (username, password, security_question, region, background) VALUES (?, ?, ?, ?, ?)";
            // 创建预编译的 SQL 语句对象
            PreparedStatement stmt = conn.prepareStatement(sql);
            // 设置 SQL 语句的第一个参数为用户名
            stmt.setString(1, username);
            // 设置 SQL 语句的第二个参数为密码
            stmt.setString(2, password);
            // 设置 SQL 语句的第三个参数为密保问题答案
            stmt.setString(3, securityQuestion);
            // 设置 SQL 语句的第四个参数为地区
            stmt.setString(4, region);

            // 默认背景图片
            stmt.setString(5, "春小.png");

            // 执行插入操作
            stmt.executeUpdate();

            // 弹出注册成功的提示框
            JOptionPane.showMessageDialog(null, "注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            // 创建登录界面的实例
            new LoginJFrame();
            // 关闭当前注册窗口
            dispose();
        } catch (SQLException e) {
            // 弹出注册失败的提示框
            JOptionPane.showMessageDialog(null, "注册失败，数据库错误！", "错误", JOptionPane.ERROR_MESSAGE);
            // 打印异常堆栈信息
            e.printStackTrace();
        }
    }
}