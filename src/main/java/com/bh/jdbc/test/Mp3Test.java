package com.bh.jdbc.test;

import com.bh.jdbc.util.JdbcUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.sql.*;

public class Mp3Test {
    //将MP3保存到数据库
    @Test
    public void save() {
        //创建对象
        Connection connection = null;
        try {
            //获取连接
            connection = JdbcUtil.getConnection();
            String sql = "insert into tab_bin(filename,data) values(?, ?)";
            //创建psmt
            //PrepareStatement 测试插入数据库
            //PreparedStatement对象可以防止sql注入，而Statement不能防止sql注入
            PreparedStatement pstmt = connection.prepareStatement(sql);
            //设置参数
            pstmt.setString(1, "错位星空.mp3");
            //输入
            InputStream in = new FileInputStream("d:\\错位星空.mp3");
            pstmt.setBinaryStream(2, in);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println("sql语句出错");
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件");
        } catch (IOException e) {
            System.out.println("读取失败");
        }
    }

    /**
     * 拿到MP3
     */
    @Test
    public void takeMp3() {
        //创建对象
        Connection connection = null;
        try {
            //获取连接
            connection = JdbcUtil.getConnection();
            String sql =  "select filename,data from tab_bin where id=?";
            //PrepareStatement 测试插入数据库
            //PreparedStatement对象可以防止sql注入，而Statement不能防止sql注入
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, 1);
            //调用Statement的ResultSet rs = executeQuery(String querySql)
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            String filename = rs.getString("filename");
            //输出
            OutputStream out = new FileOutputStream("d:\\" + filename);
            //输入
            InputStream in = rs.getBinaryStream("data");
            //copy复制
            IOUtils.copy(in, out);
            //释放资源
            out.close();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
