package Ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RegisterJFrame extends JFrame {
    public RegisterJFrame() {
        initJFrame();
        setVisible(true);
    }

    private void initJFrame() {
        setTitle("用户注册");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 创建用户名标签
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(40, 25, 125, 25);
        add(userLabel);

        // 创建用户名输入框
        JTextField userText = new JTextField(20);
        userText.setBounds(160, 25, 165, 25);
        add(userText);

        // 创建密码标签
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(40, 75, 125, 25);
        add(passwordLabel);

        // 创建密码输入框
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(160, 75, 165, 25);
        add(passwordText);

        // 创建确认密码标签
        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordLabel.setBounds(40, 125, 125, 25);
        add(confirmPasswordLabel);

        // 创建确认密码输入框
        JPasswordField confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setBounds(160, 125, 165, 25);
        add(confirmPasswordText);

        // 创建密保标签
        JLabel securityQuestionLabel = new JLabel("密保问题（学号）:");
        securityQuestionLabel.setBounds(40, 175, 125, 25);
        add(securityQuestionLabel);

        // 创建密保问题输入框
        JTextField securityQuestionText = new JTextField(20);
        securityQuestionText.setBounds(160, 175, 165, 25);
        add(securityQuestionText);

        // 创建注册按钮
        JButton registerButton = new JButton("注册");
        registerButton.setBounds(80, 265, 80, 25);
        add(registerButton);

        // 创建返回按钮
        JButton backButton = new JButton("返回");
        backButton.setBounds(240, 265, 80, 25);
        add(backButton);
        // 创建地区标签
        JLabel regionLabel = new JLabel("地区:");
        regionLabel.setBounds(40, 215, 125, 25);
        add(regionLabel);

        // 创建地区选择框
        JComboBox<String> regionComboBox = new JComboBox<>();
        regionComboBox.addItem("请选择地区");
        regionComboBox.addItem("北京");
        regionComboBox.addItem("天津");
        regionComboBox.addItem("上海");
        regionComboBox.addItem("重庆");
        regionComboBox.addItem("哈尔滨");
        regionComboBox.addItem("长春");
        regionComboBox.addItem("沈阳");
        regionComboBox.addItem("呼和浩特");
        regionComboBox.addItem("乌鲁木齐");
        regionComboBox.addItem("拉萨");
        regionComboBox.addItem("西宁");
        regionComboBox.addItem("兰州");
        regionComboBox.addItem("银川");
        regionComboBox.addItem("西安");
        regionComboBox.addItem("太原");
        regionComboBox.addItem("石家庄");
        regionComboBox.addItem("济南");
        regionComboBox.addItem("郑州");
        regionComboBox.addItem("南京");
        regionComboBox.addItem("合肥");
        regionComboBox.addItem("武汉");
        regionComboBox.addItem("成都");
        regionComboBox.addItem("长沙");
        regionComboBox.addItem("昆明");
        regionComboBox.addItem("南昌");
        regionComboBox.addItem("贵阳");
        regionComboBox.addItem("南宁");
        regionComboBox.addItem("广州");
        regionComboBox.addItem("福州");
        regionComboBox.addItem("杭州");
        regionComboBox.addItem("海口");
        regionComboBox.addItem("香港");
        regionComboBox.addItem("澳门");
        regionComboBox.addItem("台北");
        regionComboBox.setBounds(160, 215, 165, 25);
        add(regionComboBox);

        // 注册按钮事件监听
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());
                String securityQuestionLabel = new String(securityQuestionText.getText());
                String region = (String) regionComboBox.getSelectedItem();
                // 验证输入
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                        || securityQuestionLabel.isEmpty() || region.equals("请选择地区")) {
                    JOptionPane.showMessageDialog(null, "所有字段均为必填项！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "两次输入的密码不一致！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 验证用户名有无被占用
                try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] userInfo = line.split(",");
                        if (userInfo[0].equals(username)) {
                            JOptionPane.showMessageDialog(null, "用户名已被占用，请选择其他用户名！", "错误", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "注册失败，无法读取用户信息文件！", "错误", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }

                // 将用户信息保存到本地文件
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                    writer.write(
                            username + "," + password + "," + securityQuestionLabel + "," + region + "," + "春小.png");
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    new LoginJFrame();
                    dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "注册失败，无法保存用户信息！", "错误", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // 返回按钮事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginJFrame();
                dispose();
            }
        });
    }
}