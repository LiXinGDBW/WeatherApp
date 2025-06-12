package Ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginJFrame extends JFrame {
    public LoginJFrame() {
        initJFrame();
        setVisible(true);
    }

    // 初始化 JFrame
    private void initJFrame() {
        setTitle("用户登录");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 创建用户名标签
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(100, 50, 80, 25);
        add(userLabel);

        // 创建用户名输入框
        JTextField userText = new JTextField(20);
        userText.setBounds(200, 50, 165, 25);
        add(userText);

        // 创建密码标签
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(100, 100, 80, 25);
        add(passwordLabel);

        // 创建密码输入框
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(200, 100, 165, 25);
        add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(36, 150, 80, 25);
        add(loginButton);

        // 创建注册按钮
        JButton registerButton = new JButton("注册");
        registerButton.setBounds(152, 150, 80, 25);
        add(registerButton);

        // 创建忘记密码按钮
        JButton forgetPasswordButton = new JButton("忘记密码");
        forgetPasswordButton.setBounds(268, 150, 84, 25);
        add(forgetPasswordButton);

        // 创建退出按钮
        JButton exitButton = new JButton("退出");
        exitButton.setBounds(384, 150, 80, 25);
        add(exitButton);

        // 登录按钮事件监听
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                // 验证用户名和密码
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (validateUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    String[] userInfo = getUserInfo(username);
                    new AppJFrame(userInfo);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 注册按钮事件监听
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterJFrame();
                dispose();
            }
        });

        // 忘记密码按钮事件监听
        forgetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RechangePasswordJFrame();
                dispose();
            }
        });

        // 退出按钮监听
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "确定要退出吗？", "退出确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // 验证用户名和密码
    private boolean validateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String fileUsername = parts[0];
                String filePassword = parts[1];
                if (fileUsername.equals(username) && filePassword.equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "无法读取用户数据文件！", "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }

    // 获取用户所有信息
    public String[] getUserInfo(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "无法读取用户数据文件！", "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }
}