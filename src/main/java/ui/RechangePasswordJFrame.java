package main.java.ui;

import javax.swing.*;

import main.java.utils.UserUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 重置密码界面类，继承自 JFrame，用于创建重置密码窗口。
 */
public class RechangePasswordJFrame extends JFrame {
    /**
     * 构造函数，初始化重置密码界面并使其可见。
     */
    public RechangePasswordJFrame() {
        // 初始化 JFrame 的各项属性和组件
        initJFrame();
        // 设置窗口可见
        setVisible(true);
    }

    /**
     * 初始化 JFrame 的方法，包括设置窗口标题、大小、关闭操作、布局等，
     * 并添加用户名、密保问题、新密码输入框和确认、返回按钮。
     */
    private void initJFrame() {
        // 设置窗口标题为“忘记密码”
        setTitle("忘记密码");
        // 设置窗口大小为 500x300 像素
        setSize(500, 300);
        // 设置窗口关闭时的操作，即退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 将窗口居中显示
        setLocationRelativeTo(null);
        // 设置窗口的布局管理器为 null，使用绝对定位
        setLayout(null);

        // 创建用户名标签，显示“用户名:”
        JLabel userLabel = new JLabel("用户名:");
        // 设置用户名标签的位置和大小
        userLabel.setBounds(100, 50, 80, 25);
        // 将用户名标签添加到窗口中
        add(userLabel);

        // 创建用户名输入框，初始长度为 20
        JTextField userText = new JTextField(20);
        // 设置用户名输入框的位置和大小
        userText.setBounds(200, 50, 165, 25);
        // 将用户名输入框添加到窗口中
        add(userText);

        // 创建密保问题标签，显示“密保问题（学号）:”
        JLabel securityQuestionLabel = new JLabel("密保问题（学号）:");
        // 设置密保问题标签的位置和大小
        securityQuestionLabel.setBounds(100, 100, 120, 25);
        // 将密保问题标签添加到窗口中
        add(securityQuestionLabel);

        // 创建密保问题输入框，初始长度为 20
        JTextField securityQuestionText = new JTextField(20);
        // 设置密保问题输入框的位置和大小
        securityQuestionText.setBounds(220, 100, 165, 25);
        // 将密保问题输入框添加到窗口中
        add(securityQuestionText);

        // 创建新密码标签，显示“新密码:”
        JLabel newPasswordLabel = new JLabel("新密码:");
        // 设置新密码标签的位置和大小
        newPasswordLabel.setBounds(100, 150, 80, 25);
        // 将新密码标签添加到窗口中
        add(newPasswordLabel);

        // 创建新密码输入框，初始长度为 20
        JTextField newPasswordText = new JTextField(20);
        // 设置新密码输入框的位置和大小
        newPasswordText.setBounds(200, 150, 165, 25);
        // 将新密码输入框添加到窗口中
        add(newPasswordText);

        // 创建确认按钮，显示“确认”
        JButton confirmButton = new JButton("确认");
        // 设置确认按钮的位置和大小
        confirmButton.setBounds(100, 200, 80, 25);
        // 将确认按钮添加到窗口中
        add(confirmButton);

        // 创建返回按钮，显示“返回”
        JButton backButton = new JButton("返回");
        // 设置返回按钮的位置和大小
        backButton.setBounds(200, 200, 80, 25);
        // 将返回按钮添加到窗口中
        add(backButton);

        // 为确认按钮添加事件监听器
        confirmButton.addActionListener(new ActionListener() {
            /**
             * 当确认按钮被点击时执行的方法。
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的用户名
                String username = userText.getText();
                // 获取用户输入的密保问题答案
                String securityQuestion = securityQuestionText.getText();
                // 获取用户输入的新密码
                String newPassword = newPasswordText.getText();

                // 验证输入是否为空
                if (username.isEmpty() || securityQuestion.isEmpty() || newPassword.isEmpty()) {
                    // 如果为空，弹出错误提示框
                    JOptionPane.showMessageDialog(null, "请填写所有字段！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 调用 UserUtils 类的 updatePassword 方法更新密码
                if (UserUtils.updatePassword(username, securityQuestion, newPassword)) {
                    // 如果更新成功，弹出成功提示框
                    JOptionPane.showMessageDialog(null, "密码重置成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    // 创建登录界面的实例
                    new LoginJFrame(); 
                    // 关闭当前重置密码窗口
                    dispose(); 
                } else {
                    // 如果更新失败，弹出错误提示框
                    JOptionPane.showMessageDialog(null, "用户名或密保问题错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 为返回按钮添加事件监听器
        backButton.addActionListener(new ActionListener() {
            /**
             * 当返回按钮被点击时执行的方法。
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建登录界面的实例
                new LoginJFrame();
                // 关闭当前重置密码窗口
                dispose();
            }
        });
    }
}