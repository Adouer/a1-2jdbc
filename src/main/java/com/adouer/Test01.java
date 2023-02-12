package com.adouer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Statement更新
 */
public class Test01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //步骤1---通过反射实例化数据库驱动类
        Class.forName("com.mysql.jdbc.Driver");
        //步骤2---调用DriverManager的静态工厂方法获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
        //步骤3---调用连接对象的工厂方法创建语句对象
        Statement statement = conn.createStatement();
        //步骤4---准备完整的SQL语句
        String sql = "update user set age = 1 where id = 1";
        //步骤5---调用语句对象的更新方法，传入SQL语句，返回本次更新所影响的记录行数
        int i = statement.executeUpdate(sql);
        System.out.println("i = " + i);
        //步骤6---关闭语句对象和数据库连接对象（后打开的先关）
        statement.close();
        conn.close();
    }
}
