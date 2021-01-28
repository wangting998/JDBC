package com.bh.jdbc.util;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * 与数据库连接生成----工具类
 */
public class JdbcUtils {
    private static Properties properties = null;
    static {
        try {
            //读取配置文件   链式编程
            InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties");

            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("读取配置文件不成功");
        }

        /**加载驱动类*/
        try {
            Class.forName(properties.getProperty("driverClassName"));
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动异常");
        }
    }

    //获取数据库连接
    //准备三个连接参数
    //获取配置文件   user,username,passworld
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password")
        );
    }


    /**
     * 连接池
     */
    private static DataSource dataSource = new ComboPooledDataSource("c3p0-config");
    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection1() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
