package com.bh.jdbc.util;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *jdbc 工具类
 */
public class JdbcUtil {
    private static String url;   //数据库
    private static String username;  //用户名
    private static String password;  //密码
    private static String driverClassName; //数据库类型

    //文件的读取，只读取一次拿到所有值 使用静态代码块
    static {
        try {
            //读取资源文件 获取值 创建Prpperties集合类
            Properties properties = new Properties();
            //获得src路径------>ClassLoader()    类加载器
            ClassLoader classLoader = JdbcUtil.class.getClassLoader();//获得类加载器
            URL res = classLoader.getResource("/jdbcconfig.properties"); //得到资源文件
            String pash = res.getPath(); //获取资源文件路径
            properties.load(new FileReader(pash)); //从输入流中读取属性的键和元素

            //读取文件
            url=properties.getProperty("url");
            username=properties.getProperty("username");
            password=properties.getProperty("password");
            driverClassName=properties.getProperty("driverClassName");
        } catch (IOException e) {
            System.out.println("文件读取失败");
        }
    }

    //获取连接对象
    public static Connection getConnection() {
        /**加载驱动类*/
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败");
        }
        Connection connection = null; //数据库连接对象
        try {
            connection=  DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
        }
        return connection;
    }
}
