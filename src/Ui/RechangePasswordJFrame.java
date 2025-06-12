package Ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RechangePasswordJFrame extends JFrame {
    public RechangePasswordJFrame() {
        initJFrame();
        setVisible(true);
    }

    private void initJFrame() {
        setTitle("忘记密码");
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

        // 创建密保问题标签
        JLabel securityQuestionLabel = new JLabel("密保问题（学号）:");
        securityQuestionLabel.setBounds(100, 100, 120, 25);
        add(securityQuestionLabel);

        // 创建密保问题输入框
        JTextField securityQuestionText = new JTextField(20);
        securityQuestionText.setBounds(220, 100, 165, 25);
        add(securityQuestionText);

        // 创建新密码标签
        JLabel newPasswordLabel = new JLabel("新密码:");
        newPasswordLabel.setBounds(100, 150, 80, 25);
        add(newPasswordLabel);

        // 创建新密码输入框
        JTextField newPasswordText = new JTextField(20);
        newPasswordText.setBounds(200, 150, 165, 25);
        add(newPasswordText);

        // 创建确认按钮
        JButton confirmButton = new JButton("确认");
        confirmButton.setBounds(100, 200, 80, 25);
        add(confirmButton);

        // 创建返回按钮
        JButton backButton = new JButton("返回");
        backButton.setBounds(200, 200, 80, 25);
        add(backButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String securityQuestion = securityQuestionText.getText();
                String newPassword = newPasswordText.getText();

                // 用户名和密保问题的验证逻辑
                if (username.isEmpty() || securityQuestion.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请填写所有字段！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (updatePassword(username, securityQuestion, newPassword)) {
                    JOptionPane.showMessageDialog(null, "密码重置成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    new LoginJFrame();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密保问题错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 添加返回按钮的事件监听器
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginJFrame();
                dispose();
            }
        });
    }

    // 验证密保并更新密码
    private boolean updatePassword(String username, String securityQuestion, String newPassword) {

        // 表示存储用户数据的文件
        File file = new File("users.txt");

        // 用于存储文件中的所有行内容（包括未修改的和修改后的行）
        List<String> lines = new ArrayList<>();

        // 标志变量，表示是否成功更新了密码
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String fileUsername = parts[0];
                String fileSecurityQuestion = parts[2];
                String region = parts[3];

                // 验证用户名和密保问题
                if (fileUsername.equals(username) && fileSecurityQuestion.equals(securityQuestion)) {

                    // 更新密码
                    line = fileUsername + "," + newPassword + "," + fileSecurityQuestion + "," + region;
                    isUpdated = true;
                }
                lines.add(line); // 将行添加到内存中
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "无法读取用户数据文件！", "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }

        // 写回文件
        if (isUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "无法写入用户数据文件！", "错误", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return false;
            }
        }
        return isUpdated;
    }
}
