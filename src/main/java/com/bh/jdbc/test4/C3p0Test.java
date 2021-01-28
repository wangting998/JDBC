package com.bh.jdbc.test4;

import com.bh.jdbc.util.JdbcUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3p0Test {
    /**
     * C3P0池类：ComboPooledDataSource。
     * @throws SQLException
     */
        /*@Test
        public void fun1() throws PropertyVetoException, SQLException {
            //池类：ComboPooledDataSource
            ComboPooledDataSource ds = new ComboPooledDataSource();
            //代码配置
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/user?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=FALSE");
            ds.setUser("root");
            ds.setPassword("123456");
            ds.setDriverClass("com.mysql.cj.jdbc.Driver");
            //设置连接每次增量为5
            ds.setAcquireIncrement(5);
            //设置初始化连接数量20
            ds.setInitialPoolSize(20);
            //设置最小连接数2
            ds.setMinPoolSize(2);
            //设置最大连接数50
            ds.setMaxPoolSize(50);
            //创建Connection对象，获得链接
            Connection con = ds.getConnection();
            System.out.println(con);
            //释放资源
            con.close();
        }*/

    /**
     * c3p0配置：c3p0-config.xml
     * @throws SQLException
     */
       /* @Test
        public void fun2() throws SQLException {
            //配置文件配置
            ComboPooledDataSource ds = new ComboPooledDataSource("c3p0-config");
            //创建Connection对象，获得链接
            Connection con = ds.getConnection();
            System.out.println(con);
            //释放资源
            con.close();
        }*/

    /**
     * 不连接连接池的
     */
        /*@Test
        public void testUpdate() {
            //创建对象
            QueryRunner queryRunner = new QueryRunner();
            Connection connection = null;
            try {
                 //得到链接
                 connection = JdbcUtils.getConnection();
            } catch (SQLException throwables) {
                System.out.println("连接异常");
            }
            String sql = "insert into branch values(?,?,?)";
            try {
                queryRunner.update(JdbcUtils.getConnection(),sql,5,"外交部","济南");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //释放资源
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/

    /**
     * 改变JdbcUtils后,直接连接连接池
     */
    @Test
    public void testUpdate2() {
        //创建对象
        QueryRunner queryRunner = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "insert into branch values(?,?,?)";
        try {
            queryRunner.update(sql, 6, "外交部", "济南");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
