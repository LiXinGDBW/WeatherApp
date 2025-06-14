package main;

import ui.LoginJFrame;

/**
 * 程序的入口类，负责启动整个应用程序。
 */
public class App {
    /**
     * 程序的主方法，程序从这里开始执行。
     * 
     * @param args 命令行参数，在本程序中未使用。
     * @throws Exception 如果在创建登录界面时发生异常。
     */
    public static void main(String[] args) throws Exception {
        // 创建一个登录界面的实例，启动登录流程
        new LoginJFrame();
    }
}