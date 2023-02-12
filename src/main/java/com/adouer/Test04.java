package com.adouer;

import com.adouer.entity.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * PreparedStatement查询
 */
public class Test04 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ArrayList<User> users = new ArrayList<>();
        //步骤1---通过反射实例化数据库驱动类
        Class.forName("com.mysql.jdbc.Driver");
        //步骤2---调用DriverManager的静态工厂方法获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
        //步骤3---准备带?的SQL语句
        String sql = "select * from user where user_name = ? AND password = ?";
        //步骤3---调用连接对象的工厂方法创建语句对象
        PreparedStatement pst = conn.prepareStatement(sql);
        //步骤5---调用预处理语句对象的setXXX()方法为SQL语句中的每个?传参
        pst.setString(1,"zs");
        //pst.setString(2,"1 or 1=1"); 不会发生sql注入
        pst.setString(2,"zs111");
        //步骤6---调用预处理语句对象的更新方法，返回本次更新所影响的记录行数
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String userName = rs.getString("user_name");
            String password = rs.getString("password");
            users.add(new User(id, name, age, userName, password));
        }
        for (User user : users) {
            System.out.println("user = " + user);
        }
        //步骤7---关闭预处理语句对象和数据库连接对象
        rs.close();
        pst.close();
        conn.close();
    }
}