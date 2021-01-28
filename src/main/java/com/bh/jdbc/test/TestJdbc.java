package com.bh.jdbc.test;
import com.bh.jdbc.util.JdbcUtil;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class TestJdbc {
    /**
     * 获取数据库连接
     * @return
     */
    public static Connection methods() {
        /**
         *  一、得到连接
         * 1. 准备四大连接参数
         */
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/user?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=FALSE";
        String username = "root";  //用户名写错会出现：SQLNonTransientConnectionException
        String password = "123456"; //密码写错会出现：SQLException
        Connection connection = null; //数据库连接对象

        try {
            /**
             * 2.加载驱动类
             */
            Class.forName(driverClassName);
            /**
             * 3.通过三个参数调用DriverManager的getConnection(),得到连接
             */
            //Connection对象，代表与数据源进行的唯一会话。
            // 所有的java.sql.Driver实现类，都提供了static块，块内的代码就是把自己注册到DriverManager中！
            connection=  DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动失败");
        }catch (SQLException e) {
            System.out.println("数据连接失败");
        }
        return connection;
    }
    
    @Test
    public void testfile() {
        //读取资源文件 获取值 创建Prpperties集合类
        Properties properties = new Properties();
        //获得src路径------>ClassLoader()    类加载器
        ClassLoader classLoader = JdbcUtil.class.getClassLoader();//获得类加载器
        URL res = classLoader.getResource("jdbcconfig.properties"); //得到资源文件
        String pash = res.getPath(); //获取资源文件路径
        try {
            properties.load(new FileReader(pash)); //从输入流中读取属性的键和元素
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读取文件
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String driverClassName = properties.getProperty("driverClassName");

        System.out.println(url + "---" + username + "---" +  password + "---" +  driverClassName);
    }

    @Test
    public void test2() {
        /**
         * 一：得到Connection对象
         * 二：得到Statement语句发送器，发送select语句
         * 三：对擦寻返回的"表格"进行解析
         */

        /**
         * executeUpdate(String sql )
         * 二、对数据库做增、删、改
         *      1. 通过Connection对象创建Statement
         *      Statement语句的发送器，它的功能就是向数据库发送sql语句！
         *      2. 调用它的int executeUpdate(String sql)，它可以发送DML、DDL
         */
        //调用方法
        Connection con = null;
        try {
            con = methods();

            //1.通过Connection得到Statement对象
            Statement statement= con.createStatement();

            //2.使用Statement发送Sql语句！
            //String sql = "INSERT INTO branch VALUES(4,'外交部','济南高新区')";
            String sql = "UPDATE branch SET branch_name='人力资源部', location='济南历下区' WHERE id=4";
            //String sql = "DELETE FROM bh";
            int row  = statement.executeUpdate(sql);
            System.out.println(row);


            /**
             * executeQuery(String sql)
             * 二、得到Statement，执行sql语句
             *      1. 得到Statement对象：Connection的createStatement()方法
             *      2. 调用Statement的ResultSet rs = executeQuery(String querySql)
             */
            //1.得到Statement对象：Connection的createStatement()方法
            Statement statement1= con.createStatement();
            //2。调用Statement的ResultSet rs = executeQuery(String querySql)
            ResultSet resultSet = statement1.executeQuery("select * from branch");
            /**
             * 三：解析ResultSet
             * 1. 把行光标移动到第一行，可以调用next()方法完成！
             */
            while (resultSet.next()) { //把光标向下移动一行，并判断下一行是否存在！
                int id = resultSet.getInt(1); //通过列编号来获取该列的值！
                String branch_name = resultSet.getString("branch_name"); //通过列名称来获取该列的值
                String location = resultSet.getString("location");
                System.out.println(id + "," + branch_name + "," + location);
            }

            /**
             *  四、关闭资源
             *  倒关
             */
            resultSet.close();
            statement.clearBatch();
            con.close();
        } catch (SQLException throwables) {
            System.out.println("Sql语句出现问题");
        } catch (Exception e) {
            System.out.println("调用方法失败");
        }

    }

    @Test
    public void test() {
        /**
         * 加载数据
         * 获取连接
         * 生成发送器
         * 发送sql语句
         * 关闭数据库
         */

        /**
         * executeUpdate(String sql )
         * 二、对数据库做增、删、改
         *      1. 通过Connection对象创建Statement
         *      Statement语句的发送器，它的功能就是向数据库发送sql语句！
         *      2. 调用它的int executeUpdate(String sql)，它可以发送DML、DDL
         */
        //调用方法
        Connection con = JdbcUtil.getConnection();
        try {
            //1.通过Connection得到Statement对象
            Statement statement= con.createStatement();
            //2.使用Statement发送Sql语句！
            //String sql = "INSERT INTO branch VALUES(4,'外交部','济南高新区')";
            String sql = "UPDATE branch SET branch_name='人力资源部', location='济南历下区' WHERE id=4";
            //String sql = "DELETE FROM bh";
            int row  = statement.executeUpdate(sql);
            System.out.println(row);

            /**
             *  四、关闭资源
             *  倒关
             */
            statement.clearBatch();
            con.close();
        } catch (SQLException throwables) {
            System.out.println("Sql语句出现问题");
        } catch (Exception e) {
            System.out.println("调用方法失败");
        }
    }
}
