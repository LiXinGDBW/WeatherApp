package ui;

import javax.swing.*;

import utils.BackgroundUtils;
import utils.WeatherUtils;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 主应用界面类，继承自 JFrame，用于创建天气查询系统的主窗口。
 */
public class AppJFrame extends JFrame {

    // 用户信息
    private String username;
    private String region;
    private String background;

    /**
     * 构造函数，初始化主应用界面并使其可见。
     * 
     * @param userInfo 包含用户信息的字符串数组，如用户名、地区、背景图片等。
     */
    public AppJFrame(String[] userInfo) {

        // 获取用户信息
        this.username = userInfo[0];
        this.region = userInfo[1];
        this.background = userInfo[2];

        // 初始化界面
        initJFrame();
        // 初始化菜单栏
        initJMenuBar();
        // 设置窗口可见
        setVisible(true);
    }

    // 背景面板
    private BackgroundPanel backgroundPanel;

    /**
     * 初始化 JFrame 的方法，包括设置窗口标题、大小、关闭操作、布局等，
     * 并添加标题、天气信息、城市选择区域和底部状态栏。
     */
    private void initJFrame() {
        // 设置窗口标题为“查询天气 V2.0”
        setTitle("查询天气 V2.0");
        // 设置窗口大小为 600x400 像素
        setSize(600, 400);
        // 设置窗口关闭时的操作，即退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 将窗口居中显示
        setLocationRelativeTo(null);
        // 设置窗口的布局管理器为 null，使用绝对定位
        setLayout(null);

        // 初始化背景面板
        backgroundPanel = new BackgroundPanel();
        // 设置窗口的内容面板为背景面板
        setContentPane(backgroundPanel);
        // 设置背景面板的背景图片
        backgroundPanel.setBackgroundImage(background);

        // 设置布局为 BorderLayout
        setLayout(new BorderLayout());

        // 中间内容区域
        JPanel contentPanel = new JPanel();

        // 使用 GridLayout 将内容分为三部分
        contentPanel.setLayout(new GridLayout(3, 1));
        // 设置内容面板为透明
        contentPanel.setOpaque(false);

        // 默认选中用户地区的天气信息
        String selectedRegion = region;

        // 创建标题区域
        JPanel titlePanel = new JPanel();
        // 设置标题区域的布局为 FlowLayout
        titlePanel.setLayout(new FlowLayout());
        // 设置标题区域为透明
        titlePanel.setOpaque(false);
        // 创建标题标签，显示“欢迎使用天气查询系统”
        JLabel titleLabel = new JLabel("欢迎使用天气查询系统");
        // 设置标题标签的字体为宋体，普通样式，大小为 24
        titleLabel.setFont(new Font("宋体", Font.PLAIN, 24));
        // 将标题标签添加到标题区域
        titlePanel.add(titleLabel);

        // 创建天气信息区域
        JPanel weatherPanel = new JPanel();
        // 设置天气信息区域的布局为 FlowLayout
        weatherPanel.setLayout(new FlowLayout());
        // 设置天气信息区域为透明
        weatherPanel.setOpaque(false);
        // 创建天气信息标签，显示“天气信息”
        JLabel weatherLabel = new JLabel("天气信息");
        // 设置天气信息标签的字体为宋体，普通样式，大小为 18
        weatherLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        // 将天气信息标签添加到天气信息区域
        weatherPanel.add(weatherLabel);

        // 自动获取用户地区的天气信息
        String cityCode = WeatherUtils.getCityCode(selectedRegion);
        String weatherInfo = WeatherUtils.fetchWeatherInfo(cityCode);
        if (weatherInfo != null) {
            // 如果获取到天气信息，更新天气信息标签的文本
            weatherLabel.setText(selectedRegion + "天气：" + '\n' + weatherInfo);
        } else {
            // 如果未获取到天气信息，更新天气信息标签的文本
            weatherLabel.setText("当前地区天气获取失败！");
        }

        // 城市选择区域
        JPanel cityPanel = new JPanel();
        // 设置城市选择区域的布局为 FlowLayout
        cityPanel.setLayout(new FlowLayout());
        // 设置城市选择区域为透明
        cityPanel.setOpaque(false);
        // 创建城市选择标签，显示“选择城市：”
        JLabel cityLabel = new JLabel("选择城市：");
        // 调用 WeatherUtils 类的 loadCityNames 方法加载城市名称
        String[] cityNames = WeatherUtils.loadCityNames();
        // 创建城市选择下拉框
        JComboBox<String> cityComboBox = new JComboBox<>(cityNames);
        // 设置下拉框的默认选中项为用户所在地区
        cityComboBox.setSelectedItem(region);
        // 将城市选择标签添加到城市选择区域
        cityPanel.add(cityLabel);
        // 将城市选择下拉框添加到城市选择区域
        cityPanel.add(cityComboBox);

        // 为城市选择下拉框添加事件监听器
        cityComboBox.addActionListener(new ActionListener() {
            /**
             * 当城市选择下拉框的选项发生变化时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户选择的地区
                String selectedRegion = (String) cityComboBox.getSelectedItem();
                // 调用 WeatherUtils 类的 getCityCode 方法获取城市编码
                String cityCode = WeatherUtils.getCityCode(selectedRegion);
                // 调用 WeatherUtils 类的 fetchWeatherInfo 方法获取天气信息
                String weatherInfo = WeatherUtils.fetchWeatherInfo(cityCode);
                if (weatherInfo != null) {
                    // 如果获取到天气信息，更新天气信息标签的文本
                    weatherLabel.setText(selectedRegion + "天气：" + '\n' + weatherInfo);
                } else {
                    // 如果未获取到天气信息，更新天气信息标签的文本
                    weatherLabel.setText("天气信息获取失败！");
                }
            }
        });

        // 将标题、天气信息和城市选择区域添加到中间内容区域
        contentPanel.add(titlePanel);
        contentPanel.add(weatherPanel);
        contentPanel.add(cityPanel);

        // 将中间内容区域添加到主窗口的中央
        add(contentPanel, BorderLayout.CENTER);

        // 底部状态栏
        JPanel statusBar = new JPanel();
        // 设置底部状态栏的布局为 BorderLayout
        statusBar.setLayout(new BorderLayout());
        // 设置底部状态栏为透明
        statusBar.setOpaque(false);

        // 时间栏
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // 设置时间栏为透明
        timePanel.setOpaque(false);
        // 创建时间标签
        JLabel timeLabel = new JLabel();
        // 设置时间标签的字体为宋体，普通样式，大小为 14
        timeLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        // 将时间标签添加到时间栏
        timePanel.add(timeLabel);

        // 用户栏
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // 设置用户栏为透明
        userPanel.setOpaque(false);
        // 创建用户标签，显示当前用户的用户名
        JLabel userLabel = new JLabel("用户：" + username);
        // 设置用户标签的字体为宋体，普通样式，大小为 14
        userLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        // 将用户标签添加到用户栏
        userPanel.add(userLabel);

        // 将时间栏和用户栏分别添加到底部状态栏的左右两侧
        statusBar.add(timePanel, BorderLayout.WEST);
        statusBar.add(userPanel, BorderLayout.EAST);

        // 定时器更新时间
        Timer timer = new Timer(1000, e -> {

            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();

            // 格式化时间
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 更新时间标签的文本
            timeLabel.setText("当前时间：" + now.format(formatter));
        });

        // 启动定时器
        timer.start();

        // 将底部状态栏添加到主窗口的底部
        add(statusBar, BorderLayout.SOUTH);
    }

    /**
     * 初始化菜单栏的方法，包括创建菜单、设置、返回登录、退出、关于等菜单项，并添加事件监听器。
     */
    private void initJMenuBar() {
        // 创建菜单栏
        JMenuBar jMenuBar = new JMenuBar();

        // 菜单栏
        JMenu menuJMenu = new JMenu("菜单");

        // 设置
        JMenuItem settingItem = new JMenuItem("设置");

        // 为设置菜单项添加事件监听器
        settingItem.addActionListener(new ActionListener() {
            /**
             * 当设置菜单项被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // 创建一个新的 JDialog 作为设置窗口
                JDialog settingsDialog = new JDialog(AppJFrame.this, "设置", true);
                // 设置设置窗口的大小为 400x400 像素
                settingsDialog.setSize(400, 400);
                // 将设置窗口居中显示
                settingsDialog.setLocationRelativeTo(AppJFrame.this);

                // 主界面
                JPanel panel = new JPanel();
                // 设置主界面的布局为 null，使用绝对定位
                panel.setLayout(null);

                // 窗口大小板块
                JLabel label = new JLabel("选择窗口大小：");
                // 设置窗口大小标签的位置和大小
                label.setBounds(20, 20, 150, 30);
                // 将窗口大小标签添加到主界面
                panel.add(label);

                // 创建单选按钮
                JRadioButton size1 = new JRadioButton("小窗口");
                // 设置小窗口单选按钮的位置和大小
                size1.setBounds(20, 60, 100, 30);
                JRadioButton size2 = new JRadioButton("中窗口");
                // 设置中窗口单选按钮的位置和大小
                size2.setBounds(20, 100, 100, 30);
                JRadioButton size3 = new JRadioButton("大窗口");
                // 设置大窗口单选按钮的位置和大小
                size3.setBounds(20, 140, 100, 30);

                // 将单选按钮添加到 sizeGroup
                ButtonGroup sizeGroup = new ButtonGroup();
                sizeGroup.add(size1);
                sizeGroup.add(size2);
                sizeGroup.add(size3);

                // 根据当前窗口大小设置默认选中项
                int currentWidth = AppJFrame.this.getWidth();
                int currentHeight = AppJFrame.this.getHeight();
                if (currentWidth == 600 && currentHeight == 400) {
                    size1.setSelected(true);
                } else if (currentWidth == 800 && currentHeight == 600) {
                    size2.setSelected(true);
                } else if (currentWidth == 1024 && currentHeight == 768) {
                    size3.setSelected(true);
                }

                // 将单选按钮添加到主界面
                panel.add(size1);
                panel.add(size2);
                panel.add(size3);

                // 背景图片选择
                JLabel backgroundLabel = new JLabel("选择背景图片：");
                // 设置背景图片选择标签的位置和大小
                backgroundLabel.setBounds(20, 200, 150, 30);
                // 将背景图片选择标签添加到主界面
                panel.add(backgroundLabel);

                JComboBox<String> backgroundComboBox = new JComboBox<>();
                // 设置背景图片选择下拉框的位置和大小
                backgroundComboBox.setBounds(20, 240, 200, 30);
                // 将背景图片选择下拉框添加到主界面
                panel.add(backgroundComboBox);
                // 添加背景图片选项
                backgroundComboBox.addItem("春天");
                backgroundComboBox.addItem("夏天");
                backgroundComboBox.addItem("秋天");
                backgroundComboBox.addItem("冬天");

                // 保存按钮
                JButton saveButton = new JButton("保存");
                // 设置保存按钮的位置和大小
                saveButton.setBounds(150, 300, 100, 30);
                // 为保存按钮添加事件监听器
                saveButton.addActionListener(event -> {
                    if (size1.isSelected()) {
                        // 如果选择小窗口，设置主窗口的大小为 600x400 像素
                        AppJFrame.this.setSize(600, 400);
                        if ("春天".equals(backgroundComboBox.getSelectedItem())) {
                            // 如果选择春天背景图片，设置背景面板的背景图片为“春小.png”
                            backgroundPanel.setBackgroundImage("春小.png");
                            // 调用 BackgroundUtils 类的 updateBackground 方法更新数据库中的背景图片信息
                            BackgroundUtils.updateBackground(username, "春小.png");
                        } else if ("夏天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("夏小.png");
                            BackgroundUtils.updateBackground(username, "夏小.png");
                        } else if ("秋天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("秋小.png");
                            BackgroundUtils.updateBackground(username, "秋小.png");
                        } else if ("冬天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("冬小.png");
                            BackgroundUtils.updateBackground(username, "冬小.png");
                        }
                    } else if (size2.isSelected()) {
                        // 如果选择中窗口，设置主窗口的大小为 800x600 像素
                        AppJFrame.this.setSize(800, 600);
                        if ("春天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("春中.png");
                            BackgroundUtils.updateBackground(username, "春中.png");
                        } else if ("夏天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("夏中.png");
                            BackgroundUtils.updateBackground(username, "夏中.png");
                        } else if ("秋天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("秋中.png");
                            BackgroundUtils.updateBackground(username, "秋中.png");
                        } else if ("冬天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("冬中.png");
                            BackgroundUtils.updateBackground(username, "冬中.png");
                        }
                    } else if (size3.isSelected()) {
                        // 如果选择大窗口，设置主窗口的大小为 1024x768 像素
                        AppJFrame.this.setSize(1024, 768);
                        if ("春天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("春大.png");
                            BackgroundUtils.updateBackground(username, "春大.png");
                        } else if ("夏天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("夏大.png");
                            BackgroundUtils.updateBackground(username, "夏大.png");
                        } else if ("秋天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("秋大.png");
                            BackgroundUtils.updateBackground(username, "秋大.png");
                        } else if ("冬天".equals(backgroundComboBox.getSelectedItem())) {
                            backgroundPanel.setBackgroundImage("冬大.png");
                            BackgroundUtils.updateBackground(username, "冬大.png");
                        }
                    }

                    // 刷新图片
                    revalidate();
                    repaint();

                    // 关闭设置窗口
                    settingsDialog.dispose();
                });
                // 将保存按钮添加到主界面
                panel.add(saveButton);
                // 将主界面添加到设置窗口
                settingsDialog.add(panel);
                // 设置设置窗口可见
                settingsDialog.setVisible(true);
            }
        });
        // 将设置菜单项添加到菜单中
        menuJMenu.add(settingItem);

        // 返回登录
        JMenuItem loginItem = new JMenuItem("返回登录");
        // 为返回登录菜单项添加事件监听器
        loginItem.addActionListener(new ActionListener() {
            /**
             * 当返回登录菜单项被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出确认对话框，询问用户是否确定返回登录界面
                int result = JOptionPane.showConfirmDialog(null, "确定要返回登录界面吗？", "返回确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // 如果用户选择是，创建登录界面的实例
                    new LoginJFrame();
                    // 关闭当前主窗口
                    dispose();
                }
            }
        });
        // 将返回登录菜单项添加到菜单中
        menuJMenu.add(loginItem);

        // 退出
        JMenuItem exitItem = new JMenuItem("退出");
        // 为退出菜单项添加事件监听器
        exitItem.addActionListener(new ActionListener() {
            /**
             * 当退出菜单项被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出确认对话框，询问用户是否确定退出
                int result = JOptionPane.showConfirmDialog(null, "确定要退出吗？", "退出确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    // 如果用户选择是，退出程序
                    System.exit(0);
                }
            }
        });
        // 将退出菜单项添加到菜单中
        menuJMenu.add(exitItem);

        // 关于栏
        JMenu aboutJMenu = new JMenu("关于");
        JMenuItem version = new JMenuItem("版本");

        // 为关于版本菜单项添加事件监听器
        version.addActionListener(new ActionListener() {
            /**
             * 当关于版本菜单项被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出版本信息提示框
                JOptionPane.showMessageDialog(null, "天气查询系统\n版本 2.0", "版本", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem author = new JMenuItem("作者");

        // 为关于作者菜单项添加事件监听器
        author.addActionListener(new ActionListener() {
            /**
             * 当关于作者菜单项被点击时执行的方法。
             * 
             * @param e 事件对象，包含事件的相关信息。
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出作者信息提示框
                JOptionPane.showMessageDialog(null, "作者\n计科24-2刘乐飞\n计科24-2魏祥涵\n计科24-2张博文", "作者",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // 将版本和作者菜单项添加到关于栏中
        aboutJMenu.add(version);
        aboutJMenu.add(author);

        // 将菜单和关于栏添加到菜单栏中
        jMenuBar.add(menuJMenu);
        jMenuBar.add(aboutJMenu);
        // 设置窗口的菜单栏
        setJMenuBar(jMenuBar);
    }

    // 自定义背景面板
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        /**
         * 设置背景图片的方法。
         * 
         * @param imagePath 背景图片的路径。
         */
        public void setBackgroundImage(String imagePath) {

            // 加载背景图片
            backgroundImage = new ImageIcon(getClass().getResource("/images/" + imagePath))
                    .getImage();
            // 重绘面板，更新背景图片
            repaint();
        }

        /**
         * 重写 paintComponent 方法，用于绘制背景图片。
         * 
         * @param g 图形上下文对象，用于绘制图形和文本。
         */
        @Override
        protected void paintComponent(Graphics g) {
            // 调用父类的 paintComponent 方法，确保面板的默认绘制行为
            super.paintComponent(g);

            // 绘制背景图片
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}