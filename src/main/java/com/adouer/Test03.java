package com.adouer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * PreparedStatement更新
 */
public class Test03 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //步骤1---通过反射实例化数据库驱动类
        Class.forName("com.mysql.jdbc.Driver");
        //步骤2---调用DriverManager的静态工厂方法获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
        //步骤3---准备带?的SQL语句
        String sql = "update user set age = ? where id = 1";
        //步骤3---调用连接对象的工厂方法创建语句对象
        PreparedStatement pst = conn.prepareStatement(sql);
        //步骤5---调用预处理语句对象的setXXX()方法为SQL语句中的每个?传参
        pst.setInt(1, 19);
        //步骤6---调用预处理语句对象的更新方法，返回本次更新所影响的记录行数
        int i = pst.executeUpdate();
        System.out.println("i = " + i);
        //步骤7---关闭预处理语句对象和数据库连接对象
        pst.close();
        conn.close();
    }
}