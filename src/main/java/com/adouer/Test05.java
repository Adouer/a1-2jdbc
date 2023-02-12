package com.adouer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * PreparedStatement更新 批处理
 */
public class Test05 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //步骤1---通过反射实例化数据库驱动类
        Class.forName("com.mysql.jdbc.Driver");
        //步骤2---调用DriverManager的静态工厂方法获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
        //步骤3---准备带?的SQL语句
        String sql = "insert into user values (null,?,?,?,?)";
        //步骤4---调用连接对象的工厂方法创建语句对象
        PreparedStatement pst = conn.prepareStatement(sql);
        //步骤5---调用预处理语句对象的setXXX()方法为SQL语句中的每个?传参
        long begin = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) {
            pst.setString(1, "user" + i);
            pst.setInt(2, (int) (Math.random() * 100));
            pst.setString(3, "user" + i);
            pst.setString(4, "user" + i);
            pst.addBatch();
            if(i % 500 == 0){
                //步骤6---调用预处理语句对象的更新方法，返回本次更新所影响的记录行数
                pst.executeBatch();
                //清空
                pst.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("time=" + (end-begin) + "ms");
        //步骤7---关闭预处理语句对象和数据库连接对象
        pst.close();
        conn.close();
    }
}
