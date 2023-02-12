package com.adouer;

import com.adouer.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Statement查询
 */
public class Test02 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ArrayList<User> users = new ArrayList<>();

        //步骤1---通过反射实例化数据库驱动类
        Class.forName("com.mysql.jdbc.Driver");
        //步骤2---调用DriverManager的静态工厂方法获取数据库连接对象
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
        //步骤3---调用连接对象的工厂方法创建语句对象
        Statement statement = conn.createStatement();
        //步骤4---准备完整的SQL语句
        Scanner scanner = new Scanner(System.in);
        System.out.print("账号：");
        String uN = scanner.nextLine();
        System.out.print("密码：");
        String pw = scanner.nextLine();
        //账号：'zs',密码：1 or 1=1 时发生sql注入
        String sql = "select * from user where user_name = " + uN + "AND `password` = " + pw;
        //步骤5---调用语句对象的查询方法，传入SQL语句，返回结果集对象
        ResultSet rs = statement.executeQuery(sql);
        //步骤6---使用while循环遍历结果集，调用next()方法将指针移动到下一条记录
        while (rs.next()) {
            //步骤7---使用结果集对象的getXXX()方法获取当前记录中不同数据类型的字段值
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
        //步骤8---关闭结果集对象，语句对象，数据库连接对象
        rs.close();
        statement.close();
        conn.close();
    }
}
