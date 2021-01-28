package com.bh.jdbc.test4;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DbcpTest {
    @Test
    public void fun1() throws SQLException {
        //Dbcp池类
        BasicDataSource ds = new BasicDataSource();
        //设置连接池操作信息
        ds.setUsername("root"); //设置用户名
        ds.setPassword("123456"); //设置登录密码
        //设置连接数据库名
        ds.setUrl("jdbc:mysql://localhost:3306/user?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=FALSE");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver"); //设置数据库类型

        ds.setMaxActive(20); //设置同一时刻内允许向连接池获得连接的最大连接数
        ds.setMaxIdle(10); //设置在连接池内允许保存的最大空闲连接数
        ds.setInitialSize(10); //设置连接池初始化连接个数
        ds.setMinIdle(2); //设置在连接池内允许保存的最小空闲连接数
        ds.setMaxWait(10000);  //等待连接池分配连接的最长时间（单位为毫秒），超出该时间抛出异常

        //创建Connection对象，获得链接
        Connection con = ds.getConnection();
        System.out.println(con.getClass().getName());
        //释放资源
        con.close();
    }
}
