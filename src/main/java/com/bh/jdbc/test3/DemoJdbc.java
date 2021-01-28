package com.bh.jdbc.test3;

import com.bh.jdbc.util.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DemoJdbc {
        //抽取公共代码
        private final String url = "jdbc:mysql://localhost:3306/user?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=FALSE";
        private final String username = "root";
        private final String password = "123456";
        //加载驱动
        static {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    //修改数据
    @Test
    public void testUpdate() throws Exception {
        /**
         * 得到连接
         *      加载驱动
         *      username
         *      password
         *      url:
         *          jdbc:mysql://localhost:3306/user?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=FALSE
         */
        //加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //得到连接
        String url = "jdbc:mysql://localhost:3306/user?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=FALSE";
        String username = "root";  //用户名写错会出现：SQLNonTransientConnectionException
        String password = "123456"; //密码写错会出现：SQLException
        Connection connection = DriverManager.getConnection(url,username,password);
        System.out.println(connection);
        /**
         * 释放资源
         */
        connection.close();
    }

    @Test
    public void testInsert() {
            Connection connection = null;
        //得到连接
        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            System.out.println("获取连接异常");
        }

        System.out.println("连接：" + connection);

        //释放资源
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testJdbcUtils() {
        //获取连接 创建对象
        Connection connection = null;
        try {
            connection= JdbcUtils.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //完成运算
        System.out.println(connection);
        //释放资源
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
