package Ui;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppJFrame extends JFrame {

    // 用户信息
    private String username;
    private String region;
    private String background;

    // 城市编号映射表
    private Map<String, String> cityCodeMap;

    // 主界面
    public AppJFrame(String[] userInfo) {

        // 获取用户信息
        this.username = userInfo[0];
        this.region = userInfo[3];
        this.background = userInfo[4];

        // 初始化城市编号映射表
        initCityCodeMap();

        // 界面
        initJFrame();
        initJMenuBar();
        setVisible(true);
    }

    // 背景面板
    private BackgroundPanel backgroundPanel;

    // 初始化 JFrame
    private void initJFrame() {
        setTitle("查询天气 V1.0");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 初始化背景面板
        backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);
        backgroundPanel.setBackgroundImage(background);

        // 设置布局
        setLayout(new BorderLayout());

        // 中间内容区域
        JPanel contentPanel = new JPanel();

        // 使用 GridLayout 将内容分为三部分
        contentPanel.setLayout(new GridLayout(3, 1));
        contentPanel.setOpaque(false);

        // 默认选中用户地区的天气信息
        String selectedRegion = region;

        // 创建标题区域
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("欢迎使用天气查询系统");
        titleLabel.setFont(new Font("宋体", Font.PLAIN, 24));
        titlePanel.add(titleLabel);

        // 创建天气信息
        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new FlowLayout());
        weatherPanel.setOpaque(false);
        JLabel weatherLabel = new JLabel("天气信息");
        weatherLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        weatherPanel.add(weatherLabel);

        // 自动获取用户地区的天气信息
        String cityCode = cityCodeMap.get(selectedRegion);
        String weatherInfo = fetchWeatherInfo(cityCode);
        if (weatherInfo != null) {
            weatherLabel.setText(selectedRegion + "天气：" + '\n' + weatherInfo);
        } else {
            weatherLabel.setText("当前地区天气获取失败！");
        }

        // 城市选择区域
        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new FlowLayout());
        cityPanel.setOpaque(false);
        JLabel cityLabel = new JLabel("选择城市：");
        JComboBox<String> cityComboBox = new JComboBox<>(cityCodeMap.keySet().toArray(new String[0]));
        cityComboBox.setSelectedItem(region);
        cityPanel.add(cityLabel);
        cityPanel.add(cityComboBox);

        // 下拉框选择事件
        cityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRegion = (String) cityComboBox.getSelectedItem();
                String cityCode = cityCodeMap.get(selectedRegion);
                String weatherInfo = fetchWeatherInfo(cityCode);
                if (weatherInfo != null) {
                    weatherLabel.setText(selectedRegion + "天气：" + '\n' + weatherInfo);
                } else {
                    weatherLabel.setText("天气信息获取失败！");
                }
            }
        });

        // 将标题、天气信息和城市选择添加到内容区域
        contentPanel.add(titlePanel);
        contentPanel.add(weatherPanel);
        contentPanel.add(cityPanel);

        // 添加内容区域到主窗口
        add(contentPanel, BorderLayout.CENTER);

        // 底部状态栏
        JPanel statusBar = new JPanel();
        statusBar.setLayout(new BorderLayout());
        statusBar.setOpaque(false);

        // 时间栏
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel.setOpaque(false);
        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        timePanel.add(timeLabel);

        // 用户栏
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("用户：" + username);
        userLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        userPanel.add(userLabel);

        // 将时间栏和用户栏分别添加到底部状态栏
        statusBar.add(timePanel, BorderLayout.WEST);
        statusBar.add(userPanel, BorderLayout.EAST);

        // 定时器更新时间
        Timer timer = new Timer(1000, e -> {

            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();

            // 格式化时间
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 更新 JLabel 的文本
            timeLabel.setText("当前时间：" + now.format(formatter));
        });

        // 启动定时器
        timer.start();

        // 添加状态栏到主窗口
        add(statusBar, BorderLayout.SOUTH);
    }

    // 初始化菜单栏
    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        // 菜单栏
        JMenu menuJMenu = new JMenu("菜单");

        // 设置
        JMenuItem settingItem = new JMenuItem("设置");

        // 设置监听
        settingItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 创建一个新的 JDialog 作为设置窗口
                JDialog settingsDialog = new JDialog(AppJFrame.this, "设置", true);
                settingsDialog.setSize(400, 400);
                settingsDialog.setLocationRelativeTo(AppJFrame.this);

                // 主界面
                JPanel panel = new JPanel();
                panel.setLayout(null);

                // 窗口大小板块
                JLabel label = new JLabel("选择窗口大小：");
                label.setBounds(20, 20, 150, 30);
                panel.add(label);

                // 创建单选按钮
                JRadioButton size1 = new JRadioButton("小窗口");
                size1.setBounds(20, 60, 100, 30);
                JRadioButton size2 = new JRadioButton("中窗口");
                size2.setBounds(20, 100, 100, 30);
                JRadioButton size3 = new JRadioButton("大窗口");
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

                panel.add(size1);
                panel.add(size2);
                panel.add(size3);

                // 背景图片选择
                JLabel backgroundLabel = new JLabel("选择背景图片：");
                backgroundLabel.setBounds(20, 200, 150, 30);
                panel.add(backgroundLabel);

                JComboBox<String> backgroundComboBox = new JComboBox<>();
                backgroundComboBox.setBounds(20, 240, 200, 30);
                panel.add(backgroundComboBox);
                backgroundComboBox.addItem("春天");
                backgroundComboBox.addItem("夏天");
                backgroundComboBox.addItem("秋天");
                backgroundComboBox.addItem("冬天");

                // 保存按钮
                JButton saveButton = new JButton("保存");
                saveButton.setBounds(150, 300, 100, 30);
                saveButton.addActionListener(event -> {
                    if (size1.isSelected()) {
                        AppJFrame.this.setSize(600, 400);
                        if ((String) backgroundComboBox.getSelectedItem() == "春天") {
                            backgroundPanel.setBackgroundImage("春小.png");
                            updateBackground(username, "春小.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "夏天") {
                            backgroundPanel.setBackgroundImage("夏小.png");
                            updateBackground(username, "夏小.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "秋天") {
                            backgroundPanel.setBackgroundImage("秋小.png");
                            updateBackground(username, "秋小.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "冬天") {
                            backgroundPanel.setBackgroundImage("冬小.png");
                            updateBackground(username, "冬小.png");
                        }
                    } else if (size2.isSelected()) {
                        AppJFrame.this.setSize(800, 600);
                        if ((String) backgroundComboBox.getSelectedItem() == "春天") {
                            backgroundPanel.setBackgroundImage("春中.png");
                            updateBackground(username, "春中.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "夏天") {
                            backgroundPanel.setBackgroundImage("夏中.png");
                            updateBackground(username, "夏中.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "秋天") {
                            backgroundPanel.setBackgroundImage("秋中.png");
                            updateBackground(username, "秋中.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "冬天") {
                            backgroundPanel.setBackgroundImage("冬中.png");
                            updateBackground(username, "冬中.png");
                        }
                    } else if (size3.isSelected()) {
                        AppJFrame.this.setSize(1024, 768);
                        if ((String) backgroundComboBox.getSelectedItem() == "春天") {
                            backgroundPanel.setBackgroundImage("春大.png");
                            updateBackground(username, "春大.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "夏天") {
                            backgroundPanel.setBackgroundImage("夏大.png");
                            updateBackground(username, "夏大.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "秋天") {
                            backgroundPanel.setBackgroundImage("秋大.png");
                            updateBackground(username, "秋大.png");
                        } else if ((String) backgroundComboBox.getSelectedItem() == "冬天") {
                            backgroundPanel.setBackgroundImage("冬大.png");
                            updateBackground(username, "冬大.png");
                        }
                    }

                    // 刷新图片
                    revalidate();
                    repaint();

                    // 关闭设置窗口
                    settingsDialog.dispose();
                });
                panel.add(saveButton);
                settingsDialog.add(panel);
                settingsDialog.setVisible(true);
            }
        });
        menuJMenu.add(settingItem);

        // 返回登录
        JMenuItem loginItem = new JMenuItem("返回登录");
        loginItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "确定要返回登录界面吗？", "返回确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    new LoginJFrame();
                    dispose();
                }
            }
        });
        menuJMenu.add(loginItem);

        // 退出
        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "确定要退出吗？", "退出确认", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        menuJMenu.add(exitItem);

        // 关于栏
        JMenu aboutJMenu = new JMenu("关于");
        JMenuItem version = new JMenuItem("版本");

        // 关于版本监听
        version.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "天气查询系统\n版本 1.0", "版本", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem author = new JMenuItem("作者");

        // 关于作者监听
        author.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "作者\n计科24-2刘乐飞\n计科24-2魏祥涵\n计科24-2张博文", "作者",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        aboutJMenu.add(version);
        aboutJMenu.add(author);

        // 添加菜单栏
        jMenuBar.add(menuJMenu);
        jMenuBar.add(aboutJMenu);
        setJMenuBar(jMenuBar);
    }

    // 获取天气信息的方法
    private String fetchWeatherInfo(String cityCode) {
        try {
            // 构造查询 URL
            String url = "https://wap.weather.com.cn/mweather15d/" + cityCode + ".shtml";
            Document doc = Jsoup.connect(url).get();

            // 使用 CSS 选择器提取目标元素
            Element temperatureDiv = doc.selectFirst("div.h15listtem.h15k");
            Element weatherElement = doc
                    .selectFirst("div.h15listbody > ul > li:nth-child(1) > div.h15tiao > div:nth-child(2) > p");
            // 提取文本内容
            if (temperatureDiv != null || weatherElement != null) {
                // 返回温度和天气信息
                return temperatureDiv.text() + weatherElement.text();
            } else {
                return "未找到目标元素";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 自定义背景面板
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public void setBackgroundImage(String imagePath) {

            // 加载背景图片
            backgroundImage = new ImageIcon(new File("png/" + imagePath).getAbsolutePath()).getImage();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 绘制背景图片
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // 修改文件默认背景图片
    private void updateBackground(String username, String background) {

        // 表示存储用户数据的文件
        File file = new File("users.txt");

        boolean isUpdated = false;
        // 用于存储文件中的所有行内容（包括未修改的和修改后的行）
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String fileUsername = parts[0];
                String filePassword = parts[1];
                String fileSecurityQuestion = parts[2];
                String region = parts[3];

                // 验证用户名和密保问题
                if (fileUsername.equals(username)) {

                    // 更新背景图片
                    line = fileUsername + "," + filePassword + "," + fileSecurityQuestion + "," + region + ","
                            + background;
                    isUpdated = true;
                }
                // 将行添加到内存中
                lines.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "无法读取用户数据文件！", "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
            }
        }
    }

    // 初始化城市编号映射表
    private void initCityCodeMap() {
        cityCodeMap = new LinkedHashMap<>();
        cityCodeMap.put("北京", "101010100");
        cityCodeMap.put("天津", "101030100");
        cityCodeMap.put("上海", "101020100");
        cityCodeMap.put("重庆", "101040100");
        cityCodeMap.put("哈尔滨", "101050101");
        cityCodeMap.put("长春", "101060101");
        cityCodeMap.put("沈阳", "101070101");
        cityCodeMap.put("呼和浩特", "101080101");
        cityCodeMap.put("乌鲁木齐", "101130101");
        cityCodeMap.put("拉萨", "101140101");
        cityCodeMap.put("西宁", "101150101");
        cityCodeMap.put("兰州", "101160101");
        cityCodeMap.put("银川", "101170101");
        cityCodeMap.put("西安", "101110101");
        cityCodeMap.put("太原", "101100101");
        cityCodeMap.put("石家庄", "101090101");
        cityCodeMap.put("济南", "101120101");
        cityCodeMap.put("郑州", "101180101");
        cityCodeMap.put("南京", "101190101");
        cityCodeMap.put("合肥", "101220101");
        cityCodeMap.put("武汉", "101200101");
        cityCodeMap.put("成都", "101270101");
        cityCodeMap.put("长沙", "101250101");
        cityCodeMap.put("昆明", "101290101");
        cityCodeMap.put("南昌", "101240101");
        cityCodeMap.put("贵阳", "101260101");
        cityCodeMap.put("南宁", "101300101");
        cityCodeMap.put("广州", "101280101");
        cityCodeMap.put("福州", "101230101");
        cityCodeMap.put("杭州", "101210101");
        cityCodeMap.put("海口", "101310101");
        cityCodeMap.put("香港", "101320101");
        cityCodeMap.put("澳门", "101330101");
        cityCodeMap.put("台北", "101340101");
    }
}
