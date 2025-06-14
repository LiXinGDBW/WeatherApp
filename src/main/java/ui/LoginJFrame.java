package ui;

import javax.swing.*;

import utils.UserUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录界面类，继承自 JFrame，用于创建用户登录窗口。
 */
public class LoginJFrame extends JFrame {
    /**
     * 构造函数，初始化登录界面并使其可见。
     */
    public LoginJFrame() {
        // 初始化 JFrame 的各项属性和组件
        initJFrame();
        // 设置窗口可见
        setVisible(true);
    }

    /**
     * 初始化 JFrame 的方法，包括设置窗口标题、大小、关闭操作、布局等，
     * 并添加用户名、密码输入框和各种按钮。
     */
    private void initJFrame() {
        // 设置窗口标题为“用户登录”
        setTitle("用户登录");
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

        // 创建密码标签，显示“密码:”
        JLabel passwordLabel = new JLabel("密码:");
        // 设置密码标签的位置和大小
        passwordLabel.setBounds(100, 100, 80, 25);
        // 将密码标签添加到窗口中
        add(passwordLabel);

        // 创建密码输入框，初始长度为 20
        JPasswordField passwordText = new JPasswordField(20);
        // 设置密码输入框的位置和大小
        passwordText.setBounds(200, 100, 165, 25);
        // 将密码输入框添加到窗口中
        add(passwordText);

        // 创建登录按钮，显示“登录”
        JButton loginButton = new JButton("登录");
        // 设置登录按钮的位置和大小
        loginButton.setBounds(36, 150, 80, 25);
        // 将登录按钮添加到窗口中
        add(loginButton);

        // 创建注册按钮，显示“注册”
        JButton registerButton = new JButton("注册");
        // 设置注册按钮的位置和大小
        registerButton.setBounds(152, 150, 80, 25);
        // 将注册按钮添加到窗口中
        add(registerButton);

        // 创建忘记密码按钮，显示“忘记密码”
        JButton forgetPasswordButton = new JButton("忘记密码");
        // 设置忘记密码按钮的位置和大小
        forgetPasswordButton.setBounds(268, 150, 84, 25);
        // 将忘记密码按钮添加到窗口中
        add(forgetPasswordButton);

        // 创建退出按钮，显示“退出”
        JButton exitButton = new JButton("退出");
        // 设置退出按钮的位置和大小
        exitButton.setBounds(384, 150, 80, 25);
        // 将退出按钮添加到窗口中
        add(exitButton);

        // 为登录按钮添加事件监听器
        loginButton.addActionListener(new ActionListener() {
            /**
             * 当登录按钮被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的用户名
                String username = userText.getText();
                // 获取用户输入的密码
                String password = new String(passwordText.getPassword());

                // 验证用户名和密码是否为空
                if (username.isEmpty() || password.isEmpty()) {
                    // 如果为空，弹出错误提示框
                    JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 调用 UserUtils 类的 validateUser 方法验证用户名和密码
                if (UserUtils.validateUser(username, password)) {
                    // 如果验证成功，弹出成功提示框
                    JOptionPane.showMessageDialog(null, "登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    // 调用 UserUtils 类的 getUserInfo 方法获取用户信息
                    String[] userInfo = UserUtils.getUserInfo(username);
                    // 创建主应用界面的实例
                    new AppJFrame(userInfo);
                    // 关闭当前登录窗口
                    dispose();
                } else {
                    // 如果验证失败，弹出错误提示框
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 为注册按钮添加事件监听器
        registerButton.addActionListener(new ActionListener() {
            /**
             * 当注册按钮被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建注册界面的实例
                new RegisterJFrame();
                // 关闭当前登录窗口
                dispose();
            }
        });

        // 为忘记密码按钮添加事件监听器
        forgetPasswordButton.addActionListener(new ActionListener() {
            /**
             * 当忘记密码按钮被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建重置密码界面的实例
                new RechangePasswordJFrame();
                // 关闭当前登录窗口
                dispose();
            }
        });

        // 为退出按钮添加事件监听器
        exitButton.addActionListener(new ActionListener() {
            /**
             * 当退出按钮被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出确认对话框，询问用户是否确定退出
                int result = JOptionPane.showConfirmDialog(null, "确定要退出吗？", "退出确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // 如果用户选择是，则退出程序
                    System.exit(0);
                }
            }
        });
    }
}