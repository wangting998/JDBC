package com.bh.jdbc.test4;

import com.bh.jdbc.pojo.Branch;
import com.bh.jdbc.util.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * ResultSetHandler
 *      在执行 select 语句之后得到的是 ResultSet，然后还需要对 ResultSet 进行转换，得到最终的数据。
 *      把 ResultSet 的数据放到一个 List 中，也可能想把数据放到一个 Map 中，或是一个 Bean 中。
 * DBUtils 提供了一个接口 ResultSetHandler，它就是用来 ResultSet 转换成目标类型的工具。可以
 *          自己去实现这个接口，把 ResultSet 转换成想要的类型。
 * DBUtils 提供了很多个 ResultSetHandler 接口的实现，这些实现已经基本够用了，通常不用去实现 ResultSet 接口。
 *
 * MapHandler：单行处理器！把结果集转换成 Map<String,Object>，其中列名为键！
 * MapListHandler：多行处理器！把结果集转换成 List<Map<String,Object>>；
 * BeanHandler：单行处理器！把结果集转换成 Bean，该处理器需要 Class 参数，即 Bean 的类 型；
 * BeanListHandler：多行处理器！把结果集转换成 List<Bean>；
 * ColumnListHandler：多行单列处理器！把结果集转换成 List<Object>，使用 ColumnListHandler
 *                  时需要指定某一列的名称或编号，例如：new ColumListHandler(“name”)表示把 name 列的数据放到 List 中。
 * ScalarHandler：单行单列处理器！把结果集转换成 Object。一般用于聚集查询，例如 select count(*) from tab_student。
 */

/**
 * QueryRunner 的查询方法是：
 *      public <T> T query(String sql, ResultSetHandler<T> rh, Object… params)
 *      public <T> T query(Connection con, String sql, ResultSetHandler<T> rh, Object… params)
 * query()方法会通过 sql 语句和 params 查询出 ResultSet，然后通过 rh 把 ResultSet 转换成对应的
 * 类型再返回。
 */
public class DBUtilsTest {
    /**
     * 查询单行
     * MapHandler：单行处理器！
     *          把结果集转换成 Map<String,Object>，其中列名为键！
     * @throws SQLException
     */
    @Test
    public void fun1() throws SQLException {
        //连接连接池
        DataSource dataSource = JdbcUtils.getDataSource();
        //创建对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from branch where id=?";
        //MapHandler：单行处理器！把结果集转换成 Map<String,Object>，其中列名为键！
        Map<String,Object> map = queryRunner.query(sql, new MapHandler(), 1);
        System.out.println(map);
    }

    /**
     * 查询多行
     * MapListHandler：多行处理器！把结果集转换成 List<Map<String,Object>>；
     * @throws SQLException
     */
    @Test
    public void fun2() throws SQLException {
        //连接连接池
        DataSource dataSource = JdbcUtils.getDataSource();
        //创建对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from branch";
        //MapListHandler：多行处理器！把结果集转换成 List<Map<String,Object>>；
        List<Map<String,Object>> list = queryRunner.query(sql, new MapListHandler());
        for (Map<String,Object> map : list) {
            System.out.println(map);
        }
    }

    /**
     * BeanHandler：单行处理器！
     *      把结果集转换成 Bean，该处理器需要 Class 参数，即 Bean 的类 型；
     * @throws SQLException
     */
    @Test
    public void fun3() throws SQLException {
        //连接连接池
        DataSource dataSource = JdbcUtils.getDataSource();
        //创建对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from branch where id = ?";
        Branch branch= queryRunner.query(sql, new BeanHandler<Branch>(Branch.class),1);
        System.out.println(branch);
    }

    /**
     * BeanListHandler：多行处理器！把结果集转换成 List<Bean>；
     * @throws SQLException
     */
    @Test
    public void fun4() throws SQLException {
        //连接连接池
        DataSource dataSource = JdbcUtils.getDataSource();
        //创建对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from branch";
        //BeanListHandler：多行处理器！把结果集转换成 List<Bean>；
        List<Branch> list = queryRunner.query(sql, new BeanListHandler<Branch>(Branch.class));
        for (Branch branch : list) {
            System.out.println(branch);
        }
    }

    /**
     * ColumnListHandler：多行单列处理器！把结果集转换成 List<Object>,使用 ColumnListHandler
     * 时需要指定某一列的名称或编号，例如：new ColumListHandler(“name”)表示把 name 列的数据放到 List 中。
     * @throws SQLException
     */
    @Test
    public void fun5() throws SQLException {
        //连接连接池
        DataSource dataSource = JdbcUtils.getDataSource();
        //创建对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from branch";
        //ColumnListHandler：多行单列处理器！把结果集转换成 List<Object>,使用 ColumnListHandler
        //时需要指定某一列的名称或编号，例如：new ColumListHandler(“name”)表示把 name 列的数据放到 List 中。
        List<Object> list = queryRunner.query(sql, new ColumnListHandler<>("branch_name"));
        for(Object s : list) {
            System.out.println(s);
        }
    }

    /**
     * ScalarHandler：单行单列处理器！把结果集转换成 Object。一般用于聚集查询，例如 select count(*) from tab_student。
     * @throws SQLException
     */
    @Test
    public void fun6() throws SQLException {
        //连接连接池
        DataSource dataSource = JdbcUtils.getDataSource();
        //创建对象
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select count(*) from branch";
        Number number=(Number)queryRunner.query(sql, new ScalarHandler<>());
        int cnt = number.intValue();
        System.out.println(cnt);
    }

    @Test
    public void fun10() throws SQLException {
        DataSource ds = JdbcUtils.getDataSource();
        QueryRunner qr = new QueryRunner(ds);
        String sql = "insert into branch values(?,?,?)";
        Object[][] params = new Object[10][];//表示 要插入10行记录
        for(int i = 0; i < params.length; i++) {
            params[i] = new Object[]{7 + i, "人力资源" + i, "济南" + i};
            }
            qr.batch(sql, params);
        }
}
