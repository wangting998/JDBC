package com.bh.jdbc.test1;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {
    public static Connection getConnection() throws Exception {
        //加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //准备三大连接参数
        String url = "jdbc:mysql://localhost:3306/user?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=FALSE";
        return DriverManager.getConnection(url, "root", "123456");
    }

    /**
     * 插入
     * @throws Exception
     */
    @Test
    public void insert() throws Exception {
        //创建对象 得到连接
        Connection con = getConnection();
        //通过Connection得到Statement对象
        //Statement语句的发送器，它的功能就是向数据库发送sql语句！
        Statement stmt = con.createStatement();
        String sql = "insert into users values('zhangSan', '123')";
        stmt.executeUpdate(sql);
        System.out.println("插入成功！");
    }

    /**
     * 修改
     * @throws Exception
     */
    @Test
    public void update() throws Exception {
        //创建对象 得到连接
        Connection con = getConnection();
        //通过Connection得到Statement对象
        Statement stmt = con.createStatement();
        String sql = "update users set password='456'  where username='zhangSan'";
        stmt.executeUpdate(sql);
        System.out.println("修改成功！");
    }

    /**
     * 删除
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        //创建对象 得到连接
        Connection con = getConnection();
        //通过Connection得到Statement对象
        Statement stmt = con.createStatement();
        String sql = "delete from users where username='zhangSan'";
        stmt.executeUpdate(sql);
        System.out.println("删除成功！");
    }

    /**
     * 查询
     * @throws Exception
     */
    @Test
    public void query() throws Exception {
        //创建Connection对象，得到链接
        Connection con = getConnection();
        //1.得到Statement对象：Connection的createStatement()方法
        Statement stmt = con.createStatement();
        String sql = "select * from users";
        //2.调用Statement的ResultSet rs = executeQuery(String querySql)
        ResultSet rs = stmt.executeQuery(sql);
        /**
         * 解析ResultSet
         * 1. 把行光标移动到第一行，可以调用next()方法完成！
         */
        while(rs.next()) {
            String username = rs.getString(1);
            String password = rs.getString(2);
            System.out.println(username + ", " + password);
        }
    }
}
